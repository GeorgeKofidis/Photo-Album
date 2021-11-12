import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.FontUIResource;
import java.awt.Color;

public class Intro extends JFrame {

    public JPanel Intro;
    private JButton importphotoButton;
    private JButton viewalbumButton;
    private JButton closeButton;
    private JButton infoButton;
    private JButton databaseButton;

    public Intro() {
        infoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(null, "Photo Album \n Ver: 5.0 " +
                        "\n \n Γεώργιος Κωφίδης, 4665 \n Δημήτριος Ιωαννίδης, 4578 \n Αλέξανδρος Κεΐσογλου, 4631 \n Γεώργιος Σαραπτσής - Βενέτης, 4610");

            }
        });
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        importphotoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Main.frame_Photo_Album.setVisible(true);

            }
        });
        viewalbumButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.frame_Album_View.setVisible(true);
            }
        });
        databaseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.frame_Database_View.setVisible(true);
            }
        });
    }
}
