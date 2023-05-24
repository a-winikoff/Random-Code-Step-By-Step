public class Problem{
  private String name;
  private boolean completed;
  private ProblemFolder parent;
  private final int lineNum;

  public Problem(String name, ProblemFolder p, int linum){
    this.name=name;
    completed=false;
    parent = p;
    lineNum = linum;
  }

  public String getName(){
    return name;
  }
  public ProblemFolder getParent(){
    return parent;
  }
  public boolean isDone(){
    return completed;
  }
  public void complete(){
    completed=true;
  }
  public void uncomplete(){
    completed=false;
  }
  public String toString(){
    return name+((completed)?"{}":"");
  }
  public int getLine(){
    return lineNum;
  }
}