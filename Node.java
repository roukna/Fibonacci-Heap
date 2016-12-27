public class Node<T extends Comparable<T>, S extends Comparable<S>>
implements Comparable<Node<T,S>> {

private T nodeKey;
private S hashNodeKey;
private int degree;

private Node<T,S> parent;
private Node<T,S> child;
private Node<T,S> prev;
private Node<T,S> next;

private boolean isChildCut; //Indicator of Child Cut value for a node
private boolean isMaximum;

public Node() {
	
	setNodeKey(null);
}

public Node(T nodeKey, S hashNodeKey) {

	this.setNodeKey(nodeKey);
	this.setHashNodeKey(hashNodeKey);
	setNodeNext(this);
	setNodePrev(this);
}

public T getNodeKey() {

	return nodeKey;
}

public S getHashNodeKey() {

	return hashNodeKey;
}

public int compareTo(Node<T,S> other) {
	
	return this.getNodeKey().compareTo(other.getNodeKey());
}

public Node<T,S> getNodeChild() {

	return child;
}

public void setNodeChild(Node<T,S> child) {

	this.child = child;
}

public Node<T,S> getNodeParent() {
	
	return parent;
}

public void setNodeParent(Node<T,S> parent) {
	
	this.parent = parent;
}

public boolean isChildCut() {
	
	return isChildCut; 
}

public void setMarked(boolean isChildCut) {
	
	this.isChildCut = isChildCut;
}

public Node<T,S> getNodeNext() {
	
	return next;
}

public void setNodeNext(Node<T,S> next) {
	
	this.next = next;
}

public Node<T,S> getNodePrev() {
	return prev;
}

public void setNodePrev(Node<T,S> prev) {
	
	this.prev = prev;
}

public void setNodeKey(T nodeKey) {
	
	this.nodeKey = nodeKey;
}

public void setHashNodeKey(S hashNodeKey) {
	
	this.hashNodeKey = hashNodeKey;
}

int getNodeDegree() {
	
	return degree;
}

public void setNodeDegree(int degree) {
	
	this.degree = degree;
}

public boolean isMaximum() {
	
	return isMaximum;
}

void setMaximum(boolean isMaximum) {
	
	this.isMaximum = isMaximum;
}

void displayNode() {

	System.out.println("Node Key:"+this.getNodeKey());
	System.out.println("Node Hash Key:"+this.getHashNodeKey());
	System.out.println("Node Degree:"+this.getNodeDegree());
	System.out.println("Node Child:"+(this.getNodeChild() != null ? this.getNodeChild().getNodeKey() : null));
	System.out.println("Node Parent:"+(this.getNodeParent() != null ? this.getNodeParent().getNodeKey() : null));
	System.out.println("Node Next:"+(this.getNodeNext() != null ? this.getNodeNext().getNodeKey() : null));
	System.out.println("Node Prev:"+(this.getNodePrev() != null ? this.getNodePrev().getNodeKey() : null));
	System.out.println("Node isChildCut:"+this.isChildCut());
	System.out.println("Node isMaximum:"+this.isMaximum());
	
}

}
