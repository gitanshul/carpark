import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MLCP {

	private String name;
	private Floor[] floors;
	private int slotsPerFloor;
	
	private Queue<Car> parkedCars;
	
	public MLCP(String name, int noOfFloors, int slotsPerFloor) {
		this.name = name;
		this.floors = new Floor[noOfFloors];
		this.slotsPerFloor = slotsPerFloor;
		initialize();
	}

	private void initialize() {
		this.parkedCars = new ArrayBlockingQueue<Car>(this.getTotalSlotCount());
		
		for(int i=0; i < this.floors.length; i++){
			floors[i] = new Floor("MLCP-"+(i+1), i+1, this.slotsPerFloor); 
		}
	}

	public class Floor {
		private String name;
		private int level;	
		private int noOfSlots;
		private Slot[] slots;
		private int emptySlotCount;
		
		public Floor(String name, int level, int noOfSlots) {
			this.name = name;
			this.level = level;			
			this.noOfSlots = noOfSlots;
			
			initialize();
		}

		private void initialize() {
			emptySlotCount = noOfSlots;
			slots = new Slot[noOfSlots];
			for(int i=0; i < this.noOfSlots; i++){
				Slot s = new Slot(this.name+"-Slot-"+(i+1), SlotStatus.EMPTY, this);
				slots[i] = s;
			}
		}

		public Slot[] getSlots(){
			return this.slots;
		}
	}

	public class Slot {
		private String name;
		private SlotStatus status;
		private long startTime;		
		private long endTime;		
		private Floor floor;
		
		private Car parkedCar;
				
		public Slot(String name, SlotStatus status, Floor f){
			this.name = name;
			this.status = status;
			this.floor = f;
		}
		
		Car getCar(){
			return parkedCar;
		}
		
		synchronized boolean setCar(Car car){
			if(SlotStatus.EMPTY.equals(this.status)){
				this.startTime = System.currentTimeMillis();
				this.parkedCar = car;
				this.status = SlotStatus.FULL;
				parkedCars.add(car);
				this.floor.emptySlotCount--;
				return true;
			}
			return false;
		}
		
		Car empty(){
			this.endTime = System.currentTimeMillis();
			this.status = SlotStatus.EMPTY;
			parkedCars.remove(parkedCar);
			Car carToRemove = parkedCar;
			parkedCar = null;
			this.floor.emptySlotCount++;
			return carToRemove;
		}
		
		public SlotStatus getStatus(){
			return this.status;
		}
		
		public long getStartTime() {
			return startTime;
		}

		public long getEndTime() {
			return endTime;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public Slot getNextAvailableSlot(){		
		for(Floor f : floors){
			if(f.emptySlotCount > 0){
				for(Slot s : f.slots){
					if(SlotStatus.EMPTY.equals(s.status)){
						return s;
					}
				}
			}
		}
		return null;
	}

	public Slot getRandomAvailableSlot(){		
		for(Floor f : floors){
			if(f.emptySlotCount > 0){
				while(true){
					Slot s = f.getSlots()[MLCPUtil.getRandom(0, f.noOfSlots-1)];
					if(SlotStatus.EMPTY.equals(s.getStatus())){
						return s;
					}
				}
			}
		}
		return null;
	}

	public int getEmptySlotCount(){
		int totalEmptySlots = 0;
		
		for(Floor f : floors){
			totalEmptySlots += f.emptySlotCount;
		}
		
		return totalEmptySlots;
	}

	public Queue<Car> getParkedCars(){
		return parkedCars;
	}
	
	public void shuffleCars(){
		//TODO: implement
	}
	
	public void swapCars(Slot s1, Slot s2){
		//TODO: implement
	}
	
	public String getName() {
		return name;
	}
	
	public enum SlotStatus {
		EMPTY, FULL
	}
	
	public Floor[] getFloors() {
		return floors;
	}

	public int getTotalSlotCount(){
		return floors.length*slotsPerFloor;
	}
	
	public void printStatus(){
		
		MLCPUtil.pprnt("No of Occupied Slots :"+(this.getTotalSlotCount()-this.getEmptySlotCount()),"=");
		MLCPUtil.pprnt("No of Empty Slots :"+(this.getEmptySlotCount()),"=");
		MLCPUtil.pprnt("Empty Slots","+");
		for(Floor f : floors){
			for(Slot s : f.getSlots()){
				if(SlotStatus.EMPTY.equals(s.getStatus())){
					MLCPUtil.pprnt(s.toString(),"-");
				}
			}
		}
		
		MLCPUtil.pprnt("Filled Slots","+");
		for(Floor f : floors){
			for(Slot s : f.getSlots()){
				if(!SlotStatus.EMPTY.equals(s.getStatus())){
					MLCPUtil.pprnt(s.toString()+" had Car : "+s.getCar().getCarNumber(),"-");
				}
			}
		}
	}
	
	@Override
	public String toString() {
		
		return "Testing "+this.name+" With "+this.floors.length+" Floors and "+
					this.slotsPerFloor+" Slots per floor";
	}
}
