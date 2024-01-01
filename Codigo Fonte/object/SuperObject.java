
package object;

import hyrulean.GamePanel;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import loaders.ImageLoader;


// Super classe de todos objetos
public abstract class SuperObject{
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public boolean movable = false;
    public boolean collisionOn = false;
    public int state = 5;
    public int speed = 0;
    
    public Rectangle hitbox = new Rectangle(0, 0, 48, 48);
    public Rectangle defaultHitbox = new Rectangle(0, 0, 48, 48);
    public int worldX, worldY;
 
    public SuperObject(String name, String imgPath, int worldX, int worldY) {
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;
        initializeImage(imgPath);
    }
    
    public void draw(Graphics2D g2, GamePanel gp){
        
        int screenX = gp.getScreenXPosition(worldX);
        int screenY = gp.getScreenYPosition(worldY);
        if (gp.isOnScreenRange(worldX, worldY)){
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
        
        else if (gp.isOnBorder(worldX, worldY)){
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            
        }
        if (gp.debug){
            g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
    
    public void initializeImage(String imgPath) {
        ImageLoader imgLoader = new ImageLoader();
        this.image = imgLoader.loadImage(imgPath);
    }

}
