package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Objects;

public class ImageUtils {

    public static BufferedImage SwapPlayerSprite(BufferedImage _sprite)
    {
        BufferedImage sprite = new BufferedImage(_sprite.getWidth(), _sprite.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < sprite.getHeight(); y++)
            for(int x = 0; x < sprite.getWidth(); x++) {
                sprite.setRGB(x, y, _sprite.getRGB(x, y));
                if (_sprite.getRGB(x, y) == new Color(133, 174, 110, 255).getRGB())
                    sprite.setRGB(x, y, Color.red.getRGB());
            }
        return sprite;
    }

    public static void SaveImage(String path, BufferedImage image) throws IOException {
        System.out.println(path);
        File output = new File("res/" + path);
        output.createNewFile();

        ImageIO.write(image, "png", output);
    }

    public static BufferedImage ReadImage(String path) throws IOException {
        return ImageIO.read(Objects.requireNonNull(ImageUtils.class.getResourceAsStream(path)));
    }



}
