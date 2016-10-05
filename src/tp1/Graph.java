package tp1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class Graph {

	private final static int MAX_NUMBERS_PER_LINE = 2;

	/**
	 * Vérifier si le graphe contient un sommet
	 * 
	 * @param number
	 *            Sommet à chercher
	 * @return Le sommet retrouvé, ou null sinon
	 */
	abstract Optional<? extends Vertex> containsVertex(int number);

	/**
	 * Ajout un sommet, en vérifiant d'abord qu'il n'est pas déjà dans le
	 * graphe.
	 * 
	 * @param vertex
	 *            numéro du sommet
	 * @return Le sommet ajouté, ou retrouvé
	 */
	abstract Vertex addVertex(int vertex);

	/**
	 * Ajoute au graphe deux sommets, sans dupliquer, et créée entre eux des
	 * liens de voisinage
	 * 
	 * @param v1
	 * @param v2
	 */
	abstract void addVertices(int v1, int v2);

	abstract String getNeighborsSeparator();

	abstract int getVerticesCount();
	abstract int getArcsCount();
	abstract int getVertexMaxNumber();
	abstract void printStats();
	
	/**
	 * Nombre de sommets accessibles, en itérant, donc descendants inclus
	 * @param vertexNumber Numéro du sommet de départ
	 */
	abstract int getAccessibleNeighborsCount(int vertexNumber);
	
	final static class GraphProvider {

		Graph graph = null;

		public static Graph loadDotFile(String path) {
			String extension = "";
			String expectedExtension = "dot";
			int i = path.lastIndexOf('.');
			if (i > 0) {
				extension = path.substring(i + 1);
			}
			if (!extension.equals(expectedExtension)) {
				System.err.println("Format de fichier invalide (" + expectedExtension + " attendu)");
			}

			Graph graph = getGraphType(path);

			/**
			 * Toutes les lignes commençant avec un digit sont considérées.
			 **/
			try (Stream<String> stream = Files.lines(Paths.get(path))) {
				stream.map(line -> line.trim().replace(";", ""))
						.filter(line -> !line.isEmpty() && Character.isDigit(line.charAt(0)))
						.forEach(line -> loadLine(graph, line));
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
		static Graph getGraphType(String path) {
			try (Stream<String> stream = Files.lines(Paths.get(path))) {
				Optional<String> firstLine = stream.findFirst();
				if (!firstLine.isPresent()) {
					System.err.println("Fichier '" + path + "' vide !");
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
				graph.addVertex(vertices[0]);
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

	}

}
