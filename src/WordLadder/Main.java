package WordLadder;

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

		// read in the data here
		ArrayList<String> dictionary = new ArrayList<String>();
		Map<String, ArrayList<String>> adjacentWords = new LinkedHashMap<String, ArrayList<String>>();
		// storing all the words into dictionary arraylist
		while (in.hasNext()) {
			dictionary.add(in.next());
		}

		// create graph here
		int pointer = 0;

		// linked all the word in the adjacent list and stored it into a map.
		while (pointer != dictionary.size()) {
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i < dictionary.size(); i++) {
				if (checkNeigh(dictionary.get(pointer), dictionary.get(i))) {
					temp.add(dictionary.get(i));
				}
			}
			// with the key being the actual word, the list will contain all the
			// adjacent list.
			adjacentWords.put(dictionary.get(pointer), temp);
			pointer++;
		}

		// do the work here

		// compute the path for the adjacent.
		List<String> path = findChain(adjacentWords, word1, word2);

		// end timer and print total time

		System.out.println("Word Ladder length is " + path.size());

		// printing it out.
		if (path.size() == 0) {
			System.out.println("There is no ladder found!");
		} else {
			for (int i = 0; i < path.size(); i++) {
				System.out.print(path.get(i));
				if (i != path.size() - 1) {
					System.out.print(" -> ");
				}
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (double) (end - start) / 1000
				+ " seconds");
	}

	// check if the first word and second word if it is adjacent
	private static boolean checkNeigh(String fn, String sn) {
		int diff = 0;

		for (int i = 0; i < fn.length(); i++) {
			if (fn.charAt(i) != sn.charAt(i))
				diff++;

		}
		// if it is, return true, else false.
		return (diff == 1);
	}

	// this function computes to find the adjacent list
	public static List<String> findChain(

	Map<String, ArrayList<String>> adjacentWords, String fn, String sn) {

		Map<String, String> pw = new HashMap<String, String>();
		Queue<String> q = new LinkedList<String>();
		
		q.add(fn); //start the ball rolling
		
		while (!q.isEmpty()) {
			String current = q.element();
			q.remove();
			List<String> a = adjacentWords.get(current);
			
			//compute all the path for the word.
			if (a != null)
				for (String adjWord : a)
					if (pw.get(adjWord) == null) {
						pw.put(adjWord, current);
						q.add(adjWord);
					}
		}

		pw.put(fn, null);
		// search for the correct path
		return getChainFromPreviousMap(pw, fn, sn);
	}

	public static List<String> getChainFromPreviousMap(Map<String, String> p,
			String fn, String sn) {
		
		LinkedList<String> result = new LinkedList<String>();

		if (p.get(sn) != null)
			//look thru the list that was being populated to get the path. 
			for (String str = sn; str != null; str = p.get(str))
				result.addFirst(str);

		return result;
	}

}
