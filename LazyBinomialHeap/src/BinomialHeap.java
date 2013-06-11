/**
 * BinomialHeap
 *
 * An implementation of lazy binomial heap over non-negative integers.
 */
public class BinomialHeap
{
	LinkedList list;
	Tree tree;
	int size;
	BinomialTree min;
	
	BinomialHeap(LinkedList list, Tree tree){
		this.list=list;
		this.tree=tree;
	}
	static class BinomialTree {
		// mid is only important if left == right == null
		BinomialTree next;
		BinomialTree child;
		int value;
		
		BinomialTree(BinomialTree next, BinomialTree child) {
			this.next = next;
			this.value = next.value;
			assert(next.value <= child.value);
			this.child = child;
		}
		
		BinomialTree(int value) {
			this.next = this.child = null;
			this.value = value;
		}
	}
	
	static class LinkedList {
		int size;
		BinomialTree tree;
		LinkedList next;
		
		LinkedList(BinomialTree tree, int size, LinkedList next) {
			this.size = size;
			this.tree = tree;
			this.next=next;//changed
		}
	}
	
	static class Tree {
		Tree left;
		LinkedList center;
		Tree right;
		
		Tree(Tree left, LinkedList center, Tree right) {
			this.left = left;
			this.center = center;
			this.right = right;
		}
	}
	
	

   /**
    * public boolean empty()
    *
    * precondition: none
    *
    * The method returns true if and only if the heap
    * is empty.
    *
    */
    public boolean empty()
    {
    	if(min.value==null){
    		return true;
    	}
    	return false; // should be replaced by student code
    }

   /**
    * public void insert(int value)
    *
    * Insert value into the heap
    *
    */
    public void insert(int value)
    {
    	return; // should be replaced by student code
    }

   /**
    * public void deleteMin()
    *
    * Delete the minimum value.
    * Return the number of linking actions that occured in the process.
    *
    */
    public int deleteMin()
    {
     	return 42; // should be replaced by student code
    }

   /**
    * public int findMin()
    *
    * Return the minimum value
    *
    */
    public int findMin()
    {
    	return 42;// should be replaced by student code
    }

   /**
    * public void meld (BinomialHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (BinomialHeap heap2)
    {
    	  return; // should be replaced by student code
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *
    */
    public int size()
    {
    	return 42; // should be replaced by student code
    }


   /**
    * public static int sortArray(int[] array)
    *
    * Sort an array by using insert and deleteMin actions on a new heap.
    * Return the number of linking actions that occured in the process.
    *
    */
    public static int sortArray(int[] array)
    {
        return 42; //	 to be replaced by student code
    }

   /**
    * public int[] treesRanks()
    *
    * Return an array containing the ranks of the trees that represent the heap
    * in ascending order.
    *
    */
    public int[] treesRanks()
    {
        int[] arr = new int[42]; //
        return arr; //	 to be replaced by student code
    }

   /**
    * public class BinomialHeapTree
    *
    * If you wish to implement classes other than BinomialHeap
    * (for example BinomialHeapTree), do it in this file, not in
    * another file
    *
    */
    public class BinomialHeapTree{

    }

}
