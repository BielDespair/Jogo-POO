
package object;

import Entity.Entity;


// Objeto chave
public class Chest extends SuperObject{
    
    public Item chestContent;
    public Chest(String name, String imgPath, int worldX, int worldY) {
        super(name, imgPath, worldX, worldY);
        collision = true;
        movable = true;
    }
    
    public void interation(Entity entity){
        
    }
    
    
    
}
