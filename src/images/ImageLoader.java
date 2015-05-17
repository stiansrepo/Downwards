package images;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * @author DesktopStian
 */
public class ImageLoader {

    String path;
    FileInputStream filestream;
    File file;
    BufferedImage[] bi;
    String[] filepaths;

    public ImageLoader() throws FileNotFoundException, IOException {
        filepaths = new String[]{"player.png", "monster.png", "floor.png", "wall.png", "water.png", "silt.png", "door.png", "chest.png", "rubble.png", "grit.png"};
        int cnt=0;
        for (String s : filepaths) {
            getClass().getClassLoader().getResource(s).getPath();
            file = new File(path);
            bi[cnt] = ImageIO.read(file);
        }
    }

    public enum ImageType {

        PLAYER,
        MONSTER,
        TILE;
    }
}
