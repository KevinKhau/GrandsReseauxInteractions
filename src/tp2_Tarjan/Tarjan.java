package tp2_Tarjan;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import tp1_Introduction.Graph;
import tp1_Introduction.Graph.GraphProvider;
import tp1_Introduction.OrientedGraph;
import tp1_Introduction.OrientedVertex;

public class Tarjan {

	List<StronglyConnectedComponent> components = new ArrayList<>();

	Stack<OrientedVertex> stack = new Stack<>();
	int count = 1;

	public Tarjan(OrientedGraph g) {
		g.vertices.stream().filter(v -> v != null && v.index == 0).forEach(v -> buildComponent(v));
	}

	/**
	 * @param v
	 *            Sommet de départ
	 */
	private void buildComponent(OrientedVertex v) {
		v.index = count;
		v.low = count;
		stack.push(v);
		count++;
		v.insideStack = true;
		v.to.values().stream().filter(w -> (w != null)).forEach(w -> {
			if (w.index == 0) {
				buildComponent(w);
				v.low = Math.min(v.low, w.low);
			} else if (w.insideStack) {
				v.low = Math.min(v.low, w.index);
			}
		});
		if (v.low == v.index) {
			components.add(new StronglyConnectedComponent(stack, v));
		}
	}

	public void printComponents() {
		System.out.println("Composantes fortement connexes : ");
		components.forEach(System.out::println);
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Attendu : 'java Tarjan [NomFichier.dot]'");
			System.exit(1);
		}
		Graph g = GraphProvider.loadDotFile(args[0]);
		if (!(g instanceof OrientedGraph)) {
			System.err.println("Seuls les graphes orientés sont traités pour le moment");
		}

		long startTime = System.nanoTime();

		Tarjan tarjan = new Tarjan((OrientedGraph) g);
		System.out.println(tarjan.components.size());

//		 tarjan.printComponents();
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println(duration + "ns");
		System.out.println(duration / 1000000 + "ms");
		System.out.println(duration / 1000000000 + "s");
	}

}
