package tp1_Introduction;

import java.util.List;
import java.util.Optional;

public abstract class Vertex {
	int number;
	boolean alreadyVisited = false;

	public Integer index = null;
	public Integer low = null;
	public boolean insideStack;

	abstract int getAccessibleNeighborsCount();

	static public Optional<? extends Vertex> containsVertex(List<? extends Vertex> l, int n) {
		for (Vertex v : l) {
			if (v != null && v.number == n) {
				return Optional.of(v);
			}
		}
		return Optional.empty();
	}

	public Vertex(int n) {
		this.number = n;
	}

	public int getNumber() {
		return number;
	}

	/**
	 * Pour l'algorithme de Tarjan. Vérifier index suffit, puisqu'à
	 * l'initialisation, index et low sont tous les deux modifiés
	 */
	public boolean isInitialized() {
		return index != null;
	}

}
