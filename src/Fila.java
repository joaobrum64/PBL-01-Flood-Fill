public class Fila {
    private Node inicio, fim;

    public void enqueue(int x, int y) {
        Node node = new Node(x, y);
        if (fim != null){
            fim.proximo = node;
        }
        fim = node;
        if (inicio == null){
            inicio = node;
        }
    }

    public Node dequeue() {
        if (inicio == null) {
            return null;
        }
        Node node = inicio;
        inicio = inicio.proximo;
        if (inicio == null){
            fim = null;
        }
        return node;
    }

    public boolean vazia() {
        return inicio == null;
    }
}