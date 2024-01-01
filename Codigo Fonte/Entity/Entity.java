
package Entity;

import hyrulean.GamePanel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import loaders.SpriteSheet;


// Superclasse para todas entidades no mapa (jogador, inimigos, npcs...)
public abstract class Entity implements Update, Draw{
    // Informações de posição e velocidade
    
    public int worldX, worldY; // Posição ''real''
    public int screenX, screenY; // Posição na janela
    public int speed;
    
    // A ''ação'' atual da entidade.
    public int state;
    protected int lastState;
    
    //Animação
    protected double animationFrame;
    protected double animationSpeed;
    
    //Se a entidade possui texturas
    protected boolean hasTexture;
    protected boolean hasSpriteSheet;
    
    //Informações do sprite sheet
    protected int sheetLines;
    protected int sheetRows;
    protected int sheetWidth;
    protected int sheetHeight;
    
    //Classes de manuseio
    protected BufferedImage spriteSheet;
    protected SpriteSheet sheet;
    protected ArrayList<BufferedImage[]> sprites;
    protected GamePanel gp;
    
    //Colisão
    public Rectangle hitbox;
    public int defaultHitboxX, defaultHitboxY;
    public boolean collisionOn = false;
    
}
