import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
/*
Very important class that is used to
load all of the images in the game
 */
public class BufferedImageLoader {
    private BufferedImage image;
    public BufferedImage loadImage(String path) throws IOException {
        image = ImageIO.read(getClass().getResource(path));
        return image;
    }

}
