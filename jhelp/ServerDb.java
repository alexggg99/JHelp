/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jhelp;


import com.sun.corba.se.impl.io.InputStreamHook;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class presents server directly working with database.
 * The complete connection string should take the form of:<br>
 * <code><pre>
 *     jdbc:subprotocol://servername:port/datasource:user=username:password=password
 * </pre></code>
 * Sample for using MS Access data source:<br>
 * <code><pre>
 *  private static final String accessDBURLPrefix
 *      = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
 *  private static final String accessDBURLSuffix
 *      = ";DriverID=22;READONLY=false}";
 *  // Initialize the JdbcOdbc Bridge Driver
 *  try {
 *         Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 *      } catch(ClassNotFoundException e) {
 *         System.err.println("JdbcOdbc Bridge Driver not found!");
 *      }
 *
 *  // Example: method for connection to a Access Database
 *  public Connection getAccessDBConnection(String filename)
 *                           throws SQLException {
 *       String databaseURL = accessDBURLPrefix + filename + accessDBURLSuffix;
 *       return DriverManager.getConnection(databaseURL, "", "");
 *   }
 *</pre></code>
 *  @author <strong >Y.D.Zakovryashin, 2009</strong>
 */
public class ServerDb implements JHelp {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    /**
     * Creates a new instance of <code>ServerDb</code> with default parameters.
     * Default parameters are:<br>
     * <ol>
     * <li><code>ServerDb</code> host is &laquo;localhost&raquo;;</li>
     * <li>{@link java.net.ServerSocket} is opened on
     * {@link jhelp.JHelp#DEFAULT_DATABASE_PORT};</li>
     * </ol>
     */
    public ServerDb() throws IOException {
        this(DEFAULT_DATABASE_PORT);
        System.out.println("SERVERDb: default constructor");
    }

    /**
     * Constructor creates new instance of <code>ServerDb</code>. 
     * @param port defines port for {@link java.net.ServerSocket} object.
     */
    public ServerDb(int port) throws IOException {
        System.out.println("SERVERDb: constructor");
        serverSocket = new ServerSocket(port);
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver loaded");
//        } catch (ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
    }

    /**
     * Constructor creates new instance of <code>ServerDb</code>. 
     * @param args array of {@link java.lang.String} type contains connection
     * parameters.
     */
    public ServerDb(String[] args) {
        System.out.println("SERVERDb: constructor");

    }

