import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.swing.*;

/**
 * Image utilities for filtering images
 *
 * @author Dimitrios Ioannidis
 * @contact dmitris.ioannidis@gmail.com
 * @version 0.1
 * @since 2021-05-13
 */

/*
References
https://www.geeksforgeeks.org/image-procesing-java-set-6-colored-image-sepia-image-conversion/
https://www.geeksforgeeks.org/image-processing-in-java-set-3-colored-image-to-greyscale-image-conversion/
https://www.geeksforgeeks.org/image-processing-java-set-10-watermarking-image/
https://www.geeksforgeeks.org/image-processing-java-set-8-creating-mirror-image/

https://www.infoworld.com/article/2076764/image-processing-with-java-2d.html
http://www.javased.com/?api=java.awt.image.BufferedImageOp
https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch21s03.html
http://www.java2s.com/Code/Java/2D-Graphics-GUI/ImageFilter.htm

https://stackoverflow.com/questions/57242753/how-to-select-multiple-files-in-jfilechooser-window
https://www.tutorialspoint.com/swingexamples/show_open_file_dialog_multiple.htm
https://www.tutorialspoint.com/how-to-open-multiple-files-using-a-file-chooser-in-javafx
https://www.codejava.net/java-se/swing/add-file-filter-for-jfilechooser-dialog
 */

public class ImageUtils {

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public BufferedImage toSepia(BufferedImage img) {
        // get width and height of the image
        int width = img.getWidth();
        int height = img.getHeight();

        //convert to sepia
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int R = (p>>16)&0xff;
                int G = (p>>8)&0xff;
                int B = p&0xff;

                //calculate newRed, newGreen, newBlue
                int newRed = (int)(0.393*R + 0.769*G + 0.189*B);
                int newGreen = (int)(0.349*R + 0.686*G + 0.168*B);
                int newBlue = (int)(0.272*R + 0.534*G + 0.131*B);

                //check condition
                if (newRed > 255)
                    R = 255;
                else
                    R = newRed;

                if (newGreen > 255)
                    G = 255;
                else
                    G = newGreen;

                if (newBlue > 255)
                    B = 255;
                else
                    B = newBlue;

                //set new RGB value
                p = (a<<24) | (R<<16) | (G<<8) | B;

                img.setRGB(x, y, p);
            }
        }

        return img;
    }

    public BufferedImage toGrayScale(BufferedImage img) {

        // get image's width and height
        int width = img.getWidth();
        int height = img.getHeight();

        // convert to greyscale
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // Here (x,y)denotes the coordinate of image
                // for modifying the pixel value.
                int p = img.getRGB(x,y);

                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                // calculate average
                int avg = (r+g+b)/3;

                // replace RGB value with avg
                p = (a<<24) | (avg<<16) | (avg<<8) | avg;

                img.setRGB(x, y, p);
            }
        }

        return img;
    }


    public Image rotateImage(BufferedImage original, double degrees) {
        if (degrees == 0.0f) {
            return original;
        }
        else {
            AffineTransform at=new AffineTransform();
            at.rotate(Math.toRadians(degrees),original.getWidth() / 2.0,original.getHeight() / 2.0);
            BufferedImageOp op=new AffineTransformOp(at,AffineTransformOp.TYPE_BICUBIC);
            return op.filter(original,null);
        }
    }

    public BufferedImage blurFilter(BufferedImage img){
        BufferedImageOp op=new ConvolveOp(new Kernel(3,3,new float[]{1 / 9f,1 / 9f,1 / 9f,1 / 9f,1 / 9f,1 / 9f,1 / 9f,1 / 9f,1 / 9f}));
        img=op.filter(img,null);
        Graphics2D g=img.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,img.getWidth(),1);
        g.fillRect(0,img.getHeight() - 1,img.getWidth(),1);
        g.fillRect(0,0,1,img.getHeight());
        g.fillRect(img.getWidth() - 1,0,1,img.getHeight());
        return img;
    }

    public BufferedImage invertFilter (BufferedImage image) {
        byte[] invertArray = new byte[256];

        for (int counter = 0; counter < 256; counter++)
            invertArray[counter] = (byte) (255 - counter);

        BufferedImageOp invertFilter = new LookupOp(new ByteLookupTable(0, invertArray), null);
        return invertFilter.filter(image, null);

    }

    public BufferedImage sharpenFilter (BufferedImage image) {
        float[] sharpenMatrix = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1.0f, 0.0f };
        BufferedImageOp sharpenFilter = new ConvolveOp(new Kernel(3, 3, sharpenMatrix),
                ConvolveOp.EDGE_NO_OP, null);
        return sharpenFilter.filter(image, null);
    }

}
