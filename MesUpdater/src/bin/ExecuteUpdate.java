package bin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ExecuteUpdate {
		
	public void executeUpdate(){
		
		String absolutePath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
	    absolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("/"));
	    absolutePath = absolutePath.replaceAll("%20"," ");
	    
		File localdestinationfolderolder = new File(absolutePath);
		File remoterepositoryfolder = null;
    	
    	if(System.getProperty("os.name").substring(0, 1).toUpperCase().equals("W")){
    		remoterepositoryfolder = new File("//" + Settings.getUpdateServerPath());
    	}else if (System.getProperty("os.name").substring(0, 1).toUpperCase().equals("L")){
    		remoterepositoryfolder = new File("//" + Settings.getUpdateServerPath());
		}else{
			System.out.println("Errore: Sistema operativo non supportato dal programma");
			System.exit(0);
		}
    	
		File[] filelist = remoterepositoryfolder.listFiles();
	
		for(File file : filelist) {
			try{
				Files.copy(file.toPath(),(new File(localdestinationfolderolder + File.separator + file.getName())).toPath(),StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Copia file da: [" + remoterepositoryfolder + File.separator + file.getName() + "] a: [" + localdestinationfolderolder + File.separator + file.getName() + "]");
			}catch(IOException e){
	    		e.printStackTrace();
	        }
		}
    	 
	}
	
}
