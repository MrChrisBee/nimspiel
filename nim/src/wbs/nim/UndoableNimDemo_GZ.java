package wbs.nim;

// RETROANALYSE IS FUN :)

public class UndoableNimDemo_GZ {

	public static void main(String[] args) {
		UndoableNimCB nim = new UndoableNimCB();
		System.out.println("ausgangsstellung");
		System.out.println(nim);
		System.out.println();
		System.out.println("stellung nach dem ersten zug");
		System.out.println(nim.doMove(nim.suggestMove()));
		System.out.println();
		System.out.println("stellung nach dem zweiten zug");
		System.out.println(nim.doMove(nim.suggestMove()));
		System.out.println();
		System.out.println("stellung nach dem dritten zug");
		System.out.println(nim.doMove(nim.suggestMove()));
		System.out.println();
		// wir nehmen die letzten beiden zuege zurück
		nim.undo(2);
		System.out.println("stellung nach der rücknahme der letzten beiden züge\n");
		System.out.println(nim);
		System.out.println();
		
		
		// wir spielen bis zum ende
		while (!nim.isOver()) {
			nim.doMove(nim.suggestMove());
			System.out.println(nim);
			System.out.println();
		}
		System.out.println("schlussstellung");
		System.out.println(nim);
		System.out.println();
		System.out.println("wir nehmen alle züge zurück");
		System.out.println("und sind wieder bei der ausgangsstellung :)");
		nim.reset();
		System.out.println("****************************");
		System.out.println(nim);
	}
}
