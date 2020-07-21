import java.awt.*;
/*
Block that the player cannot walk through
(made for parameter of the levels)

 */
public class Block  extends GameObject{
    Game game;
    public Block(int x, int y, ID id, SpriteSheet ss, Game game) {
        super(x, y, id, ss);
        this.game = game;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
g.drawImage(game.wall, x, y, 64, 64,null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 64, 64);
    }
}
