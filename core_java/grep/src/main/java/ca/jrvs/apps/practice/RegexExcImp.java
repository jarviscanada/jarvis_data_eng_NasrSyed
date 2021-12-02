package ca.jrvs.apps.practice;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexExcImp implements RegexExc{

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    RegexExcImp obj = new RegexExcImp();

    String filename = "file.jpg";
    System.out.println(obj.matchJpeg(filename));
    String ip = "192.168.1.1";
    System.out.println(obj.matchIp(ip));
    String line = " ";
    System.out.println(obj.isEmptyLine(line));

  }

  @Override
  public boolean matchJpeg(String filename) {
    // identify patterns
    Pattern p = Pattern.compile("\\.jpg$", Pattern.CASE_INSENSITIVE);
    Pattern p2 = Pattern.compile("\\.jpeg$", Pattern.CASE_INSENSITIVE);
    // create matcher objects
    Matcher m = p.matcher(filename);
    Matcher m2 = p2.matcher(filename);
    boolean match = m.find();
    boolean match2 = m2.find();

    if (match || match2){
      System.out.println("Match found!");
      return true;
    } else {
      System.out.println("No Match found.");
    }
    return false;

  }

  @Override
  public boolean matchIp(String ip) {
    String address = "((0|([1-9]{1,3})).){3}(0|([1-9]{1,3}))";
    Pattern p = Pattern.compile(address);
    Matcher m = p.matcher(ip);
    boolean match = m.find();

    if (match){
      System.out.println("IP Match found!");
      return true;
    } else{
      System.out.println("No IP match found.");
    }
    return false;
  }

  @Override
  public boolean isEmptyLine(String line) {
    Pattern p = Pattern.compile("^\\s*$");
    Matcher m = p.matcher(line);
    boolean match = m.find();


    if (match){
      System.out.println("Empty spaces found!");
      return true;
    } else {
      System.out.println("No empty spaces found.");
    }
    return false;

  }


}

