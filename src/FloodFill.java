import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class FloodFill {
    private static final int NOVA_COR = 0xFFA032C7;
    private static int frameCont = 0;

    public static void floodFill(BufferedImage img, int startX, int startY, boolean usePilha, AnimationFrame frame) {

        //Obtendo dimensões da imagem e cor inicial do ponto de partida definido.

        int largura = img.getWidth();
        int altura = img.getHeight();
        int corInicial = img.getRGB(startX, startY);

        //Evita loop infinito caso corInicial = NOVA_COR

        if (corInicial == NOVA_COR) return;

        //Cria pilha e fila para floodFill

        Pilha pilha = new Pilha();
        Fila fila = new Fila();

        //Adiciona o ponto inicial para pilha e fila

        if (usePilha){
            pilha.push(startX, startY);
        }
        else {
            fila.enqueue(startX, startY);
        }

        //Diretório para frames da animação

        File pastaFrames = new File("frames");
        if (!pastaFrames.exists()) {
            pastaFrames.mkdir();
        } else {
            File[] arquivos = pastaFrames.listFiles();
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    arquivo.delete();
                }
            }
        }

        //Loop que persiste enquanto houver elementos na estrutura escolhida

        while ((usePilha && !pilha.vazia()) || (!usePilha && !fila.vazia())) {

            //Remove um ponto

            Node node = usePilha ? pilha.pop() : fila.dequeue();
            int x = node.x, y = node.y;

            //Verifica se o ponto está dentro dos limites e tem a corInicial

            if (x < 0 || x >= largura || y < 0 || y >= altura || img.getRGB(x, y) != corInicial) {
                continue;
            }

            //Pinta o ponto com a nova cor, atualiza a animação e salva o frame

            img.setRGB(x, y, NOVA_COR);
            SwingUtilities.invokeLater(() -> frame.updateImage(img));
            salvarFrame(img, usePilha ? "pilha" : "fila");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Adiciona os pontos adjacentes

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

    //Salva cada frame em um arquivo png e os numera
    private static void salvarFrame(BufferedImage img, String tipo) {
        try {
            String nomeArquivo = String.format("frames/%s_frame_%04d.png", tipo, frameCont++);
            ImageIO.write(img, "png", new File(nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
