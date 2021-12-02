# Introduction
## Grep App
The `grep` Java application simulates the `grep` command typically used in linux bash to retrieve a particular indicated pattern in a file. 

**This application takes in 3 parameters:** 
1. the regex pattern the user wants to search
2. the root directory in which the file is stored
3. the output file name and location

As a result, the application through its various method calls in the main class, will execute the program and return a output file of the respective regex pattern place inputted by the user.

# Quick Start
## How to use the app:
This following command creates a JVM instance and runs the application via a `.jar` file. This is used to incorporate all the dependencies and packages altogether in a single file.

```
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data/txt ./out/grep.txt
```

# Implementation

## Pseudocode
The following `process` method pseudocode explains how the program iterates through a text file and returns and output file containing sentences that match the regex pattern by user. 

```
list = []
for file in listFiles(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)

```

## Performance Issue

In terms of memory usage, the primary method to address this concern would be to make use of modfiying the heap space. 

Initializing a larger heap space as well as modifying the maximum heap space would address the issue of memory. 

`java -Xms10m -Xmx10m -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data/txt ./out/grep.txt`

*The following code above initializes the .jar file with an initial and maximal heap space of 10MB, which is enough for the compilation to occur.*

Apart from heap space modifications, the memory issues are also addressed via certain methods that are used to reduce memory usage in the program:

When reading files, the following formulae conserve data:
```

      FileReader fr = new FileReader(inputFile);
      BufferedReader br = new BufferedReader(fr);
```

When writing to files, the following formulae conserve data:
```
      FileOutputStream fos = new FileOutputStream(outFile);
      OutputStreamWriter osw = new OutputStreamWriter(fos);
      BufferedWriter bw = new BufferedWriter(osw);
```


# Test
Sample test files placed in directories were used as a target example. A common ***Romeo Juliet*** regex pattern was inputted onto a ***Shakespeare*** file for testing. The result successfully returned lines that contained the ***Romeo Juliet*** regex pattern in an output file specified in the Intellij configurations.

# Deployment
Java application was compiled and packaged via Maven (including all dependencies and packages). 

Docker was used to deploy the application.
 - Java app was compiled via maven to contain all dependencies and packages.
 - Docker image was built, and volume was designated for text file and output file storage on the docker container.
 - Docker image was pushed to [hub.docker.com](https://hub.docker.com/) and verified.

# Improvement
Three things that could have been improved on regarding this project: 
1. Attention to detail when compiling code.
2. Understanding the inner workings of the Docker commands to be able to better interpret errors in compiling.
3. More research on any given topic to understand each concept more thoroughly. 