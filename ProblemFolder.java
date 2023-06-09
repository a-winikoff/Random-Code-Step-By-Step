import java.util.*;

public class ProblemFolder{
  private List<ProblemFolder> subfolders;
  private List<Problem> problems;
  private final String name;
  private final int totalDone;
  private final int total;
  private String description;
  private final int lineNum; // Line of initial instantiation
  private final ProblemFolder parent; //For subfolders only, null for RootProblemFolder

// All instantiation methods
  public ProblemFolder(ProblemFolder p, String name, int done, int total, int linum){
    parent = p;
    this.name = name;
    totalDone = done;
    this.total = total;
    description = "No description included.";
    subfolders = new ArrayList<>();
    problems = new ArrayList<>();
    lineNum = linum;
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
  public int getLine(){
    return lineNum;
  }

// Other methods
  public String getName(){
    return name;
  }
  public List<Problem> getProblems(){
    return problems;
  }
  public List<ProblemFolder> getSubfolders(){
    return subfolders;
  }
  public String getDescription(){
    return description;
  }
  public int getDone(){
    return totalDone;
  }
  public String toString(){
    return name + "(" + ((totalDone!=0)?totalDone+"/":"") + total + ")";
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