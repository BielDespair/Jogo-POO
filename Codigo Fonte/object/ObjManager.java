package object;

import hyrulean.GamePanel;
import java.awt.Graphics2D;


// Adiciona os objetos existentes ao mapa e lida com sua interação com o jogador.
public class ObjManager{
    
    public SuperObject objList[];
    private GamePanel gp;
    
    public ObjManager(GamePanel gp) {
        objList = new SuperObject[10];
        this.gp = gp;
    }
    
    public void setObjects(){
        Chest chest1 = new Chest("chest1", "/res/objects/bau.png", 12*gp.tileSize, 14*gp.tileSize);
        objList[0] = chest1;
        //Box box1 = new Box("box1","res/objects/box.png", 12*gp.tileSize, 14*gp.tileSize);
        
    }
    
    public void draw(Graphics2D g2) {
        for (int i = 0; i < objList.length; i++){
            if (objList[i] != null){
                objList[i].draw(g2, gp);
            }
        }
    }
}
