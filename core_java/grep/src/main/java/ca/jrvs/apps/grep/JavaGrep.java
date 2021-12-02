package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.*;

public interface JavaGrep {
  // note that in documentation, all comments go above the indicted method or item used.

  /**
   * Top level search workflow.
   *
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverses a given directory and returns all files.
   *
   * @param rootDir as your input directory.
   * @return files under the rootDir.
   */
  List<File> listFiles(String rootDir);

  /**
   * Reads a file and returns all the lines.
   *
   * Use FileReader, BufferedReader, character encoding.
   *
   * @param inputFile - this is the file to be read.
   * @return lines
   * @throws IllegalArgumentException if a given inputFile is not a file.
   */
  List<String> readLines(File inputFile);

  /**
   * check if a line contains a regex pattern (passed by user)
   *
   * @param line - this is your input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * this writes lines to an output file Explore: FileOutputStream, OutputStreamWriter, BufferedWriter
   * 1. FileOutputStream: this is used to write data/streams of raw bytes to files. storing data to files.
   * 2. OutputStreamWriter: this wraps a java outstream and turns the byte based output stream into a character based writer.
   * 3. BufferedWriter: this is used to buffer and wrap around other writers operations that may be costly/inefficient.
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  void writeToFile(List<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutFile();

  void setOutFile(String outFile);

}













