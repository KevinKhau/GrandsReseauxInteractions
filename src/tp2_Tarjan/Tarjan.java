package tp2_Tarjan;

import java.util.LinkedList;
import java.util.Stack;

import tp1_Introduction.Graph;
import tp1_Introduction.OrientedGraph;
import tp1_Introduction.OrientedVertex;
import tp1_Introduction.Graph.GraphProvider;

public class Tarjan {

	LinkedList<StronglyConnectedComponent> components = new LinkedList<>();

	Stack<OrientedVertex> stack = new Stack<>();
	int count = 0;

	public Tarjan(OrientedGraph g) {
		for (OrientedVertex v : g.vertices) {
			if (v.index == null) {
				buildComponent(v);
			}
		}
	}

	/**
	 * @param v
	 *            Sommet de départ
	 */
	private void buildComponent(OrientedVertex v) {
		// if (u == null) {
		// System.err.println("Sommet u null. Étrange.");
		// throw new NullPointerException();
		// }
		v.index = count;
		v.low = count;
		stack.push(v);
		count++;
		v.insideStack = true;
		for (OrientedVertex w : v.to) {
			if (w.index == null) {
				buildComponent(w);
				v.low = Math.min(v.low, w.low);
			} else if (w.insideStack) {
				v.low = Math.min(v.low, w.index);
			}
		}
		if (v.low.equals(v.index)) {
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
		Tarjan tarjan = new Tarjan((OrientedGraph) g);
		System.out.println(tarjan.components.size());
//		tarjan.printComponents();
	}

}