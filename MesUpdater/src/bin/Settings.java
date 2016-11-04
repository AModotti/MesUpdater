package bin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Settings {
    
    private static String dbconnectionstring;
    private static String driver; 
    private static String dbuser;  
    private static String importtojdeuser;
    private static String dbpassword;
    private static String environment;
    private static String updateserverpath;
    private static String application;
    private static String enablelog;
    
    public static void storageSettings(String xmldbconnectionstring,String xmldriver,String xmldbuser,String xmlimporttojdeuser,String xmlupdateserverpath,String xmlapplication,String xmlenablelog){
    	
    	dbconnectionstring = xmldbconnectionstring;
    	driver = xmldriver;
    	dbuser = xmldbuser;
    	importtojdeuser = xmlimporttojdeuser;
    	updateserverpath = xmlupdateserverpath;
    	application = xmlapplication;
    	enablelog = xmlenablelog;
    	
    	if(dbuser.equals("PRODDTA")){
    		dbpassword = "PRODDTA";
    	}else if(dbuser.equals("CRPDTA")){
    		dbpassword = "CRPDTA";
    	}else if(dbuser.equals("admin")){
    		dbpassword = "sowhat";
    	}else if(dbuser.equals("root")){
    		dbpassword = "sowhat";
    	}else{
    		dbpassword = "";
    	}
    	
    }

    public static String checkDbConnection() {
   	 
        String checkdbConnection = null;
        
        try{
            Class.forName(driver).newInstance();
            checkdbConnection = "true";

        }catch (ClassNotFoundException e) {
            checkdbConnection = "false";
        }catch (InstantiationException e) {
            checkdbConnection = "false";
        }catch (IllegalAccessException e) {
            checkdbConnection = "false";
        }
        
        try{
        	Connection dbConnection = DriverManager.getConnection(dbconnectionstring,dbuser,dbpassword);
        	dbConnection.close();
        	checkdbConnection = "true";
        }catch (SQLException e) {
            checkdbConnection = "false";
        }

        return checkdbConnection;
 
    }
    
    public static Connection getDbConnection() {
 
        Connection dbConnection = null;

        try{
            Class.forName(driver).newInstance();
        }catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }catch (InstantiationException e) {
        	e.printStackTrace();
        }catch (IllegalAccessException e) {
        	e.printStackTrace();
        }
        
        try{
            dbConnection = DriverManager.getConnection(dbconnectionstring,dbuser,dbpassword);
            return dbConnection;
        }catch (SQLException e) {
        	e.printStackTrace();
        }

        return dbConnection;
 
    }
    
    public static String getDate(){
        
        String datenow;
        
        Date date = new Date(); 
        
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
                              
        datenow = dateformat.format(date);
        
        return datenow;
        
    }
    
    public static String getTime(){
        
        String timenow;
        
        Date date = new Date(); 

        DateFormat timeformat = new SimpleDateFormat("HH.mm.ss");

        timenow = timeformat.format(date);
        
        return timenow;
        
    }
    
    public static String getEventDate(){
        
        String eventdate;
        
        Date date = new Date(); 
        
        DateFormat dateformatevent = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
                
        eventdate = dateformatevent.format(date);
        
        return eventdate;
        
    }
    
    public static String getEnvironment(){
    	
    	if(dbuser.equals("CRPDTA")){ 
            environment = "Test";
        }else if (dbuser.equals("PRODDTA")){ 
            environment = "Produzione";  
        }else if (dbuser.equals("admin")){ 
            environment = "Dipartimentale";  
        }else if (dbuser.equals("root")){ 
            environment = "Dipartimentale";  
        }
    	
    	return environment;
    }
    
    public static String getDriver(){
    	return driver;
    }
    
    public static String getUpdateServerPath(){
    	return updateserverpath;
    }
    
    public static String getApplication(){
    	return application;
    }
        
    public static String getUEnableLog(){
    	return enablelog;
    }
    
    public static String getImportToJdeUser() {
    	
    	return importtojdeuser;
    }
    
}
