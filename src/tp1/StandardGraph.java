package tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

public class StandardGraph extends Graph {
	
	public final static String NAME = "graph";
	public final static String NEIGHBORS_SEPARATOR = "--";
	public LinkedList<StandardVertex> vertices = new LinkedList<StandardVertex>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}
	
	@SuppressWarnings("unchecked")
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
		return vertices.stream().mapToInt(v -> v.neighbors.size()).sum() / 2;
	}

	@Override
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
				getVertexMaxNumber()
		}));
		System.out.println(stats.stream().map(Object::toString).collect(Collectors.joining(" ")));
	}
	
}
