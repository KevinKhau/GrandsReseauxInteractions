package tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrientedGraph extends Graph {

	public final static String NAME = "digraph";
	public final static String NEIGHBORS_SEPARATOR = "->";
	public LinkedList<OrientedVertex> vertices = new LinkedList<OrientedVertex>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}

	@Override
	Optional<OrientedVertex> containsVertex(int numberArg) {
		return (Optional<OrientedVertex>) Vertex.containsVertex(vertices, numberArg);
	}

	@Override
	OrientedVertex addVertex(int number) {
		Optional<OrientedVertex> v = containsVertex(number);
		if (!v.isPresent()) {
			OrientedVertex otherVertex = new OrientedVertex(number);
			vertices.add(otherVertex);
			return otherVertex;
		} else {
			return v.get();
		}
	}

	@Override
	void addVertices(int v1, int v2) {
		OrientedVertex vertex1 = addVertex(v1);
		OrientedVertex vertex2 = addVertex(v2);
		vertex1.addChild(vertex2);
		vertex2.addParent(vertex1);
	}

	@Override
	int getVerticesCount() {
		return vertices.size();
	}

	/**
	 * Obtenir le nombre d'arcs : chaque arc est comptabilisé deux fois : pour
	 * le sommet sortant, et celui entrant. Il suffit donc, pour tous les
	 * sommets, de ne prendre que les arcs entrants.
	 */
	@Override
	int getArcsCount() {
		return vertices.stream().mapToInt(v -> v.from.size()).sum();
	}

	/**
	 * @return Degré sortant maximum d'un sommet
	 */
	int getMaxOutArc() {
		return vertices.stream().max((v1, v2) -> Integer.compare(v1.to.size(), v2.to.size())).get().to.size();
	}

	/**
	 * @return Degré entrant maximum d'un sommet
	 */
	int getMaxInArc() {
		return vertices.stream().max((v1, v2) -> Integer.compare(v1.from.size(), v2.from.size())).get().from.size();
	}

	/**
	 * @return Valeur du plus haut sommet
	 */
	int getVertexMaxNumber() {
		return vertices.stream().max((v1, v2) -> Integer.compare(v1.number, v2.number)).get().number;
	}

	int getAccessibleNeighborsCount(int vertexNumber) {
		return vertices.stream().filter(v -> v.number == vertexNumber).findAny().get().getAccessibleNeighborsCount();
	}
	
	@Override
	void printStats() {
		ArrayList<Integer> stats = new ArrayList<>(Arrays.asList(new Integer[] {
				getVerticesCount(),
				getArcsCount(),
				getMaxOutArc(),
				getMaxInArc(),
				getVertexMaxNumber()
		}));
		System.out.println(stats.stream().map(Object::toString).collect(Collectors.joining(" ")));
	}

	@Override
	public String toString() {
		return "OrientedGraph [vertices=" + vertices + "\n]";
	}

}
