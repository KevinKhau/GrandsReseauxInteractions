package tp1_Introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrientedVertex extends Vertex implements Cloneable {

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

	@Override
	public boolean remove(int k) {
		if (removed == false && getChildrenCount() < k) {
			removed = true;
			for (OrientedVertex parent : from) {
				parent.to.remove(this); // appel gourmand en O(n) au pire, alors qu'avec des indices, 0(1). Mais on aurait alors plein d'ArrayLists inutilement grands.
				parent.remove(k);
			}
			from.clear();
			to.clear();
			return true;
		} else {
			return false;
		}
	}

}
