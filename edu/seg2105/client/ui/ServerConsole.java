import ocsf.server.*;

import java.io.IOException;
import java.util.Scanner;

import ocsf.client.*;

public class ServerConsole implements ChatIF{
    final public static int DEFAULT_PORT = 5555;

    EchoServer server;

    Scanner fromConsole; 

    public ServerConsole(int port) 
    {
        server = new EchoServer(port);
      
      // Create scanner object to read from console
      fromConsole = new Scanner(System.in); 
    }
    
    public void display(String message) 
  {
    System.out.println("SERVER MSG> " + message);
  }

  public void accept() 
  {
    try
    {
      String message;

      while (true) 
      {
        message = fromConsole.nextLine();
        server.handleMessageFromServerUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }
}