import ocsf.server.*;

import java.io.IOException;
import java.util.Scanner;

import ocsf.client.*;

public class ServerConsole implements ChatIF{
    final public static int DEFAULT_PORT = 5555;

    ChatClient client;

    Scanner fromConsole; 

    public ServerConsole(String host, int port) 
    {
      try 
      {
        client= new ChatClient(host, port, this);
        
        
      } 
      catch(IOException exception) 
      {
        System.out.println("Error: Can't setup connection!"
                  + " Terminating client.");
        System.exit(1);
      }
      
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
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  public static void main(String[] args) 
  {
    String host = "";
    int port = 0;

    try
    {
      host = args[0];
      port = Integer.parseInt(args[1]);
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      host = "localhost";
      port = DEFAULT_PORT;
    }
    catch(NumberFormatException e){
      port = DEFAULT_PORT;
    }

    ServerConsole chat= new ServerConsole(host, port);
    chat.accept();  //Wait for console data
  }

}
