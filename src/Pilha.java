public class Pilha {
    private Node topo;

    public void push(int x, int y) {
        Node node = new Node(x, y);
        node.proximo = topo;
        topo = node;
    }

    public Node pop() {
        if (topo == null){
            return null;
        }
        Node node = topo;
        topo = topo.proximo;
        return node;
    }

    public boolean vazia() {
        return topo == null;
    }
}
