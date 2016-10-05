package tp1;

import tp1.Graph.GraphProvider;

public class StatsDeBase {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Attendu : 'java StatsDeBase [NomFichier.dot]'");
			System.exit(1);
		}
		Graph g = GraphProvider.loadDotFile(args[0]);
//		System.out.println(g);
		g.printStats();
	}

}
