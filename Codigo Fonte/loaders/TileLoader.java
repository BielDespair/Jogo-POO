package loaders;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import map.Tile;


// Classe que inicializa todas subimagens de um tile sheet
public class TileLoader {
    public ImageLoader loader = new ImageLoader();
    public ArrayList<Tile> tileSets = new ArrayList<Tile>();   

    
    // Inicializa uma imagem e pega todas subimagens de acordo com a largura e altura dos tiles e adiciona a um array de objetos Tile.
    public void loadTiles(String path, int sheetLines, int sheetCols, int tileWidth, int tileHeight){
        BufferedImage spriteSheet = loader.loadImage(path);
        SpriteSheet sheet = new SpriteSheet(spriteSheet);
        Tile[] tiles = new Tile[sheetLines*sheetCols];
        
        int index = 0;
        for (int i = 0; i < sheetLines; i++){
            for (int j = 0; j < sheetCols; j++) {
                Tile tile = new Tile(sheet.grabImage(j, i, tileWidth, tileHeight));
                tiles[index] = tile;
                
                index++;
            }
        }
        System.out.println("Sprites " + path + " carregados com sucesso");
        for (int i = 0; i < tiles.length; i++){
        tileSets.add(tiles[i]);
                }
    }
}
