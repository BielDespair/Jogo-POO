package Entity;

import hyrulean.GamePanel;
import hyrulean.KeyHandler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import loaders.ImageLoader;
import loaders.SpriteSheet;


// O protagonista do jogo. A entidade que o usuário controlará.
public class Player extends Entity{
    // Vida, armadura, ataque, velocidade, mana, items
    public int HP;
    public int keysNumber;
    
    //Manuseio
    private final KeyHandler keyH;
    private final ImageLoader loader = new ImageLoader();

    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH; 
        
        //Colisao (hitbox)
        hitbox = new Rectangle();
        hitbox.x = gp.tileSize/4;
        hitbox.y = (int) ((gp.tileSize)-(gp.tileSize*(0.25)));
        hitbox.width = gp.tileSize/4;
        hitbox.height = gp.tileSize/5;
        
        //TEMPORARIO
        defaultHitboxX = gp.tileSize/4;
        defaultHitboxY = (int) ((gp.tileSize)-(gp.tileSize*(0.25)));
        //TEMPORARIO
        
        //Posição do jogador é (quase) sempre ao centro da tela.
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        setDefaultValues(); // Inicializa os valores iniciais
        initializeSprites(); // Inicializa as imagems do jogador 
    }
    
    // Inicializa os atributos iniciais do jogador
    private void setDefaultValues(){
        // Atributos de gameplay
        speed = 5;
        HP = 100;
        
        //Posição inicial no mapa
        worldX = gp.tileSize*0;
        worldY = gp.tileSize*2;
    }
    
    // Método que lida com o desenho do jogador.
    public void draw(Graphics g2) {
        int frame = (int)animationFrame;
        BufferedImage img = sprites.get(this.state)[frame];
        
        int x = screenX;
        int y = screenY;
        
        if (screenX > worldX){
            x = worldX;
            
        }
        
        if(screenY > worldY){
            
        y = worldY;}

        int rightOffset = gp.screenWidth - screenX;
        if (rightOffset > gp.worldWidth - worldX){
            x = gp.screenWidth - (gp.worldWidth - worldX);
        }
        int bottomOffset = gp.screenHeight - screenY;
        if (bottomOffset > gp.worldHeight - worldY){
            y = gp.screenHeight- (gp.worldHeight - worldY);
        }
        
        
        g2.drawImage(img, x, y, gp.tileSize, gp.tileSize, null);
        if (gp.debug){
            g2.setColor(Color.WHITE);
        g2.drawRect(x+hitbox.x, y+hitbox.y, hitbox.width, hitbox.height);
        }
        
        
    }
    
    
    // Atualiza as informações de posição e animação.
    public void update() {
        runAnimation();
        
        // Aciona o modo debug
        if (keyH.quotePressed){
            gp.debug = !gp.debug;
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        
        
        
        // Realiza a ação do personagem de acordo com a tecla apertada.
        if (keyH.upPressed || keyH.leftPressed || keyH.downPressed || keyH.rightPressed || keyH.spacePressed){
            
            if (keyH.shiftPressed){
                this.speed = 3;
                this.animationSpeed = 0.40;
        }
            else {
                this.speed = 2; this.animationSpeed = 0.21;
            }
            if (keyH.upPressed){
                this.state = 0;
            }
            if (keyH.leftPressed){
                this.state = 1;
            }
            if (keyH.downPressed) {
                this.state = 2;
            }
            if (keyH.rightPressed) {
                this.state = 3;
            }
            if (keyH.spacePressed) {
                this.state = 4;
            }
            
            
            
            //Checagem de colisão
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, true, gp.objM);
            
            // Só deixa o jogador se mover se não estiver havendo colisão na direção do movimento
            if (collisionOn == false) {
                switch (state) {
                    case 0:
                        worldY -= this.speed;
                        break;
                    case 1: worldX -= this.speed; break;
                    case 2: worldY += this.speed; break;
                    case 3: worldX += this.speed; break;
                }
            }
        }
        else {
            if (state != 5){
                lastState = state;
                state = 5;
            }
        }
        
    }
    
    //Método chamado no update() para rodar a animação do jogador
    private void runAnimation(){
        int frameStart = 0;
        int animationLength = sprites.get(state).length;
        
        //Lida com a animação quando o jogador não está realizando nenhuma ação (idle)
        if (state == 5) {
            switch (lastState) {
                case 1:
                    frameStart = 4;
                    animationLength = 0;
                    break;
                    
                case 2:
                    frameStart = 1;
                    animationLength = 0;
                    break;
                
                case 3:
                    frameStart = 7;
                    animationLength = 0;
                    break;
                default:
                    frameStart = 0;
                    animationLength = 0;
                    break;
            }
        }
        
        animationFrame += animationSpeed;
        if (animationFrame >= animationLength){
            animationFrame = frameStart;
        }
        

    }
    
    

    
    // Inicializa o array de sprites com a sequência de imagens para animação.
    public void initializeSprites(){
        //Sprites e Animações
        sheetWidth = 100;
        sheetHeight = 100;
        sheetLines = 6;
        sheetRows = 10;
        spriteSheet = loader.loadImage("/res/player/link.png");
        sheet = new SpriteSheet(this.spriteSheet);
        
        sprites = new ArrayList<>();
        for (int i = 0; i < this.sheetLines; i++) {
            BufferedImage[] animationFrames = new BufferedImage[this.sheetRows];
            for (int j = 0; j < this.sheetRows; j++){
                int escalar = 0;
                if (i == 1){escalar = 1;}
                if (i == 2){escalar = -1;}
                animationFrames[j] = sheet.grabImage(j, i+escalar, sheetWidth, sheetHeight);
            }
            this.sprites.add(animationFrames);
        }
    }
    
    
}
