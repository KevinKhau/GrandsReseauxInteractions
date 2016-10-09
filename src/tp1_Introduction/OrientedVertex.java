package tp1_Introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrientedVertex extends Vertex {

	public OrientedVertex(int n) {
		super(n);
	}

	List<OrientedVertex> from = new ArrayList<>(); // parents
	public List<OrientedVertex> to = new ArrayList<>(); // enfants pointés

	void addParent(OrientedVertex v) {
		from.add(v);
	}

	void addChild(OrientedVertex v) {
		to.add(v);
	}

	@Override
	public String toString() {
		return "\nOrientedVertex [number=" + number + ", from="
				+ Arrays.toString(from.stream().map(v -> v.number).toArray(Integer[]::new)) + ", to="
				+ Arrays.toString(to.stream().map(v -> v.number).toArray(Integer[]::new)) + ", index=" + index
				+ ", low=" + low + "]";
	}

	public int getChildrenCount() {
		return to.size();
	}

	/**
	 * Parcours en profondeur
	 */
	@Override
	int getAccessibleNeighborsCount() {
		int count = 0;
		if (!alreadyVisited) {
			alreadyVisited = true;
			count++;
		}
		for (OrientedVertex child : to) {
			if (!child.alreadyVisited) {
				count += child.getAccessibleNeighborsCount();
			}
		}
		return count;
	}

}
