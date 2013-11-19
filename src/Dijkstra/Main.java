package Dijkstra;

import java.io.*;
import java.util.*;

/**
 * program to find word ladder with shortest distance for two words in a
 * dictionary distance between elements of the word ladder is the absolute
 * difference in the positions of the alphabet of the non-matching letter
 */
public class Main {

	public static void main(String[] args) throws IOException {

		
		long start = System.currentTimeMillis();
		String inputFileName = args[0]; // dictionary
		String word1 = args[1]; // first word
		String word2 = args[2]; // second word

		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);

		ArrayList<Vertex> dictionary = new ArrayList<Vertex>();

		// storing all the words into dictionary arraylist
		while (in.hasNext()) {
			dictionary.add(new Vertex(in.next()));

		}

		// do the work here
		// creating the adjacent list looping through all the words in the
		// dictionary first
		for (int i = 0; i < dictionary.size(); i++) {
			// then compare with the dictionary to see what is the words that
			// are adjacent.
			for (Vertex temp : dictionary) {

				// this line check if the words are adjacent
				if (findNeigh(temp, dictionary.get(i))) {
					// get the weight between the two words
					int weight = findNeight(temp, dictionary.get(i));
					// store the words into the word's adjacent list.
					dictionary.get(i).adjacencies.add(new Edge(temp, weight));
				}
			}
		}

		// this will compute all the path for the first word using the
		// dictionary.
		computePaths(findVertex(word1, dictionary));

		// this will define the shortest path with the ending word.
		List<Vertex> path = getShortestPathTo(findVertex(word2, dictionary));

		// end timer and print total time
		System.out.println("The path size is " + path.size());
		System.out.println();
		// This print out the path that is being returned.
		if (path.size() == 0) {
			System.out.println("No ladder is possible.");
		} else {
			for (int i = 0; i < path.size(); i++) {
				// this condition is to check if it is the last one, it is
				// already
				// the last one, it will not print the ->
				if (i < path.size() - 1) {
					System.out.print(path.get(i).name + " -> ");
				} else {
					System.out.print(path.get(i));
				}
			}
			System.out.println();
			
			long end = System.currentTimeMillis();
			System.out.println("\nElapsed time: " + (double) (end - start)
					/ 1000 + " seconds");
		}
	}

	// this function compute the shortest path to the target
	public static List<Vertex> getShortestPathTo(Vertex target) {
		// the end of this function will return the path arraylist
		List<Vertex> path = new ArrayList<Vertex>();

		// it will look through all the previous words from the targeted vertex.
		// It will keep looping till there is no other vertex.

		for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
			path.add(vertex); // as long as there is a previous, it will put
								// inside the path.
		Collections.reverse(path); // this reverse the order of path.
		return path;
	}

	public static void computePaths(Vertex source) {
		source.minDistance = 0.;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
		vertexQueue.add(source); // start the ball rolling

		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.poll();

			// look through all the adjacent list
			for (Edge e : u.adjacencies) {
				Vertex v = e.target; // set the target
				double weight = e.weight; // set the weight
				double distanceThroughU = u.minDistance + weight; // it will set
																	// the
																	// weight
																	// from the
																	// adjacent.
				if (distanceThroughU < v.minDistance) {
					vertexQueue.remove(v); // remove the previous item if the
											// distance is shorter
					v.minDistance = distanceThroughU; // pump in the distance
					v.previous = u; // set it's next word
					vertexQueue.add(v); // set to the next word so that it can
										// continue.
				}
			}
		}
	}

	// check if adjacent
	public static boolean findNeigh(Vertex fn, Vertex sn) {
		int diff = 0;
		// go through the word
		for (int i = 0; i < fn.name.length(); i++) {
			if (fn.name.charAt(i) != sn.name.charAt(i))
				diff++; // if the word is wrong, add to diff

		}
		// return if there is only 1 different from the word.
		return (diff == 1);

	}

	// this calculate the weight between the two word
	public static int findNeight(Vertex fn, Vertex sn) {
		int diff = -1;
		// check if the word is adjacent
		if (findNeigh(fn, sn)) {
			diff = Math.abs(fn.name.compareTo(sn.name)); // give an absolute
															// figure on the
															// different so that
															// there is no
															// negative.
		}

		// return the number
		return diff;
	}

	// find the vertex in the dictionary. because this is not string, therefore
	// need to use this method.
	public static Vertex findVertex(String word, ArrayList<Vertex> dictionary) {
		Vertex v = null;

		// check if the word exist.
		for (Vertex temp : dictionary) {
			if (temp.name.equals(word)) {
				v = temp;
				break;
			}
		}
		// return the vertex, if not null if not found.
		return v;
	}

}

// instead of creating another class. i put it into the same file.
class Vertex implements Comparable<Vertex> {
	public final String name;
	public ArrayList<Edge> adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Vertex previous;

	public Vertex(String argName) {
		name = argName;
		adjacencies = new ArrayList<Edge>();
	}

	public String toString() {
		return name;
	}

	public int compareTo(Vertex other) {
		return Double.compare(minDistance, other.minDistance);
	}
}

// instead of creating another class, i put it together.
class Edge {
	public final Vertex target;
	public final double weight;

	public Edge(Vertex argTarget, double argWeight) {
		target = argTarget;
		weight = argWeight;
	}
}
