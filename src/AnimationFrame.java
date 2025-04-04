import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationFrame extends JFrame {
    private BufferedImage imagem;
    private final int scale = 20;

    public AnimationFrame(String titulo, int x, int y) {
        super(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(12 * scale, 8 * scale);
        setLocation(x, y);
        setVisible(true);
    }

    public void updateImage(BufferedImage novaImagem) {
        this.imagem = novaImagem;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (imagem != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(imagem, 0, 0, imagem.getWidth() * scale, imagem.getHeight() * scale, null);
        }
    }
}
