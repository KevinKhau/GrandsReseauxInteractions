package tp1_Introduction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Graph {

	private final static int MAX_NUMBERS_PER_LINE = 2;

	protected HashSet<Integer> numbers = new HashSet<>();

	/**
	 * Obtenir un sommet, soit déjà existant et indexé, soit un nouveau indexé
	 * dans la méthode-même
	 * 
	 * @param vertex
	 *            numéro du sommet
	 * @return Le sommet obtenu, ou créé
	 */
	abstract Vertex retrieveOrCreate(int vertex);

	/**
	 * Ajoute au graphe deux sommets, sans dupliquer, et créée entre eux des
	 * liens de voisinage
	 * 
	 * @param v1
	 * @param v2
	 */
	abstract void addVertices(int v1, int v2);

	/**
	 * Redimensionne la liste si pas assez de mémoire allouée
	 * 
	 * @param until
	 *            taille incluse jusqu'à laquelle redimensionnée
	 */
	abstract protected void resizeList(int until);

	abstract String getNeighborsSeparator();

	public abstract int getVerticesCount();

	/**
	 * Obtenir le nombre de sommets qui ne sont pas marqués
	 * {@link Vertex#removed}
	 */
	public abstract int getActiveVerticesCount();

	/**
	 * Obtenir le nombre d'arcs : chaque arc est comptabilisé deux fois : pour
	 * les deux sommets qu'il lie. Il suffit donc, pour tous les sommets, de ne
	 * prendre que les arcs entrants.
	 */
	public abstract int getArcsCount();

	/**
	 * @return Valeur du plus haut sommet
	 */
	abstract int getVertexMaxNumber();

	abstract void printStats();

	/**
	 * Nombre de sommets accessibles, en itérant, donc descendants inclus
	 * 
	 * @param vertexNumber
	 *            Numéro du sommet de départ
	 */
	abstract int getAccessibleNeighborsCount(int vertexNumber);

	/**
	 * Renumérote les valeurs de 0 à n-1 s'il y a n sommets, au cas où il y
	 * aurait des valeurs manquantes. Les numéros des sommets ne correspondent
	 * alors plus aux indices des List et Map dans lesquels ils avaient été
	 * stockés.
	 */
	public abstract void renumber();

	/**
	 * Réarranger les sommets pour s'assurer qu'à chaque indice, le sommet avec
	 * le même numéro est associé Les sommets marqués "removed" sont totalement
	 * supprimés et deviennent inaccessibles.
	 */
	public abstract void rearrange();

	public abstract void outputDotFile(Path path);

	public final static class GraphProvider {

		Graph graph = null;

		public static boolean checkExtension(String path, String expected) {
			String extension = "";
			int i = path.lastIndexOf('.');
			if (i > 0) {
				extension = path.substring(i + 1);
			}
			if (!extension.equals(expected)) {
				System.err.println("Format de fichier invalide ('" + expected + "' attendu)");
				return false;
			}
			return true;
		}

		public static Graph loadDotFile(String path) {
			checkExtension(path, "dot");
			Graph graph = getGraphTypeFromDot(path);

			/**
			 * Toutes les lignes commençant avec un digit sont considérées.
			 **/
			try (Stream<String> stream = Files.lines(Paths.get(path))) {
				stream.map(line -> line.trim().replace(";", ""))
						.filter(line -> !line.isEmpty() && Character.isDigit(line.charAt(0)))
						.limit(1000000)
						.forEach(line -> loadLine(graph, line));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return graph;
		}

		public static Graph loadDatFile(Path path) {
			checkExtension(path.toString(), "dat");
			final int DOMINATE = 1;
			OrientedGraph graph = new OrientedGraph();
			try {
				List<String> lines = Files.readAllLines(path);
				int id = 1;
				for (String l : lines) {
					String[] tmp = l.trim().split(" ");
					for (int i = 0; i < id - 1; i++) {
						if (Integer.parseInt(tmp[i]) == DOMINATE) {
							graph.addVertices(id, i + 1);
						}
					}
					for (int i = id; i < tmp.length; i++) {
						if (Integer.parseInt(tmp[i]) == DOMINATE) {
							graph.addVertices(id, i + 1);
						}
					}
					id++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return graph;
		}
		
		public static Graph loadEdgesFile(Path path) {
			checkExtension(path.toString(), "edges");
			OrientedGraph graph = new OrientedGraph();
			try (Stream<String> stream = Files.lines(path)) {
				stream.forEach(line -> loadEdgesLine(graph, line));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return graph;
		}

		/**
		 * Obtenir le type de graphe correspondant à la première ligne du
		 * fichier, ce qui initialise aussi les types des sommets.
		 * 
		 * @param path
		 *            Chemin du fichier
		 * @return Graphe : normal ou orienté
		 */
		static Graph getGraphTypeFromDot(String path) {
			try (Stream<String> stream = Files.lines(Paths.get(path))) {
				Optional<String> firstLine = stream.findFirst();
				if (!firstLine.isPresent()) {
					System.err.println("Fichier '" + path + "' vide !");
					System.exit(1);
				}

				String firstWord = firstLine.get().trim().split(" ")[0];
				switch (firstWord) {
				case OrientedGraph.NAME:
					return new OrientedGraph();
				case StandardGraph.NAME:
					return new StandardGraph();
				default:
					System.err.println("Type de graphe non reconnu : " + firstWord);
					return null;
				}

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		/**
		 * Charger les sommets dans le graphe
		 * 
		 * @param graph
		 *            Graphe où ajouter les sommets
		 * @param line
		 *            Une ligne du fichier
		 */
		static void loadLine(Graph graph, String line) {
			String[] tmp = line.split(graph.getNeighborsSeparator());
			int[] vertices = null;
			try {
				vertices = Arrays.stream(tmp).map(String::trim).mapToInt(Integer::parseInt).toArray();
			} catch (NumberFormatException e) {
				System.err.println("Ligne : " + line);
				System.err.println("Format incorrect, n'a pas pu être parsé correctement");
				System.err.println("Attendu : [Numéro][Séparateur][Numéro]");
				System.exit(1);
			}

			switch (vertices.length) {
			case 1:
				graph.retrieveOrCreate(vertices[0]);
				break;
			case 2:
				graph.addVertices(vertices[0], vertices[1]);
				break;
			default:
				System.err.println("Ligne : " + line);
				System.err.println("Attendu : maximum " + Graph.MAX_NUMBERS_PER_LINE + " numéros");
				break;
			}
		}

		static void loadEdgesLine(OrientedGraph graph, String line) {
			String[] tmp = line.split("\t");
			switch (tmp.length) {
			case 1:
				graph.retrieveOrCreate(Integer.parseInt(tmp[0]));
				break;
			case 2:
				graph.addVertices(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
				break;
			default:
				System.err.println("Ligne : " + line);
				System.err.println("Attendu : maximum " + Graph.MAX_NUMBERS_PER_LINE + " numéros");
				break;
			}
		}
	}

}
