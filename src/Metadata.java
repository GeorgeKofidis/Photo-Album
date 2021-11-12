import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;

public class Metadata {        
    
    private File file;
    private ImageInfo imageInfo;

    public Metadata ( String fileName) {
        
        this.file = new File(fileName);
        try {
                //  Block of code to try
                imageInfo = Imaging.getImageInfo(this.file);
            }
            catch(Exception e) {
                //  Block of code to handle errors
            }
    }
   
    public String getImageFileSizeNIO() {

        Path path = Paths.get(this.file.getAbsolutePath());
        long bytes = 0;

        try {

            // size of a file (in bytes)
            bytes = Files.size(path);
            //System.out.println(String.format("%,d bytes", bytes));
            //System.out.println(String.format("%,d kilobytes", bytes / 1024));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("%,d kilobytes", bytes / 1024);

    }

    public String getImageFileName() {       
        
        return file.getName();

    }    

    public String getImageDateTime() throws ImageReadException, IOException {
        final ImageMetadata metadata = Imaging.getMetadata(this.file);
        String result = "not found";
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_DATE_TIME);
            if (field != null) {
                result = field.getValueDescription().toString();  
                //System.out.println(jpegMetadata);          
            }
        }
        
        return result;
    }

    public String getImageAuthor() throws ImageReadException, IOException {
        final ImageMetadata metadata = Imaging.getMetadata(this.file);
        String result = "not found";
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_ARTIST);
            if (field != null) {
                result = field.getValueDescription().toString();
                //System.out.println(jpegMetadata);
            }
        }

        return result;
    }

    public String getImageLongitude() throws ImageReadException, IOException {
        final ImageMetadata metadata = Imaging.getMetadata(this.file);
        String result = "not found";
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            if (field != null) {
                result = field.getValueDescription().toString();            
            }
        }
        
        return result;
    }
    
    public String getImageLatitude() throws ImageReadException, IOException {
        final ImageMetadata metadata = Imaging.getMetadata(this.file);
        String result = "not found";
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            if (field != null) {
                result = field.getValueDescription().toString();            
            }
        }
        
        return result;
    }   
    
    public String getImageCameraModel() throws ImageReadException, IOException {
        final ImageMetadata metadata = Imaging.getMetadata(this.file);
        String result = "not found";
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            final TiffField camera = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_MAKE);
            final TiffField model = jpegMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_MODEL);
            if (camera != null) {
                result = camera.getValueDescription().toString();            
            }
            if (model != null) {
                result += ": " + model.getValueDescription().toString();            
            }
        }
        
        return result;
    }

    public int getImageHeight() {
        return imageInfo.getHeight();
    }

    public int getImageWidth() {        
        return imageInfo.getWidth();
    }
    
    public String getImageType() {
        return imageInfo.getFormat().toString();
    }    

    public String getImageAbsolutePath() {
        return this.file.getAbsolutePath();
    }

    public String getImageColorType() {
        return imageInfo.getColorType().toString();
    }

    // turn degree, min, sec format into decimal
    private Double DMS2DD(Double degrees, Double minutes, Double seconds, String direction) {
        Double dd = degrees + (minutes/60) + (seconds/3600);
        if (direction == "S" || direction == "W") {
            dd = dd * -1;
        }
        return dd;
    }

    // get GPS coordinates in decimal degrees
    public String getCoords(final File file) throws ImageReadException,
            IOException {

        Double lat = null;
        Double lng = null;

        // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
        final ImageMetadata metadata = Imaging.getMetadata(file);

        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

            // more specific example of how to manually access GPS values
            final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            final TiffField gpsLatitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            final TiffField gpsLongitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            if (gpsLatitudeRefField != null && gpsLatitudeField != null &&
                    gpsLongitudeRefField != null &&
                    gpsLongitudeField != null) {
                // all of these values are strings.
                final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
                final RationalNumber[] gpsLatitude = (RationalNumber[]) (gpsLatitudeField.getValue());
                final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();
                final RationalNumber[] gpsLongitude = (RationalNumber[]) gpsLongitudeField.getValue();

                final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
                final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
                final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];

                final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
                final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
                final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];

                lat = DMS2DD(
                        Double.parseDouble(gpsLatitudeDegrees.toDisplayString().replace(',', '.')),
                        Double.parseDouble(gpsLatitudeMinutes.toDisplayString().replace(',', '.')),
                        Double.parseDouble(gpsLatitudeSeconds.toDisplayString().replace(',', '.')),
                        gpsLatitudeRef);

                lng = DMS2DD(
                        Double.parseDouble(gpsLongitudeDegrees.toDisplayString().replace(',', '.')),
                        Double.parseDouble(gpsLongitudeMinutes.toDisplayString().replace(',', '.')),
                        Double.parseDouble(gpsLongitudeSeconds.toDisplayString().replace(',', '.')),
                        gpsLongitudeRef);

            }

        }
        DecimalFormat df2 = new DecimalFormat("#.#####");
        if(lat != null && lng != null) {
            return df2.format(lat).replace(',', '.') + "," + df2.format(lng).replace(',', '.');
        }
        else {
            return "";
        }
    }

}

