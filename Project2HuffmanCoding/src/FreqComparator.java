import java.util.Comparator;

/**
 * Compares elements based on their frequencies.
 * If frequencies are equal, compares elements by characters i.e. their ASCII representation.
 */
public class FreqComparator implements Comparator<FreqMapHeapTreeNode> {
    public int compare(FreqMapHeapTreeNode x, FreqMapHeapTreeNode y) {
        if(x.freq == y.freq) {
            if(x.character != null && y.character != null)
                return x.character.compareTo(y.character); // compare by character if frequencies are same
        }
        return x.freq - y.freq;
    }
}
