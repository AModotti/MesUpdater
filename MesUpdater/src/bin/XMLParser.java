package bin;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	
	public String getLocarJarFolder(){
		
		String absolutePath = "";
		
		try {
		    absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replaceAll("%20"," ");
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
		return absolutePath;
	}
		
	public String checkIfXMLFileConfigExsists(){
		
		String exists = "0";
		
		try {
		    String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replaceAll("%20"," ");
		    
			File xmlfile = new File(absolutePath + File.separator + "config.xml");
			
			if(xmlfile.exists() && !xmlfile.isDirectory()){
				exists = "1";
			}else{
				exists = "0";
			}
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
		return exists;
		
	}
	
	public String checkIfLocalXMLFileVersionExsists() {
		
		String exists = "0";
		
		try {
			String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replaceAll("%20"," ");
			
			File xmlfile = new File(absolutePath + File.separator +"version.xml");
			
			if(xmlfile.exists() && !xmlfile.isDirectory()){
				exists = "1";
			}else{
				exists = "0";
			}
		} catch (Exception e) {
	    	e.printStackTrace();
	    }
		return exists;
		
	}
	
	public String checkIfRemoteXMLFileVersionExsists(){
		
		String exists = "0";
		
		File xmlfile = new File("//" + Settings.getUpdateServerPath() + File.separator + "version.xml");
		
		if(xmlfile.exists() && !xmlfile.isDirectory()){
			exists = "1";
		}else{
			exists = "0";
		}
		
		return exists;
		
	}
	
	public void readXMLConfigFile(){
		
		try {
			String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replaceAll("%20"," ");
			
			File xmlfile = new File(absolutePath + File.separator + "config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("Settings");
					
			for (int i=0;i<nList.getLength();i++) {

				Node nNode = nList.item(i);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					String dbconnectionstring = eElement.getElementsByTagName("dbconnectionstring").item(0).getTextContent().trim();
					String driver = eElement.getElementsByTagName("driver").item(0).getTextContent().trim();
					String dbuser = eElement.getElementsByTagName("dbuser").item(0).getTextContent().trim();
					String importtojdeuser = eElement.getElementsByTagName("importtojdeuser").item(0).getTextContent().trim();
					String updateserverpath = eElement.getElementsByTagName("updateserverpath").item(0).getTextContent().trim();
					String application = eElement.getElementsByTagName("application").item(0).getTextContent().trim();
					String enablelog = eElement.getElementsByTagName("enablelog").item(0).getTextContent().trim();
					
					Settings.storageSettings(dbconnectionstring,driver,dbuser,importtojdeuser,updateserverpath,application,enablelog);
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
		
	public String readLocalXMLVersionFile(){
		
		String version = "";
		
		try {
			String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
		    absolutePath = absolutePath.replaceAll("%20"," ");
		    
			File xmlfile = new File(absolutePath + File.separator + "version.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("Mes");
					
			for (int i=0;i<nList.getLength();i++) {

				Node nNode = nList.item(i);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					version = eElement.getElementsByTagName("version").item(0).getTextContent().trim();
		
				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		return version;
		
	}
	
	public static String readRemoteXMLVersionFile(){
		
		String version = "";
		
		try {	
			File xmlfile = new File("//" + Settings.getUpdateServerPath() + File.separator + "version.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);
					
			doc.getDocumentElement().normalize();
					
			NodeList nList = doc.getElementsByTagName("Mes");
					
			for (int i=0;i<nList.getLength();i++) {

				Node nNode = nList.item(i);
						
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					version = eElement.getElementsByTagName("version").item(0).getTextContent().trim();

				}
			}
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		return version;
		
	}
	
}
