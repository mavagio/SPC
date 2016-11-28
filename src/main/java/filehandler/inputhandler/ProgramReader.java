package main.java.filehandler.inputhandler;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
*@author Martin Avagyan 
* */
public class ProgramReader {
	
	// The name of the file to open.
    String fileName;
    ArrayList<String> courses;
    String line = null;
	
	public ProgramReader(String fileName){
		this.fileName = fileName;
		this.courses  = new ArrayList<String>();
	}
	
	public  ArrayList<String> read(){
	    try {
	        FileReader fileReader = new FileReader(fileName);

	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	
	        while((line = bufferedReader.readLine()) != null) {
	        	courses.add(line);
	        }     
	        
	        bufferedReader.close();         
	    }
	    catch(FileNotFoundException e) {
	    	e.printStackTrace();            
	    }
	    catch(IOException e) {
	    	e.printStackTrace();        
	    }
	    
	    return courses;
	}	
}
