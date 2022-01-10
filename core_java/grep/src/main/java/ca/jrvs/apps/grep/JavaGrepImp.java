package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;


public class JavaGrepImp implements JavaGrep {

  final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

  private String regex;
  private String rootPath;
  private String outFile;

  // Getter methods...
  @Override
  public String getRegex() {
    return regex;
  }
  @Override
  public String getRootPath() {
    return rootPath;
  }
  @Override
  public String getOutFile() {
    return outFile;
  }

  // Setter methods...
  @Override
  public void setRegex(String regex) {this.regex = regex;}
  @Override
  public void setRootPath(String rootPath) {this.rootPath = rootPath;}
  @Override
  public void setOutFile(String outFile) {this.outFile = outFile;}

  public static void main(String[] args) {
    if (args.length !=3) {
      throw new IllegalArgumentException("Incorrect number of arguments. USAGE: JavaGrep regex rootPath outFile");
    }

    // Use default logger config
    BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try {
      javaGrepImp.process();
    } catch(Exception ex) {
      javaGrepImp.logger.error("Error: Unable to process.", ex);
    }
  }


  @Override
  public void process() throws IOException {
    List<String> list = new ArrayList<>();
    String rootDir = rootPath;
    for (File file : listFiles(rootDir)){
      for (String line : readLines(file)){
        if(containsPattern(line)){
            list.add(line);
        }
      }
    }
    writeToFile(list);

  }
  @Override
  public List<File> listFiles(String rootDir) {
    // create an Arraylist to store my files
    List<File> fileArrayList = new ArrayList<>();
    // creating a file instance based on rootDir input
    File file = new File(rootDir);
    File[] listOfFiles = file.listFiles();
    if (listOfFiles != null){
      for (File i : listOfFiles){
        fileArrayList.add(i);
      }
    }
    return fileArrayList;
  }
  @Override
  public List<String> readLines(File inputFile) {
    // creating an array to hold the lines
    List<String> lines = new ArrayList<>();
    // using a while loop to iterate the file via bufferedreader, and readline() method.
    try {
      FileReader fr = new FileReader(inputFile);
      BufferedReader br = new BufferedReader(fr);
      String line;

      while ((line = br.readLine()) != null) {
        lines.add(line);


      }
      br.close();
    } catch (IOException e){
      throw new IllegalArgumentException("Input file is not a file.");
    }
    return lines;
    }

  @Override
  public boolean containsPattern(String line) {
    return Pattern.matches(regex,line);
  }

  @Override
  public void writeToFile(List<String> lines) throws IOException {
    try {
      FileOutputStream fos = new FileOutputStream(outFile);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      BufferedWriter bw = new BufferedWriter(osw);

      for (String i : lines){
        bw.write(i);
        bw.newLine();
      }
      bw.close();
    } catch (IOException e) {
      logger.error("Did not write to file.", e);
    }
  }

}
