import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static JFrame frame_Intro;
    public static JFrame frame_Photo_Album;
    public static JFrame frame_Album_View;
    public static JFrame frame_Database_View;

    public static void main(String[] args) {

        frame_Intro = new JFrame("Intro");
        frame_Photo_Album = new JFrame("Photo_Album");
        frame_Album_View = new JFrame("Album_View");
        frame_Database_View = new JFrame("Database_View");

        frame_Intro.setContentPane(new Intro().Intro);
        frame_Photo_Album.setContentPane(new Photo_Album().PhotoAlbum);
        frame_Album_View.setContentPane(new Album_View().Album_View);
        frame_Database_View.setContentPane(new Database_View().Database_View_Panel);

        frame_Intro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame_Photo_Album.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame_Album_View.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame_Database_View.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        // make the frame 75% the height and width
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (screenSize.height * 75) / 100;
        int width = (screenSize.width * 75) / 100;

        frame_Intro.setSize(width, height);
        frame_Photo_Album.setSize(width, height);
        frame_Album_View.setSize(width, height);
        frame_Database_View.setSize(width, height);

        // center jframes on screen
        frame_Intro.setLocationRelativeTo(null);
        frame_Photo_Album.setLocationRelativeTo(null);
        frame_Album_View.setLocationRelativeTo(null);
        frame_Database_View.setLocationRelativeTo(null);

        /*frame_Intro.setUndecorated(true);
        frame_Photo_Album.setUndecorated(true);
        frame_Album_View.setUndecorated(true);
        frame_Database_View.setUndecorated(true);
         */

        frame_Intro.setVisible(true);
        frame_Photo_Album.setVisible(false);
        frame_Album_View.setVisible(false);
        frame_Database_View.setVisible(false);

        // GUI Background
        // https://www.imgonline.com.ua/eng/color-palette.php
        frame_Intro.getContentPane().setBackground(new Color(0.30f, 0.65f, 1f));
        frame_Photo_Album.getContentPane().setBackground(new Color(64, 224, 208));
        frame_Database_View.getContentPane().setBackground(new Color(64, 224, 208));
        frame_Album_View.getContentPane().setBackground(new Color(64, 224, 208));

    }
}
