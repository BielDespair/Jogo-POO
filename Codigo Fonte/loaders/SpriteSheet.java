/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loaders;

import java.awt.image.BufferedImage;


// Classe que gera uma subimagem de uma imagem.
public class SpriteSheet {
    private final BufferedImage spriteSheet;

    public SpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
    }
    
    public BufferedImage grabImage(int col, int row, int width, int height){
        return spriteSheet.getSubimage(col*width, row*height, width, height);
    }
    
    public BufferedImage grabImage(int col, int row, int width, int height, int margin){
        return spriteSheet.getSubimage(col*width+margin, row*height+margin, width, height);
    }
   
}
