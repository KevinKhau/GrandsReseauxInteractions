package tp2_Tarjan;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

import tp1_Introduction.OrientedVertex;

/**
 * Ensemble de sommets qui forment une composante fortement connexe
 */
public class StronglyConnectedComponent extends ArrayList<OrientedVertex> {

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
		String res = "StronglyConnectedComponent, total of " + this.size() + " vertices :";
		for (OrientedVertex v : this) {
			res += v.toString();
		}
		return res;
	}

}
