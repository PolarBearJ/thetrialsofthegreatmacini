import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
/*
This class is the blobs at the beginning of the game
the blobs move in a random direction and do NO damage


 */
public class Enemy extends GameObject {
    private Handler handler;
    private final Game game;
    private int sprnum = 0;
    private final Sound sound;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    private int ticks = 0;
    private BufferedImage Sprite;
    public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss, Game game, Sound sound) {
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
    }

    public void tick() {
        ticks++;
        x += velX;
        y += velY;
        choose = r.nextInt(10);

        for(int i = 0; i < handler.gameObjects.size(); i++){
            GameObject tempOjbect = handler.gameObjects.get(i);
            if(tempOjbect.getId()==ID.BLOCK){
                if(getBoundsBig().intersects(tempOjbect.getBounds())){
                    x += (velX*2)*-1;
                    y += (velY*2)*-1;
                    velX *= -1;
                    velY *= -1;
                } else if(choose==0){
                    velX=(r.nextInt(4- -4) + -4);
                    velY=(r.nextInt(4- -4) + -4);

                }
            }
            if(tempOjbect.getId()==ID.BULLET){
                if(getBounds().intersects(tempOjbect.getBounds())) {
                    sound.playSound("Night_elves_hit.wav");
                    hp -= 25;
                    handler.removeObject(tempOjbect);
                }
            }

        }
        if(hp <= 0) handler.removeObject(this);
        if(ticks % 15 == 0) sprnum++;
        if(sprnum>1){
            sprnum = 0;
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Draw shadow
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.drawImage(game.shadow, x-4, y+15, 64, 64, null);
        if(velX > 0 ){
            Sprite = game.blobright[sprnum];
        } else {
            Sprite = game.blobleft[sprnum];
        }
        alpha = 1f;
        alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g.drawImage(Sprite, x, y, 64, 64, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 84,84);
    }
    public Rectangle getBoundsBig() {
        return new Rectangle(x-16, y-16, 64,64);
    }
}
