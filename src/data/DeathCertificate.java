package data;

import snid.CivicDoc;

public class DeathCertificate implements CivicDoc {
    private String id;
    private String cause;
    private String date;
    private String place;

    /**
     * @param id
     * @param cause
     * @param date
     * @param place
     */
    public DeathCertificate(String id, String cause, String date, String place) {
        this.id = id;
        this.cause = cause;
        this.date = date;
        this.place = place;
    }

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
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

    @Override
    public String getRefNo() {
        // TODO Auto-generated method stub
        return null;
    }

}