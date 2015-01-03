/*
 * Server.java
 *
 */
package jhelp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class sets a network connection between end client's objects
 * of {@link jhelp.Client} type and single {@link jhelp.ServerDb} object.
 * @author <strong >Y.D.Zakovryashin, 2009</strong>
 * @version 1.0
 * @see jhelp.Client
 * @see jhelp.ClientThread
 * @see jhelp.ServerDb
 */
public class Server implements JHelp {

    /**
     *
     */
    private ServerSocket serverSocket;
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

    /** Creates a new instance of Server */
    public Server() throws IOException {
        this(DEFAULT_SERVER_PORT, DEFAULT_DATABASE_PORT);
        System.out.println("SERVER: Default Server Constructed");
    }

    /**
     *
     * @param port
     * @param dbPort
     */
    public Server(int port, int dbPort) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = new Socket(InetAddress.getLocalHost().getHostName(), dbPort);
        System.out.println("SERVER: Server Constructed");
    }

    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) throws IOException, Exception {
        System.out.println("SERVER: main");
        Server server = new Server();
        if (server.connect() == JHelp.OK) {
            server.run();
            server.disconnect();
        }
    }

    /**
     *
     */
    private void run() throws Exception {
        System.out.println("SERVER: run"); 
        while(true){
            try{
                Socket sock = serverSocket.accept();
                ClientThread clnt = new ClientThread(this, sock);
                Thread clntTread = new Thread(clnt);
                clntTread.start();
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * The method sets connection to database ({@link jhelp.ServerDb} object) and
     * create {@link java.net.ServerSocket} object for waiting of client's
     * connection requests. This method uses default parameters for connection.
     * @return error code. The method returns {@link JHelp#OK} if streams are
     * successfully opened, otherwise the method returns {@link JHelp#ERROR}.
     * @throws java.io.IOException
     */
    @Override
    public int connect() throws Exception{
        System.out.println("SERVER: connect");
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
        return OK;
    }

    /**
     * The method sets connection to database ({@link jhelp.ServerDb} object) and
     * create {@link java.net.ServerSocket} object for waiting of client's
     * connection requests.
     * @param args specifies properties of connection.
     * @return error code. The method returns {@link JHelp#OK} if connection are
     * openeds uccessfully, otherwise the method returns {@link JHelp#ERROR}.
     */
    public int connect(String[] args) {
        System.out.println("SERVER: connect");
        return OK;
    }

    /**
     * Transports initial {@link Data} object from {@link ClientThread} object to
     * {@link ServerDb} object and returns modified {@link Data} object to
     * {@link ClientThread} object.
     * @param data Initial {@link Data} object which was obtained from client
     * application.
     * @return modified {@link Data} object
     */
    public Data getData(Data data) {
        System.out.println("SERVER:getData");
        try {
            output.writeObject(data);
            Object result =  input.readObject();
            return (Data)result;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * The method closes connection with database.
     * @return error code. The method returns {@link JHelp#OK} if a connection
     * with database ({@link ServerDb} object) closed successfully,
     * otherwise the method returns {@link JHelp#ERROR} or any error code.
     */
    public int disconnect() {
        System.out.println("SERVER: disconnect");
        try {
            serverSocket.close();
            clientSocket.close();
        } catch (IOException ex) {
            return JHelp.ERROR;
        }
        return JHelp.DISCONNECT;
    }
}
