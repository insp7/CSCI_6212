import java.util.Arrays;

/**
 * Simple Priority Queue Implementation using Min Heap property.
 * 
 * Class PriorityQ is a Min Heap Tree implementation.
 * In a min heap, all elements are smaller than their children.
 * Hence the root node will be the very smallest element.
 * Going down the tree, elements get bigger and bigger.
 * This is the fundamental property of a min heap.
 * 
 * Some elementary operations with Min Heap:
 * 1. Insertion:  
 * Element inserted in the next empty spot, by looking top to bottom then left to right.
 * If the element inserted breaks the min heap property i.e. element < element's parent, 
 * we bubble it up the tree to the right spot so the min heap property becomes valid again.
 * 
 * 2. Removal:
 * Remove the minimum element from the tree i.e. root node.
 * Swap that value with the last element of the heap tree.
 * If new root element not in right spot (i.e. root value > root's children's value), then,
 * bubble it down the tree to the right spot so the min heap property becomes valid again. 
 * 
 * How this min heap is stored:
 * While we can implement it using left and right nodes for a particular node object, an even better implementation is using arrays.
 * This is possible as we leverage how the heap tree fundamentally behaves when elements are inserted and removed.
 *
 * We use the following equations to map from an index to its left child or right child or parent:
 * Get Parent index: (index-2)/2
 * Get Left Child index: (index*2)+1
 * Get Right Child index: (index*2)+2
 * 
 * Note - Java supports zero based indexing for arrays.
 */
class PriorityQ {
    private int size = 0;
    private int capacity = 16;

    int[] items = new int[capacity];
    
    // Methods to fetch left, right and parent indexes for a given element
    private int getLeftChildIndex(int parentIndex) { return 2 * parentIndex + 1; }
    private int getRightChildIndex(int parentIndex) { return 2 * parentIndex + 2; }
    private int getParentIndex(int childIndex) { return (childIndex - 1) / 2; }

    // Methods to check if left, right, parent elements exists for a given element
    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }
    private boolean hasParent(int index) { return getParentIndex(index) >= 0; }

    // Methods to get values of left, right and parent elements for a given element
    private int leftChild(int index) { return items[getLeftChildIndex(index)]; }
    private int rightChild(int index) { return items[getRightChildIndex(index)]; }
    private int parent(int index) { return items[getParentIndex(index)]; }

    private void swap(int indexOne, int indexTwo) {
        int temp = items[indexOne];
        items[indexOne] = items[indexTwo];
        items[indexTwo] = temp;
    }

    /**
     * If the array is full, 
     * create a new array of double that size
     * and copy all the elements over to the newly created array.
     */
    private void ensureExtraCapacity() {
        if(size == capacity) {
            items = Arrays.copyOf(items, capacity * 2);
            capacity *= 2;
        }
    }

    private boolean isHeapEmpty() {
        return size == 0;
    }

    /**
     * @return Returns the first element in the array [Heap Tree]. If the array is empty, returns an exception.
     */
    public int getMinElement() {
        if(isHeapEmpty())
            throw new IllegalStateException("Array [Heap Tree] is empty");
        return items[0];
    }

    /**
     * Removes the minimum element (i.e. root element which is highest priority) from the min heap tree.
     * @return Returns the minimum element(aka highest priority element) from the heap. If array is empty, returns an exception.
     */
    public int remove() {
        if(isHeapEmpty())
            throw new IllegalStateException("Array [Heap Tree] is empty");
        int item = items[0]; // fetch the value of first element
        items[0] = items[size - 1]; // move the value of the last element to the first element of array
        size--; // shrink the array size
        heapifyDown();
        return item;
    }

    /**
     * Adds an element to the min heap. New element is inserted at the newest empty index(i.e. the very last spot)
     * @param item the element to add to the heap
     */
    public void add(int item) {
        ensureExtraCapacity();
        items[size] = item;
        size++;
        heapifyUp();
    }

    /**
     * heapifyUp() logic:
     * Starts with the very last element added.
     * While current element has a parent AND 
     * value of parent > value of current element:
     * swap current element's value with its parent element,
     * then update the index to traverse upwards.
     */
    private void heapifyUp() {
        int index = size - 1;
        while(hasParent(index) && parent(index) > items[index]) { 
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    } 

    /**
     * Start with the min element i.e root/first element.
     * While there exists left child for current element,
     * then, assume smaller child = left child, 
     * then, if right child exists AND right child value < left child value: smaller child = right child
     * 
     * if current element is less than the smaller child of current element, GREAT
     * else our heap is out of order, to fix it: swap current element with the smaller child
     * 
     * update index to move down to the smaller child.
     */
    private void heapifyDown() {
        int index = 0;
        while(hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if(hasRightChild(index) && rightChild(index) < leftChild(index)) {
                smallerChildIndex = getLeftChildIndex(index);
            }

            if(items[index] < items[smallerChildIndex])
                break;
            else
                swap(index, smallerChildIndex);
            index = smallerChildIndex;
        }
    }
}