package tp1_Introduction;

public abstract class Vertex {
	int number;
	boolean alreadyVisited = false;

	public int index = 0;
	public int low = 0;
	public boolean insideStack;

	abstract int getAccessibleNeighborsCount();

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
		return index != 0;
	}

}
