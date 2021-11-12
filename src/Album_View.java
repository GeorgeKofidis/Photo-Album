import org.apache.commons.imaging.ImageReadException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

public class Album_View extends JFrame {
    public JPanel Album_View;
    private JTextField title_textField;
    private JTextField type_textField;
    private JTextField size_textField;
    private JTextField width_textField;
    private JTextField height_textField;
    private JTextField date_textField;
    private JTextField location_textField;
    private JTextField latitude_textField;
    private JTextField longitude_textField;
    private JTextField model_textField;
    private JTextField author_textField;
    private JTextField participants_textField;
    private JTextField event_textField;
    private JLabel IMG;
    private JButton nextButton;
    private JButton previousButton;
    private JButton homeButton;
    private JButton deleteButton;
    private JLabel message;
    // new metadata
    private JTextField comments_textField;
    private JTextField tags_textField;
    private JTextField attributes_textField;
    private JTextField keyword_textField;
    private JButton updateButton;
    private JButton firstButton;
    private JButton lastButton;
    private JLabel pageLabel;
    private JComboBox searchOptions;
    private JLabel settingsLabel;
    private JComboBox options;
    private JLabel filterLabel;
    private JButton gpsButton;
    private JTextArea GPStextArea;

    private Database db = new Database();
    private ImageUtils imgUtils = new ImageUtils();
    private OpenStreetMapUtils geoUtils = new OpenStreetMapUtils();
    Image image = null;
    private Photo photo;
    private ArrayList<Photo> photos = new ArrayList<Photo>();

    private int photo_id = -1;
    private int list_index = 0;
    private int list_size = 0;

    private String searchOption = "Title";

    // https://www.w3schools.com/java/java_arraylist.asp
    // https://www.geeksforgeeks.org/arraylist-in-java/
    // https://beginnersbook.com/2013/12/java-arraylist-of-object-sort-example-comparable-and-comparator/
    // https://www.codejava.net/java-se/jdbc/insert-file-data-into-mysql-database-using-jdbc
    // https://www.codejava.net/java-se/jdbc/read-file-data-from-database-using-jdbc
    //https://www.w3spoint.com/retrieve-image-from-database-in-java
    // https://www.baeldung.com/java-compare-strings
    // https://www.w3schools.com/java/ref_string_contains.asp


