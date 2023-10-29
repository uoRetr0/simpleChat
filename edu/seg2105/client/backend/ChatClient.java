// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import ocsf.client.*;

import java.io.*;


/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  String loginID;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String loginID, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = loginID;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
    
    
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if (message.startsWith("#")){
        handleCommand(message);
      }
      else{
        sendToServer(message);
      }
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  private void handleCommand(String command){
    String[] commandParts = command.split(" ");

    if (command.equals("#quit")){
      quit();
    }
    else if (command.equals("#logoff")){
      try {
        closeConnection();
      } catch (IOException e) {
        quit();
      }
    }
    else if (command.equals("#sethost")){
      if (isConnected()){
        System.out.println("Error: cannot set host while already connected to server");
        return;
      }
      try {
        setHost(commandParts[1]);
        System.out.println("Host set to: " + commandParts[1]);

      } catch (Exception e) {
        System.out.println("Error occurred while trying to set Host");
      }
    }
    else if (commandParts[0].equals("#setport")){
      if (isConnected()){
        System.out.println("Error: cannot set port while already connected to server");
        return;
      }
      try {
        setPort(Integer.parseInt(commandParts[1]));
        System.out.println("Port set to: " + commandParts[1]);

      } catch (Exception e) {
        System.out.println("Error occurred while trying to set port");
      }
    }
    else if (command.equals("#login")){
      if (isConnected()){
        System.out.println("Error: cannot login while already connect to server");
        return;
      }
      try {
        openConnection();
      } catch (IOException e) {
        System.out.println("Error occured while trying to connect to server");
      }
    }
    else if (command.equals("#gethost")){
      System.out.println("Current host is: " + getHost());
    }
    else if (command.equals("#getport")){
      System.out.println("Current port is: " + getPort());
    }
    else{
      System.out.println("Command unknown");
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }

  @Override
  protected void connectionException(Exception exception){
    clientUI.display("The server is shut down");
    quit();
  }

  @Override
  protected void connectionClosed(){
    clientUI.display("Connection closed");
  }


}
//End of ChatClient class
