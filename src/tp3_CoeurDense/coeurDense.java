package tp3_CoeurDense;

import tp1_Introduction.Graph;
import tp1_Introduction.OrientedGraph;
import tp1_Introduction.Graph.GraphProvider;
import tp2_Tarjan.Tarjan;

public class coeurDense {

	OrientedGraph graph;

	double maxDensity = 0;
	int maxK = 0;

	public coeurDense(OrientedGraph g) {
		graph = g;
	}

	/**
	 * Calcule la densité maximale sur tous les k-cores possibles du graphe
	 * 
	 * @return a densité maximale
	 */
	public double calculate() {
		maxDensity = 0;
		int k = 0;
		while (true) {
			double nbActiveVertices = (double) graph.getActiveVerticesCount();
			if (nbActiveVertices == 0) {
				return maxDensity;
			}
			double density = (double) graph.getArcsCount() / nbActiveVertices;
			if (maxDensity < density) {
				maxDensity = density;
				maxK = k;
			}
			k++;
			graph.core(k);
		}
	}

	@Override
	public String toString() {
		return maxK + " " + maxDensity;
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Attendu : 'java coeurDense [NomFichier.dot]'");
			System.exit(1);
		}

		Graph g = GraphProvider.loadDotFile(args[0]);
		if (!(g instanceof OrientedGraph)) {
			System.err.println("Seuls les graphes orientés sont traités pour le moment");
		}

//		long startTime = System.nanoTime();

		coeurDense cd = new coeurDense((OrientedGraph) g);
		cd.calculate();
		System.out.println(cd);

//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime);
//		System.out.println(duration + "ns");
//		System.out.println(duration / 1000000 + "ms");
//		System.out.println(duration / 1000000000 + "s");
	}
}
