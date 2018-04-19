package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import objects.Customer;
import policies.SLMS;

/**
 * 
 * @author B�rbara P. Gonz�lez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 * The main class reads the valid data text files inside inputFile and extracts the time stamps.  It cal
 */

public class Main {
	
	
	public static void main(String[] args) {
		
		//Queue that will hold the list of files to be processed
		ArrayQueue<String> listOfFiles = new ArrayQueue<String>();
		
		//Folder where the file that holds the names of files is located
		String parentDirectory = "inputFiles";
		
		//Looks for the dataFiles.txt file in the inputFiles.
		File file = new File(parentDirectory, "dataFiles.txt");
		
		BufferedReader br = null;
        String fileName;
   
        try {

            br = new BufferedReader(new FileReader(file));
            while ((fileName = br.readLine()) != null) {
            	
            	
            //System.out.println(fileName);
            //Enqueuing the list of files to analyze
            listOfFiles.enqueue(fileName);  
          
            
              
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();}
            }
        }//end of try
        
        
        
        //will read the first file. Later this must be put in a while
        //to read all files in the listOfFiles.
       
        	
       ArrayQueue<Customer> listToProcess = readFileData(listOfFiles);
       
       
      SLMS policy1 = new SLMS(listToProcess);
      policy1.result();
       
        
	}//end of main(String[] args)
	
    public static ArrayQueue<Customer> readFileData(ArrayQueue<String> listOfFiles) {
    		
    		
    		//Queue that will hold the list of data about customers
    		ArrayQueue<Customer> listOfCustomers = new ArrayQueue<Customer>();
    		
    		//Folder where the file is supposed to be located
    		String parentDirectory = "inputFiles";
    		
    		//getting the name of the file from the list of files to read
    		String fileName = listOfFiles.dequeue();
    		
    		//Looks for the file in the inputFiles with that direction.
    		File file = new File(parentDirectory, fileName);
    		
    		BufferedReader br = null;
            String line;
            int id = 1;
       
            try {

                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                	
                //System.out.println(line);
                	
                	String[] part = line.split(" ");
                	
                	Customer job = new Customer();
                	
                	job.setId(id);
                	job.setArrivalTime(Integer.parseInt(part[0]));
                	job.setServiceTime(Integer.parseInt(part[1]));
                	job.setRemainingTime(Integer.parseInt(part[1]));
                	job.setDepartureTime(0);
                	id++;
                //System.out.println(job);
                listOfCustomers.enqueue(job);
                                 
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();}
                }
            }//end of try
         return listOfCustomers;
    }//end of readFileData
    
  
            
}// end of Main
