import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class FloodFill {
    private static final int NOVA_COR = 0xFFA032C7;
    private static int frameCont = 0;

    public static void floodFill(BufferedImage img, int startX, int startY, boolean usePilha, AnimationFrame frame) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        int corInicial = img.getRGB(startX, startY);

        if (corInicial == NOVA_COR) return;

        Pilha pilha = new Pilha();
        Fila fila = new Fila();

        if (usePilha){
            pilha.push(startX, startY);
        }
        else {
            fila.enqueue(startX, startY);
        }

        File frames = new File("frames");
        if (!frames.exists()) {
            frames.mkdir();
        }

        while ((usePilha && !pilha.vazia()) || (!usePilha && !fila.vazia())) {
            Node node = usePilha ? pilha.pop() : fila.dequeue();
            int x = node.x, y = node.y;

            if (x < 0 || x >= largura || y < 0 || y >= altura || img.getRGB(x, y) != corInicial) {
                continue;
            }

            img.setRGB(x, y, NOVA_COR);
            SwingUtilities.invokeLater(() -> frame.updateImage(img));
            salvarFrame(img, usePilha ? "pilha" : "fila");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    private static void salvarFrame(BufferedImage img, String tipo) {
        try {
            String nomeArquivo = String.format("frames/%s_frame_%04d.png", tipo, frameCont++);
            ImageIO.write(img, "png", new File(nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
