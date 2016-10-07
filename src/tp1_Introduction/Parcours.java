package tp1_Introduction;

import tp1_Introduction.Graph.GraphProvider;

public class Parcours {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Attendu : 'java Parcours [NomFichier.dot] [n° sommet]'");
			System.exit(1);
		}
		int vertexNumber = Integer.parseInt(args[1]);
		long startTime = System.nanoTime();
		Graph g = GraphProvider.loadDotFile(args[0]);
//		System.out.println(g);
		g.printStats();
		System.out.println(g.getAccessibleNeighborsCount(vertexNumber));
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration + "ns");
		System.out.println(duration/1000000 + "ms");
	}

}
