package data;

import java.io.*;
import java.util.regex.PatternSyntaxException;
//import java.util.ArrayList;
//import java.util.Arrays;

public class SNIDDb {

    //File Handling Tools
    private char delimiter;
    private String fileName;
    private String currentLine;
    private BufferedReader reader;
    private FileReader open;
    private FileWriter opener;
    private BufferedWriter writer;

    /**
     * SNIDDb Constructor
     * 
     * <p>
     * Default Constructor for SNIDDb Object. An object which opens specified file
     * for reading
     * </p>
     * 
     * @param fileName - The name of the file to be opened for reading
     * @param delimit  - char which is used to seperate data in the file
     * @throws FileNotFoundException
     */
    public SNIDDb(String fileName, char delimit) throws FileNotFoundException {
        try{
            this.delimiter = delimit;
            this.fileName = fileName;
            this.writer = null;
            this.currentLine = null;

            open = new FileReader(fileName);
            reader = new BufferedReader(open);

        }catch(FileNotFoundException f){
            //System.err.println("Message: " + f.getMessage());
            //f.printStackTrace();
            throw f;
        }
    }

    //Getter Method for Attributes
    /**
     * 
     * @return charcter representing how the lines of data are separated
     */
    public char getDelimiter(){
        return delimiter;
    }

    /**
     * 
     * @return String representing the file name
     */
    public String getFileName(){ 
        return fileName;
    }
    // public BufferedReader getBFReader(){ return null;}
    // public FileReader getFReader(){ return null;}
    // public FileWriter getFWriter(){ return null;}
    
    /**
     * <p>
     * Returns a boolean expression. If there is more data to be read in the file,
     * it will return true otherwise it will return false
     * </p>
     * 
     * @return {@code boolean}
     * @throws IOException
     */
    public boolean hasNext() throws IOException {
        try{
            
            currentLine = reader.readLine();
            if(currentLine != null)
                return true;

        }catch(IOException i){
            //System.out.println(i.getMessage());
            //i.printStackTrace();
            throw i;
        }
        
        return false;
    }

    /**
     * <p>Reads a line of text from the file. Splits the line based on 
     * the delimiter that was given to the constructor</p>
     * 
     * @return String Array of tokens
     * @throws PatternSyntaxException if the delimeter is invalid
     * @throws Exception if something else goes wrong
     */
    public String[] getNext(){

        try{
            return (currentLine.split(Character.toString(getDelimiter())));

        }catch(PatternSyntaxException p){
            throw p;
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * <p>
     * closes the file for reading. Reopens the file for writing [not appending]
     * </p>
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void rewrite() throws FileNotFoundException, IOException {
        try{

            reader.close(); //closes the Buffered Reader
            open.close(); //closes the FileReader

            //next 3 lines reset the Buffered Reader, FileReader and current Line to null
            reader = null; 
            opener = null;
            currentLine = null;

            //creates a FileWriter and BufferedWriter object
            opener = new FileWriter(getFileName());
            writer = new BufferedWriter(opener);

        }catch(FileNotFoundException f){
            /*System.out.println(f.getMessage());
            f.printStackTrace();*/
            throw f;
        }catch(IOException i){
            /*System.out.println(i.getMessage());
            i.printStackTrace();*/
            throw i;
        }
    }

    /**
     * <p>
     * Writes a line based on the array of Sting in the file separated by the
     * delimiter
     * </p>
     * 
     * @param tokens - String Array full of tokens from a Citizen object
     * @throws IOException
     */
    // assumes that the data is in the same order as it would be when it is read
    public void putNext(String[] tokens) throws IOException {
        try{
            
            //places the data into the line format for storage using StringBuffer
            StringBuffer line = new StringBuffer();
            for(int i= 0; i < tokens.length-1;i++)
                line.append(tokens[i] + Character.toString(getDelimiter()));
            line.append(tokens[tokens.length-1]);

            writer.write(line.toString());
            writer.newLine();
            writer.flush();

        }catch(IOException i){
            /*System.out.println(i.getMessage());
            i.printStackTrace();*/
            throw i;
        }

    }

    /**
     * <p>
     * This method closes the file for writing. It sets the attributes back to null
     * </p>
     * 
     * @throws IOException
     */
    public void close() throws IOException {
        try{
            writer.close(); //closes the BufferedWriter object
            opener.close(); //closes the FileWriter object

            //next 3 lines reset the BufferedWriter, FileWriter to null
            reader = null; 
            opener = null;
            currentLine = null;
            
        }catch(IOException i){
            /*System.out.println(i.getMessage());
            i.printStackTrace();*/
            throw i;
        }
    }


    /*
    public static void main(String[] args){

        try{
            File pas = new File("pas");
            if(pas.createNewFile()){
                System.out.println("File created");
            }else{
                System.out.println("File already exists");
            }
        }catch(FileNotFoundException f){
            System.out.println(f.getMessage());
            f.printStackTrace();
        }catch(IOException i){
            System.out.println(i.getMessage());
            i.printStackTrace();
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        SNIDDb test = new SNIDDb("test",':');
        ArrayList<String[]> testlst = new ArrayList<>();
        while(test.hasNext()){
            testlst.add(test.getNext());
        }

        for(String[] i: testlst){
            System.out.println(Arrays.toString(i));
        }
        String[] obj = {"newitem","reallynew","2014","bounce"};
        testlst.add(obj);
        test.rewrite();
        for(String[] i: testlst){
            test.putNext(i);
        }
        test.closeFile();

    }
    */


}