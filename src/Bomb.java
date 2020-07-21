import java.awt.*;
/*
Used by the minions in the first boss fight
instead of creating 10 objects the minions now create
1 which grows over time and does damage to the player

 */
public class Bomb extends GameObject {
    private final Handler handler;
    private final Game game;
    private final Sound sound;
    private int shotTimer = 0;
    private int radius = 40;

    public Bomb(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game, Sound sound){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
    }

    public void tick() {
        shotTimer++;
        if(shotTimer % 60 == 0){
            handler.removeObject(this);
        }
        if(shotTimer % 10 == 0){
            radius += 20;
            x-= 10;
            y-= 10;
        }
        for(int i = 0; i<handler.gameObjects.size()-1; i++){
            GameObject tempObject = handler.gameObjects.get(i);
            if(tempObject.getId()==ID.PLAYER){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.setPhealth(game.getPhealth()-10);
                    sound.playSound("Warrior_hit.wav");
                }
            }
        }

    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g.drawImage(game.snowsplotion, x, y, radius, radius, null);
//        g.setColor(Color.green);
//        g.drawRect((int) (x+radius*.1), (int) (y+radius*.1), (int)(radius-radius*0.2), (int)(radius-radius*0.2));
    }




    public Rectangle getBounds() {
        return new Rectangle((int) (x+radius*.1), (int) (y+radius*.1), (int)(radius-radius*0.2), (int)(radius-radius*0.2));
    }
}
