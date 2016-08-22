package wbs.nim;

class Spieler extends Thread {
	private long counter = 0;
	private NimGZ spiel;

	public long getCounter() {
		return counter;
	}

	public Spieler(NimGZ spiel) {
		this.spiel = spiel;
	}

	public void run() {
		while (!spiel.isOver()) {
			synchronized (spiel) {
				if (!spiel.isOver()){
					spiel.doMove(spiel.suggestMove());	
				}
			}
			counter++;
		}
	}
}

public class NimThreadDemo {
	/*
	 * wir erzeugen ein sehr grosses nim-objekt.
	 * (min. hunderttausende von reihen, jede reihe hunderttausende von
	 * steinen).
	 * dann erzeugen wir 3 threads. jeder thread bekommt eine referenz auf
	 * dieses nim-objekt.
	 * dann starten wir die threads. die threads ziehen so lange, bis das
	 * spiel beendet ist.
	 * am ende geben wir aus, wie viele züge jeder thread gemacht hat.
	 * die klasse Nim selbst. soll nicht verändert werden.
	 */

	public static void main(String[] args) throws InterruptedException {
		NimGZ spiel = new NimGZ(100_000, 100_000);
		Spieler spieler1 = new Spieler(spiel);
		Spieler spieler2 = new Spieler(spiel);
		Spieler spieler3 = new Spieler(spiel);

		spieler1.start();
		spieler2.start();
		spieler3.start();

		try {
			spieler1.join();
			spieler2.join();
			spieler3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (spiel.isOver()) {
			System.out.println("Ende des Spiels");
			System.out.println("Anzahl Züge Spieler 1: "
					+ spieler1.getCounter());
			System.out.println("Anzahl Züge Spieler 2: "
					+ spieler2.getCounter());
			System.out.println("Anzahl Züge Spieler 3: "
					+ spieler3.getCounter());
		}
	}
}
