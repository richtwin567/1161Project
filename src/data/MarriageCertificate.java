package data;

import snid.CivicDoc;

/**
 * Represents a marriage certificate which is a type of civic document. The marriage certificate stores the groom's ID, the bride's ID,
 * the date of the MarriageCertificate the unique reference number for the document.
 * @author Anakai Richards - ID: 620132232
 * @author Matthew Palmer - ID: 620131688
 * @author Michael Young - ID: 620131387
 * @version 1.0
 * @see snid.CivicDoc
 */
public class MarriageCertificate implements CivicDoc{
    private String groomId;
    private String brideId;
    private String date;
    private String refNo;
    private static int counter = 0;
    private final char type = 'M';

    /**
     * Constructor for the marriage certificate class. The reference number is automatically generated.
     * @param groomId the groom's ID number
     * @param brideId the bride's ID number
     * @param date the date of the marriage
     */
    public MarriageCertificate(String groomId, String brideId, String date) {
        this.brideId = brideId;
        this.date = date;
        this.groomId = groomId;
        refNo = generateID();
    }

    /**
     * For reading the from the file.
     * @param groomId the groom's ID number
     * @param brideId the bride's ID number
     * @param date the date of the marriage
     * @param refNo the doc reference number
     */
    public MarriageCertificate(String refNo,String groomId, String brideId, String date) {
        this.brideId = brideId;
        this.date = date;
        this.groomId = groomId;
        this.refNo = refNo;
        counter++;
    }

    /**
     * Gets the groom's ID number
     * @return {@code String} representing the groom's ID 
     */
    public String getGroomId() {
        return groomId;
    }

    /**
     * Gets the bride's ID number
     * @return {@code String} representing the bride's ID
     */
    public String getBrideId() {
        return brideId;
    }

    /**
     * Gets the date of the marriage
     * @return {@code String} representing the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the refernce number for this marriage certificate.
     * @return the refernce number for this Marriage certificate.
     */
    @Override
    public String getRefNo() {
        return refNo;
    }

    /**
     * Generates the unique refernce number for this marriage certificate
     * @return the refernce number as a {@code String}
     */
    private String generateID(){
        return String.format("%06d", counter++);
    }
    
    /**
     * Gets the type of Civic document, in this case a marriage certificate.
     * @return {@code char} 'M' representing marriage certificate
     */
    public char getType() {
        return type;
    }

    /**
     * Formats the document information for printing
     * @return the document information.
     */
    @Override
    public String toString() {
        return String.format("Ref No.:\t%s\nGroom ID:\t%s\nBride ID:\t%s\nDate:\t\t%s", getRefNo(),getGroomId(),getBrideId(),getDate());
    }

    /**
     * Formats the marriage certificate information for the GUI
     * @return the marriage certificate information
     */
    @Override
    public String toGUIPrint() {
        return String.format("<b>%s</b><br/><i>Ref No.:</i>&#9&#9 %s<br/><i>Groom's ID:</i>&#9&#9 %s<br/><i>Bride's ID:</i>&#9&#9 %s<br/><i>Date:</i>&#9&#9 %s<br/>", "Marriage Certificate", getRefNo(), getGroomId(),getBrideId(), getDate());
    
    }

}