package start;

import java.io.File;

import bin.ExecuteUpdate;
import bin.IpAddress;
import bin.Settings;
import bin.StoreUpdate;
import bin.XMLParser;

public class Start {

	public static void main(String[] args) {

		String checkexistslocalconfigxmlfile = ""; 
		String checkexistslocalversionxmlfile = "";
		String checkexistsremoteversionxmlfile = "";
		String localxml = ""; 
		String remotexml = "";
		String newlocalversion = "";
		
		try {
			
			XMLParser xmlp = new XMLParser();
			
			checkexistslocalconfigxmlfile = xmlp.checkIfXMLFileConfigExsists();
			
			if(checkexistslocalconfigxmlfile.equals("0")){
				System.out.println("Errore: impossibile trovare il file locale: config.xml.");
				System.exit(0);
			}else{
				xmlp.readXMLConfigFile();
				
				//CONTROLLO CHE IL DATABASE SIA RAGGIUNGIBILE
				String checkdbconnection = Settings.checkDbConnection();
				
				//SE NON E' RAGGIUNGIBILE
				if(checkdbconnection.equals("false")){
					System.out.println("");
					System.out.println("Eurolls M.E.S. - Manufacturing Execution System");
					System.out.println("");
					System.out.println("Errore: impossibile connettersi al Database.");
					System.out.println("Non è stato possibile connettersi al Database a causa di un errore. Controllare la connettività o le impostazioni di rete.");
					System.exit(0);
				//SE E' RAGGIUNGIBILE	
				}else{			    
					IpAddress ipaddr = new IpAddress();
					System.out.println("");
					System.out.println("Eurolls M.E.S. - Manufacturing Execution System");
					System.out.println("");
					System.out.println("Sistema operativo: " + System.getProperty("os.name"));
					System.out.println("Indirizzo ip: [" + ipaddr.getIpAddress() + "]");
					System.out.println("Cartella locale Mes presente in: [" + xmlp.getLocarJarFolder() + "]");
					System.out.println("Percorso server aggiornamenti: [" + Settings.getUpdateServerPath() + "]");
					
					System.out.println("Verifica prerequisiti per aggiornamento...");
					
					checkexistslocalversionxmlfile = xmlp.checkIfLocalXMLFileVersionExsists();
					checkexistsremoteversionxmlfile = xmlp.checkIfRemoteXMLFileVersionExsists();
					
					if(checkexistslocalversionxmlfile.equals("0")){
						System.out.println("Errore: impossibile trovare il file locale: version.xml.");
						System.exit(0);
					}
					
					if(checkexistsremoteversionxmlfile.equals("0")){
						System.out.println("Errore: impossibile trovare il file remoto: version.xml.");
						System.exit(0);
					}

					localxml = xmlp.readLocalXMLVersionFile();
					remotexml = XMLParser.readRemoteXMLVersionFile();
							
					System.out.println("Versione installata localmente: [" + localxml + "]");
					System.out.println("Versione presente sul server: [" + remotexml + "]");
					
					if(!localxml.equals(remotexml)){
						System.out.println("Aggiornamento in corso...");
						
						ExecuteUpdate eu = new ExecuteUpdate();
						eu.executeUpdate();
						
						newlocalversion = xmlp.readLocalXMLVersionFile();
			
				    	System.out.println("Aggiornamento terminato con successo. Nuova versione installata localemente: " + "[" +  newlocalversion + "]");
				    	
				    	StoreUpdate.executeStoreUpdate(ipaddr.getIpAddress(),Settings.getApplication(),newlocalversion);
				    	
				    	System.out.println("Avvio programma...");
				    	
				    	Runtime.getRuntime().exec("java -jar " + xmlp.getLocarJarFolder() + File.separator + Settings.getApplication()); 	
					}else{
						System.out.println("Il programma è già all'ultima versione disponibile.");
						System.out.println("Avvio programma...");
					
						Runtime.getRuntime().exec("java -jar " + xmlp.getLocarJarFolder() + File.separator + Settings.getApplication());	
					}
				}
			}

		} catch (Exception e) {
			System.out.println("Errore: contattare l'amministratore di sistema.");
			e.printStackTrace();
			System.exit(0);
		}

	}
	
}
