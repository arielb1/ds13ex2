/**

 * BinomialHeap
 *
 * An implementation of lazy binomial heap over non-negative integers.
 */
public class BinomialHeap
{
    /*
     * A rather standard Binomial (min)-Heap implementation. We represent the
     *     trees as binary trees via the standard isomorphism - a chain
     *     of BinomialTrees linked by next represent a tree node. This
     *     representation allows for quick links and spreads. As an
     *     optimisation, we don't store the depth-zero `subnode' of a node
     *     of positive depth - this does not affect spreads because the
     *     spread-node is discarded anyway.
     *
     * To allow for constant-time melds, we use a tree which is emptied
     *     at deleteMin. During meld, we join the other node's tree and
     *     list with our own tree into a binary tree. In order to keep
     *     traversal complexity in check, each tree-node must have a nonempty
     *     list or 2 children - keeping the tree's size under half the heap's.
     *
     * Additionally, in order to keep recursion depth in check, when we create
     *     a tree-node, we put the shorter node on the left. This forces
     *     each tree of left height $k$ to have at least $2^k$ children,
     *     bounding the depth of naive recursion and obviating us of
     *     the need of keeping an explicit stack.
     *
     * Finally, to allow for constant-time findMin, the linked list's
     *     first element contains the heap's minimum.
     */

        // A linked list containing the heap's subtrees, except for
        //    the nodes melded before the last deleteMin (that were not
        //    heap minima immediately after their insertion)
	private LinkedList list;

        // A binary tree containing the subtrees melded in before the last
        //    last deleteMin, during which the tree's contents are melded
        //    onto the linked list.
	private Tree tree;

        // Contains the number of elements in the heap.
	private int size;

        // Contains the (binary) tree's left-depth.
        private int tree_depth;

	private static class BinomialTree {
		BinomialTree next;
		BinomialTree child;
		int value;
		
		BinomialTree(BinomialTree next, BinomialTree child) {
                        this.next = next.next == null ? null : next;
		        this.value = next.value;
			assert(next.value <= child.value);
			this.child = child;
		}

		//a leaf/root
		BinomialTree(int value) {
			this.next = this.child = null;
			this.value = value;
		}
	}
	
	private static class LinkedList {
		int degree; // tree's degree
		BinomialTree tree;
		LinkedList next;
                
		LinkedList(BinomialTree tree, int degree, LinkedList next) {
			// degree should be tree's degree
			this.degree = degree;
			this.tree = tree;
			this.next = next;
		}
	}
	
	private static class Tree {
		Tree left;
		LinkedList center;
		Tree right;

                Tree(Tree left, LinkedList center, Tree right,
		     int left_height, int right_height) {
		    if(left_height < right_height) {
        		    this.left = left;
			    this.right = right;
		    } else {
			this.left = right;
			this.right = left;
		    }
		    this.center = center;
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
    public void insert(int value)
    {
        // An element is inserted to the heap as a Binomial Tree of 
        //     degree zero. Because the list's head must contain the heap's
        //     minimum, the new tree may have to be inserted as the 
        //     linked list's second element rather than the first.

    	BinomialTree t = new BinomialTree(value);

    	if (list == null || list.tree.value > value)
    		list = new LinkedList(t, 0, list);
    	else {
    		LinkedList l = new LinkedList(t, 0, list.next);
    		list = new LinkedList(list.tree, list.degree, l);
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
    public int deleteMin()
    {
        int max_deg = 0;
        int link_count = 0;
        size--;
        for(int s=size;s>0;s = s >> 2) max_deg++;
        BinomialTree[] target = new BinomialTree[max_deg];
        
        BinomialTree cur = list.tree;
        for(int deg=list.degree;deg>0;deg--,cur=cur.next)
            target[deg-1] = cur.child;
        assert(cur == null);

        link_count += link_list(target, list.next);
        link_count += link_tree(target, tree);

        tree = null;
        tree_depth = 0;
        list = collect_target(target);
     	return link_count;
    }

    private int link_list(BinomialTree[] target, LinkedList list) {
        int link_count = 0;

        while(list != null) {
            if(target[list.degree] == null)
                target[list.degree] = list.tree;
            else {
                link_count++;
                target[list.degree] = (list.tree.value >
                                       target[list.degree].value) ?
                      new BinomialTree(target[list.degree], list.tree)
                    : new BinomialTree(list.tree, target[list.degree]);
            }
            list = list.next;
        }

        return link_count;
    }

    private int link_tree(BinomialTree[] target, Tree tree) {
        int link_count = 0;

        for(Tree cur = tree; cur != null; cur = cur.right) {
            link_count += link_tree(target, cur.left);
            link_count += link_list(target, cur.center);
        }

        return link_count;
    }

    private LinkedList collect_target(BinomialTree[] target) {
        BinomialTree min_tree = null;
        int min_deg = -1;
        LinkedList list = null;
        for(int deg=0;deg<target.length;deg++) {
            BinomialTree t = target[deg];
            if(t == null) continue;
            if(min_tree == null) {
                min_tree = t;
                min_deg = deg;
            } else if(t.value < min_tree.value) {
                list = new LinkedList(min_tree, min_deg, list);
                min_tree = t;
                min_deg = deg;
            } else list = new LinkedList(t, deg, list);
        }

        if(min_tree != null)
            list = new LinkedList(min_tree, min_deg, list);

        return list;
    }

   /**
    * public int findMin()
    *
    * Return the minimum value
    *
    */
    public int findMin()
    {
        // The heap's minimum subtree is stored at the linked list's head,
        //     making retrieval of the minimum easy. Note that this
        //     NullPointerExceptions on an empty heap - so don't.

    	return list.tree.value;
    }

   /**
    * public void meld (BinomialHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (BinomialHeap heap2)
    {
        // A few base cases to avoid tree-node proliferation and the
        //     resulting complexity increase
    	if(heap2.empty())
    		return;
    	if(empty()) {
    		list = heap2.list;
    		tree = heap2.tree;
		size += heap2.size();
    		return;
    	}

        size += heap2.size();

        // In case the second heap has a smaller minimum, it must be prepended
        //     it to our linked list and only add the cdr of the second
        //     heap's linked list to the tree.
        if(heap2.list.tree.value < list.tree.value){
                list = new LinkedList(heap2.list.tree, heap2.list.degree,
				      list);
                if(heap2.size() > 1)
                    tree = new Tree(tree, heap2.list.next, heap2.tree,
				    tree_depth, heap2.tree_depth);
                else
                    // If heap2.size() == 1 then it has no other elements and
                    //     the tree dosen't need to be enlarged.
                    return;
        }
        else
	    tree = new Tree(tree, heap2.list, heap2.tree,
			    tree_depth, heap2.tree_depth);
        
        if(heap2.tree_depth > tree_depth)
    	    tree_depth = heap2.tree_depth;
    	else if(heap2.tree_depth == tree_depth)
    	    tree_depth++;
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
    public static int sortArray(int[] array)
    {
    	int count_links = 0;
    	BinomialHeap heap=new BinomialHeap();

        // A binomial heapsort - like in a standard heapsort, the
        //     array's elements are inserted into the heap then popped
        //     one-by-one into the result.

    	for(int i=0;i<array.length;i++){
    		heap.insert(array[i]);
    	}


    	for(int i=0;i<array.length;i++){
    		array[i] = heap.findMin();
    		count_links += heap.deleteMin();
    	}
    	
        return count_links; 
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
