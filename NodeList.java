import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;

/*This class provides an iterator over the nodes in a list.*/

public class NodeList<T extends Comparable<T>, S extends Comparable<S>>
        implements Iterator<Node<T,S>> {

    private Queue<Node<T,S>> elements = new LinkedList<Node<T,S>>();

    public NodeList(Node<T,S> startNode) {
        if (startNode == null) {
            return;
        }

        Node<T,S> currentNode = startNode;
        do {
            elements.add(currentNode);
            currentNode = currentNode.getNodeNext();
        } while (startNode != currentNode);
    }

    public boolean hasNext() {
        return elements.peek() != null;
    }

    public Node<T,S> next() {
        return elements.poll();
    }

    public void remove() {
        throw new UnsupportedOperationException(
                "Remove for Node List has not implemented");
    }
}


