/**
 * Represents a node in the MinHeap Tree
 * Consists of the character and its frequency.
 * Also maintains references of left and right child.
 */
public class FreqMapHeapTreeNode {
    int freq;
    Character character;
    FreqMapHeapTreeNode leftChild;
    FreqMapHeapTreeNode rightChild;

    public FreqMapHeapTreeNode(Character character, int freq, FreqMapHeapTreeNode leftChild, FreqMapHeapTreeNode rightChild) {
        this.character = character;
        this.freq = freq;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
}