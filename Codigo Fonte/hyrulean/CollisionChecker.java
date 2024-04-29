package hyrulean;

import Entity.Entity;
import object.ObjManager;
import object.SuperObject;


// Esta classe lida com a colisão do jogador com objetos colidíveis.
public class CollisionChecker {
    
    private GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }
    
    
    
    // Checa se os 2 tiles na direção em que o jogador está se movendo possuem colisão ou não.
    public void checkTile(Entity entity){
        
        // Pega a posição dos dois tiles acima, á esquerda, á direita e abaixo do jogador
        int entityLeftWorldX = entity.worldX + entity.hitbox.x;
        int entityRightWorldX = entity.worldX + entity.hitbox.x + entity.hitbox.width;
        int entityTopWorldY = entity.worldY + entity.hitbox.y;
        int entityBottomWorldY = entity.worldY + entity.hitbox.y + entity.hitbox.height;
        
        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;
        
        // tileNum é o ID do tile pego na matriz do mapa.
        int tileNum1, tileNum2;
        
        switch (entity.state){
            // Se o jogador estiver se movendo para cima...
            case 0:
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.collision.map[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.collision.map[entityTopRow][entityRightCol];
                if (tileNum1 == 0){
                    entity.collisionOn = true;
                }
                
                else if (tileNum2 == 0){
                    entity.collisionOn = true;
                }
                break;
                
            case 1:
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.collision.map[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.collision.map[entityBottomRow][entityLeftCol];
                if (tileNum1 == 0){
                    entity.collisionOn = true;
                }
                
                else if (tileNum2 == 0){
                    entity.collisionOn = true;
                }
                break;
            case 2:
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.collision.map[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.collision.map[entityBottomRow][entityRightCol];
                if (tileNum1 == 0){
                    entity.collisionOn = true;
                }
                
                else if (tileNum2 == 0){
                    entity.collisionOn = true;
                }
                break;
            case 3:
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.collision.map[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.collision.map[entityBottomRow][entityRightCol];
                if (tileNum1 == 0){
                    entity.collisionOn = true;
                }
                
                else if (tileNum2 == 0){
                    entity.collisionOn = true;
                }
                break;
                    /*if(gp.tileM.tileSets.get(tileNum1).collision == true || gp.tileM.tileSets.get(tileNum1).collision == true){
                        entity.collisionOn = true;
                    }*/
                    
                
                    /*if(gp.tileM.tileSets.get(tileNum2).collision == true || gp.tileM.tileSets.get(tileNum2).collision == true){
                        entity.collisionOn = true;
                    }*/
            default:
                break;
        }
    }
    
    public int checkObject(Entity entity, boolean player, ObjManager objM) {
        int index = 999;
        
        int i = 0;
        for (SuperObject obj : objM.objList) {
            if (obj != null) {
                // Colisão ''real'' da entidade no mapa
                entity.hitbox.x = entity.worldX + entity.hitbox.x;
                entity.hitbox.y = entity.worldY + entity.hitbox.y;
                
                // Colisão ''real'' do objeto no mapa
                obj.hitbox.x = obj.worldX + obj.hitbox.x;
                obj.hitbox.y = obj.worldY + obj.hitbox.y;
                
                switch (entity.state) {
                    case 0:
                        entity.hitbox.y -= entity.speed;
                        if (entity.hitbox.intersects(obj.hitbox)){

                            enableCollision(entity, obj, player, i);
                            moveObj(entity, obj);
                            System.out.println("Colisao acima");
                        }
                            break;
                    case 1:
                        entity.hitbox.x -= entity.speed;
                        if (entity.hitbox.intersects(obj.hitbox)){
                            enableCollision(entity, obj, player, i);
                            moveObj(entity, obj);
                            System.out.println("Colisao a esquerda");
                        }
                        break;
                    case 2:
                        entity.hitbox.y += entity.speed;
                        if (entity.hitbox.intersects(obj.hitbox)){
                            enableCollision(entity, obj, player, i);
                            moveObj(entity, obj);
                            System.out.println("Colisao abaixo");
                        }
                        break;
                    case 3:
                        entity.hitbox.x += entity.speed;
                        if (entity.hitbox.intersects(obj.hitbox)){
                            enableCollision(entity, obj, player, i);
                            moveObj(entity, obj);
                            System.out.println("Colisao na direita");
                        }
                        break;
                }
                
                entity.hitbox.y = entity.defaultHitboxY;
                entity.hitbox.x = entity.defaultHitboxX;
                obj.hitbox.x = obj.defaultHitbox.x;
                obj.hitbox.y = obj.defaultHitbox.y;
            }
            i++;
        }
        return index;
        
    }
    
    private int enableCollision(Entity entity, SuperObject obj, boolean player, int i){
        if (obj.collision) {
        entity.collisionOn = true;
        }
        if(player) {
            return i;
        }
        return 999;
    }
    
    
    private void moveObj(Entity entity, SuperObject obj){
        if (obj.movable) {
            switch (entity.state){
                case 0:
                    obj.worldY -= entity.speed;
                    break;
                case 1:
                    obj.worldX -= entity.speed;
                    break;
                case 2:
                    obj.worldY += entity.speed;
                    break;
                case 3:
                    obj.worldX += entity.speed;
                    break;
            }
        }
    }

}
