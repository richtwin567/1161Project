package data;

import snid.CivicDoc;

public class DeathCertificate implements CivicDoc {
    private String cause;
    private String date;
    private String place;
    private String refNo;
    private static int counter = 0;
    private final char type = 'D';

    /**
     * @param id
     * @param cause
     * @param date
     * @param place
     */
    public DeathCertificate(String cause, String date, String place) {
        this.cause = cause;
        this.date = date;
        this.place = place;
        refNo = generateID();
    }

    

    /**
     * @return String return the cause
     */
    public String getCause() {
        return cause;
    }

    /**
     * @return String return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return String return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @return 
     */
    @Override
    public String getRefNo() {
        return refNo;
    }

    /**
     * 
     * @return
     */
    private String generateID(){
        return String.format("%06d", counter++);
    }

    /**
     * @return char return the type
     */
    public char getType() {
        return type;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return String.format("RefNo.:\t\t%s\nCause of Death:\t%s\nDate of Death:\t%s\nPlace of Death:\t%s",getRefNo(),getCause(),getDate(),getPlace());
    }
}