package hyrulean;
import Entity.Player;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import map.TileManager;
import object.ObjManager;




// Esta classe lida com a janela do jogo e sua atualização (FPS)
public class GamePanel extends JPanel implements Runnable{
    public boolean debug;
    
    // Configurações Gráficas
    final int starterTileSize = 16; // Tiles de 16x16
    public final int scale = 3; // Escalamento dos tiles para exibição na tela
    
    public final int tileSize = starterTileSize*scale; // Tamanho dos tiles com o escalonamento
    public final int maxScreenTilesHorizontal = 20; // Máximo de tiles horizontais
    public final int maxScreenTilesVertical = 15; // Máximo de tiles verticais
    
    public final int screenWidth = tileSize * maxScreenTilesHorizontal; // Largura da tela
    public final int screenHeight = tileSize * maxScreenTilesVertical; // Altura da tela
    
    
    //Input e Jogador
    private KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this, keyH);
    
    
    // Mapa e tiles
    TileManager tileM = new TileManager(this);
    //Configurações do mapa
    public final int maxWorldCol = 30;
    public final int maxWorldRow = 20;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    //Objetos
    public ObjManager objM = new ObjManager(this);
    
    //Colisão
    public CollisionChecker cChecker;
    // FPS
    public int FPS = 60;
    public int drawCount = 0;
    Thread gameThread;
    
    public GamePanel() {
        // Classe para checar a colisão do jogador
        cChecker = new CollisionChecker(this);
        // Inicializa a janela
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        
        // Faz com que a janela escute por inputs do teclado
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
    }
    //Inicializa a thread da janela
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
   
    public void setupGame(){
        objM.setObjects();
    }
    @Override
    //Loop principal do jogo, aonde será rodado até que o jogador feche a janela.
    public void run() {

        double drawInterval = 1000000000/FPS; // Atualiza a tela a cada 0.16 segundos.
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while(gameThread != null) {
            
            //Cálculo do delta time
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime)/ drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            
            if(timer >= 1000000000){
                this.drawCount = 0;
                timer = 0;
            }
            
        }
    }
    
    // Atualiza todas as informações dentro da função
    public void update(){
        player.update();
    }
    
    // Desenha os gráficos na tela
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //Desenha o mapa
        tileM.draw(g2);
        //Desenha o jogador
        player.draw(g2);
        //Desenha os objetos
        objM.draw(g2);
        
        // Debug
        if (debug) {
            g2.drawString(String.valueOf("DEBUG ON"), 1, 10);
            g2.drawString(String.valueOf("FRAME:" + drawCount), 1, 25);
            g2.drawString(String.valueOf("STATE: "+ player.state), 1, 40);
            g2.drawString(String.valueOf("PLAYER X: " + player.worldX), 1, 55);
            g2.drawString(String.valueOf("PLAYER Y: " + player.worldY), 1, 70);
            }
        
        g2.dispose();
    }
    
    public int getScreenXPosition(int x){
        int rightOffset = screenWidth - player.screenX;
        if (player.screenX > player.worldX){
            return x;
        }
        else if (rightOffset > worldWidth - player.worldX){
            return screenWidth - (worldWidth - x);
        }
        else {
            return x - player.worldX + player.screenX;
            
        }

    }
    public int getScreenYPosition(int y){
        int bottomOffset = screenHeight - player.screenY;
        if (player.screenY > player.worldY){
            return y;
        }
        else if (bottomOffset > worldHeight - player.worldY){
            return screenHeight - (worldHeight - y);
            
        }
        else {
            return y - player.worldY + player.screenY;
        }
    }
    
    
    public boolean isOnScreenRange(int x, int y){
        // Verifica se a coordenada para desenho está no raio do jogador, do contrário, gastaria recursos para desenhar coisas fora do campo de visão da janela
        return x + tileSize > player.worldX - player.screenX &&
                        x - tileSize < player.worldX + player.screenX &&
                        y + tileSize  > player.worldY - player.screenY &&
                        y - tileSize < player.worldY + player.screenY;
    }
    
    public boolean isOnBorder(int x, int y){
        int rightOffset = screenWidth - player.screenX;
        int bottomOffset = screenHeight - player.screenY;
        return  player.screenX > player.worldX ||
                player.screenY > player.worldY ||
                rightOffset > worldWidth - player.worldX ||
                bottomOffset > worldHeight - player.worldY;
    }
}
