package classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import objects.Customer;
import policies.MLMS;
import policies.MLMSBLL;
import policies.MLMSBWT;
import policies.SLMS;

/**
 * 
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 **/


/**
 * This class will essentially run the show. 
 *
 */


public class Main {

	
	public static void main(String[] args) throws IOException {
		
		/**
		 * First step is to find the file called dataFiles.txt that will hold
		 * the name of files that 
		 **/

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
		}catch (@SuppressWarnings("hiding") IOException e) {
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
  // while(!listOfFiles.isEmpty()) {
	    	
	    String f = listOfFiles.first();
	    
	     ArrayQueue<Customer> listcust = readFileData(listOfFiles);
	     
	     
	     if(listcust.size()>0) { 
	    	    	 
	    	f.split("\\.");
	    	
	 		PrintWriter writer2 = new PrintWriter(f +"_OUT.txt", "UTF-8");
	 		
	 		writer2.println("Number of customers is: " + listcust.size());
	 		
	 		SLMS SLMS1 = new SLMS(copyOf(listcust), 1);    
	 		writer2.println("SLMS 1: " + SLMS1.result());		

	 		SLMS SLMS3 = new SLMS(copyOf(listcust), 3);
	 		writer2.println("SLMS 3: " + SLMS3.result());
	 	   
	 		SLMS SLMS5 = new SLMS(copyOf(listcust), 5);
	 		writer2.println("SLMS 5: " + SLMS5.result());
	 		
	 		MLMS MLMS1 = new MLMS(copyOf(listcust), 1);
	 		writer2.println("MLMS 1: " + MLMS1.result());
	 		
	 		MLMS MLMS3 = new MLMS(copyOf(listcust), 3);
	 		writer2.println("MLMS 3: " + MLMS3.result());
	 		
	 		MLMS MLMS5 = new MLMS(copyOf(listcust), 5);
	 		writer2.println("MLMS 5: " + MLMS5.result());
	 		
	 		//MLMSBLL MLMSBLL1 = new MLMSBLL(copyOf(listcust), 1);
	 	//	writer2.println("MLMSBLL 1: " + MLMSBLL1.result());
	 		
	 		MLMSBLL MLMSBLL3 = new MLMSBLL(copyOf(listcust), 3);
	 		writer2.println("MLMSBLL 3: " + MLMSBLL3.result());
	 		
	 	//	MLMSBLL MLMSBLL5 = new MLMSBLL(copyOf(listcust), 5);
	 	//	writer2.println("MLMSBLL 5: " + MLMSBLL5.result());
	 		
	 //		MLMSBWT MLMSBWT1 = new MLMSBWT(copyOf(listcust),1);
	 	//	writer2.println("MLMSBWT 1: " + MLMSBWT1.result());
	 		
	 		//MLMSBWT MLMSBWT3 = new MLMSBWT(copyOf(listcust),3);
	 	//	writer2.println("MLMSBWT 3: " + MLMSBWT3.result());
	 		
	 	//	MLMSBWT MLMSBWT5 = new MLMSBWT(copyOf(listcust),5);
	 	//	writer2.println("MLMSBWT 5: " + MLMSBWT5.result());
	 		
						
			writer2.close();
		 	  
		   
	 		
	    }
	   
		
//	}//end of while
	    
	
		  
		  
		 
		
	}//end of main

	
	
	/**
	 * 
	 * @param listOfFiles
	 * @return
	 */
	
	@SuppressWarnings("resource")
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

	        	sc = new Scanner(file).useDelimiter("\\s+");
	        	
	        	//Checks to see if file is int format if not then throw message
	        	if(!sc.hasNextInt()) {
	        		fileCreatorExceptions(2, fileName);
	        	}
	        	else {
	        		 while (sc.hasNextInt()) {
	 	            	
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
	
	
	/**
	 * 
	 * @param listcust to copy
	 * @return a copy of the list that doesn't have the same
	 * reference to the original list. Its a completely new list with the same elements
	 * but they are not linked in any way. Therefore you can edit the copy list and the original
	 * will stay the same
	 */
	public static ArrayQueue<Customer> copyOf(ArrayQueue<Customer>listcust){
		
		ArrayQueue<Customer> copy = new ArrayQueue<>();
		
		//System.out.println(listcust.size());
		int i=0;
		while(!(i==listcust.size())) {
			
			Customer c = listcust.dequeue();
			
			//System.out.println(c);
		
			
			Customer copyC = new Customer();
			
			//System.out.println(copyC);
			
			copyC.setArrivalTime(c.getArrivalTime());
			copyC.setDepartureTime(c.getDepartureTime());
			copyC.setId(c.getId());
			copyC.setRemainingTime(c.getRemainingTime());
			copyC.setServiceTime(c.getServiceTime());
			
			listcust.enqueue(c);
			
			copy.enqueue(copyC);
			i++;
		}
		
		return copy;
		
	}
	
	
	
	

	
	
}//end of Main
