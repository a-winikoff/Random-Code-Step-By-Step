import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.*;
import java.io.*;

public class RootProblemFolder extends ProblemFolder{
  private List<String> text;
  private List<Problem> all = new ArrayList<>();
  private List<ProblemFolder> folders = new ArrayList<>();
  private final List<String> folderNames;
  private final String path;

// Instantiation
  public RootProblemFolder(String path) throws java.io.IOException{
    super(null, line0(path).substring(0,line0(path).indexOf("(")), Integer.parseInt(line0(path).substring(line0(path).indexOf("(")+1,line0(path).indexOf("/"))), Integer.parseInt(line0(path).substring(line0(path).indexOf("/")+1,line0(path).indexOf(")"))), 0); // Lines 0 and 1
    this.path = path;
    text = Files.readAllLines(Paths.get(path));
    this.addDescription(text.get(1));
    ProblemFolder currentFolder = this;
    folders.add(this);

    String line = removeFrontSpaces(text.get(2)); // Line 2
    if (line.substring(line.length()-5).contains("/"))
      currentFolder = new ProblemFolder(this, line.substring(0,line.indexOf("(")), Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf("/"))), Integer.parseInt(line.substring(line.indexOf("/")+1,line.indexOf(")"))), 2);
    else
      currentFolder = new ProblemFolder(this, line.substring(0,line.indexOf("(")), 0, Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf(")"))), 2);
    this.addFolder(currentFolder);
    folders.add(currentFolder);
    
    for (int l=3; l<text.size(); l++){ // All remaining lines
      line = removeFrontSpaces(text.get(l));
      if (line.substring(line.length()-1).equals(")")){ // Line is a subfolder
        while (currentFolder.getParent()!=null){ // Make currentFolder be the parent folder to the folder instantiated by this line of input
          int subSize = currentFolder.getProblems().size();
          for (ProblemFolder pf:currentFolder.getSubfolders())
            subSize+=pf.size();
          if (subSize==currentFolder.size())
            currentFolder = currentFolder.getParent();
          else
            break;
        }
        if (line.substring(line.length()-5).contains("/")) // Instantiate new subfolder
          currentFolder = new ProblemFolder(currentFolder, line.substring(0,line.indexOf("(")), Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf("/"))), Integer.parseInt(line.substring(line.indexOf("/")+1,line.indexOf(")"))), l);
        else
          currentFolder = new ProblemFolder(currentFolder, line.substring(0,line.indexOf("(")), 0, Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf(")"))), l);
        currentFolder.getParent().addFolder(currentFolder);
        folders.add(currentFolder);
      }
      else if (line.substring(line.length()-1).equals(".")){ // Line is a description
        currentFolder.addDescription(line);
      }
      else{ // Line is a problem
        String n = line;
        if (n.contains("{}"))
          n = n.substring(0, n.indexOf("{"));
        Problem p = new Problem(n, currentFolder, l);
        if (line.contains("{}"))
          p.complete();
        currentFolder.addProblem(p);
        all.add(p);

        // If there are more problems that are in the parent folder, but not in this folder
        while (currentFolder.size()==currentFolder.getProblems().size())
          currentFolder = currentFolder.getParent();
      }
    }

    folderNames = folders.stream().map(f -> f.getName()).collect(Collectors.toList());
  }
  public static String line0(String filepath) throws java.io.IOException{
    BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
    String line = reader.readLine();
    reader.close();
    return line;
  }

// ProblemFolder usable methods
  public ProblemFolder getProblemFolder(String n){
    ProblemFolder pf = null;
    for (ProblemFolder f:folders)
      if (f.getName().equals(n)){
        pf = f;
        break;
      }
    if (pf==null)
      return null;
    return pf;
  }
  public List<Problem> getProblems(String n){
    ProblemFolder pf = getProblemFolder(n);
    if (pf==null)
      return null;
    return pf.getProblems();
  }
  public List<Problem> getAllProblems(){
    return all;
  }
  public List<Problem> getAllProblems(String n){
    ProblemFolder pf = getProblemFolder(n);
    if (pf==null)
      return null;
    return getAllProblems(pf);
  }
  public List<Problem> getAllProblems(ProblemFolder pf){
    List<Problem> ps = new ArrayList<>();
    if (pf.getProblems().size()!=0||pf.getSubfolders().size()!=0){
      ps.addAll(pf.getProblems());
      for (ProblemFolder pfs:pf.getSubfolders())
        ps.addAll(getAllProblems(pfs));
    }
    return ps;
  }
  public List<String> getFolderNames(){
    return new ArrayList<>(folderNames);
  }
  public List<String> getFolderNamesLower(){
    return folderNames.stream().map(s -> s.toLowerCase()).collect(Collectors.toList());
  }

