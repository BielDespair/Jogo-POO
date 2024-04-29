package map;

import loaders.TileLoader;
import hyrulean.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import loaders.mapLoader;

// Classe que lida com os tiles de cada camada do mapa.
public class TileManager {

    private final GamePanel gp;
    private final TileLoader tileLoader = new TileLoader();
    public mapLoader[] layers = this.getLayers();
    public final mapLoader collision = new mapLoader("/res/maps/forest/collision.txt");

    //Tile Sets do mapa.
    public ArrayList<Tile> tileSets;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tileSets = tileLoader.tileSets;
        loadTiles();
    }

    // Inicializa todas as imagens e adiciona ao array de tiles.
    public void loadTiles() {
        tileLoader.loadTiles("/res/tiles/Forest Cliff.png", 16, 12, 16, 16);
        tileLoader.loadTiles("/res/tiles/Water Tileset.png", 10, 11, 16, 16);
        tileLoader.loadTiles("/res/tiles/Forest Tilesett.png", 20, 11, 16, 16);
        tileLoader.loadTiles("/res/tiles/Forest Structures.png", 22, 26, 16, 16);
        tileLoader.loadTiles("/res/tiles/Trees.png", 33, 24, 16, 16);
        tileLoader.loadTiles("/res/tiles/Forest Props.png", 37, 30, 16, 16);

        // Adiciona colisão aos tiles com index 0
        tileSets.get(0).collision = true;

    }

    public mapLoader[] getLayers() {
        mapLoader[] layers = new mapLoader[4];
        layers[0] = new mapLoader("/res/maps/forest/layer1.txt");
        layers[1] = new mapLoader("/res/maps/forest/layer2.txt");
        layers[2] = new mapLoader("/res/maps/forest/layer3.txt");
        layers[3] = new mapLoader("/res/maps/forest/layer4.txt");

        return layers;

    }

    // Pega cada mapa (camada) e verifica qual tile deve ser desenhado em qual posição.
    public void draw(Graphics2D g2) {

        // Variáveis a ser incrementada no loop
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // Posição ''real'' do tile no mundo
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Posição do tile na tela
            int screenX = gp.getScreenXPosition(worldX);
            int screenY = gp.getScreenYPosition(worldY);

            // Estas variáveis tem a função de parar a câmera ao chegar na borda do mapa
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }

            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // Verifica se o tile está no raio do jogador, do contrário, gastaria recursos para desenhar coisas fora do campo de visão da janela
            if (gp.isOnScreenRange(worldX, worldY)) {

                // Desenhando todas as camadas
                this.drawTile(this.layers[0], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[1], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[2], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[3], worldRow, worldCol, screenX, screenY, g2);

                // Debug
                if (gp.debug) {
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                }

            } // Se o jogador chegou na borda do mapa
            else if (gp.isOnBorder(worldX, worldY)) {

                // Desenhando todas as camadas
                this.drawTile(this.layers[0], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[1], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[2], worldRow, worldCol, screenX, screenY, g2);
                this.drawTile(this.layers[3], worldRow, worldCol, screenX, screenY, g2);

                // Debug
                if (gp.debug) {
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                    g2.dispose();
                }

            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    // Método que desenha uma única camada
    public void drawTile(mapLoader layer, int worldRow, int worldCol, int screenX, int screenY, Graphics2D g2) {

        int tileNum = layer.map[worldRow][worldCol];
        if (tileNum > -1) {
            g2.drawImage(tileSets.get(tileNum).image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public void drawLayer(int layer, Graphics2D g2) {
        // Variáveis a ser incrementada no loop
        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            // Posição ''real'' do tile no mundo
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;

            // Posição do tile na tela
            int screenX = gp.getScreenXPosition(worldX);
            int screenY = gp.getScreenYPosition(worldY);

            // Estas variáveis tem a função de parar a câmera ao chegar na borda do mapa
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }

            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if (rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if (bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            // Verifica se o tile está no raio do jogador, do contrário, gastaria recursos para desenhar coisas fora do campo de visão da janela
            if (gp.isOnScreenRange(worldX, worldY)) {

                // Desenhando todas as camadas
                this.drawTile(this.layers[layer], worldRow, worldCol, screenX, screenY, g2);

                // Debug
                if (gp.debug && layer == 3) {
                    g2.setColor(Color.BLACK);
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                }

            } // Se o jogador chegou na borda do mapa
            else if (gp.isOnBorder(worldX, worldY)) {

                // Desenhando todas as camadas
                this.drawTile(this.layers[layer], worldRow, worldCol, screenX, screenY, g2);

                // Debug
                if (gp.debug && layer == 3) {
                    
                    g2.setColor(Color.BLACK);
                    g2.drawRect(screenX, screenY, gp.tileSize, gp.tileSize);
                }

            }

            worldCol++;
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

}
