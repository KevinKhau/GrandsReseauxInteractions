package tp1_Introduction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OrientedGraph extends Graph {

	public final static String NAME = "digraph";
	public final static String NEIGHBORS_SEPARATOR = "->";
	public List<OrientedVertex> vertices = new ArrayList<>();

	@Override
	String getNeighborsSeparator() {
		return NEIGHBORS_SEPARATOR;
	}

	@Override
	OrientedVertex retrieveOrCreate(int number) {
		OrientedVertex v = null;
		try {
			v = vertices.get(number);
		} catch (IndexOutOfBoundsException e) {
			// si number pas encore indexé
			OrientedVertex otherVertex = new OrientedVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		}
		if (v == null) {
			// vertices.get(number) a renvoyé null
			OrientedVertex otherVertex = new OrientedVertex(number);
			addToList(number, otherVertex);
			return otherVertex;
		} else { // number déjà indexé, récupération
			return v;
		}
	}

	@Override
	void addVertices(int v1, int v2) {
		OrientedVertex vertex1 = retrieveOrCreate(v1);
		OrientedVertex vertex2 = retrieveOrCreate(v2);
		vertex1.addChild(vertex2);
		vertex2.addParent(vertex1);
	}

	/**
	 * Ajouter un sommet à la liste, SANS DÉCALER d'autres éléments
	 * 
	 * @param index
	 * @param v
	 */
	private void addToList(int index, OrientedVertex v) {
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
		return (int) vertices.stream().filter(v -> v != null).count();
	}
	
	@Override
	public int getActiveVerticesCount() {
		return (int) vertices.stream().filter(v -> v != null && !v.removed).count();
	}

	@Override
	public int getArcsCount() {
		return vertices.stream().filter(v -> v != null).mapToInt(v -> v.from.size()).sum();
	}

	/**
	 * @return Degré sortant maximum d'un sommet
	 */
	int getMaxOutArc() {
		return vertices.stream().filter(v -> v != null).max((v1, v2) -> Integer.compare(v1.to.size(), v2.to.size()))
				.get().to.size();
	}

	/**
	 * @return Degré entrant maximum d'un sommet
	 */
	int getMaxInArc() {
		return vertices.stream().filter(v -> v != null).max((v1, v2) -> Integer.compare(v1.from.size(), v2.from.size()))
				.get().from.size();
	}

	@Override
	int getVertexMaxNumber() {
		return vertices.size() - 1; // Logiquement, la dernière valeur du
									// tableau devrait être la plus haute
		// return vertices.stream().filter(v -> v != null).max((v1, v2) ->
		// Integer.compare(v1.number, v2.number)).get().number;
	}

	int getAccessibleNeighborsCount(int vertexNumber) {
		OrientedVertex v = vertices.get(vertexNumber);
		return v == null ? -1 : v.getAccessibleNeighborsCount();
	}

	@Override
	void printStats() {
		ArrayList<Integer> stats = new ArrayList<>(Arrays.asList(new Integer[] { getVerticesCount(), getArcsCount(),
				getMaxOutArc(), getMaxInArc(), getVertexMaxNumber() }));
		System.out.println(stats.stream().map(Object::toString).collect(Collectors.joining(" ")));
	}

	@Override
	public String toString() {
		return "OrientedGraph [vertices=" + vertices + "\n]";
	}

	@Override
	public void outputDotFile(Path path) {
		List<String> out = new LinkedList<>();
		String fileName = path.getFileName().toString();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
		out.add(NAME + " " + fileName + " {");
		vertices.stream().filter(v -> v != null).forEach(v -> {
			if (v.to.isEmpty()) {
				out.add(String.valueOf(v.number) + " ;");
			} else {
				out.addAll(v.to.values().stream()
						.map(son -> v.number + " " + NEIGHBORS_SEPARATOR + " " + String.valueOf(son.number) + " ;")
						.collect(Collectors.toList()));
			}
		});
		out.add("}");
		System.out.println("Fichier " + path + " de " + out.size() + " lignes généré");
		try {
			Files.write(path, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void core(int k) {
		for (int i = 0; i < vertices.size(); i++) {
			OrientedVertex v = vertices.get(i);
			if (v == null) {
				continue;
			}
			if (v.removed) {
				vertices.set(i, null);
			} else if (v.remove(k)) {
				vertices.set(i, null);
			}
		}
	}

	@Override
	public void renumber() {
		int count = 0;
		for (OrientedVertex v : vertices) {
			if (v != null && !v.removed) {
				v.number = count;
				count++;
			}
		}
	}

	@Override
	public void rearrange() {
		List<OrientedVertex> res = new ArrayList<>(vertices.size());
		List<OrientedVertex> old = vertices;
		vertices = res;
		old.stream().filter(v -> v != null && !v.removed).forEach(v -> addToList(v.number, v));
		vertices.stream().forEach(OrientedVertex::rearrange);
	}

}
