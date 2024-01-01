/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loaders;
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class mapLoader {
    public int[][] map;
    public int width;
    public int height;

    public mapLoader (String path) {
        loadMap(path);
    }
    
    // Lê o arquivo do mapa e inicializa uma matrix de 2 dimensões com as informações de posição de tiles.
    public void loadMap(String path){
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            String dimensions[] = line.split("x");
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
            map = new int[height][];
            for (int row = 0; row < height; row++){
                line = br.readLine();
                int[] cols = new int[width];
                String numbers[] = line.split(" ");
                for (int col = 0; col < width; col++){
  
                    int tileNum = Integer.parseInt(numbers[col]);
                    cols[col] = tileNum;
                    
                }
            map[row] = cols;
            }
            br.close();
            
        }
        
        catch (Exception e) {
            System.out.println("There was an error when trying to read the map file " + e);
        }
    }
}