package policies;

import java.util.ArrayList;

import classes.ArrayQueue;
import objects.Customer;
import objects.Result;
import objects.ServicePost;

/**
 * @author Barbara P. Gonzalez Rivera - 802-14-2976
 * @author Ramineh Lopez - 402-12-3657
 * ICOM4035 - 030
 */

//Multiple Lines Multiple Servers

public class MLMS {
	
	ArrayQueue<Customer> listToCust;
	int numberOfServicePosts = 0;
	int timeAllServicesCompleted = 0;
	int averageWaitingTime = 0;

	ArrayQueue<Customer> listToProcess = new ArrayQueue<>();
	
	ArrayList<ServicePost> listOfServicePost = new ArrayList<>();
	
	ArrayQueue<Customer> terminatedJobs = new ArrayQueue<>();
	
	Result finalResult = new Result(0,0,0);
	
	
	public MLMS(ArrayQueue<Customer> list, int i) {
		this.listToCust = list;
		this.numberOfServicePosts = i;
	}

	public Result result() {
		
				//Creates and Initialize the amount of ServicePost wanted
				//Adds the service post to my master list of service posts.
				if(numberOfServicePosts==1) {
					ServicePost servicePost1 = new ServicePost(); //aka cajero #1
					listOfServicePost.add(servicePost1); //Anado cajero #1 a la lista de cajeros
				}
				if(numberOfServicePosts==3) {
					ServicePost servicePost1 = new ServicePost();
					listOfServicePost.add(servicePost1);
					
					ServicePost servicePost2 = new ServicePost(); 
					listOfServicePost.add(servicePost2); 
					
					ServicePost servicePost3 = new ServicePost(); 
					listOfServicePost.add(servicePost3);
				}
				if(numberOfServicePosts==5) {
					ServicePost servicePost1 = new ServicePost(); 
					listOfServicePost.add(servicePost1); 
					ServicePost servicePost2 = new ServicePost(); 
					listOfServicePost.add(servicePost2); 
					ServicePost servicePost3 = new ServicePost(); 
					listOfServicePost.add(servicePost3); 
					ServicePost servicePost4 = new ServicePost(); 
					listOfServicePost.add(servicePost4); 
					ServicePost servicePost5 = new ServicePost();
					listOfServicePost.add(servicePost5);
				}
		
		
		
		
		
		return finalResult;
		
		
	}//end of result
	
	


	
	
	
	




}
