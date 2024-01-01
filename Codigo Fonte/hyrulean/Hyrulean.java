package hyrulean;
import java.io.IOException;
import javax.swing.JFrame;
import loaders.ImageLoader;
import loaders.TileLoader;


// Classe main.
public class Hyrulean {
    public ImageLoader imgLoader = new ImageLoader();
    
    public static void main(String[] args) throws IOException {
        // Inicialização da janela
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Hyrulean");
        
        
        // Adiciona o gamepanel para ser rodado na janela
        window.add(gamePanel);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
        
        //Inicializa as posições iniciais dos objetos.
        gamePanel.setupGame();
        //Inicializa a thread do gamepanel
        
        gamePanel.startGameThread();
    }

}