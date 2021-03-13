import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class fajl {
    private static fajl f;
    //private fajl myObj;
    public fajl(){
        f = this;
    }
    public void CreateFile(){
        try {
            File myObj = new File("zmijica.txt");
            if (myObj.createNewFile()) {
              System.out.println("File created: " + myObj.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
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
          } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
          }
    }

    public void ReadFile(){
        //TODO 
    }
}
