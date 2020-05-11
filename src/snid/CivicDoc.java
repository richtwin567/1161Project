package snid;

/**
 * This define the interactions the system should have with a Citizen's civic
 * documents
 *
 * @author Anakai Richards
 */
public interface CivicDoc {

    /**
     * Gets the reference number for the document
     *
     * @return the document's reference number as a String
     */
    public String getRefNo();

    /**
     * Get the type of Civic Document
     * @return the type of Civic Document
     */
    public char getType();

    /**
     * Formatts the Civic Document for the GUI
     * @return the civic document information
     */
    public String toGUIPrint();
}
