package tp1_Introduction;

import tp1_Introduction.Graph.GraphProvider;

public class StatsDeBase {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Attendu : 'java StatsDeBase [NomFichier.dot]'");
			System.exit(1);
		}
		
		long startTime = System.nanoTime();
		
		Graph g = GraphProvider.loadDotFile(args[0]);
		// System.out.println(g);
		g.printStats();
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration + "ns");
		System.out.println(duration/1000000 + "ms");
		System.out.println(duration/1000000000 + "s");
	}

}
