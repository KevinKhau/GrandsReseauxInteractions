package tp1;

import java.util.LinkedList;
import java.util.Optional;

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

	@Override
	int getArcsCount() {
		int count = 0;
		for (OrientedVertex v : vertices) {
			count += v.from.size() + v.to.size();
		}
		return count/2;
	}
	
	/**
	 * @return Degré sortant maximum d'un sommet
	 */
	int getMaxOutArc() {
		OrientedVertex res = vertices.stream().max((v1, v2) -> Integer.compare(v1.to.size(), v2.to.size())).get();
		return res.to.size();
	}
	
	/**
	 * @return Degré entrant maximum d'un sommet
	 */
	int getMaxInArc() {
		OrientedVertex res = vertices.stream().max((v1, v2) -> Integer.compare(v1.from.size(), v2.from.size())).get();
		return res.from.size();
	}

	/**
	 * @return Valeur du plus haut sommet
	 */
	int getVertexMaxNumber() {
		OrientedVertex res = vertices.stream().max((v1, v2) -> Integer.compare(v1.number, v2.number)).get();
		return res.number;
	}
	
	@Override
	void printStats() {
		System.out.print(getVerticesCount());
		System.out.print(" ");
		System.out.print(getArcsCount());
		System.out.print(" ");
		System.out.print(getMaxOutArc());
		System.out.print(" ");
		System.out.print(getMaxInArc());
		System.out.print(" ");
		System.out.print(getVertexMaxNumber());
	}
	
	@Override
	public String toString() {
		return "OrientedGraph [vertices=" + vertices + "\n]";
	}
	
}
