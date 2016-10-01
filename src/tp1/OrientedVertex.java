package tp1;

import java.util.Arrays;
import java.util.HashSet;

public class OrientedVertex extends Vertex {

	public OrientedVertex(int n) {
		super(n);
	}

	HashSet<OrientedVertex> from = new HashSet<>(); // parents
	HashSet<OrientedVertex> to = new HashSet<>(); // enfants pointés

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
				+ Arrays.toString(to.stream().map(v -> v.number).toArray(Integer[]::new)) + "]";
	}

}
