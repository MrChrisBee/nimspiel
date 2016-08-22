package wbs.nim;

public class NimMove {
	private int row;
	private int count;

	// ggf exception werfen
	public NimMove(int row, int count) {
		super();
		this.row = row;
		this.count = count;
	}

	public int getRow() {
		return row;
	}

	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "NimMove [row=" + row + ", count=" + count + "]";
	}

}
