
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import java.io.IOException;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;

  ChatIF serverUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client.getInfo("id"));

    if (msg.toString().startsWith("#login")){
      String[] tmp = msg.toString().split(" ");
      client.setInfo("id", tmp[1]);
      System.out.println(tmp[1] + " has logged on");
      this.sendToAllClients(tmp[1] + " has logged on");
    }
    else {
      String message = (String)client.getInfo("id") + "> " + msg;
      this.sendToAllClients(message);
    }
    
  }

  public void handleMessageFromServerUI(String message)
  {
    if (message.startsWith("#")){
      handleCommand(message);
    }
    else{
      serverUI.display(message);
      sendToAllClients("SERVER MSG> " + message);
    }
  }

  private void handleCommand(String command){
    String[] commandParts = command.split(" ");

    if (command.equals("#quit")){
      System.exit(0);
    }
    else if (command.equals("#stop")){
      stopListening();
    }
    else if (command.equals("#close")){
      try {
        close();
      } catch (IOException e) {
        System.out.println("Error occured while trying to close server");
      }
    }
    else if (commandParts[0].equals("#setport")){
      if (isListening()){
        System.out.println("Cannot set port while server is open");
        return;
      }
      try {
        setPort(Integer.parseInt(commandParts[1]));
        System.out.println("Port set to: " + commandParts[1]);

      } catch (Exception e) {
        System.out.println("Error occurred while trying to set port");
      }
    }
    else if (command.equals("#start")){
      if (isListening()){
        System.out.println("Cannot start server if its already running");
        return;
      }
      try {
        listen();
      } catch (IOException e) {
        System.out.println("Error occured while trying to start server");
      }
    }
    else if (command.equals("#getport")){
      System.out.println("Current port is: " + getPort());
    }
    else{
      System.out.println("Command unknown");
    }
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }

  @Override
  protected void clientConnected(ConnectionToClient client){
    System.out.println("A new client has connected to the server");
  }

  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client){
    System.out.println(client.getInfo("id") + " has disconnected");
  }
  
  
  //Class methods ***************************************************
  

}
//End of EchoServer class
