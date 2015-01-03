/*
 * Class ClientThread.
 */
package jhelp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a network connection between end client of
 * {@link jhelp.Client} type and {@link jhelp.Server} object. Every object of
 * this class may work in separate thread.
 * @author <strong >Y.D.Zakovryashin, 2009</strong>
 * @version 1.0
 * @see jhelp.Client
 * @see jhelp.Server
 */
public class ClientThread implements JHelp, Runnable {

    /**
     *
     */
    private Server server;
    /**
     *
     */
    private Socket clientSocket;
    /**
     *
     */
    private ObjectInputStream input;
    /**
     *
     */
    private ObjectOutputStream output;

    /**
     * Creates a new instance of Client
     * @param server reference to {@link Server} object.
     * @param socket reference to {@link java.net.Socket} object for connection
     * with client application.
     */
    public ClientThread(Server server, Socket socket) throws IOException {
        this.server = server;
        clientSocket = socket;
        System.out.println("MClient: constructor");
    }
    
    
    /**
     * The method defines main job cycle for the object.
     */
    public void run() {
        System.out.println("MClient: run");
        Object obj;
        try {
            connect();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            while ((obj = input.readObject()) != null) {
                System.out.println("Received " + obj.toString() + " from Client ");
                Data result = getData((Data)obj); 
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.flush();
                    output.close();
                    input.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }    
      
    }

    
    
    /**
     * Opens input and output streams for data interchanging with
     * client application.  The method uses default parameters.
     * @return error code. The method returns {@link JHelp#OK} if streams are
     * successfully opened, otherwise the method returns {@link JHelp#ERROR}.
     */
    public int connect() throws ClassNotFoundException {
        System.out.println("MClient: connect");
        try{
           output = new ObjectOutputStream(clientSocket.getOutputStream());
           input = new ObjectInputStream(clientSocket.getInputStream());
        }catch(IOException ex){
            System.out.println(ex.getMessage());
        }
            
        return JHelp.OK;
    }

    /**
     * Opens input and output streams for data interchanging with
     * client application. This method uses parameters specified by parameter
     * <code>args</code>.
     * @param args defines properties for input and output streams.
     * @return error code. The method returns {@link JHelp#OK} if streams are
     * successfully opened, otherwise the method returns {@link JHelp#ERROR}.
     */
    public int connect(String[] args) {
        System.out.println("MClient: connect");
        
        return JHelp.OK;
    }

    /**
     * Transports {@link Data} object from client application to {@link Server}
     * and returns modified {@link Data} object to client application.
     * @param data {@link Data} object which was obtained from client
     * application.
     * @return modified {@link Data} object
     */
    public Data getData(Data data) {
        System.out.println("MClient: getData");
        Data result = server.getData(data);
        try {
            output.writeObject(result);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    /**
     * The method closes connection with client application.
     * @return error code. The method returns {@link JHelp#OK} if input/output 
     * streams and connection with client application was closed successfully,
     * otherwise the method returns {@link JHelp#ERROR}.
     */
    public int disconnect() {
        System.out.println("Client: disconnect");
        return JHelp.OK;
    }
}