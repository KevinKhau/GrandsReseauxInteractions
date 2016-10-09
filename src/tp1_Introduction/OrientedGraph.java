package tp1_Introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrientedGraph extends Graph {

	public final static String NAME = "digraph";
	public final static String NEIGHBORS_SEPARATOR = "->";
	public List<OrientedVertex> vertices = new ArrayList<>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}

	@Override
	OrientedVertex retrieveOrCreate(int number) {
		OrientedVertex v = null;
		try {
			v = vertices.get(number);
		} catch (IndexOutOfBoundsException e) {
			// si number pas encore indexé
			OrientedVertex otherVertex = new OrientedVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		}
		if (v == null) {
			// vertices.get(number) a renvoyé null
			OrientedVertex otherVertex = new OrientedVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		} else { // number déjà indexé, récupération
			return v;
		}
	}

	@Override
	void addVertices(int v1, int v2) {
		OrientedVertex vertex1 = retrieveOrCreate(v1);
		OrientedVertex vertex2 = retrieveOrCreate(v2);
		vertex1.addChild(vertex2);
		vertex2.addParent(vertex1);
	}

	/**
	 * Ajouter un sommet à la liste, SANS DÉCALER d'autres éléments
	 * @param index
	 * @param v
	 */
	private void addToList(int index, OrientedVertex v) {
		if (vertices.size() <= index) {
			resizeList(index);
		}
		vertices.set(index, v);
	}

	@Override
	protected void resizeList(int until) {
		for (int i = vertices.size() - 1; i < until; i++) {
			vertices.add(null);
		}
	}

	@Override
	int getVerticesCount() {
		return (int) vertices.stream().filter(v -> v != null).count();
	}

	@Override
	int getArcsCount() {
		return vertices.stream().filter(v -> v != null).mapToInt(v -> v.from.size()).sum();
	}

	/**
	 * @return Degré sortant maximum d'un sommet
	 */
	int getMaxOutArc() {
		return vertices.stream().filter(v -> v != null).max((v1, v2) -> Integer.compare(v1.to.size(), v2.to.size())).get().to.size();
	}

	/**
	 * @return Degré entrant maximum d'un sommet
	 */
	int getMaxInArc() {
		return vertices.stream().filter(v -> v != null).max((v1, v2) -> Integer.compare(v1.from.size(), v2.from.size())).get().from.size();
	}

	@Override
	int getVertexMaxNumber() {
		return vertices.size() - 1; // Logiquement, la dernière valeur du tableau devrait être la plus haute
//		return vertices.stream().filter(v -> v != null).max((v1, v2) -> Integer.compare(v1.number, v2.number)).get().number;
	}

	int getAccessibleNeighborsCount(int vertexNumber) {
		OrientedVertex v = vertices.get(vertexNumber);
		return v == null ? -1 : v.getAccessibleNeighborsCount();
	}

	@Override
	void printStats() {
		ArrayList<Integer> stats = new ArrayList<>(Arrays.asList(new Integer[] { getVerticesCount(), getArcsCount(),
				getMaxOutArc(), getMaxInArc(), getVertexMaxNumber() }));
		System.out.println(stats.stream().map(Object::toString).collect(Collectors.joining(" ")));
	}

	@Override
	public String toString() {
		return "OrientedGraph [vertices=" + vertices + "\n]";
	}

}
