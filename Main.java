import java.util.*;
import java.nio.file.*;

class Main {

  public static void main(String[] args) {
    Path path = Paths.get("CodeStepByStep (Folders and Descriptions).txt");
    String[] foldersFile = new String[0];
    try {foldersFile = Files.readAllLines(path).toArray(String[]::new);}
    catch (Exception e) {e.printStackTrace();}
    
    path = Paths.get("CodeStepByStep.txt");
    String[] file = new String[0];
    try {file = Files.readAllLines(path).toArray(String[]::new);}
    catch (Exception e) {e.printStackTrace();}
  
    //int count = 0;
    //for (String line:file)
    //  System.out.println(count++ + ": " + line);

    RootProblemFolder folder = new RootProblemFolder(file);
    int count = 1;
    System.out.println(folder.getAllProblems("collection implementation").size());
    for (Problem problem:folder.getAllProblems("collection implementation"))
      System.out.println(count++ + ": " + problem);
  }
}