package bin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class StoreUpdate {
	
	public static void executeStoreUpdate(String ipaddress, String application, String newlocalversion) {
		
		Connection conn = null;
        PreparedStatement ps = null;
        String sql1 = "";
        String sql2 = "";
        
		String checkdbconnection = Settings.checkDbConnection();
		
		if(checkdbconnection.equals("false")){
			System.out.println("Errore: impossibile connettersi al Database.");
			System.out.println("Non è stato possibile connettersi al Database a causa di un errore. Controllare la connettività o le impostazioni di rete.");
		}else{	
			
			if(Settings.getDriver().equalsIgnoreCase("com.mysql.jdbc.Driver")){
				
				sql1 = "DELETE FROM F55FQ10011 WHERE TRIM(UPADDR) = ?";
		        
				sql2 = "INSERT INTO F55FQ10011 (UPADDR,UPAPPLICATION,UPVER,UPADATE) VALUES (?, ?, ?, STR_TO_DATE(?,'%d/%m/%Y %H.%i.%s')) ";
		        
			}else if(Settings.getDriver().equalsIgnoreCase("oracle.jdbc.driver.OracleDriver")){
				
				sql1 = "DELETE FROM F55FQ10011 WHERE TRIM(UPADDR) = ?";
		        
				sql2 = "INSERT INTO F55FQ10011 (UPADDR,UPAPPLICATION,UPVER,UPADATE) VALUES (?, ?, ?, TO_DATE(?,'DD/MM/YYYY HH24.MI.SS')) ";
		        
			}
				        
	        System.out.println("Inizio storicizzazione versione...");
	        
	        try {
	
	            conn = Settings.getDbConnection();
	            ps = conn.prepareStatement(sql1);
	          
	            ps.setString(1, ipaddress);                                                                    
	            
	            ps.executeUpdate();
	
	        } catch (SQLException Sqlex) {
	
	            System.out.println(Sqlex.getMessage());
	
	        } finally {
	 
	            if (ps != null) {
	                try {
	                    ps.close();
	                } catch (SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
	
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
	
	        }
	                
	        try {
	
	            conn = Settings.getDbConnection();
	            ps = conn.prepareStatement(sql2);
	          
	            ps.setString(1, ipaddress); 
	            ps.setString(2, application); 
	            ps.setString(3, newlocalversion);
	            ps.setString(4, Settings.getEventDate());
	            
	            ps.executeUpdate();
	
	        } catch (SQLException Sqlex) {
	
	            System.out.println(Sqlex.getMessage());
	
	        } finally {
	 
	            if (ps != null) {
	                try {
	                    ps.close();
	                } catch (SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
	
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                    System.out.println(e.getMessage());
	                }
	            }
	
	        }
	        
	        System.out.println("Storicizzazione versione terminata con successo");
		}
	}

}
