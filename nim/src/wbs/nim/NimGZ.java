package wbs.nim;

import java.util.Random;

public class NimGZ {

	public static final int DEFAULT_ROWS = 5;
	public static final int MAX_TOKENS_PER_ROW = 10;

	private int[] rows;
	private int notEmptyRows;
	private int xorOverAllRows;

	private static Random random = new Random();

	public NimGZ() {
		this(DEFAULT_ROWS, MAX_TOKENS_PER_ROW);
	}

	public NimGZ(int numberOfRows, int max_tokens_per_row) throws NimException {
		if (numberOfRows < 1 || max_tokens_per_row < 1) {
			throw new NimException("invalid ...");
		}
		rows = new int[numberOfRows];
		notEmptyRows = numberOfRows;
		for (int i = 0; i < rows.length; i++) {
			rows[i] = random.nextInt(max_tokens_per_row) + 1;
			xorOverAllRows ^= rows[i];
		}
	}

	/*
	 * das spiel soll nicht deterministisch verlaufen. gibt es in einer stellung
	 * mehrere mögliche züge, soll irgendeiner ausgewählt werden (zufallsprinzip).
	 * 
	 * in einer gewinnstellung soll ein optimaler zugvorschlag gemacht werden
	 */
	public NimMove suggestMove() throws NimException {
		if (isOver()) {
			throw new NimException("game over...");
		}
		NimMove nimMove = null;
		// wir beginnen mit der suche bei irgendeiner reihe
		// wenn wir bis zum ende nichts gefunden haben, setzen
		// wir die suche am anfang fort
		int pos = random.nextInt(rows.length);

		if (isWinSituation()) { // gewinnstellung
			int highestOneBit = Integer.highestOneBit(xorOverAllRows);
			for (; (highestOneBit & rows[pos]) == 0; pos = (pos + 1)
					% rows.length) {
				// empty body
			}

			nimMove = new NimMove(pos, rows[pos] ^ xorOverAllRows);

		} else { // verluststellung
			for (; rows[pos] == 0; pos = (pos + 1) % rows.length) {
				// empty body
			}
			nimMove = new NimMove(pos, random.nextInt(rows[pos]));
		}
		return nimMove;

	}

	// prüfe, ob der zug legal ist
	// aktualisiere rows[]
	// aktualisiere xorOverAllRows
	// aktualisiere ggf notEmptyRows

	// das neue bitmuster in xorOverAllRows erhält man durch
	// xor-verknüpfung mit der alten anzahl der spielsteine
	// und anschliessende xor-verknüpfung mit der neuen anzahl von spielsteinen
	public NimGZ doMove(NimMove move) throws NimException {
		// check precondition
		if (!isLegalMove(move)) {
			throw new NimException("not a legal move...");
		}
		int row = move.getRow();
		int count = move.getCount();
		if (count == 0) {
			notEmptyRows--;
		}
		xorOverAllRows ^= rows[row];
		xorOverAllRows ^= count;
		rows[row] = count;
		return this;
	}

	public boolean isOver() {
		return notEmptyRows == 0;
	}

	// count ist die anzahl der steine, die nach dem zug in der reihe
	// bleiben (nicht die anzahl der steine, die aus der reihe genommen werden)
	public boolean isLegalMove(NimMove move) {
		int row = move.getRow();
		int count = move.getCount();
		return (row >= 0 && row < rows.length && count >= 0 && count < rows[row]);
	}

	public boolean isWinSituation() {
		return xorOverAllRows != 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows.length; i++) {
			sb.append(int2String(rows[i]) + "  " + rows[i]);
			sb.append("\n");
		}
		sb.append("--------------------------------\n");
		sb.append(int2String(xorOverAllRows) + "  xorOverAllRows");
		return sb.toString();
	}

	private String int2String(int zahl) {
		StringBuilder sb = new StringBuilder(32);
		for (int n = (1 << 31); n != 0; n >>>= 1) {
			sb.append((zahl & n) == 0 ? 0 : 1);
		}
		return sb.toString();
	}

}
