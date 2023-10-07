import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Huffman Coding implementation using Priority Queue.
 */
public class HuffmanCoding {
    private static Map<Character, String> huffCodes;

    /**
     * Generates compressed text for a given plain text and its character-wise huffman codes
     * @param text Plain text input
     * @param huffCodes Character-wise huffman codes for compression
     * @return
     */
    private static String generateCompressedText(String text, Map<Character, String> huffCodes) {
        StringBuilder compressedText = new StringBuilder();
        for(int i = 0; i < text.length(); i++) {
            compressedText.append(huffCodes.get(text.charAt(i)));
        }
        return compressedText.toString();
    }

    /**
     * Method to generate Huffman codes by traversing the generated Huffman Tree (MinHeap Tree)
     * @param root Root node of the Huffman Tree
     * @param huffCode Represents the Huffman code generated so far (Note: The method is recursive)
     */
    private static void generateHuffCode(FreqMapHeapTreeNode root, String huffCode) {
        if(root.leftChild == null && root.rightChild == null && root.character != null) {
            huffCodes.put(root.character, huffCode);
            return;
        }
        generateHuffCode(root.leftChild, huffCode + "0");
        generateHuffCode(root.rightChild, huffCode + "1");
    }

    /**
     * Method to count the frequency of characters in a given string
     * @param text The plain text input string
     * @return Returns a HashMap of all characters' frequencies
     */
    private static Map<Character, Integer> getFrequencies(String text) {
        if(text.isEmpty())
            throw new IllegalStateException("Invalid input: String is empty");
        char[] characters = text.toCharArray();
        Map<Character, Integer> frequencies = new HashMap<>();
        for(char character: characters) {
            if(frequencies.containsKey(character))
                frequencies.put(character, frequencies.get(character) + 1);
            else
                frequencies.put(character, 1);
        }
        return frequencies;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("---Huffman Coding Lossless Compression---");

        // input text from user
        System.out.println("Enter Plain Text:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String text = br.readLine();

//        uncomment below lines to take input text from a file
//        String filePath = ""; // enter file path url here
//        String text = new String(Files.readAllBytes(Paths.get(filePath)));

        long startTime = System.nanoTime(); // Start timer
        Map<Character, Integer> frequencyMap = getFrequencies(text); // calculate all characters' frequencies
        System.out.println("Character Frequencies: " + frequencyMap);

        /**
         * Comparator implemented such that least frequent character is first.
         * When frequencies for 2 characters match, then they are sorted as per their ASCII representation in the priority queue.
         * Check FreqComparator class for the implementation.
         */
        Queue<FreqMapHeapTreeNode> priorityQ = new PriorityQueue<>(new FreqComparator());
        frequencyMap.forEach((character, frequency) -> priorityQ.add(new FreqMapHeapTreeNode(character, frequency, null, null)));
        FreqMapHeapTreeNode root = null; // initialize root to null

        /**
         * Run until the min heap tree is successfully created.
         * When the min heap tree is created, only 1 element (root node) will be remaining in the priority queue.
         */
        while(priorityQ.size() > 1) {
            // extract element with the highest priority i.e. the lowest frequency
            FreqMapHeapTreeNode node1 = priorityQ.peek();
            priorityQ.poll();

            // extract one more element
            FreqMapHeapTreeNode node2 = priorityQ.peek();
            priorityQ.poll();

            // create new element by adding frequencies of the 2 extracted elements
            FreqMapHeapTreeNode newNode = new FreqMapHeapTreeNode(null, node1.freq + node2.freq, node1, node2);
            root = newNode;
            priorityQ.add(newNode);
        }

        // generate huff codes
        huffCodes = new HashMap<>();
        if(root != null)
            generateHuffCode(root, "");
        long endTime = System.nanoTime(); // End timer

        String compressedText = generateCompressedText(text, huffCodes);
        double compressedTextLength = compressedText.length();
        double plainTextLength = text.length() * 8; // since 1 character = 1 byte = 8 bits
        double reductionPercentage = ((plainTextLength - compressedTextLength) / plainTextLength) * 100; // Reduction Percentage = [(Initial Quantity - Final Quantity) / Initial Quantity] x 100%

        System.out.println("Huff Codes: " + huffCodes);
        System.out.println("Compressed Text (in encoded format): " + compressedText);
        System.out.println("Plain Text Size: " + (int)plainTextLength + " bits");
        System.out.println("Compressed Text Size: " + (int)compressedTextLength + " bits");
        System.out.println("Size Reduction Percentage(%) for this text: " + String.format("%.2f", reductionPercentage) + "%");
        System.out.println("Time Elapsed(in nanoseconds): " + (endTime - startTime));
    }
}
