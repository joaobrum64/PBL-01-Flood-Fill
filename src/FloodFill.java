import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FloodFill {
    private static final int BRANCO = 0xFFFFFFFF;
    private static final int NOVA_COR = 0xFFA032C7;

    public static void floodFill(BufferedImage img, int startX, int startY, boolean usePilha) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        int corFundo = img.getRGB(startX, startY);

        if (corFundo != BRANCO) return;

        Pilha pilha = new Pilha();
        Fila fila = new Fila();

        if (usePilha) {
            pilha.push(startX, startY);
        } else {
            fila.enqueue(startX, startY);
        }

        while ((usePilha && !pilha.vazia()) || (!usePilha && !fila.vazia())) {
            Node node = usePilha ? pilha.pop() : fila.dequeue();
            int x = node.x;
            int y = node.y;

            if (x < 0 || x >= largura || y < 0 || y >= altura || img.getRGB(x, y) != BRANCO) {
                continue;
            }

            img.setRGB(x, y, NOVA_COR);
            salvarFrame(img, "frame_" + x + "_" + y + ".png");

            if (usePilha) {
                pilha.push(x + 1, y);
                pilha.push(x - 1, y);
                pilha.push(x, y + 1);
                pilha.push(x, y - 1);
            } else {
                fila.enqueue(x + 1, y);
                fila.enqueue(x - 1, y);
                fila.enqueue(x, y + 1);
                fila.enqueue(x, y - 1);
            }
        }
    }

    private static void salvarFrame(BufferedImage img, String nomeArquivo) {
        try {
            ImageIO.write(img, "png", new File("frames/" + nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}