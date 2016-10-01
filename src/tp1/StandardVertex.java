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

	int getNeighborsCount() {
		return neighbors.size();
	}
	
	@Override
	int getAccessibleNeighborsCount() {
		int count = 0;
		if (!alreadyVisited) {
			alreadyVisited = true;
			count++;
		}
		for (StandardVertex child : neighbors) {
			if (!child.alreadyVisited) {
				count += child.getAccessibleNeighborsCount();
			}
		}
		return count;
	}
	
}
