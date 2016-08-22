package wbs.nim;

public class UndoableNim extends Nim {

	public void undo(int i) {
		int zugziel = nimList.size() - i -1;
	
		if (i < zugziel) {
			throw new IllegalArgumentException("ist das nicht ein bisschen viel zurückzunehmen?");
		}
	
		// bring die Liste auf die passende Länge
		for (int j = nimList.size()-1; j > zugziel; j--) {
			nimList.remove(j);
		}
		// aktuellen Wert der Liste als Spielstand setzen
		setXorOverAllRows(nimList.get(nimList.size()).getXorOverAllRows());
		setNotEmptyRows(nimList.get(nimList.size()).getNotEmptyRows());
		setRows(nimList.get(nimList.size()).getRows());
	}

	public void reset() {
		undo(nimList.size());
	}

}
