import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;

import javax.xml.crypto.Data;


public class Score {
    
    private int score;
    private String name;
    private File obj;

    public Score(int score, String name){
        this.score = score;
        this.name = name;
        obj = new File("score.txt");
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }

    public File getobj(){
        return obj;
    }
    
    public void setScore(int score){
        this.score = score;
    }

    public void setName(String name){
        this.name = name;
    }


    public void WriteScoreToFile(){
        try {
            FileWriter myWriter = new FileWriter(obj);
            
            myWriter.write(getName() + ", " + score);
           
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } 
        catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }

    public void ReadScoreFile() throws IOException{
        try {
            //Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(obj));
            String CurrentLine;
            while ((CurrentLine = br.readLine()) != null) {
                String ime;
                int bod;
                String tokens[] = CurrentLine.split(", ");
                ime = tokens[0].trim();
                bod = Integer.parseInt(tokens[1].trim());
                
            }
            br.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }
}
