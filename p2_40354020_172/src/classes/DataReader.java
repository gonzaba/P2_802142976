package classes;

public class  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int id=0;
		int arrivalTime=0;
		int remainingTime=0;
		String line;
		String parentDirectory = "inputFiles";
	
	for(int ii = 1;ii<5;ii++) {	
	//	File f=new File("inputFiles/input"+ii+".txt");
		BufferedReader reader=null;
		try {
			Scanner scanner = new Scanner(new File(parentDirectory, "input"+ii+".txt"));
			scanner.useDelimiter(",");
		//	System.out.println("tohere");
			//System.out.println(scanner.);
			
			while(scanner.hasNextInt()) {
				arrivalTime = scanner.nextInt();
				remainingTime = scanner.nextInt();
				id++;
			//	System.out.println(" x "+id+" y "+arrivalTime+" z "+remainingTime);
				Job each=new Job (id, arrivalTime, remainingTime);
				inputQueue.enqueue(each);
				
			}
						
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		int time=0;
//		int loop=0;
		while(!processingQueue.isEmpty() || ! inputQueue.isEmpty()) {
//			loop++;
//			if(loop>100) {return;}
			if (!processingQueue.isEmpty()) {
				Job ntr=processingQueue.first();
//				System.out.println("t "+time+" "+ntr);
				ntr.isServed(1);
				if (ntr.getremainingTime()==0) {
					ntr.setDepartureTime(time);
					terminatedJobs.enqueue(processingQueue.first());
					processingQueue.dequeue();
				}
				else {
					processingQueue.enqueue(processingQueue.dequeue());
				}
			}
			if (!inputQueue.isEmpty()) {
				Job input=inputQueue.first();
				if (input.getarrivalTime()==time) {
					processingQueue.enqueue(inputQueue.dequeue());
				}
			}	
			time++;		
		}
			double totalTime=0.0;
			double firstValue=0.0;
			double secondValue=0.0;
			double averageTime=0.0;
			int count=0;
			
			while (!terminatedJobs.isEmpty()) {
				firstValue=terminatedJobs.first().getarrivalTime();
				secondValue=terminatedJobs.first().getDepartureTime();
				totalTime=(secondValue-firstValue)+totalTime;
				count++;
				terminatedJobs.dequeue();
			}
			
			averageTime=totalTime/count;
			
			System.out.print(" Average Time in System for input"+ii+".txt is: " + averageTime);
			System.out.println();
			System.out.println();
		//	System.out.printf("%.f", averageTime);
			
		}
		
	}
}

