import java.sql.Blob;

public class Photo  {
    private String img_title;
    private String img_type;
    private String img_size;
    private String img_date;
    private String img_path;
    private String img_gps_latitude;
    private String img_gps_longitude;
    private String img_camera_model;
    private String img_author;
    private Blob img_blob;
    private int img_width;
    private int img_height;
    private int img_id;
    private String img_participants;
    private String img_event;

    // new metadata
    private String img_comments;
    private String img_tags;
    private String img_attributes;

    public Photo(
            String img_title,
            String img_type,
            String img_size,
            int img_width,
            int img_height,
            String img_date,
            String img_path,
            String img_gps_latitude,
            String img_gps_longitude,
            String img_camera_model,
            String img_author,
            Blob img_blob,
            int img_id,
            String img_participants,
            String img_event,
            // new metadata
            String img_comments,
            String img_tags,
            String img_attributes
    ) {

        this.img_title = img_title;
        this.img_type = img_type;
        this.img_size = img_size;
        this.img_width = img_width;
        this.img_height = img_height;
        this.img_date = img_date;
        this.img_path = img_path;
        this.img_gps_latitude = img_gps_latitude;
        this.img_gps_longitude = img_gps_longitude;
        this.img_camera_model = img_camera_model;
        this.img_author = img_author;
        this.img_blob = img_blob;
        this.img_id = img_id;
        this.img_participants = img_participants;
        this.img_event = img_event;
        // new metadata
        this.img_comments = img_comments;
        this.img_tags = img_tags;
        this.img_attributes = img_attributes;
    }

    public String getPhotoTitle() {
        return img_title;
    }

    public void setPhotoTitle(String img_title) {
        this.img_title = img_title;
    }

    public String getPhotoType() {
        return img_type;
    }

    public void setPhotoType(String img_type) {
        this.img_type = img_type;
    }

    public String getPhotoImageSize() {
        return img_size;
    }

    public void setPhotoImageSize(String img_size) {
        this.img_size = img_size;
    }

    public String getPhotoImageDate() {
        return img_date;
    }

    public void setPhotoImageDate(String img_date) {
        this.img_date = img_date;
    }

    public String getPhotoImagePath() {
        return img_path;
    }

    public void setPhotoImagePath(String img_path) {
        this.img_path = img_path;
    }

    public String getPhotoImageLatitude() {
        return img_gps_latitude;
    }

    public void setPhotoImageLatitude(String img_gps_latitude) {
        this.img_gps_latitude = img_gps_latitude;
    }

    public String getPhotoImageLongitude() {
        return img_gps_longitude;
    }

    public void setPhotoImageLongitude(String img_gps_longitude) {
        this.img_gps_longitude = img_gps_longitude;
    }

    public String getPhotoImageCameraModel() {
        return img_camera_model;
    }

    public void setPhotoImageCameraModel(String img_camera_model) {
        this.img_camera_model = img_camera_model;
    }

    public String getPhotoImageAuthor() {
        return img_author;
    }

    public void setPhotoImageAuthor(String img_author) {
        this.img_author = img_author;
    }

    public int getPhotoImageWidth() {
        return img_width;
    }

    public void setPhotoImageWidth(int img_width) {
        this.img_width = img_width;
    }

    public int getPhotoImageHeight() {
        return img_height;
    }

    public void setPhotoImageHeight(int img_height) {
        this.img_height = img_height;
    }

    public Blob getPhotoImageBlob() {
        return img_blob;
    }

    public void setPhotoImageBlob(Blob img_blob) {
        this.img_blob = img_blob;
    }

    public int getPhotoImageID() {
        return img_id;
    }

    public void setPhotoParticipants() { this.img_participants = img_participants; }

    public String getPhotoParticipants() { return img_participants; }

    public void setPhotoEvent() { this.img_event = img_event; }

    public String getPhotoEvent() { return img_event; }

    public String getPhotoImg_Comments() {
        return img_comments;
    }

    public void setPhotoImg_Comments(String img_comments) {
        this.img_comments = img_comments;
    }

    public String getPhotoImg_Tags() {
        return img_tags;
    }

    public void setPhotoImg_Tags(String img_tags) {
        this.img_tags = img_tags;
    }

    public String getPhotoImg_Attributes() {
        return img_attributes;
    }

    public void setPhotoImg_Attributes(String img_attributes) {
        this.img_attributes = img_attributes;
    }
}

