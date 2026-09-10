package vjsaarathi.distrib.kv.cli;

import java.io.OutputStream;
import java.util.Scanner;

import vjsaarathi.distrib.kv.storage.StateMachine;

public class ClientMain {
  private static final String PROMPT = "kv> ";
  private static final OutputStream outputStream = System.out;

  private static void writeOutput(String message) throws Exception {
    outputStream.write(message.getBytes());
  }

  private static void writeOutputLine(String message) throws Exception {
    message = message + "\n";
    outputStream.write(message.getBytes());
  }

  public static void main(String[] args) {
    StateMachine stateMachine = new StateMachine();
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        writeOutput(PROMPT);
        if (scanner.hasNextLine()) {
          String[] line = scanner.nextLine().split(" ");
          if (line.length == 0 || line[0].isBlank()) {
            continue; // Skip empty lines
          }
          switch (line[0].trim().toLowerCase()) {
            case "exit":
              writeOutputLine("Exiting...");
              scanner.close();
              return;
            case "put":
              handlePutCommand(stateMachine, line);
              break;
            case "get":
              handleGetCommand(stateMachine, line);
              break;
            case "delete":
              handleDeleteCommand(stateMachine, line);
              break;
            case "exists":
              handleExistsCommand(stateMachine, line);
              break;
            default:
              writeOutputLine("Unknown command: " + line[0]);
          }
        }
      }
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }
  }

  private static void handleGetCommand(StateMachine stateMachine, String[] line) throws Exception {
    if (line.length != 2) {
      writeOutputLine("Usage: get <key>");
      return;
    }
    stateMachine.get(line[1].trim()).ifPresentOrElse(
        v -> {
          try {
            writeOutputLine(v);
          } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
          }
        },
        () -> {
          try {
            writeOutputLine("Key not found");
          } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
          }
        });
  }

  private static void handlePutCommand(StateMachine stateMachine, String[] line) throws Exception {
    if (line.length != 3) {
      writeOutputLine("Usage: put <key> <value>");
      return;
    }
    String key = line[1].trim();
    String value = line[2].trim();
    stateMachine.put(key, value);
    writeOutputLine("OK");
  }

  private static void handleDeleteCommand(StateMachine stateMachine, String[] line) throws Exception {
    if (line.length != 2) {
      writeOutputLine("Usage: delete <key>");
      return;
    }
    String key = line[1].trim();
    boolean deleted = stateMachine.delete(key);
    if (deleted) {
      writeOutputLine("Key deleted");
    } else {
      writeOutputLine("Key not found");
    }
  }

  private static void handleExistsCommand(StateMachine stateMachine, String[] line) throws Exception {
    if (line.length != 2) {
      writeOutputLine("Usage: exists <key>");
      return;
    }
    String key = line[1].trim();
    boolean contains = stateMachine.contains(key);
    writeOutputLine(contains ? "Key exists" : "Key does not exist");
  }
}
