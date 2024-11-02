import java.io.*;
public class AnagramFinder {
    /**
     * Sorts an array of characters using the insertion sort algorithm.
     *
     * @param wordCharArray The array of characters provided by the user, converted to lowercase for sorting.
     * @return A string representing the newly sorted array.
     */
    public static String insertionSort(char[] wordCharArray) {
        // Iterate through the array starting from the second element
        for (int i = 1; i < wordCharArray.length; i++) {
            // Initialize pointer one that stores the current character
            char key = wordCharArray[i];
            // Initialize pointer two to the element before the current one
            int j = i - 1;
            // Move elements greater than the key to one position ahead of
            // their current position until an element smaller than or equal to the key is encountered
            while (j >= 0 && wordCharArray[j] > key) {
                wordCharArray[j + 1] = wordCharArray[j]; // Shift the element to the right
                j = j - 1;    // Move the pointer to the previous position
            }
            wordCharArray[j + 1] = key; // Place the key in its correct position in the sorted subarray
        }
        // Build a string from the sorted array using the StringBuilder java class
        StringBuilder sortedWord = new StringBuilder();
        // Iterate through the sorted char array of the given string in lowercase
        for (char c : wordCharArray) {
            // Add each char in the sorted array by appending to StringBuilder
            sortedWord.append(c);
        }
        // Convert the StringBuilder to a String and return the sorted given string in lowercase
        return sortedWord.toString();
    }
    /**
     * Sorts a MyList of strings using the insertion sort algorithm.
     * The sorting is done lexicographically using natural string order.
     *
     * @param anagramList The MyList of strings to be sorted.
     * @return A sorted MyList of strings.
     */
    public static MyList<String> insertionSort(MyList<String> anagramList) {
        // Check if the list is null
        if (anagramList == null) {
            // If no anagrams are found, print a message
            System.out.println("No anagrams found.");
            // Exit with a failure status
            System.exit(0);
        }
        // Get the size of the list
        int n = anagramList.size();
        // Iterate through the list starting from the second element
        for (int i = 1; i < n; i++) {
            // Initialize pointer one that stores the string in the MyList
            String key = anagramList.get(i);
            // Initialize pointer two to the element before the current one
            int j = i - 1;
            // Compare strings lexicographically and move elements greater than the key to one
            // position ahead of their current position until a smaller or equal string is encountered
            while (j >= 0 && anagramList.get(j).compareTo(key) > 0) {
                anagramList.set(j + 1, anagramList.get(j)); // Shift the element to the right using the set method in MyList
                j = j - 1;            // Move the pointer to the previous position
            }
            anagramList.set(j + 1, key); // Place the key in its correct position in the sorted subarray using the set method in MyList
        }
        // Return the sorted MyList of anagrams in lexicographically order
        return anagramList;
    }
    /**
     * Processes words from a dictionary file to find and organize anagrams.
     *
     * @param map The MyMap to store anagrams, where the key is the sorted version of a word, and the value is a MyLinkedList of anagrams.
     * @param dictionaryFile The path to the dictionary file containing words to process.
     */
    private static void processWords(MyMap<String, MyList<String>> map, String dictionaryFile) {
        // Read the dictionary file to find anagrams
        try {
            // Open the dictionary file for reading
            BufferedReader reader = new BufferedReader(new FileReader(dictionaryFile));
            String line;
            // Iterate through each line in the dictionary file
            while ((line = reader.readLine()) != null) {
                // Convert the line to lowercase and to a char array
                String lowerSortLine = insertionSort(line.toLowerCase().toCharArray());
                // Check if the sorted line matches the sorted word
                if (map.get(lowerSortLine) == null) {
                    // If no anagramList exists for the sorted line, create a new one and add it to the map
                    MyList<String> anagramList = new MyLinkedList<>();
                    map.put(lowerSortLine, anagramList);
                    anagramList.add(line);
                } else {
                    // If an anagramList already exists for the sorted line, retrieve it and add the word to the list
                    MyList<String> anagramList = map.get(lowerSortLine);
                    anagramList.add(line);
                }
            }
            // Close the reader after processing
            reader.close();
        } catch (IOException ex) {
            // Handle I/O exceptions
            System.err.println("Error: An I/O error occurred reading '" + dictionaryFile + "'.");
            System.exit(1);
        }
    }
    /**
     * Finds and prints anagrams of a given word using the specified data structure.
     *
     * @param anagrams The MyLinkedList of strings containing anagrams to be processed and printed.
     */
    private static void anagramList(MyList<String> anagrams, String word) {
        // Check if there are any anagrams found
        if (anagrams.size() == 0) {
            // If no anagrams are found, print a message
            System.out.println("No anagrams found.");
        } else {
            // If anagrams are found, sort the list using insertion sort
            MyList<String> anagramList = insertionSort(anagrams);
            // Print the sorted anagrams
            for (int i = 0; i < anagramList.size(); i++) {
                // If the input word does not equal the word in the list, print it out
                if (!word.equals(anagramList.get(i))) {
                    System.out.println(anagramList.get(i));
                }
            }
        }
    }
    /**
     * The main method of the AnagramFinder program.
     * It processes command line arguments, reads a dictionary file, and finds and prints anagrams based on the specified data structure.
     *
     * @param args Command line arguments containing the word, dictionary file, and data structure type.
     *             Usage: java AnagramFinder <word> <dictionary file> <bst|avl|hash>
     */
    public static void main(String[] args) {
        // Check if the number of arguments is correct
        if (args.length != 3) {
            // Print an error message for incorrect usage
            System.err.println("Usage: java AnagramFinder <word> <dictionary file> <bst|avl|hash>");
            // Exit with a failure status
            System.exit(1);
        } else {
            // Extract command line arguments
            String word = args[0];
            String dictionaryFile = args[1];
            String dataStructure = args[2];
            MyMap<String, MyList<String>> map;
            // Check if the word contains only letters
            if (word.matches("[a-zA-Z]+")) {
                // Check if the dictionary file is valid
                if (dictionaryFile.equals("words.txt") || dictionaryFile.equals("dictionary.txt")) {
                    // Initialize the appropriate data structure based on user input
                    switch (dataStructure) {
                        case "bst" -> {
                            map = new BSTMap<>();
                            processWords(map, dictionaryFile);
                            char[] lowercaseWord = word.toLowerCase().toCharArray();
                            // Sort the characters of the word
                            String sortedWord = insertionSort(lowercaseWord);
                            anagramList(insertionSort(map.get(sortedWord)), word);
                        }
                        case "avl" -> {
                            map = new AVLTreeMap<>();
                            processWords(map, dictionaryFile);
                            char[] lowercaseWord = word.toLowerCase().toCharArray();
                            // Sort the characters of the word
                            String sortedWord = insertionSort(lowercaseWord);
                            anagramList(insertionSort(map.get(sortedWord)), word);
                        }
                        case "hash" -> {
                            map = new MyHashMap<>();
                            processWords(map, dictionaryFile);
                            char[] lowercaseWord = word.toLowerCase().toCharArray();
                            // Sort the characters of the word
                            String sortedWord = insertionSort(lowercaseWord);
                            anagramList(insertionSort(map.get(sortedWord)), word);
                        }
                        default -> {
                            // Print an error message for an invalid data structure
                            System.err.println("Error: Invalid data structure '" + dataStructure + "' received.");
                            // Exit with a failure status
                            System.exit(1);
                        }
                    }
                } else {
                    // Print an error message for an invalid dictionary file
                    System.err.println("Error: Cannot open file '" + dictionaryFile + "' for input.");
                    // Exit with a failure status
                    System.exit(1);
                }
            } else {
                // Print an error message for an invalid word
                System.err.println("Usage: java AnagramFinder <word> <dictionary file> <bst|avl|hash>");
                // Exit with a failure status
                System.exit(1);
            }
        }
    }
}





