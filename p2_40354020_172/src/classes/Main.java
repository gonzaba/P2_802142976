package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;

import objects.Customer;
import objects.Result;
import policies.SLMS;

/**
 * 
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 **/




public class Main {

	
	public static void main(String[] args) throws IOException {
		
		/**
		 * First step is to find the file called dataFiles.txt that will hold
		 * the name of files that 
		 *  
		 *  */

		//ArrayList that will hold the list of files that will be processed
		//individually with each policy
		ArrayQueue<String> listOfFiles = new ArrayQueue<>();
		
		
		//Folder where the file that holds the names of files is located
		String parentDirectory = "inputFiles";
				
		//Looks for the dataFiles.txt file in the inputFiles.
		File file = new File(parentDirectory, "dataFiles.txt");
		
		 String fileName = "";
		 Scanner sc = null;
		 
		 try {
			 //Created scanner to read the file
			 sc = new Scanner(file);	
			 
			//while Scanner has a another line to read
			while(sc.hasNext()) {				
				
				//Find the next string
				fileName = sc.next();
			
				listOfFiles.enqueue(fileName);
				
			}//end of while
		
		//File not found in directory
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found");
			e.printStackTrace();
		}catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (sc != null) {
                sc.close();
            }
        }//end of try
		 
	 /**
	  * After all file names have been extracted from the dataFile.txt now comes the part
	  * of reading each file to create the list of customers. 
	  * 
	  * Then it will pass the list of customers to each approach and it will be processed.
	  * 
	  **/
		   
		 
	     //uncomment when everything is completed
	  //  while(!listOfFiles.isEmpty()) {
	    	
	    String f = listOfFiles.first();
	    
	     ArrayQueue<Customer> listcust = readFileData(listOfFiles);
	     
	     
	     if(listcust.size()>0) { 
	    	    	 
	    	f.split("\\.");
	    	
	 		PrintWriter writer2 = new PrintWriter(f +"_OUT.txt", "UTF-8");
	 		
	 		writer2.println("Number of customers is: " + listcust.size());
	 		
	 		
	 		
	 		SLMS SLMS1 = new SLMS(listcust, 1);
	 		    
	 		writer2.println("SLMS 1: " + SLMS1.result());
	 		

	 	   
		     //SLMS SLMS3 = new SLMS(readFileData(listOfFiles), 3);
	 		//writer2.println("SLMS 3: " + SLMS3.result());
		    // SLMS SLMS5 = new SLMS(readFileData(listOfFiles), 5);
	 		//writer2.println("SLMS 5: " + SLMS5.result());
	 		
	 		writer2.close();
	 		
	    }
	        
	    
	     
	     //MUST CREATE COPY list to pass the same list to each approach!!!
	
	  
		
		
		 /**
		  *  
		  *  //uncomment when each policy is completed
		  *  
		  *   MLMS policy2 = new MLMS(readFileData(listOfFiles));
		  policy2.result();
		  
		  MLMSBLL policy3 = new MLMSBLL(readFileData(listOfFiles));
		  policy3.result();
		  
		  MLMSBWT policy4 = new MLMSBWT(readFileData(listOfFiles));
		  policy4.result();
	     */ 
	  
	// }//end of while
	    
	
		  
		  
		  
		 
	     
		
	}//end of main

	
	
	/**
	 * 
	 * @param listOfFiles
	 * @return
	 */
	
	private static ArrayQueue<Customer> readFileData(ArrayQueue<String> listOfFiles) throws IOException {
		
		//Queue that will hold the list of data about customers
		ArrayQueue<Customer> listOfCustomers = new ArrayQueue<Customer>();
		
		//Folder where the file is supposed to be located
		String parentDirectory = "inputFiles";
		
		//getting the name of the file from the list of files to read
		String fileName = listOfFiles.dequeue();
		
		//Looks for the file in the inputFiles with that direction.
		File file = new File(parentDirectory, fileName);
		
	
		Scanner sc = null;
		
		//checks if the name files inside of dataFiles are .txt format
		if(!fileName.contains(".txt")) {
			
			fileCreatorExceptions(2, fileName);
		}
		else {			
			  //id given to the customer for easier debugging
	        int id = 1;
	   
	        try {

	        	sc = new Scanner(file).useDelimiter("\\s");
	        	
	        	//Checks to see if file is int format if not then throw message
	        	if(!sc.hasNextInt()) {
	        		fileCreatorExceptions(2, fileName);
	        	}
	        	else {
	        		 while (sc.hasNext()) {
	 	            	
	 	            	int ar = sc.nextInt();
	 	           		int st = sc.nextInt();
	 	           	
	 	            	Customer job = new Customer();
	 	            	
	 	            	job.setId(id);
	 	            	job.setArrivalTime(ar);
	 	            	job.setServiceTime(st);
	 	            	job.setRemainingTime(st);
	 	            	job.setDepartureTime(0);
	 	            	id++;
	 	            
	 	            	//System.out.println(fileName + " " + job);
	 	            	listOfCustomers.enqueue(job);
	        		 }                            
	            }//end of else
	        	}catch (FileNotFoundException e) {
	        		fileCreatorExceptions(1, fileName);
	            	e.printStackTrace();
	        
	        	} catch (IOException e) {           	
	        		e.printStackTrace();
	            
	        	} finally {
	            	if (sc != null) {
	                	sc.close();
	            	}
	        	}//end of try
	      
		}
		
		
		return listOfCustomers;
		
       
      
        
	}//end of readFileData
	
	
	
	public static void fileCreatorExceptions(int caseType, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
		
		/*
		 * Here it will create the new files with the new name format.
		 * These files created here are specifically for the exceptions.
		 * 
		 * There exist another method that will print the output of the files
		 * that have the valid format.
		 * 
		 */
		
		fileName.split("\\.");
		
		PrintWriter writer = new PrintWriter(fileName +"_OUT.txt", "UTF-8");
		
		//If file does not exist
		if(caseType==1) {
			
			writer.println("Input file not found.");
			writer.close();
			
		}
		
		//If file has no data, is incomplete, or does not fit the expected format.
		if(caseType==2) {
			
			writer.println("Input file does not meet the expected format or it is empty.");
			writer.close();
		}	
		
	}
	
	
	
	

	
	
}//end of Main
