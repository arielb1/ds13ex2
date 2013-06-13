/**
 * BinomialHeap
 *
 * An implementation of lazy binomial heap over non-negative integers.
 */
public class BinomialHeap
{
	private LinkedList list;
	private Tree tree;
	private int size;
	private int count_links;
	
	private static class BinomialTree {
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
	
	private static class LinkedList {
		int degree;//tree's degree
		BinomialTree tree;
		LinkedList next;
		
		LinkedList(BinomialTree tree, int degree, LinkedList next) {
			// degree should be tree's degree
			this.degree = degree;
			this.tree = tree;
			this.next=next;//changed
		}
	}
	
	private static class Tree {
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
   		return size == 0;
    }

   /**
    * public void insert(int value)
    *
    * Insert value into the heap
    *
    */
    public void insert(int value)//mor
    {
    	BinomialTree t=new BinomialTree(value);
    	if (list.tree.value> value){
    		list = new LinkedList(t,0,list);
    	}
    	else{
    		LinkedList l=new LinkedList(t,0,list.next);
    		list=new LinkedList(list.tree,list.degree,l);
    	}
    	size++;
    }

   /**
    * public void deleteMin()
    *
    * Delete the minimum value.
    * Return the number of linking actions that occured in the process.
    *
    */
    public int deleteMin()//ariel
    {
     	return 42; // should be replaced by student code
    }

   /**
    * public int findMin()
    *
    * Return the minimum value
    *
    */
    public int findMin()//mor
    {
    	return 42;// should be replaced by student code
    }

   /**
    * public void meld (BinomialHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (BinomialHeap heap2)//mor
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
    	return size;
    }


   /**
    * public static int sortArray(int[] array)
    *
    * Sort an array by using insert and deleteMin actions on a new heap.
    * Return the number of linking actions that occurred in the process.
    *
    */
    public static int sortArray(int[] array)//mor
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
    public int[] treesRanks()//ariel
    {
        int[] arr = new int[42]; //
        return arr; //	 to be replaced by student code
    }

}
