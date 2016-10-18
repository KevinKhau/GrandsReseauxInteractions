package tp1_Introduction;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StandardGraph extends Graph {
	
	public final static String NAME = "graph";
	public final static String NEIGHBORS_SEPARATOR = "--";
	public ArrayList<StandardVertex> vertices = new ArrayList<>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}
	
	@Override
	StandardVertex retrieveOrCreate(int number) {
		StandardVertex v = null;
		try {
			v = vertices.get(number);
		} catch (IndexOutOfBoundsException e) {
			// si number pas encore indexé
			StandardVertex otherVertex = new StandardVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		}
		if (v == null) {
			// vertices.get(number) a renvoyé null
			StandardVertex otherVertex = new StandardVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		} else { // number déjà indexé, récupération
			return v;
		}
		
	}

	@Override
	void addVertices(int v1, int v2) {
		StandardVertex vertex1 = retrieveOrCreate(v1);
		StandardVertex vertex2 = retrieveOrCreate(v2);
		vertex1.addNeighbor(vertex2);
		vertex2.addNeighbor(vertex1);
	}
	
	/**
	 * Ajouter un sommet à la liste, SANS DÉCALER d'autres éléments
	 * @param index
	 * @param v
	 */
	private void addToList(int index, StandardVertex v) {
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
	public int getVerticesCount() {
		return vertices.size();
	}
	
	@Override
	public int getArcsCount() {
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

	@Override
	public int getActiveVerticesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renumber() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rearrange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void outputDotFile(Path path) {
		// TODO Auto-generated method stub
		
	}

}
