package app;

import java.util.ArrayList;
import snid.Biometric;
import snid.BiometricData;
import snid.Citizen;

/**
 * 
 */
public class SNIDApp {

    // SNIDDb to be used to populate citiens.
    private ArrayList<DeathData> deathLog;
    private ArrayList<MarriageData> marriageLog;
    private ArrayList<Citizen> citizens;

    /**
     * 
     * @param id
     * @return
     */
    private Citizen searchDb(String id) {
        int m, l, f;
        f = 0;
        l = citizens.size();
        try {
            while (f <= l) {
                m = (f + (l - f)) / 2;
                if (citizens.get(m).getId().compareTo(id) == 0) {
                    return citizens.get(m);
                } else if (citizens.get(m).getId().compareTo(id) < 0) {
                    f = m + 1;
                } else {
                    l = m - 1;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 
     * @param id
     * @param cause
     * @param place
     * @param date
     */
    public void registerDeath(String id, String cause, String place, String date) {
        try {
            Citizen newlyDead = searchDb(id);
            newlyDead.setLifeStatus(1);
            deathLog.add(new DeathData(id, cause, date, place));
        } catch (Exception e) {
            //System.out.println("Death could not be registered");
        }
    }

    /**
     * 
     * @param groomId
     * @param brideId
     * @param date
     */
    public void registerMarriage(String groomId, String brideId, String date) {
        Citizen bride = searchDb(brideId);
        Citizen groom = searchDb(groomId);
        if (bride != null && groom != null) {
            marriageLog.add(new MarriageData(groomId, brideId, date));
        } else {
            //System.out.println("Marriage could not be registered");
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public String mailingLabel(String id) {
        try {
            Citizen citizen = searchDb(id);
            return String.format("%s\n%s", citizen.getNameFull(), citizen.getAddress());
        } catch (Exception e) {
            //Citizen not found
            return null;
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public String getMother(String id) {
        try {
            Citizen person = searchDb(id);
            Citizen mother = (Citizen) person.getParent('M');
            return String.format("%s,%s,%s,%s", mother.getId(), mother.getNameAttr().getFirstName(),
                    mother.getNameAttr().getMiddleName(), mother.getNameAttr().getLastName());
        } catch (Exception e) {
            //This citizen has no mother
            return "";
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public String getFather(String id) {
        try {
            Citizen person = searchDb(id);
            Citizen father = (Citizen) person.getParent('M');
            return String.format("%s,%s,%s,%s", father.getId(), father.getNameAttr().getFirstName(),
                    father.getNameAttr().getMiddleName(), father.getNameAttr().getLastName());
        } catch (Exception e) {
            //This citizen has no father
            return "";
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    public String search(String id) {
        try {
            Citizen citizen = searchDb(id);
            return String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),
                    citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
    } catch (Exception e) {
        //No matching citizen found
        return "";
        }
    }

    /**
     * 
     * @param firstName
     * @param lastName
     * @return
     */
    public String[] search(String firstName, String lastName) {
        ArrayList<String> matches = new ArrayList<>();
        for (Citizen citizen: citizens){
            if (citizen.getNameAttr().getFirstName().equals(firstName) && citizen.getNameAttr().getLastName().equals(lastName)){
                String print = String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                matches.add(print);
            }
        }
        matches.trimToSize();
        return (String[])matches.toArray();
    }

    /**
     * 
     * @param tag
     * @param value
     * @return
     */
    public String search(char tag, String value) {
        try {
            BiometricData key = new BiometricData(tag, value);
            for(Citizen citizen : citizens){
                Biometric biodata = citizen.getBiometric(Character.toString(tag));
                if (((BiometricData)biodata).match(key)==0){
                    return String.format("%s,%s,%s,%s,%s", citizen.getId(), citizen.getGender()=='M' ? "Male" : "Female", citizen.getNameAttr().getFirstName(),citizen.getNameAttr().getMiddleName(), citizen.getNameAttr().getLastName());
                }
            }            
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 
     */
    private class MarriageData {

        private String groomId;
        private String brideId;
        private String date;

        /**
         * 
         * @param groomId
         * @param brideId
         * @param date
         */
        public MarriageData(String groomId, String brideId, String date) {
            this.brideId = brideId;
            this.date = date;
            this.groomId = groomId;
        }

        /**
         * @return String return the groomId
         */
        public String getGroomId() {
            return groomId;
        }

        /**
         * @param groomId the groomId to set
         */
        public void setGroomId(String groomId) {
            this.groomId = groomId;
        }

        /**
         * @return String return the brideId
         */
        public String getBrideId() {
            return brideId;
        }

        /**
         * @param brideId the brideId to set
         */
        public void setBrideId(String brideId) {
            this.brideId = brideId;
        }

        /**
         * @return String return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }
    }

    /**
     * 
     */
    private class DeathData {
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
        public DeathData(String id, String cause, String date, String place) {
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
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return String return the cause
         */
        public String getCause() {
            return cause;
        }

        /**
         * @param cause the cause to set
         */
        public void setCause(String cause) {
            this.cause = cause;
        }

        /**
         * @return String return the date
         */
        public String getDate() {
            return date;
        }

        /**
         * @param date the date to set
         */
        public void setDate(String date) {
            this.date = date;
        }

        /**
         * @return String return the place
         */
        public String getPlace() {
            return place;
        }

        /**
         * @param place the place to set
         */
        public void setPlace(String place) {
            this.place = place;
        }

    }

}