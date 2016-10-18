package tp1_Introduction;

public abstract class Vertex {
	int number;
	boolean alreadyVisited = false;

	public int index = 0;
	public int low = 0;
	public boolean insideStack;
	
	public boolean removed = false;

	abstract int getAccessibleNeighborsCount();
	
	/**
	 * Pour k-core. Retirera les sommets dont le nombre d'arcs entrants est strictement inférieur à k.
	 * @param k 
	 * @return true si le sommet a été retiré
	 */
	public abstract boolean remove(int k);

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
	
	/**
	 * Réorganise les données des voisins pour que les indices correspondent bien aux numéros de sommet
	 */
	public abstract void rearrange();

}
