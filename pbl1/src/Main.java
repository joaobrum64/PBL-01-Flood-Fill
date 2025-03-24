import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public static void main(String[] args) throws IOException {
    BufferedImage imagem = ImageIO.read(new File("imag.png"));
    FloodFill.floodFill(imagem, 0, 0, true);
    ImageIO.write(imagem, "png", new File("pilha.png"));
    imagem = ImageIO.read(new File("imag.png"));
    FloodFill.floodFill(imagem, 0, 0, false);
    ImageIO.write(imagem, "png", new File("fila.png"));
}