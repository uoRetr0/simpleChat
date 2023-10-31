import java.io.IOException;
import java.util.Scanner;

public class ServerConsole implements ChatIF{
    final public static int DEFAULT_PORT = 5555;

    EchoServer server;

    Scanner fromConsole; 

    public ServerConsole(int port) 
    {
      server = new EchoServer(port, this);
    	try {
			  server.listen();
      } catch (IOException e) {
			// TODO Auto-generated catch block
		
		  }

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

  public static void main(String[] args) 
  {

    int port = 0;

    try
    {
      port = Integer.parseInt(args[0]);
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }

    try 
    {
      ServerConsole chat= new ServerConsole(port);
      chat.accept();
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}