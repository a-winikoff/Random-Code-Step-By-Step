public class Problem{
  private String name;
  private boolean completed;

  public Problem(String name){
    this.name=name;
    completed=false;
  }

  public String getName(){
    return name;
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
}