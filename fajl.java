import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
//import java.util.Scanner; 

public class fajl {

    private static fajl f;
    private File myObj;

    public fajl(String imeFajla){
        f = this;
        myObj = new File("zmijica.txt");
        CreateFile();
    }
    public void CreateFile(){
        try {
            //File myObj = new File("zmijica.txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            
            } else {
              System.out.println("File already exists.");
            }
        }
        catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }

    public void GetFileInfo(){
        if (myObj.exists()) {
            System.out.println("File name: " + myObj.getName());
            System.out.println("Writeable: " + myObj.canWrite());
            System.out.println("Readable " + myObj.canRead());
            System.out.println("File size in bytes " + myObj.length());
        }
        else {
            System.out.println("The file does not exist.");
        }
    }

    public void WriteToFile(_Object object){
        try {
            FileWriter myWriter = new FileWriter("zmijica.txt");
            if(object instanceof Wall){
                myWriter.write("W", object.GetX(), object.GetY()); //TODO napraviti u object klasi string name, getere i setere
            }
            else if(object instanceof Food){
                myWriter.write("F", object.GetX(), object.GetY());
            }
            else if(object instanceof SnakeBody){
                myWriter.write("S", object.GetX(), object.GetY());
            }
            //myWriter.write(object.getName(), object.GetX(), object.GetY());
           
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } 
        catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }

    public void ReadFile() throws IOException{
        //TODO 
        //BufferedReader br = new BufferedReader(new FileReader(myObj));  na drugi nacin uradjeno
        try {
            //Scanner myReader = new Scanner(myObj);
            BufferedReader br = new BufferedReader(new FileReader(myObj));
            String CurrentLine;
            while ((CurrentLine = br.readLine()) != null) {
                String objekat;
                int x,y;
                String tokens[] = CurrentLine.split(", ");
                objekat = tokens[0].trim();
                x = Integer.parseInt(tokens[1].trim());
                y = Integer.parseInt(tokens[2].trim());
                
            }
            br.close();
        } 
        catch (FileNotFoundException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }
}
