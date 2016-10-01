package tp1;

import java.util.LinkedList;
import java.util.Optional;

public class StandardGraph extends Graph {
	
	public final static String NAME = "graph";
	public final static String NEIGHBORS_SEPARATOR = "--";
	public LinkedList<StandardVertex> vertices = new LinkedList<StandardVertex>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}
	
	@Override
	Optional<StandardVertex> containsVertex(int numberArg) {
		return (Optional<StandardVertex>) Vertex.containsVertex(vertices, numberArg);
	}

	@Override
	StandardVertex addVertex(int number) {
		Optional<StandardVertex> v = containsVertex(number);
		if (!v.isPresent()) {
			StandardVertex otherVertex = new StandardVertex(number); 
			vertices.add(otherVertex);
			return otherVertex;
		} else {
			return v.get();
		}
		
	}

	@Override
	void addVertices(int v1, int v2) {
		StandardVertex vertex1 = addVertex(v1);
		StandardVertex vertex2 = addVertex(v2);
		vertex1.addNeighbor(vertex2);
		vertex2.addNeighbor(vertex1);
	}
	
	@Override
	int getVerticesCount() {
		return vertices.size();
	}
	
	@Override
	int getArcsCount() {
		int count = 0;
		for (StandardVertex v : vertices) {
			count += v.neighbors.size();
		}
		return count/2;
	}

	@Override
	int getVertexMaxNumber() {
		StandardVertex res = vertices.stream().max((v1, v2) -> Integer.compare(v1.number, v2.number)).get();
		return res.number;
	}

	@Override
	void printStats() {
		System.out.print(getVerticesCount());
		System.out.print(" ");
		System.out.print(getArcsCount());
		System.out.print(" ");
		System.out.print(getVertexMaxNumber());
	}
	
}
