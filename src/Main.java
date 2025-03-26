import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage imagemPilha = ImageIO.read(new File("imag.png"));
        BufferedImage imagemFila = ImageIO.read(new File("imag.png"));

        AnimationFrame frameFila = new AnimationFrame("Fila", 100, 100);
        AnimationFrame framePilha = new AnimationFrame("Pilha", 100, 300);

        new Thread(() -> {
            FloodFill.floodFill(imagemPilha, 0, 0, true, framePilha);
            try {
                ImageIO.write(imagemPilha, "png", new File("pilha.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            FloodFill.floodFill(imagemFila, 0, 0, false, frameFila);
            try {
                ImageIO.write(imagemFila, "png", new File("fila.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}