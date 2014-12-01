
public class Car {

	MLCP.Slot carSlot;
	
	public void park(MLCP.Slot slot){
		this.carSlot = slot;
		slot.parkedCar = this;
		slot.status = MLCP.SlotStatus.FULL;
	}
}
