package map;
import java.awt.image.BufferedImage;


// Um único tile com sua subimgem da tile sheet
public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    public Tile(BufferedImage image) {
        this.image = image;
    }
    
}
