package tp1_Introduction;

import java.util.ArrayList;

public class StandardVertex extends Vertex {
	
	public StandardVertex(int n) {
		super(n);
	}

	ArrayList<StandardVertex> neighbors = new ArrayList<>();
	
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

	@Override
	public boolean remove(int k) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void rearrange() {
		// TODO Auto-generated method stub
		
	}
	
}