    /**
     * Start method for <code>ServerDb</code> application.
     * @param args array of {@link java.lang.String} type contains connection
     * parameters.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("SERVERDb: main");
        ServerDb serverDB = new ServerDb();
        if (serverDB.connect() == JHelp.READY) {
            serverDB.run();
            serverDB.disconnect();
        }
    }

    /**
     * Method defines job cycle for client request processing.
     */
    private void run() throws IOException, ClassNotFoundException {
        System.out.println("SERVERDb: run");
        Object obj;
        while(true){
            try{
               //clientSocket = serverSocket.accept();
                obj = input.readObject();
                Data result = getData((Data)obj);
                output.writeObject(result);
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     *
     * @return error code. The method returns {@link JHelp#OK} if streams are
     * opened successfully, otherwise the method returns {@link JHelp#ERROR}.
     */
    public int connect() throws IOException {
        System.out.println("SERVERDb: connect");
        try{
            clientSocket = serverSocket.accept();
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            return JHelp.READY;
        }catch(IOException ex){
            return JHelp.ERROR; 
        }   
    }

    /**
     * Method sets connection to database and create {@link java.net.ServerSocket}
     * object for waiting of client's connection requests.
     * @return error code. Method returns {@link jhelp.JHelp#READY} in success
     * case. Otherwise method return {@link jhelp.JHelp#ERROR} or error code.
     */
    public int connect(String[] args) {
        System.out.println("SERVERDb: connect");
        return JHelp.READY;
    }

    /**
     * Method returns result of client request to a database.
     * @param data object of {@link jhelp.Data} type with request to database.
     * @return object of {@link jhelp.Data} type with results of request to a
     * database.
     * @see Data
     * @since 1.0
     */
    public Data getData(Data data) {
        System.out.println("SERVERDb: getData");
        Data result = null;
        int res;
        if(data.getOperation() == JHelp.SELECT)
            result = selectData(data);
        if(data.getOperation() == JHelp.INSERT)
                result = insertRow(data);
        if(data.getOperation() == JHelp.UPDATE)
                result = updateRow(data);
        if(data.getOperation() == JHelp.DELETE)
                result = deleteRow(data);
        return result;
    }
    
    //Select operation
    private Data selectData(Data data){
         Connection connection = null;
         Statement statement = null;
         ResultSet resultset = null;
         Data output = null;
         ArrayList<Item> values = new ArrayList<>();
         try{
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhelp", "jhelp", "jhelp");
             statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
             String query = "Select id, code, def from jhelp where code ='"+data.getKey().getItem()+"'";
             resultset = statement.executeQuery(query);
             //create array of values
             while(resultset.next()){
                 String def = resultset.getString("def");
                 int id = resultset.getInt("id");
                 values.add(new Item(id, def , JHelp.ORIGIN));
             }
             //construct output data object
             output = new Data(data.getOperation(), data.getKey(), values.toArray(new Item[values.size()]));
             return output;
                           
         }catch(SQLException ex){
             System.out.println(ex.getMessage());
         }finally{
             if(connection != null) try{connection.close();}catch(SQLException ignore){}
             if(statement != null) try{statement.close();}catch(SQLException ignore){}
             if(resultset != null) try{resultset.close();}catch(SQLException ignore){}
         }
         return null;
    }

    
    private Data insertRow(Data row){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        Data emptyData = new Data(row.getOperation(), null, null);
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhelp", "jhelp", "jhelp");
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            resultset = statement.executeQuery("Select id, code, def from jhelp where code = id < 5");
            
            //check if result set is updatable
            int concurrance = resultset.getConcurrency();
            if(concurrance != ResultSet.CONCUR_UPDATABLE || row.getValue(0).getItem() == null){
                System.out.println("Sorry cant update database !");
                return null;
            }
            //if resultset is empty
            if(!resultset.next()){
                return null;
            }else{ 
                resultset.moveToInsertRow();
                resultset.updateString("def", row.getValue(0).getItem());
                resultset.updateString("code", row.getKey().getItem());
                resultset.insertRow();
                Item[] values = new Item[1];
                values[0] = row.getValue(0);
                return new Data(row.getOperation(), row.getKey(), values);
            }
        }catch(SQLException ex){
            return null;
        }finally{
            if(connection != null) try{connection.close();}catch(SQLException ignore){}
             if(statement != null) try{statement.close();}catch(SQLException ignore){}
             if(resultset != null) try{resultset.close();}catch(SQLException ignore){}
        }
    }
    
    private Data updateRow(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhelp", "jhelp", "jhelp");
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            resultset = statement.executeQuery("Select id, code, def from jhelp where code ='"+data.getKey().getItem()+"'");
            
            
            
            //check if result set is updatable
            int concurrance = resultset.getConcurrency();
            if(concurrance != ResultSet.CONCUR_UPDATABLE){
                System.out.println("Sorry cant update database !");
                return null;
            }
                //if resultset is empty
                if(!resultset.next()){
                    return null;
                }else{ 
                    resultset.updateString("def", data.getValue(0).getItem());
                    resultset.updateRow();
                    return  data;
                }
        }catch(SQLException ex){
            return null;
        }finally{
            if(connection != null) try{connection.close();}catch(SQLException ignore){}
             if(statement != null) try{statement.close();}catch(SQLException ignore){}
             if(resultset != null) try{resultset.close();}catch(SQLException ignore){}
        }
    }
    
    private Data deleteRow(Data data){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        Data emptyData = new Data(data.getOperation(), null, null);
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jhelp", "jhelp", "jhelp");
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            resultset = statement.executeQuery("Select id, code, def from jhelp where id ='"+data.getValue(0).getId()+"'");
            
            //check if result set is updatable
            int concurrance = resultset.getConcurrency();
            if(concurrance != ResultSet.CONCUR_UPDATABLE){
                System.out.println("Sorry cant update database !");
                return null;
            }
            //if resultset is empty
            if(!resultset.next()){
                return null;
            }else{ 
                resultset.deleteRow();
                return  null;
            }
        }catch(SQLException ex){
            return null;
        }finally{
            if(connection != null) try{connection.close();}catch(SQLException ignore){}
             if(statement != null) try{statement.close();}catch(SQLException ignore){}
             if(resultset != null) try{resultset.close();}catch(SQLException ignore){}
        }
    }
    
    /**
     * Method disconnects <code>ServerDb</code> object from a database and closes
     * {@link java.net.ServerSocket} object.
     * @return disconnect result. Method returns {@link #DISCONNECT} value, if
     * the process ends successfully. Othewise the method returns error code,
     * for example {@link #ERROR}.
     * @see jhelp.JHelp#DISCONNECT
     * @since 1.0
     */
    public int disconnect() {
        System.out.println("SERVERDb: disconnect");
        try {
            serverSocket.close();
        } catch (IOException ex) {
            return JHelp.ERROR;
        }
        return JHelp.DISCONNECT;
    }
}
