package wbs.nim;

// RETROANALYSE IS FUN
/*
 * 1. Schritt: ggf. ein Randomfeld zur Generierung von Zufallszahlen
 * 2. Schritt: Konstruktor mit 2 int- Parametern implementieren
 * 3. Schritt: toString() überschreiben	
 * 4. Schritt: ggf. Hilfsmethode zur binären Darstellung eines int mit 32 Stellen
 */
public class NimDemo {
	public static void main(String[] args) {
		NimGZ nim1 = new NimGZ(100_000, 100_000);
		int i=0;
	//	Nim nim2 = new Nim(5, 10);

		
//		NimMove nm1 = new NimMove(2, 1);
//		System.out.println();
//		nim1.doMove(nm1);
//		System.out.println(nim1);
		while (! nim1.isOver()) {
			//System.out.println(nim1);
			nim1.doMove(nim1.suggestMove());
			System.out.print(i++ + ":");
			if(i%15 == 0)
				System.out.println();
		}
		System.out.println("Fertig");
	}
}
