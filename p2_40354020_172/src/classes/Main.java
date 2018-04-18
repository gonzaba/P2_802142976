package classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Bárbara P. González Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 *
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
        
        readFileData(listOfFiles);
        



        
        
        /**
         * 
         * //Queue which will hold the information of the customers that will be
		//extracted from the file.
		ArrayQueue<Customer> listOfCustomers = new ArrayQueue<Customer>();
         *   
         *    int id = 1; //auto assigned ID
          int t = 0; // time the customer arrives. Initially set to zero for initialization.
          int s = 0; //time it takes to service the customer. Initially set to zero for initialization
          int d = 0; //time of departure of the customer. Initially set to zero for initialization
          
          //First here we read the file to create the Customer Object to enqueue it to the listOfCustomers that 
          //will be the line the approaches will have
         */
         
	
	}//end of main(String[] args)
	
    public static void readFileData(ArrayQueue<String> listOfFiles) {
    		
    		
    		//Queue that will hold the list of data about customers
    		ArrayQueue<String> listOfCustomers = new ArrayQueue<String>();
    		
    		//Folder where the file is supposed to be located
    		String parentDirectory = "inputFiles";
    		
    		//getting the name of the file from the list of files to read
    		String fileName = listOfFiles.dequeue();
    		
    		//Looks for the file in the inputFiles with that direction.
    		File file = new File(parentDirectory, fileName);
    		
    		BufferedReader br = null;
            String line;
       
            try {

                br = new BufferedReader(new FileReader(file));
                while ((line = br.readLine()) != null) {
                	
                	
                System.out.println(line);
                //Enqueuing the list of files to analyze
                //listOfFiles.enqueue(line);  
                  
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
    }//end of readFileData
            
}// end of Main
