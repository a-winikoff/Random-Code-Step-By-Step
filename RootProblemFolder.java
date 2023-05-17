import java.util.*;

public class RootProblemFolder extends ProblemFolder{
  private String[] originalText;
  private ArrayList<Problem> all = new ArrayList<>();
  private ArrayList<ProblemFolder> folders = new ArrayList<>();

// Instantiation
  public RootProblemFolder(String[] lines){
    super(null, lines[0].substring(0,lines[0].indexOf("(")), Integer.parseInt(lines[0].substring(lines[0].indexOf("(")+1,lines[0].indexOf("/"))), Integer.parseInt(lines[0].substring(lines[0].indexOf("/")+1,lines[0].indexOf(")")))); // Lines 0 and 1
    originalText = lines;
    this.addDescription(lines[1]);
    ProblemFolder currentFolder = this;
    folders.add(this);

    String line = removeFrontSpaces(lines[2]); // Line 2
    if (line.substring(line.length()-5).contains("/"))
      currentFolder = new ProblemFolder(this, line.substring(0,line.indexOf("(")), Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf("/"))), Integer.parseInt(line.substring(line.indexOf("/")+1,line.indexOf(")"))));
    else
      currentFolder = new ProblemFolder(this, line.substring(0,line.indexOf("(")), 0, Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf(")"))));
    this.addFolder(currentFolder);
    folders.add(currentFolder);
    
    for (int l=3; l<lines.length; l++){ // All remaining lines
      line = removeFrontSpaces(lines[l]);
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
          currentFolder = new ProblemFolder(currentFolder, line.substring(0,line.indexOf("(")), Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf("/"))), Integer.parseInt(line.substring(line.indexOf("/")+1,line.indexOf(")"))));
        else
          currentFolder = new ProblemFolder(currentFolder, line.substring(0,line.indexOf("(")), 0, Integer.parseInt(line.substring(line.indexOf("(")+1,line.indexOf(")"))));
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
        Problem p = new Problem(n);
        if (line.contains("{}"))
          p.complete();
        currentFolder.addProblem(p);
        all.add(p);
      }
    }
  }

// ProblemList usable methods
  public ProblemFolder findProblemFolder(String n){
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
  public ArrayList<Problem> getProblems(String n){
    ProblemFolder pf = findProblemFolder(n);
    if (pf==null)
      return null;
    return pf.getProblems();
  }
  public ArrayList<Problem> getAllProblems(){
    return all;
  }
  public ArrayList<Problem> getAllProblems(String n){
    ProblemFolder pf = findProblemFolder(n);
    if (pf==null)
      return null;
    return getAllProblems(pf);
  }
  public ArrayList<Problem> getAllProblems(ProblemFolder pf){
    ArrayList<Problem> ps = new ArrayList<>();
    if (pf.getProblems().size()!=0||pf.getSubfolders().size()!=0){
      ps.addAll(pf.getProblems());
      for (ProblemFolder pfs:pf.getSubfolders())
        ps.addAll(getAllProblems(pfs));
    }
    return ps;
  }
  public String toString(){
    return "Root Folder: " + super.toString().substring(11);
  }
}