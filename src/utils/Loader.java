package utils;

import static utils.Constants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Loader {


    public static BufferedImage getAssets(String path) {
        BufferedImage sheet = null;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        try {
            sheet = ImageIO.read(Objects.requireNonNull(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;
    }

    public static int[][] getLevelData(String path) {
        int[][] lvlData = new int[Config.WIDTH_IN_TILES][Config.HEIGHT_IN_TILES];
        BufferedImage sheet = getAssets(path);
        for (int i = 0; i < Config.WIDTH_IN_TILES; i++) {
            for (int j = 0; j < Config.HEIGHT_IN_TILES; j++) {
                int pixel = sheet.getRGB(i, j);
                if (pixel == Color.BLACK.getRGB()) {
                    lvlData[i][j] = 1;
                } else {
                    lvlData[i][j] = 0;
                }
            }
        }
        return lvlData;
    }

    public static Font getFont(String path, float size) {
        Font font = null;
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }
}
