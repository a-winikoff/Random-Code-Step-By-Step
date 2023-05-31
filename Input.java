import java.util.Scanner;

public class Input{
  private static Scanner input = null;
  
  public static String get(){
    if (input==null)
      input = new Scanner(System.in);
    return input.nextLine();
  }

  public static void close(){
    if (input!=null)
      input.close();
  }
}