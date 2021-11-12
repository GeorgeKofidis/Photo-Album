import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;


public class Database {
    
   private Connection conn = null;
   private Statement stmt = null;
   private ArrayList<Photo> photos = new ArrayList<Photo>();

   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   //static final String DB_URL = "jdbc:mysql://localhost:3306/PhotoAlbumDB";
   static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
  // static final String USER = "junior";
   //static final String PASS = "developer";

   static final String USER = "root";
   static final String PASS = "";

    public Database() {
       // Add any initializations
       //selectMetadata();
     }

     public void deletePhoto(int id) {
        String SQL_DELETE = "DELETE FROM PHOTO_PROPERTIES WHERE KF=?";
        try {
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           PreparedStatement preparedStmt = conn.prepareStatement(SQL_DELETE);
           preparedStmt.setInt(1, id);
           preparedStmt.execute();
           conn.close();
        }
        catch (SQLException se) {
           se.printStackTrace();
        }
        catch (Exception e) {
           e.printStackTrace();
        }

     }

   public void updateMetadata(String img_participants, String img_event, String img_comments,
                           String img_tags, String img_attributes, int id) {

      try {
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         String query = "UPDATE PHOTO_PROPERTIES SET " +
                 "PARTICIPANTS=?, EVENT=?, COMMENTS=?, TAGS=?, ATTRIBUTES=? " +
                 "WHERE KF=?";

         PreparedStatement preparedStmt = conn.prepareStatement(query);
         preparedStmt.setString  (1,  img_participants);
         preparedStmt.setString  (2,  img_event);
         preparedStmt.setString  (3,  img_comments);
         preparedStmt.setString  (4,  img_tags);
         preparedStmt.setString  (5,  img_attributes);
         preparedStmt.setInt     (6,  id);
         preparedStmt.executeUpdate();
         conn.close();
      }
      catch (SQLException se) {
         se.printStackTrace();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         try {
            if (stmt!=null)
               stmt.close();
         }
         catch (SQLException se2) {
         }
         try {
            if (conn!=null)
               conn.close();
         }
         catch (SQLException se) {
            se.printStackTrace();
         }
      }

   }

     public void addMetadata(String img_title, String img_type,
         String img_size, int img_width, int img_height, String img_date,
         String img_path, String img_gps_latitude, String img_gps_longitude,
         String img_camera_model, String img_author, String img_filename, String img_participants, String img_event, String img_comments, String img_tags, String img_attributes) {
         
            try {
               conn = DriverManager.getConnection(DB_URL, USER, PASS);
               InputStream img_blob = new FileInputStream(img_filename);
               String query = "insert into PHOTO_PROPERTIES " +
                  "(TITLE, TYPE, SIZE, WIDTH, HEIGHT, DATE, DIRECTORY, " + 
                  "GPS_LATITUDE, GPS_LONGITUDE, CAMERA_MODEL, AUTHOR, IMAGE, PARTICIPANTS, EVENT,COMMENTS,TAGS,ATTRIBUTES) " +
                  "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

               PreparedStatement preparedStmt = conn.prepareStatement(query);
               preparedStmt.setString  (1, img_title);
               preparedStmt.setString  (2, img_type);
               preparedStmt.setString  (3, img_size);
               preparedStmt.setInt     (4, img_width);
               preparedStmt.setInt     (5, img_height);
               preparedStmt.setString  (6, img_date);
               preparedStmt.setString  (7, img_path);
               preparedStmt.setString  (8, img_gps_latitude);
               preparedStmt.setString  (9, img_gps_longitude);
               preparedStmt.setString  (10, img_camera_model);
               preparedStmt.setString  (11, img_author);
               preparedStmt.setBlob    (12, img_blob);
               preparedStmt.setString  (13, img_participants);
               preparedStmt.setString  (14, img_event);
               preparedStmt.setString  (15,img_comments);
               preparedStmt.setString  (16,img_tags);
               preparedStmt.setString  (17,img_attributes);
               preparedStmt.execute();
               conn.close();
               }
            catch (SQLException se) {
                  se.printStackTrace();
            }
            catch (Exception e) {
                  e.printStackTrace();
            }
            finally {
               try {
                  if (stmt!=null)
                     stmt.close();
                  }
               catch (SQLException se2) {
               }
               try {
                  if (conn!=null)
                     conn.close();
                  }
               catch (SQLException se) {
                     se.printStackTrace();
                  }
               }

     }

