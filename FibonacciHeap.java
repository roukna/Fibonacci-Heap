import java.util.ArrayList;
import java.util.List;

public class FibonacciHeap<T extends Comparable<T>, S extends Comparable<S>> {

    private Node<T,S> maxNode;
    private int fibSize;

    public FibonacciHeap() {
        
		maxNode = null;
        fibSize = 0;
    }

	public boolean isFibHeapEmpty() {
        
		return maxNode == null;
    }

    public void clearFibHeap() {
        
		maxNode = null;
        fibSize = 0;
    }

    public Node<T,S> fibHeapInsert(Node<T,S> iNode) {
        
		maxNode = mergeNodeList(maxNode, iNode);
        fibSize++;
        return iNode;
    }

    public Node<T,S> fibHeapFindMaximum() {
        
		return maxNode;
    }

    public void fibHeapIncreaseKey(Node<T,S> node, T newKey) {
    	
        if (newKey.compareTo(node.getNodeKey()) < 0) {        	
            throw new IllegalArgumentException("New key is smaller than old key.");
        }

        node.setNodeKey(newKey);
        
        Node<T,S> parentNode = node.getNodeParent();
        if (parentNode != null && node.compareTo(parentNode) > 0) {
            cut(node, parentNode);
            cascadingCut(parentNode);

        }
        if (node.compareTo(maxNode) > 0) {
            maxNode = node;
        }
    }

    private void cut(Node<T,S> node, Node<T,S> parentNode) {
    	
        
    	Node<T,S> maxChild = node.getNodeNext();
        Node<T,S> nextChild = node.getNodeNext();
        
        nextChild = maxChild;
        
        /*Gets the next child of parentNode.*/
        do  {
        	
        	if(nextChild.compareTo(maxChild) > 0)
        		maxChild = nextChild;
        	
        	nextChild = nextChild.getNodeNext();        	
        }while(nextChild != node);
        
        if(maxChild == node) 
        	maxChild = null;
    	
		removeNodeFromList(node);
        parentNode.setNodeDegree(parentNode.getNodeDegree()-1);
        parentNode.setNodeChild(maxChild);
        node.setNodeParent(null);        
        mergeNodeList(maxNode, node);
        node.setMarked(false);
        
    }

    private void cascadingCut(Node<T,S> node) {
        
		Node<T,S> parentNode = node.getNodeParent();
        
		if (parentNode != null) {
            if (node.isChildCut()) {
                cut(node, parentNode);
                cascadingCut(parentNode);
            } else {
                node.setMarked(true);
            }
        }

    }

    public void fibHeapDelete(Node<T,S> node) {
        
		node.setMaximum(true);
        
		Node<T,S> parentNode = node.getNodeParent();
        if (parentNode != null) {
            cut(node, parentNode);
            cascadingCut(parentNode);
        }
        maxNode = node;

        fibHeapExtractMax();
    }

    public Node<T,S> fibHeapExtractMax() {
    	
        Node<T,S> extractedMax = maxNode;
        if (extractedMax != null) {
        	
            /*Set the parent of extracted node's children to null.*/
            if (extractedMax.getNodeChild() != null) {
                
            	Node<T,S> childNode = extractedMax.getNodeChild();
                do {
                    childNode.setNodeParent(null);
                    childNode = childNode.getNodeNext();
                } while (childNode != extractedMax.getNodeChild());
            }

            Node<T,S> nextInRootList = maxNode.getNodeNext() == maxNode ? null : maxNode.getNodeNext();

            /*Remove the maximum node from the root list.*/
            removeNodeFromList(extractedMax);
            fibSize--;

            /*Merge children of maximum node with the root list.*/
            maxNode = mergeNodeList(nextInRootList, extractedMax.getNodeChild());
            
            if (nextInRootList != null) {
                maxNode = nextInRootList;
                consolidate();
            }

        }
        return extractedMax;
    }

    private void consolidate() {
	
        List<Node<T,S>> pairwiseCombine = new ArrayList<Node<T,S>>();
        NodeList<T,S> iter = new NodeList<T,S>(maxNode);
        
        while (iter.hasNext()) {
            Node<T,S> currentNode = iter.next();

            while (pairwiseCombine.size() <= currentNode.getNodeDegree() + 1) {
            	pairwiseCombine.add(null);
            }
            
            /*Pairwise combine nodes with same degree.*/
            while (pairwiseCombine.get(currentNode.getNodeDegree()) != null) {
                if (currentNode.getNodeKey().compareTo(pairwiseCombine.get(currentNode.getNodeDegree()).getNodeKey()) < 0) {
                    Node<T,S> tNode = currentNode;
                    
                    currentNode = pairwiseCombine.get(currentNode.getNodeDegree());
                    pairwiseCombine.set(currentNode.getNodeDegree(), tNode);
                }
                meld(pairwiseCombine.get(currentNode.getNodeDegree()), currentNode);
                pairwiseCombine.set(currentNode.getNodeDegree(), null);
                currentNode.setNodeDegree(currentNode.getNodeDegree() + 1);
                
            }
            
            

            while (pairwiseCombine.size() <= currentNode.getNodeDegree() + 1) {
            	pairwiseCombine.add(null);
            }
            pairwiseCombine.set(currentNode.getNodeDegree(), currentNode);
        }

        maxNode = null;
        for (int i = 0; i < pairwiseCombine.size(); i++) {
            if (pairwiseCombine.get(i) != null) {
                
            	pairwiseCombine.get(i).setNodeNext(pairwiseCombine.get(i));
            	pairwiseCombine.get(i).setNodePrev(pairwiseCombine.get(i));
                maxNode = mergeNodeList(maxNode, pairwiseCombine.get(i));
            }
        }
    }

    private void removeNodeFromList(Node<T,S> node) {

        Node<T,S> prev = node.getNodePrev();
        Node<T,S> next = node.getNodeNext();
        
        prev.setNodeNext(next);
        next.setNodePrev(prev);

        node.setNodeNext(node);
        node.setNodePrev(node);
        
    }

    /*Meld two nodes*/
    private void meld(Node<T,S> min, Node<T,S> max) {
        
    	removeNodeFromList(min);
        max.setNodeChild(mergeNodeList(min, max.getNodeChild()));
        min.setNodeParent(max);
        min.setMarked(false);
    }

    /*Merge two node lists of Fibonacci heap.*/
    public static <T extends Comparable<T>, S extends Comparable<S>>  Node<T,S> mergeNodeList(
            Node<T,S> a, Node<T,S> b) {

        if (a == null && b == null) {
            return null;
        }
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        Node<T,S> tNode = a.getNodeNext();
        a.setNodeNext(b.getNodeNext());
        a.getNodeNext().setNodePrev(a);
        b.setNodeNext(tNode);
        b.getNodeNext().setNodePrev(b);

        return a.compareTo(b) > 0 ? a : b;
    }

}