package tp2_Tarjan;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

import tp1_Introduction.OrientedVertex;

/**
 * Ensemble de sommets qui forment une composante fortement connexe
 */
public class StronglyConnectedComponent extends LinkedList<OrientedVertex> {

	private static final long serialVersionUID = 1L;

	public StronglyConnectedComponent(Stack<OrientedVertex> stack, OrientedVertex limit) {
		try {
			OrientedVertex v = null;
			do {
				v = stack.pop();
				v.insideStack = false;
				this.add(v);
			} while (v != limit);
		} catch (EmptyStackException e) {
			System.err.println("Pile vide, impossible de retirer un élément");
		}
	}

	@Override
	public String toString() {
		return "StronglyConnectedComponent " + Arrays.toString(this.stream().map(v -> v.getNumber()).toArray(Integer[]::new));
	}

}