   public DefaultTableModel getMetadataFromDatabase() {
      DefaultTableModel dm = new DefaultTableModel(0, 0);
      try {
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT TITLE, TYPE, SIZE, WIDTH, HEIGHT, DATE, DIRECTORY, " +
                 "GPS_LATITUDE, GPS_LONGITUDE, CAMERA_MODEL, AUTHOR, IMAGE, PARTICIPANTS, EVENT, COMMENTS,TAGS,ATTRIBUTES FROM PHOTO_PROPERTIES";
         ResultSet rs = stmt.executeQuery(sql);
         JTable tblTaskList = new JTable();
         String header[] = new String[] { "Title", "Type", "Size", "Width", "Height",
                 "Date", "Directory", "GPS Latitude", "GPS Longitude", "Camera/Model", "Author", "Participants", "Event","Comments","Tags","Attributes"};
         dm.setColumnIdentifiers(header);
         tblTaskList.setModel(dm);

         while (rs.next()) {
            Vector<Object> data = new Vector<Object>();
            data.add(rs.getString(1));
            data.add(rs.getString(2));
            data.add(rs.getString(3));
            data.add(rs.getString(4));
            data.add(rs.getString(5));
            data.add(rs.getString(6));
            data.add(rs.getString(7));
            data.add(rs.getString(8));
            data.add(rs.getString(9));
            data.add(rs.getString(10));
            data.add(rs.getString(11));
            data.add(rs.getString(13));
            data.add(rs.getString(14));
            // new metadata
            data.add(rs.getString(15));
            data.add(rs.getString(16));
            data.add(rs.getString(17));
            dm.addRow(data);
         }
         rs.close();
         stmt.close();
         conn.close();
      }
      catch (SQLException se) {
         se.printStackTrace();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         try {
            if (stmt!=null)
               stmt.close();
         }
         catch (SQLException se2) {
         }
         try {
            if (conn!=null)
               conn.close();
         }
         catch (SQLException se) {
            se.printStackTrace();
         }
      }
      return dm;
   }

   public ArrayList<Photo> getAllPhotosFromDatabase() {

      try {
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         stmt = conn.createStatement();
         String sql;
         sql = "SELECT TITLE, TYPE, SIZE, WIDTH, HEIGHT, DATE, DIRECTORY, " +
                 "GPS_LATITUDE, GPS_LONGITUDE, CAMERA_MODEL, AUTHOR, IMAGE, " +
                 "KF, PARTICIPANTS, EVENT, COMMENTS, TAGS, ATTRIBUTES FROM PHOTO_PROPERTIES";
         ResultSet rs = stmt.executeQuery(sql);

         while (rs.next()) {

            photos.add(new Photo(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getInt(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getString(11),
                    rs.getBlob(12),
                    rs.getInt(13),
                    rs.getString(14),
                    rs.getString(15),
                    rs.getString(16),
                    rs.getString(17),
                    rs.getString(18))
            );
         }

         rs.close();
         stmt.close();
         conn.close();
      }
      catch (SQLException se) {
         se.printStackTrace();
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
         try {
            if (stmt!=null)
               stmt.close();
         }
         catch (SQLException se2) {
         }
         try {
            if (conn!=null)
               conn.close();
         }
         catch (SQLException se) {
            se.printStackTrace();
         }
      }

      return photos;
   }
}

