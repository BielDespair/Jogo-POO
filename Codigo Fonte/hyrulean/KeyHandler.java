package hyrulean;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



// Esta classe lida com os inputs de tecla
public class KeyHandler implements KeyListener{
    //Retorna True se a tecla de mover o personagem para cima é apertada
    public boolean upPressed, leftPressed, rightPressed, downPressed, spacePressed, quotePressed, shiftPressed;
   
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Checa se o jogador apertou a tecla W
        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        // Checa se o jogador apertou a tecla A
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        // Checa se o jogador apertou a tecla S
        if (code == KeyEvent.VK_S){
            downPressed = true;   
        }
        // Checa se o jogador apertou a tecla D
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        
        if (code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        
        if (code == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
        
        if (code == KeyEvent.VK_QUOTE){
            quotePressed = true;
        }
        
        if (code == KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        
        // Checa se o soltou apertou a tecla W
        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        // Checa se o jogador soltou a tecla A
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        // Checa se o jogador soltou a tecla S
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        // Checa se o jogador soltou a tecla D
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        
        if (code == KeyEvent.VK_QUOTE) {
            quotePressed = false;
        }
        
        if (code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }
    }
}
