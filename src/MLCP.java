public class MLCP {

	Floor[] floors;

	public MLCP() {
		initialize();
	}

	private void initialize() {

	}

	public class Floor {
		private String name;
		private int level;
		private Row[] rows;

		public Floor(String name, int level, int noOfRows) {
			this.name = name;
			this.level = level;
			this.rows = new Row[noOfRows];
			initialize();
		}

		private void initialize() {
			for (int i = 0; i < rows.length; i++) {
				rows[i] = new Row(this, "Row" + i);
			}
		}

	}


	public class Slot {
		private String name;
		private SlotStatus status;
		private int startTime;
		private int endTime;

		Car parkedCar;
		// Row slotRow;

	}

	public class Row {
		private Floor myFloor;
		private String name;
		private Slot slots;

		public Row(Floor f, String name) {
			this.myFloor = f;
			this.name = name;
		}
	}


	public enum SlotStatus {
		EMPTY, FULL
	}

}
