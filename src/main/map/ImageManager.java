package main.map;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Michael
 */
public class ImageManager {

    public static HashMap<String, Image> images = new HashMap<>();

    public static Image getImage(String name) {
        Image image = images.get(name);
        if (image == null) {
            try {
                image = ImageIO.read(
                        ClassLoader.getSystemResource("main/images/" + name));
                images.put(name, image);
            } catch (IOException ex) {
                Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return image;
    }

}
