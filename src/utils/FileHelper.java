package utils;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.nio.file.FileSystems;
import java.io.FileWriter;   // Import the FileWriter class
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileNotFoundException;  // Import this class to handle errors

public class FileHelper {
    public static void createFile(String fileName) {
      String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/" + fileName;
      
      try {
        File myObj = new File(path);
        if (myObj.createNewFile()) {
          System.out.println("File created: " + myObj.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }

    public static void deleteFile(String fileName) {
      String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/" + fileName;
      
      File myObj = new File(path); 
      if (myObj.delete()) { 
        System.out.println("Deleted the file: " + myObj.getName());
      } else {
        System.out.println("Failed to delete the file.");
      } 
    }

    public static void appendFile(String input, String fileName)
    {
      writeFile(input, fileName, true);
    }

    public static void updateFile(List<String> inputs, String fileName)
    {
      writeFile(inputs, fileName, false);
    }

    public static void writeFile(String input, String fileName, boolean append) {
        String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/" + fileName;

        try {
          FileWriter myWriter = new FileWriter(path, append);
          myWriter.write(input);
          myWriter.write(System.lineSeparator());
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }

    public static void writeFile(List<String> inputs, String fileName, boolean append) {
      String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/" + fileName;

      try {
        FileWriter myWriter = new FileWriter(path, append);
        for (String input : inputs) {
          myWriter.write(input);
          myWriter.write(System.lineSeparator());
        }

        myWriter.close();
        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
  }

    public static List<String> readFile(String fileName) {
      String path = FileSystems.getDefault().getPath("").toAbsolutePath().toString() + "/" + fileName;
      
      List<String> returnVal = new ArrayList<String>();
        try {
          File myObj = new File(path);
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            returnVal.add(data);
          }
          myReader.close();
        } catch (FileNotFoundException e) {
         
        }

        return returnVal;
      }
    
}