// Text manipulation methods
  public List<String> getText(){
    return text;
  }
  private void updateAllLines(Problem p){
    updateLine(p);
    ProblemFolder parent = p.getParent();
    while (parent!=null){
      updateLine(parent);
      parent = parent.getParent();
    }
  }
  private void updateLine(ProblemFolder pf){
    text.set(pf.getLine(), pf.toString());
  }
  private void updateLine(Problem p){
    text.set(p.getLine(), p.toString());
  }

// Main random problem methods
  public void simulateRandomProblem() throws java.io.IOException{
    Scanner input = new Scanner(System.in);
    if (!simYN(input, "Would you like to get a random problem? (y/n): ", true))
      return;
    String folder = "Java";
    if (simYN(input, "Would you like to choose a folder? (y/n): ", false))
      while (true){
        folder = simString(input, "Within which folder is the problem? (or \'exit\'): ", true);
        if (folder.equals(""))
          return;
        if (!getFolderNamesLower().contains(folder.toLowerCase()))
          System.out.println("Folder does not exist.");
        else
          break;
      }
    Problem problem = getRandomProblem(folder);
    System.out.println("Here is your problem:\nProblem: "+problem.getName());
    String path = problem.getName();
    ProblemFolder parent = problem.getParent();
    while (parent!=null){
      path = parent.getName()+"\\"+path;
      parent = parent.getParent();
    }
    System.out.println("Path: "+path+"\n");
    if (simYN(input, "Mark problem as completed? (y/n): ", false)){
      problem.complete();
      updateAllLines(problem);
      updateFile();
      System.out.println("Updated file.");
    }
    input.close();
    System.out.println("\n\n");
    simulateRandomProblem();
  }
  public Problem getRandomProblem(){
    return getRandomProblem(this);
  }
  public Problem getRandomProblem(String folderName){
    return getRandomProblem(getProblemFolder(folderName));
  }
  public Problem getRandomProblem(ProblemFolder pf){
    List<Problem> problems = getAllProblems(pf);
    Collections.shuffle(problems);
    for (Problem p:problems)
      if (!p.isDone())
        return p;
    return null;
  }
  public void updateFile() throws java.io.IOException{
    Files.write(Paths.get(path), text);
  }
  public boolean simYN(Scanner input, String message, boolean supportsBreaks){
    String read = "";
    System.out.print(message);
    while (read.equals("")){
      read = input.nextLine().toLowerCase();
      if (read.equals("n")||read.equals("no")||supportsBreaks&&(read.equals("exit")||read.equals("stop")||read.equals("close")))
        return false;
      if (read.equals("y")||read.equals("yes"))
        return true;
      if (!read.equals("")){
        System.out.println("Invalid response.");
        read = "";
        System.out.print(message);
      }
    }
    return false; // Never reached
  }
  public String simString(Scanner input, String message, boolean supportsBreaks){
    String read = "";
    System.out.print(message);
    while (read.equals("")){
      read = input.nextLine();
      if (supportsBreaks&&(read.equals("exit")||read.equals("stop")||read.equals("close")))
        return "";
    }
    return read;
  }
  public void resetInputFile() throws java.io.IOException{
    Scanner input = new Scanner(System.in);
    if (!simYN(input, "Are you sure you would like to reset the input text file to the default? (y/n): ", true))
      return;
    input.close();
    System.out.println("Resetting text file...");

    text = Files.readAllLines(Paths.get("CodeStepByStep (base).txt"));
    updateFile();

    System.out.println("\nFile reset to default.\nRestart program for changes to reflect.");
  }
}