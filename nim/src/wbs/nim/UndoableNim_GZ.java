package wbs.nim;

import java.util.Stack;

/*
 * die felder rows, notEmptyRows und xorOverAllRows sollten auf
 * protected gesetzt werden
 */

public class UndoableNim_GZ extends Nim {

	protected Stack<NimMove> moves = new Stack<>();

	public UndoableNim_GZ() {
		super();
	}

	public UndoableNim_GZ(int anzReihen, int maxSteine) throws NimException {
		super(anzReihen, maxSteine);
	}

	@Override
	public UndoableNim_GZ doMove(NimMove move) throws NimException {
		int index = move.getRow();
		int taken = rows[index] - move.getCount();
		super.doMove(move);
		moves.push(new NimMove(index, taken));
		return this;
	}

	public UndoableNim_GZ undo(int anzahlZuege) throws NimException {
		if (anzahlZuege > moves.size() || anzahlZuege < 0) {
			throw new NimException("invalid...");
		}
		NimMove move;
		int index;
		int taken;
		for (; anzahlZuege > 0; anzahlZuege--) {
			move = moves.pop();
			index = move.getRow();
			taken = move.getCount();
			xorOverAllRows ^= rows[index];
			xorOverAllRows ^= (rows[index] + taken);
			if (rows[index] == 0) {
				notEmptyRows++;
			}
			rows[index] += taken;
		}
		return this;
	}

	public UndoableNim_GZ reset() {
		return undo(moves.size());
	}
}
