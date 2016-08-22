package wbs.nim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nim {
	public static int DEFAULT_ROWS = 5;
	public static int MAX_TOKENS_PER_ROW = 10;
	private int[] rows;
	private int xorOverAllRows;
	private int notEmptyRows;

	private static Random rnd = new Random();

	public Nim(int reihen, int steine) {
		/*
		 * Erstelle ein Nim Spiel mit reihen Reihen und je Maximal steine
		 * Steinen
		 */
		if (reihen < 1 || steine < 1)
			throw new NimException("Ungültige Kombination von Reihen Steinen, beides muss größer als 0 sein.");
		rows = new int[reihen];
		notEmptyRows = reihen;
		for (int i = 0; i < reihen; i++) {
			rows[i] = rnd.nextInt(steine) + 1;
			xorOverAllRows ^= rows[i];
		}
	}

	public Nim() {
		this(DEFAULT_ROWS, MAX_TOKENS_PER_ROW);
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < rows.length; i++) {
			result.append(toBinaryString(rows[i]));
			result.append("  " + rows[i]);
			result.append("\n");
		}
		result.append("-------------------------------\n");
		result.append(toBinaryString(xorOverAllRows));
		result.append("  xorOverAllRows");
		return result.toString();
	}

	public static String toBinaryString(int zahl) {
		StringBuffer sb = new StringBuffer(32);
		for (int n = (1 << 31); n != 0; n >>>= 1) {
			sb.append((zahl & n) != 0 ? 1 : 0);
		}
		return sb.toString();
	}

	public NimMove suggestMove() throws NimException {
		if (isOver()) {
			throw new NimException("Das Spiel ist bereits vorbei, kein Vorschlag möglich!");
		}
		// für eine Zufällige Auswahl eines Zuges brauchen wir eine
		// Konstruktion von mehreren NimMove(s)
		List<NimMove> possibleMoves = new ArrayList<>();
		// sind wir in einer Win-Situation?
		boolean isWin = isWinSituation();
		// ja, dann bleib dort => liefer eine Situation in der xorOverAllRows ==
		// 0 ist
		int rowsCounter = -1;
		for (int value : rows) {
			rowsCounter++;
			if (value == 0)
				continue; // leere Reihen überspringen
			if (isWin) {
				if ((value & Integer.highestOneBit(xorOverAllRows)) > 0) {
					possibleMoves.add(new NimMove(rowsCounter, xorOverAllRows ^ value));
				}
			} else {
				for (int i = 1; i <= value; i++) {
					possibleMoves.add(new NimMove(rowsCounter, value - i));
				}
			}
		}
		int x = possibleMoves.size();

		return possibleMoves.get(rnd.nextInt(x));
	}

	public Nim doMove(NimMove move) {

		// prüfe auf gültigen Zug
		if (!isLegalMove(move))
			throw new NimException("Ihr Zug ist nicht gültig!");

		int row = move.getRow();
		int count = move.getCount();
		// ist noch ein Stein übrig ?
		if (count == 0) {
			// nein Spieler möchte alle Steine der Reihe nehmen
			notEmptyRows--;
		}
		// aktualisiere das rows Array
		// xorOverAllRows pflegen 1. lösche alten Inhalt
		xorOverAllRows ^= rows[row];
		// xorOverAllRows pflegen 2. setze neuen Inhalt
		xorOverAllRows ^= count;
		rows[row] = count;
		return this;
	}

	// count ist die Anzahl der Steine die in der Reihe bleiben
	// also nimm alle außer count
	public boolean isLegalMove(NimMove move) {
		Boolean result = false;
		int row = move.getRow();
		int count = move.getCount();
		// wurde eine gültige Reihe gewählt?
		// ist die Anzahl der noch verbleibenden Steine im gültigen Bereich?
		if (row >= 0 && row < rows.length && count >= 0 && count < this.rows[row]) {
			result = true;
		}
		return result;
	}

	public boolean isWinSituation() {
		return xorOverAllRows != 0;
	}

	public boolean isOver() {
		return notEmptyRows == 0;
	}

}
