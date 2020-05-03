package data;

import snid.CivicDoc;

public class MarriageCertificate implements CivicDoc{
    private String groomId;
    private String brideId;
    private String date;
    private String refNo;
    private static int counter = 0;
    private final char type = 'M';

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
        return String.format("Ref No.:\t%s\nGroom ID:\t%s\nBride ID:\t%s\nDate:\t%s", getRefNo(),getGroomId(),getBrideId(),getGroomId());
    }

}