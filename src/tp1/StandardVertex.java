package tp1;

import java.util.HashSet;

public class StandardVertex extends Vertex {
	
	public StandardVertex(int n) {
		super(n);
	}

	HashSet<StandardVertex> neighbors = new HashSet<>();
	
	public void addNeighbor(StandardVertex v) {
		neighbors.add(v);
	}
	
}
