import org.apache.commons.imaging.ImageReadException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.ImageIcon;
import javax.swing.plaf.FontUIResource;
import java.io.IOException;
import java.util.Enumeration;

public class Photo_Album extends JFrame {
    public JPanel PhotoAlbum;
    private JButton saveButton;
    private JButton importButton;
    private JLabel IMG;
    private JLabel messages;
    private JTextField title_textField;
    private JTextField date_textField;
    private JTextField location_textField;
    private JTextField author_textField;
    private JTextField size_textField;
    private JTextField width_textField;
    private JTextField height_textField;
    private JTextField latitude_textField;
    private JTextField longitude_textField;
    private JTextField model_textField;
    private JTextField type_textField;
    private JTextField participants_textField;
    private JTextField event_textField;
    // new metadata
    private JTextField comments_textField;
    private JTextField tags_textField;
    private JTextField attributes_textField;

    private JButton homeButton;
    private JButton importManyButton;
    private JTextField textField1;
    private String imagePath;
    private Database db = new Database();


    public Photo_Album() {

        messages.setVisible(false);
        IMG.setVisible(false);
        saveButton.setVisible(false);
        importButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                IMG.setVisible(true);
                super.mouseClicked(e);
                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setDialogTitle("Select an image");
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpeg, jpg, png images", "jpeg", "jpg", "png");
                jfc.addChoosableFileFilter(filter);
                int returnValue = jfc.showDialog(null, "Import");
                Metadata metadataObj;
                if (returnValue == JFileChooser.APPROVE_OPTION)
                {
                    BufferedImage myPicture = null;
                    imagePath = jfc.getSelectedFile().getAbsolutePath();
                    try {
                        metadataObj = new Metadata(imagePath);
                        final String name = metadataObj.getImageFileName();
                        String[] result = name.split("\\.");
                        title_textField.setText(result[0]);
                        size_textField.setText(metadataObj.getImageFileSizeNIO());
                        width_textField.setText(String.format("%d", metadataObj.getImageWidth()));
                        height_textField.setText(String.format("%d", metadataObj.getImageHeight()));
                        date_textField.setText(metadataObj.getImageDateTime());
                        location_textField.setText(metadataObj.getImageAbsolutePath());
                        latitude_textField.setText(metadataObj.getImageLatitude());
                        longitude_textField.setText(metadataObj.getImageLongitude());
                        model_textField.setText(metadataObj.getImageCameraModel());
                        author_textField.setText(metadataObj.getImageAuthor());
                        type_textField.setText(result[1]);

                        myPicture = ImageIO.read(new File(imagePath));
                        Graphics2D g = (Graphics2D) myPicture.getGraphics();
                        JLabel imgLabel = new JLabel(new ImageIcon(myPicture));
                    }
                    catch (IOException | ImageReadException ex) {
                        ex.printStackTrace();
                    }
                    Image img = myPicture.getScaledInstance(IMG.getWidth(), IMG.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(820, 680, Image.SCALE_DEFAULT));
                    IMG.setIcon(imageIcon);
                    IMG.setText(" ");
                }
                else
                    messages.setVisible(true);
                    messages.setText("You have canceled the selection process");

                if (title_textField.getText().length() > 0) {
                    saveButton.setVisible(true);
                }

            }
        });
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                messages.setVisible(false);

                db.addMetadata(title_textField.getText(),
                    type_textField.getText(), size_textField.getText(),
                    Integer.parseInt(width_textField.getText()),
                    Integer.parseInt(height_textField.getText()),
                    date_textField.getText(),
                    location_textField.getText(), latitude_textField.getText(),
                    longitude_textField.getText(), model_textField.getText(),
                    author_textField.getText(),imagePath, participants_textField.getText(),
                    event_textField.getText(),
                    comments_textField.getText(),
                    tags_textField.getText(),
                    attributes_textField.getText());

                messages.setVisible(true);
                messages.setText("Image has been successfully saved!");
                saveButton.setVisible(false);
            }
        });
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Main.frame_Photo_Album.setVisible(false);
                Main.frame_Intro.setVisible(true);

            }
        });
        importManyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setDialogTitle("Select multiple images");
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("jpeg, jpg, png images", "jpeg", "jpg", "png");
                fileChooser.addChoosableFileFilter(filter);
                int option = fileChooser.showDialog(null, "Import");
                if(option == JFileChooser.APPROVE_OPTION){
                    File[] files = fileChooser.getSelectedFiles();

                    for(File file: files){
                        addMultipleMetadata(file.getAbsolutePath());
                        //System.out.println(file.getName());
                        //System.out.println(file.getAbsolutePath());
                    }
                    messages.setVisible(true);
                    messages.setText("Images has been successfully saved!");
                }else{
                    messages.setVisible(true);
                    messages.setText("Open command canceled!");

                }
            }
        });
    }

    public void addMultipleMetadata(String imagePath) {
        Metadata metadataObj = null;

        try {
            metadataObj = new Metadata(imagePath);
            final String name = metadataObj.getImageFileName();
            String[] result = name.split("\\.");

            db.addMetadata(result[0],
                    result[1], metadataObj.getImageFileSizeNIO(),
                    metadataObj.getImageWidth(),
                    metadataObj.getImageHeight(),
                    metadataObj.getImageDateTime(),
                    metadataObj.getImageAbsolutePath(), metadataObj.getImageLatitude(),
                    metadataObj.getImageLongitude(), metadataObj.getImageCameraModel(),
                    metadataObj.getImageAuthor(),imagePath, "",
                    "","","","");
        }
        catch (IOException | ImageReadException ex) {
            ex.printStackTrace();
        }
    }

}
