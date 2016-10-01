package tp1;

import tp1.Graph.GraphProvider;

public class Parcours {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Attendu : 'java Parcours [NomFichier.dot] [n° sommet]'");
			System.exit(1);
		}
		int vertexNumber = Integer.parseInt(args[1]);
		Graph g = GraphProvider.loadDotFile(args[0]);
//		System.out.println(g);
		System.out.println(g.getAccessibleNeighborsCount(vertexNumber));
	}

}
