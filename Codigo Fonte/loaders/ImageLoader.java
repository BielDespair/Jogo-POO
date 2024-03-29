package loaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


// Classe para inicialização de imagens
public class ImageLoader {
    private BufferedImage image;
    
    public BufferedImage loadImage(String path){
        try {
        image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
