import java.util.*;

public class ProblemFolder{
  private ArrayList<ProblemFolder> subfolders;
  private ArrayList<Problem> problems;
  private String name;
  private int totalDone;
  private int total;
  private String description;
  private ProblemFolder parent; //For subfolders only, null for RootProblemFolder

// All instantiation methods
  public ProblemFolder(ProblemFolder p, String name, int done, int total){
    parent = p;
    this.name = name;
    totalDone = done;
    this.total = total;
    description = "No description included.";
    subfolders = new ArrayList<>();
    problems = new ArrayList<>();
  }
  public ProblemFolder getParent(){
    return parent;
  }
  public void addDescription(String d){ // Will not add a description if one already exists
    if (description.equals("No description included."))
      description = d;
  }
  public void addFolder(ProblemFolder f){
    subfolders.add(f);
  }
  public void addProblem(Problem p){
    problems.add(p);
  }
  public int size(){
    return total;
  }

// Other methods
  public String getName(){
    return name;
  }
  public ArrayList<Problem> getProblems(){
    return problems;
  }
  public ArrayList<ProblemFolder> getSubfolders(){
    return subfolders;
  }
  public int getDone(){
    return totalDone;
  }
  public String toString(){
    return "Subfolder: " + name + "(" + ((totalDone!=0)?totalDone+"/":"") + total + ")";
  }

// String manipulation methods
  public static int countFrontSpaces(String input){
    for (int i=0; i<input.length(); i++)
      if (input.charAt(i)!=' ')
        return i;
    return input.length();
  }
  public static String removeFrontSpaces(String input){
    return input.substring(countFrontSpaces(input));
  }
}