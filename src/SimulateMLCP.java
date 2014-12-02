import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class SimulateMLCP {

	private Queue<Car> externalCarPool;
	public void simulateMLCP(MLCP mlcp, Queue<Car> carPool){

		BlockingQueue<Car> waitingCars = new ArrayBlockingQueue<Car>(mlcp.getTotalSlotCount());
		externalCarPool = carPool;
		
		MLCPUtil.pprnt("Welcome to "+mlcp.getName()+" Simulation");
		Thread t1 = new Thread(new CarFeeder(waitingCars, mlcp));
		Thread t2 = new Thread(new CarAlloter(waitingCars, mlcp));
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public class CarRemover implements Runnable{
		BlockingQueue<Car> waitingCarQ;
		MLCP mlcp;
		public CarRemover(BlockingQueue<Car> carQ, MLCP mlcp) {
			this.waitingCarQ = carQ;
			this.mlcp = mlcp;
		}
		
		@Override
		public void run() {
			
		}
	}
	
	public class CarFeeder implements Runnable{
		BlockingQueue<Car> waitingCarQ;
		MLCP mlcp;
		public CarFeeder(BlockingQueue<Car> carQ, MLCP mlcp) {
			this.waitingCarQ = carQ;
			this.mlcp = mlcp;
		}
		
		@Override
		public void run() {
			int counter = 0;
			while(true){
				long sleepTime = MLCPUtil.getRandomSleepTime(500,1000);
				Car nCar = externalCarPool.poll();
				if(null==nCar){
					waitingCarQ.add(new Car("X")); // Special car to signal that there are no more cars left for today
					break;
				}
				MLCPUtil.pprnt("Beep Beep ! A new car has arrived : "+nCar.getCarNumber());
				waitingCarQ.add(nCar);
				counter++;
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			MLCPUtil.pprnt("CarFeeder: I have worked on "+counter+" cars so far. I am OFF now !!");
		}
	}

	public class CarAlloter implements Runnable{
		BlockingQueue<Car> carQueue;
		MLCP mlcp;
		public CarAlloter(BlockingQueue<Car> carQ, MLCP mlcp) {
			this.carQueue = carQ;
			this.mlcp = mlcp;
		}
		
		@Override
		public void run() {
			int counter = 0;
			while(true){
				try {
					Car car = carQueue.take();
					if(car.getCarNumber().equals("X")){ // If the special car is found, it's the signal that there are no more cars.
						break;
					}
					Thread.sleep(500); //Giving 2 secs for the car to get itself parked.
					MLCP.Slot slot = mlcp.getNextAvailableSlot();
					car.park(slot);
					MLCPUtil.pprnt1("Voila ! "+car.getCarNumber()+" Parked in the slot : "+slot.toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				counter++;				
			}
			MLCPUtil.pprnt1("Car Alloter: I have alloted "+counter+" cars. I am going OFF !!");			
		}
	}
}
