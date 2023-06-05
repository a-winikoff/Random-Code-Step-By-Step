import java.io.*;

class Main {

  public static void main(String[] args) throws java.io.IOException{
    String path = "CodeStepByStep.txt";
    boolean attemptReset = (RootProblemFolder.line0(path).charAt(0)=='*'); // Starts as true in the source Main.java file

    if (attemptReset)
      RootProblemFolder.resetInputFile(path);

    RootProblemFolder folder = new RootProblemFolder(path);

    folder.simulateRandomProblem();

    folder.printCompletedProblems();

    Input.close();
  }
}