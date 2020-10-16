package ml.molive.scchallengebarebones;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;

public class Main {
  public static final String[] IDENTIFIERS = {
      "clear",
      "copy",
      "decr",
      "do",
      "end",
      "incr",
      "init",
      "not",
      "to",
      "while",
  };

  private static final Map<String, Long> variables = new HashMap<>();

  private static int pc = 0;

  private static final List<String> commandStream = new ArrayList<>();

  private static final Stack<Integer> stack = new Stack<>();

  public static void main(String[] args) throws FileNotFoundException {
    Scanner scanner = new Scanner(new File(args[0]));
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] lineSplit = line.split("#");
      if (lineSplit.length == 0) continue;
      line = lineSplit[0]; //remove comments
      for (String command : line.split(";")) {
          commandStream.add(command.strip());
      }
    }
    while (pc < commandStream.size()) {
      runLine(commandStream.get(pc));
      pc += 1;
    }
    for (String name: variables.keySet()){
      System.out.println(name + ": " + variables.get(name).toString());
    }
  }

  private static void runLine(@NotNull String line) {
    String[] parts = line.split("\\s+");
    //parts = (String[]) Arrays.stream(parts).filter(String::isBlank).toArray();
    switch (parts[0].toLowerCase()) {
      case "clear" -> {
        if (verify(parts[1]))
          break;
        variables.put(parts[1], 0L);
      }
      case "copy" -> {
        if (verify(parts[1]))
          break;
        if (!parts[2].equals("to"))
          break;
        if (verify(parts[3]))
          break;
        variables.put(parts[3], variables.get(parts[1]));
      }
      case "incr" -> {
        if (verify(parts[1]))
          break;
        variables.put(parts[1], variables.get(parts[1]) + 1);
      }
      case "decr" -> {
        if (verify(parts[1]))
          break;
        long temp = variables.get(parts[1]);
        if (temp == 0) break;
        variables.put(parts[1], temp - 1);
      }
      case "while" -> {
        if (verify(parts[1]))
          break;
        if (!parts[2].equals("not"))
          break;
        long endValue = Long.parseLong(parts[3]);
        if (!parts[4].equals("do"))
          break;
        if (variables.get(parts[1]) == endValue) {
          advanceToNextEnd();
        } else {
          stack.push(pc);
        }
      }
      case "end" -> {
        if (stack.empty()) {
          System.err.println("Extra end statement!");
          pc = Integer.MAX_VALUE;
          break;
        }
        pc = stack.pop() - 1;
      }
    }
  }

  private static void advanceToNextEnd() {
    int depth = 1;
    String command;
    while (depth > 0) {
      pc += 1;
      if (pc >= commandStream.size()) {
        System.err.println("Missing end statement!");
        return;
      }
      command = commandStream.get(pc).split("\\s+")[0];
      if (command.equals("while")) depth += 1;
      else if (command.equals("end")) depth -= 1;
    }
  }

  /// returns true if the string is not a valid identifier
  private static boolean verify(String ident) {
    if (Arrays.asList(IDENTIFIERS).contains(ident)) return true;
    if (!ident.matches("[a-zA-Z]\\w*")) return true;
    variables.putIfAbsent(ident,0L);
    return false;
  }
}