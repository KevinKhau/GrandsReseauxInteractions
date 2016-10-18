package tp3_CoeurDense;

import java.nio.file.Path;
import java.nio.file.Paths;

import tp1_Introduction.OrientedGraph;

public class kcore extends OrientedGraph {
	
	public static void main(String[] args) {
		if (args.length < 3 || (args.length >= 4 && !args[0].equals("-r"))) {
			System.err.println("Attendu : 'java kcore (-r) [NomFichier.dot] [nbSommets] [fichierSortie]'");
			System.exit(1);
		}
		
		int nbOptionsBefore = 0;
		if (args[0].equals("-r")) {
			nbOptionsBefore++;
		}
		
		OrientedGraph g = (OrientedGraph) GraphProvider.loadDotFile(args[nbOptionsBefore + 0]);
		if (!(g instanceof OrientedGraph)) {
			System.err.println("Seuls les graphes orientés sont traités pour le moment");
		}
		int k = 0;
		try {
			k = Integer.parseInt(args[nbOptionsBefore + 1]);
		} catch (NumberFormatException e) {
			System.err.println("Attendu : Argument 2 entier");
			System.exit(1);
		}
		final Path p = Paths.get(args[nbOptionsBefore + 2]);
		
//		long startTime = System.nanoTime();
		
		g.core(k);
		if (args[0].equals("-r")) {
			g.renumber();
			g.rearrange();
		}
		g.outputDotFile(p);

//		long endTime = System.nanoTime();
//		long duration = (endTime - startTime);
//		System.out.println(duration + "ns");
//		System.out.println(duration / 1000000 + "ms");
//		System.out.println(duration / 1000000000 + "s");
	}

}
