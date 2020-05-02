package data;

import snid.CivicDoc;

public class MarriageCertificate implements CivicDoc{
    private String groomId;
    private String brideId;
    private String date;
    private String refNo;
    private static int counter=0;

    /**
     * 
     * @param groomId
     * @param brideId
     * @param date
     */
    public MarriageCertificate(String groomId, String brideId, String date) {
        this.brideId = brideId;
        this.date = date;
        this.groomId = groomId;
        refNo = generateID();
    }

    /**
     * @return String return the groomId
     */
    public String getGroomId() {
        return groomId;
    }

    /**
     * @return String return the brideId
     */
    public String getBrideId() {
        return brideId;
    }

    /**
     * @return String return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @return
     */
    @Override
    public String getRefNo() {
        return refNo;
    }

    private String generateID(){
        return String.format("%06d", counter++);
    }
    
}