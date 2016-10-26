package tp1_Introduction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrientedVertex extends Vertex {

	public OrientedVertex(int n) {
		super(n);
	}

	/**
	 * <p>
	 * On abandonne ici les ArrayList, parce que les valeurs seraient très
	 * éparses et on utiliserait beaucoup de mémoire alors qu'on associerait
	 * null à la plupart des indices.
	 * </p>
	 * <p>
	 * À la place, on utilise des Maps, puisqu'on peut y placer des indices très
	 * éloignés, et que .get() est en 0(1), pareil pour remove() donc.
	 * Tandis qu'avec une List, remove() est en 0(n) au pire, très gênant pour
	 * calculer le k-core.
	 * </p>
	 */
	Map<Integer, OrientedVertex> from = new TreeMap<>(); // parents
	public Map<Integer, OrientedVertex> to = new TreeMap<>(); // enfants pointés

	void addParent(OrientedVertex v) {
		from.put(v.number, v);
	}

	void addChild(OrientedVertex v) {
		to.put(v.number, v);
	}

	@Override
	public String toString() {
		return "\nOrientedVertex [number=" + number + ", from=" + from.keySet().toString() + ", to="
				+ to.keySet().toString() + ", index=" + index + ", low=" + low + ", removed=" + removed + "]";
	}

	public String printRaw() {
		return "\nOrientedVertex [number=" + number + ", from=" + from.keySet().toString() + ", to="
				+ to.keySet().toString() + "]";
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
		for (OrientedVertex child : to.values()) {
			if (!child.alreadyVisited) {
				count += child.getAccessibleNeighborsCount();
			}
		}
		return count;
	}

	@Override
	public boolean remove(int k) {
		for (Iterator<Map.Entry<Integer, OrientedVertex>> it = from.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Integer, OrientedVertex> entry = it.next();
			if (entry.getValue().removed) {
				it.remove();
			}
		}
		if (removed == false && getChildrenCount() < k) {
			removed = true;
			for (Map.Entry<Integer, OrientedVertex> entry : from.entrySet()) {
				OrientedVertex parent = entry.getValue();
				parent.to.remove(this.number);
				parent.remove(k);
			}
			from.clear();
			to.clear();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void rearrange() {
		Map<Integer, OrientedVertex> tmp = new TreeMap<>();
		from.values().stream().filter(v -> v != null && !v.removed).forEach(v -> tmp.put(v.number, v));
		from = tmp;

		Map<Integer, OrientedVertex> tmp2 = new TreeMap<>();
		to.values().stream().filter(v -> v != null && !v.removed).forEach(v -> tmp2.put(v.number, v));
		to = tmp2;
	}

}
