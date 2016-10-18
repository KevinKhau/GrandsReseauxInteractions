package tp3_CoeurDense;

import java.nio.file.Path;
import java.nio.file.Paths;

import tp1_Introduction.OrientedGraph;

public class kcore extends OrientedGraph {
	
	public static void main(String[] args) {
		if (args.length < 3) {
			System.err.println("Attendu : 'java kcore [NomFichier.dot] [nbSommets] [fichierSortie]'");
			System.exit(1);
		}
		OrientedGraph g = (OrientedGraph) GraphProvider.loadDotFile(args[0]);
		if (!(g instanceof OrientedGraph)) {
			System.err.println("Seuls les graphes orientés sont traités pour le moment");
		}
		int k = 0;
		try {
			k = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("Attendu : Argument 2 entier");
			System.exit(1);
		}
		final Path p = Paths.get(args[2]);
		
//		long startTime = System.nanoTime();
		
		g.core(k);
		g.outputDotFile(p);

//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime);
//		System.out.println(duration + "ns");
//		System.out.println(duration / 1000000 + "ms");
//		System.out.println(duration / 1000000000 + "s");
	}

}
