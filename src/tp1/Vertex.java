package tp1;

import java.util.List;
import java.util.Optional;

public abstract class Vertex {
	int number;
	
	static public Optional<? extends Vertex> containsVertex(List<? extends Vertex> l, int n) {
		for (Vertex v: l) {
			if (v!= null && v.number == n) {
				return Optional.of(v);
			}
		}
		return Optional.empty();
	}
	
	public Vertex(int n) {
		this.number = n;
	}
	
	public int getNumber() {
		return number;
	}
}
