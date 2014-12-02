import java.util.Queue;

public class TestMLCP {
	
	public static void main(String[] args) {
		
		TestMLCP tmlcp = new TestMLCP();
		/*
		tmlcp.TestFillUnfillAllSlots(mlcp);
		tmlcp.TestParkingOnFullSlot(mlcp);
		*/
		tmlcp.TestSimulateMLCP();

	}
	
	public void TestSimulateMLCP(){
		int noOfCarsToPark = 20;
		int noOfFloorsInMLCP = 2;
		int noOfSlotsPerFloor = 10;
		
		SimulateMLCP sim_mlcp = new SimulateMLCP();
		MLCP mlcp = new MLCP("Meenakshi Mall Car Parking", noOfFloorsInMLCP, noOfSlotsPerFloor);
		sim_mlcp.simulateMLCP(mlcp, MLCPUtil.generateCars(noOfCarsToPark));
		
		Queue<Car> parkedCars = mlcp.getParkedCars();
		if(parkedCars.size()!=noOfCarsToPark){
			MLCPUtil.pprnt("ParkedCars count mismatch");
		}
		
		for(Car c : parkedCars){
			if(!MLCP.SlotStatus.FULL.equals(c.getCarSlot().getStatus())){
				MLCPUtil.pprnt("ParkedCar : "+c.getCarNumber()+" doesn't have a slot");
				break;
			}else{
				MLCPUtil.pprnt("Car : "+c.getCarNumber()+" Parked in Slot : "+c.getCarSlot().toString());
			}
		}
		
		MLCPUtil.pprnt("TestSimulateMLCP successful");
				
	}
	
	/**
	 * Park a car in a selected slot.
	 * An attempt to park another car in the same slot should fail.
	 * @param mlcp
	 */
	public void TestParkingOnFullSlot(MLCP mlcp){
		MLCPUtil.pprnt("Running "+"TestParkingOnFullSlot()");
		Car newCar = new Car("Car-XYZ");
		Car anthrCar = new Car("Car-ABC");
		MLCP.Slot avaiSlot = mlcp.getNextAvailableSlot();
		if(newCar.park(avaiSlot) && !anthrCar.park(avaiSlot)){
			MLCPUtil.pprnt("SuccessFul "+"TestParkingOnFullSlot()");
		}		
	}
	
	/**
	 * Park cars until all the slots are full. 
	 * Then un-park cars until all the slots are empty.
	 * @param mlcp
	 */
	public void TestFillUnfillAllSlots(MLCP mlcp){
		MLCPUtil.pprnt("Running "+"TestFillUnfillAllSlots()");
		
		int noOfCarsToPark = mlcp.getTotalSlotCount();
		
		MLCPUtil.pprnt("Started Parking "+noOfCarsToPark+" Cars");
		
		Queue<Car> newCars = MLCPUtil.generateCars(noOfCarsToPark);
		for(Car c : newCars){
			MLCP.Slot avaiSlot = mlcp.getNextAvailableSlot();			
			c.park(avaiSlot);
		}
		MLCPUtil.pprnt("Done Parking "+noOfCarsToPark+" Cars");
		
		MLCP.Floor floors[] = mlcp.getFloors();
		for(MLCP.Floor f : floors){
			for(MLCP.Slot s : f.getSlots()){
				if(MLCP.SlotStatus.EMPTY.equals(s.getStatus())){
					System.out.println(s.toString()+" is Empty !!");
				}
			}
		}
		MLCPUtil.pprnt("Done Checking Empty Slots. No. of Empty Slots : "+mlcp.getEmptySlotCount());
		
		MLCPUtil.pprnt("Started Remvoing "+noOfCarsToPark+" Parked Cars");
		for(MLCP.Floor f : floors){
			for(MLCP.Slot s : f.getSlots()){
				s.getCar().unPark();
			}
		}
		MLCPUtil.pprnt("Done Remvoing "+noOfCarsToPark+" Parked Cars");
		
		if(mlcp.getEmptySlotCount()==mlcp.getTotalSlotCount()){
			MLCPUtil.pprnt("SuccessFul "+"TestFillUnfillAllSlots()");
		}
	}		
}
