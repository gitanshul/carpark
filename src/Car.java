
public class Car {

	private MLCP.Slot carSlot;
	private String carNumber;
	private String history;
	
	public Car(String number){
		this.carNumber = number;
	}
	
	public boolean park(MLCP.Slot slot){
		if(slot.setCar(this)){
			this.carSlot = slot;
			return true;
		}
		return false;
	}
	
	public void unPark(){		
		this.carSlot.empty();
		long carParkDuration = (this.carSlot.getEndTime() - this.carSlot.getStartTime())/1000l;
		this.history += this.carSlot.toString()+"|"+ carParkDuration+"|";
		this.carSlot = null;		
	}

	public MLCP.Slot getCarSlot() {
		return carSlot;
	}

	public String getCarNumber() {
		return carNumber;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(this.carNumber);		
		if(this.carSlot!=null){
			sb.append(" Parked in Slot - ").append(this.carSlot.toString());
		}else{
			sb.append(" Not parked");
		}
		return sb.toString();
	}
	
}
