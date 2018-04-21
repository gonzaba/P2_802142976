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
		return finalResult;
		
		
	}
	
	


	
	
	
	




}
