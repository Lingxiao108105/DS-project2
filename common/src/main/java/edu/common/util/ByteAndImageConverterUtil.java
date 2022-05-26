package edu.common.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteAndImageConverterUtil {

    public static WritableImage bytesToImage(byte[] imageBytes){
        if(imageBytes==null){
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(byteArrayInputStream);
        } catch (
                IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        WritableImage writableImage = SwingFXUtils.toFXImage(bufferedImage, null);
        return writableImage;
    }

    public static byte[] imageToBytes(WritableImage writableImage){
        byte[] imageBytes = null;
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100000);
            ImageIO.write(bufferedImage,"png",byteArrayOutputStream);
            imageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageBytes;
    }

}
