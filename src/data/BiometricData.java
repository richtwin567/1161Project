package data;

import java.security.InvalidParameterException;

import snid.Biometric;

/**
 * Biometric data class. There are two types of biometric data; Fingerprint denoted by 'F' and DNA denoted by 'D'.
 * @see snid.Biometric
 */
public class BiometricData implements Biometric{

    private char tag;
    private String value;

    /**
     * Constructor for biometric data
     * 
     * @param value The value for the DNA
     * @param tag The type of Biometric data. Must be Either 'F' for fingerprint or "D" for DNA
     * @throws InvalidParameterException If the tag passed isn't 'F' or 'D'
     */
    public BiometricData(char tag, String value) throws InvalidParameterException{
        if (tag=='F' || tag=='D'){
            this.tag = tag;
            this.value = value;
        }else{
            throw new InvalidParameterException("'"+tag+"' passed for tag. The tag must be 'F' for fingerprint or 'D' for DNA.");
        }
    }

    /**
     * @see snid.Biometric#getTag()
     */
    @Override
    public String getTag() {
        return Character.toString(tag);
    }

    /**
     * @see snid.Biometric#getValue()
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * @see snid.Biometric#match(Biometric)
     */
    @Override
    public int match(Biometric other) {
        return this.getTag().compareTo(other.getTag())==0 ? this.getValue().compareTo(other.getValue()) : -1 ;
    }
    
    /**
     * Formats the biometric data to be written the file. File writing purposes only.
     * @return A string to be written to file containing the biometric data
     */
    @Override
    public String toString() {
        return String.format("%c%s&", getTag().charAt(0),getValue());
    }
}