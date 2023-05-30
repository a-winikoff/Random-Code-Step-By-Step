import java.util.*;
import java.nio.file.*;

class Main {

  public static void main(String[] args) throws java.io.IOException{
//For later: make a line that calls the reset file method, that then deletes itself.
//Note: link on github should lead to a clone/copy of this repl.it, as it would otherwise modify files that I don't want people to all at once

    
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

    RootProblemFolder folder = new RootProblemFolder("CodeStepByStep.txt");
    String n = "linked lists";
    System.out.println(folder.getProblemFolder(n)+"\n"+ folder.getProblemFolder(n).getSubfolders()+"\nProblems in folder: "+ folder.getProblemFolder(n).getProblems().size()+". All problems in folder: "+ folder.getAllProblems(n).size()+"\n\n");
    for (ProblemFolder f:folder.getProblemFolder(n).getSubfolders())
      System.out.println(f+"\n"+ f.getSubfolders()+"\nProblems in folder: "+ f.getProblems().size()+". All problems in folder: "+ folder.getAllProblems(f).size()+".\n");
  }
}