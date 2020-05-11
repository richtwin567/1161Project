package data;

import snid.CivicDoc;

/**
 * Represents a death certificate which is a type of civic document. The death
 * certificate stores the cause of death, the date of death, the place of death
 * and the unique reference number for the document.
 * 
 * @see snid.CivicDoc
 */
public class DeathCertificate implements CivicDoc {
    private String cause;
    private String date;
    private String place;
    private String refNo;
    private static int counter = 0;
    private final char type = 'D';

    /**
     * The constructor for the death certificate class. A unique reference number is
     * automatically generated
     * 
     * @param cause the cause of death
     * @param date  the date of death
     * @param place the place of death
     */
    public DeathCertificate(String cause, String date, String place) {
        this.cause = cause;
        this.date = date;
        this.place = place;
        refNo = generateID();
    }

    /**
     * Gets the cause of death.
     * 
     * @return {@code String} outlining the cause of death
     */
    public String getCause() {
        return cause;
    }

    /**
     * Gets the date of death.
     * 
     * @return The date of death in {@code String} format
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the place of death.
     * 
     * @return {@code String} stating the place of death
     */
    public String getPlace() {
        return place;
    }

    /**
     * Gets the document's unique reference number
     * 
     * @return the reference number
     */
    @Override
    public String getRefNo() {
        return refNo;
    }

    /**
     * Generates the reference number for each death certificate instance
     * 
     * @return A unique 6 digit refernce number
     */
    private String generateID() {
        return String.format("%06d", counter++);
    }

    /**
     * Gets the type of civic document. In this case, a death certificate. This is
     * represented by 'D'.
     * 
     * @return the type of civic documents
     */
    public char getType() {
        return type;
    }

    /**
     * Formats the document information into a String
     * 
     * @return the document information
     */
    @Override
    public String toString() {
        return String.format("RefNo.:\t\t%s\nCause of Death:\t%s\nDate of Death:\t%s\nPlace of Death:\t%s", getRefNo(),
                getCause(), getDate(), getPlace());
    }

    /**
     * Formats the death certificate information for the GUI
     * @return the death certificate information
     */
    @Override
    public String toGUIPrint() {
        return String.format("<b>%s</b><br/><i>Ref No.:</i>&#9&#9 %s<br/><i>Cause of Death:</i>&#9 %s<br/><i>Place:</i>&#9&#9 %s<br/><i>Date:</i>&#9&#9 %s<br/>","Death Certificate", getRefNo(),getCause(),getPlace(), getDate());
    }
}