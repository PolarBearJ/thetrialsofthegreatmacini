import java.awt.*;
/*
A left over object which was first used to have health packs
but I scrapped the idea and added health potions but this remains in case
I want to continue with the game and add something similar later.


 */
public class Crate extends GameObject {
    public Crate(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(x, y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
