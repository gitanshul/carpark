import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;


public class MLCPUtil {
	public static final boolean debug = true;
	
	/**
	 * Generate Pool of n Cars with numberPlates. 
	 * @param n
	 * @return
	 */
	public static Queue<Car> generateCars(int n){
		ConcurrentLinkedQueue<Car> carPool = new ConcurrentLinkedQueue<Car>();
		for(int i =0; i <n; i++){
			carPool.add(new Car("Car-"+(i+1)));
		}		
		return carPool;
	}
	
	public static void pprnt(String s){
		pprnt(s, "-");
	}
	
	public static void pprnt(String s, String padder){
		if(debug){
			int strLen = s.length();
			int col_len = 80;
			if(strLen > 150)
				col_len = 250;
			int padding = (col_len - strLen-2)/2;
			String padStr = "";
			for(int i=0; i <padding; i++){
				padStr += padder; 
			}
			
			System.out.println(padStr+" "+s+" "+padStr);
		}
	}

	/**
	 * Returns random time value between Min and Max (in milliseconds).
	 * @return
	 */
	public static int getRandom(int min, int max){
		Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	

}