    public Album_View() {

        getRootPane().setDefaultButton(homeButton);
        homeButton.requestFocus();

        photos = db.getAllPhotosFromDatabase();

        if (!photos.isEmpty()) {
            list_size = photos.size();
            showImageData(photos.get(list_index));
            pageLabel.setText(list_index+1 + " of " + list_size);
        }
        else {
            pageLabel.setText("");
        }

        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Main.frame_Album_View.setVisible(false);
                Main.frame_Intro.setVisible(true);
            }
        });
        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                // Iterate Next
                if (list_index < list_size - 1 ) {
                    list_index++;
                    showImageData(photos.get(list_index));
                    pageLabel.setText(list_index+1 + " of " + list_size);
                }

            }
        });

        previousButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (list_index > 0) {
                    list_index--;
                    showImageData(photos.get(list_index));
                    pageLabel.setText(list_index+1 + " of " + list_size);
                }
            }
        });
        deleteButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (photo_id > -1) {
                    db.deletePhoto(photo_id);
                    photos.clear();
                    photos = db.getAllPhotosFromDatabase();

                    if (!photos.isEmpty()) {
                        list_size = photos.size();
                        list_index = 0;
                        showImageData(photos.get(list_index));
                        pageLabel.setText(list_index+1 + " of " + list_size);
                    }
                    else {
                        clearFields();
                    }

                    message.setText("Image has been successfully deleted!");
                    photo_id = -1;
                }
            }
        });

        keyword_textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

            }
        });
        keyword_textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                showImageData(stringContains(keyword_textField.getText(), searchOption));
            }
        });

        Album_View.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);

            }
        });
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                db.updateMetadata(participants_textField.getText(),
                        event_textField.getText(),
                        comments_textField.getText(),
                        tags_textField.getText(),
                        attributes_textField.getText(), photo_id);
                int tmp_list_index = list_index;
                refreshData();
                list_index = tmp_list_index;
                showImageData(photos.get(list_index));

                message.setText("Image has been successfully Updated!");
            }
        });


        homeButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                refreshData();
            }
        });
        lastButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (list_index < list_size - 1 ) {
                    list_index = list_size - 1;
                    showImageData(photos.get(list_index));
                    pageLabel.setText(list_index+1 + " of " + list_size);
                }
            }
        });
        firstButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (list_index > 0) {
                    list_index = 0;
                    showImageData(photos.get(list_index));
                    pageLabel.setText(list_index+1 + " of " + list_size);
                }
            }
        });

        searchOptions.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // retrieve the selection option from the combo box
                searchOption = (String)searchOptions.getSelectedItem();

            }
        });
        options.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // retrieve the selection option from the combo box
                String option = (String)options.getSelectedItem( );

                if (option.equals("GrayScale")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.toGrayScale(imgUtils.toBufferedImage(image)));
                        IMG.setIcon(icon);
                    }
                }
                else if (option.equals("Sepia")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.toSepia(imgUtils.toBufferedImage(image)));
                        IMG.setIcon(icon);
                    }
                }
                else if (option.equals("Rotate 90")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.rotateImage(imgUtils.toBufferedImage(image),90));
                        IMG.setIcon(icon);
                    }
                }
                else if (option.equals("Blur")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.blurFilter(imgUtils.toBufferedImage(image)));
                        IMG.setIcon(icon);
                    }
                }
                else if (option.equals("Invert")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.invertFilter(imgUtils.toBufferedImage(image)));
                        IMG.setIcon(icon);
                    }
                }
                else if (option.equals("Sharpen")) {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(imgUtils.sharpenFilter(imgUtils.toBufferedImage(image)));
                        IMG.setIcon(icon);
                    }
                }

                else {
                    if(image != null) {
                        ImageIcon icon = new ImageIcon(image);
                        IMG.setIcon(icon);
                    }
                }
            }
        });
        gpsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                GPSInfo();
            }
        });
    }


    private void showImageData(Photo photo) {
        if (photo != null) {
            title_textField.setText(photo.getPhotoTitle());
            type_textField.setText(photo.getPhotoType());
            size_textField.setText(photo.getPhotoImageSize());
            width_textField.setText(String.format("%d", photo.getPhotoImageWidth()));
            height_textField.setText(String.format("%d", photo.getPhotoImageHeight()));
            date_textField.setText(photo.getPhotoImageDate());
            location_textField.setText(photo.getPhotoImagePath());
            latitude_textField.setText(photo.getPhotoImageLatitude());
            longitude_textField.setText(photo.getPhotoImageLongitude());
            model_textField.setText(photo.getPhotoImageCameraModel());
            author_textField.setText(photo.getPhotoImageAuthor());
            photo_id = photo.getPhotoImageID();
            participants_textField.setText(photo.getPhotoParticipants());
            event_textField.setText(photo.getPhotoEvent());
            comments_textField.setText(photo.getPhotoImg_Comments());
            tags_textField.setText(photo.getPhotoImg_Tags());
            attributes_textField.setText(photo.getPhotoImg_Attributes());


            try {
                Blob blob = photo.getPhotoImageBlob();
                ImageIcon imageicon = new ImageIcon(blob.getBytes(1, (int) blob.length()), "");
                image = imageicon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                //Image image = getScaledImage(imageicon.getImage(),500,500);

                //new ImageIcon(myBufferedImage);
                ImageIcon icon = new ImageIcon(image);
                IMG.setIcon(icon);
            } catch (SQLException ex) {

            }
        }
    }

    private void clearFields() {
        title_textField.setText("");
        type_textField.setText("");
        size_textField.setText("");
        width_textField.setText("");
        height_textField.setText("");
        date_textField.setText("");
        location_textField.setText("");
        latitude_textField.setText("");
        longitude_textField.setText("");
        model_textField.setText("");
        author_textField.setText("");
        participants_textField.setText("");
        event_textField.setText("");
        IMG.setText("");
        comments_textField.setText("");
        tags_textField.setText("");
        attributes_textField.setText("");
    }

    public Photo stringContains(String keyword, String option) {

        if (option.equals("Title")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoTitle().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Camera-Model")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImageCameraModel().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Date")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImageDate().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Author")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImageAuthor().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Event")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoEvent().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Comments")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImg_Comments().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Tags")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImg_Tags().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else if (option.equals("Attributes")) {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoImg_Attributes().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }
        else {
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i).getPhotoTitle().matches("(?i).*" + keyword + ".*")) {
                    list_index = i;
                    return photos.get(i);
                }
            }
        }

        return null;
    }

    private void refreshData() {
        photos.clear();
        photos = db.getAllPhotosFromDatabase();
        if (!photos.isEmpty()) {
            list_size = photos.size();
            list_index = 0;
            showImageData(photos.get(list_index));
            pageLabel.setText(list_index+1 + " of " + list_size);
        }
        else {
            clearFields();
        }
        message.setText("");
    }

    private void GPSInfo() {
        // get GPS Data
        String photo_filepath = location_textField.getText();
        Metadata metadata = new Metadata(photo_filepath);
        String gps = null;
        String[] gpscoords = null;

        try {
            gps = metadata.getCoords(
                    new File(photo_filepath));
            if (!gps.trim().isEmpty()) {

                gpscoords = gps.split("[,]", 0);
            }
            //System.out.println(gps);
            //System.out.println(gpscoords[0]);
            //System.out.println(gpscoords[1]);

        } catch (ImageReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OpenStreetMapUtils geoUtils = new OpenStreetMapUtils();

        if (!gps.trim().isEmpty()) {

            try {
                geoUtils.GeocodeSync(gpscoords[0], gpscoords[1]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            GPStextArea.setText(geoUtils.getDisplayName());

            //System.out.println("Display Name:" + geoUtils.getDisplayName());
            /*
            System.out.println("Όνομα: " + geoUtils.getName());
            System.out.println("Οδός: " + geoUtils.getRoad());
            System.out.println("Περιοχή: " + geoUtils.getNeighbourhood());
            System.out.println("ΤΚ:" + geoUtils.getPostcode());
            System.out.println("Πόλη: " + geoUtils.getCity());
            System.out.println("Δήμος: " + geoUtils.getMunicipality());
            System.out.println("Περιφερειακή Ενότητα: " + geoUtils.getCounty());
            System.out.println("Αποκεντρωμένη Διοίκηση: " + geoUtils.getState());
            System.out.println("Περιφέρεια: " + geoUtils.getStateDistrict());
            System.out.println("Χώρα: " + geoUtils.getCountry());
            System.out.println("Κωδικός Χώρας: " + geoUtils.getCountryCode());
             */
        }

        // end gps data
    }


}
