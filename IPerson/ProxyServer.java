/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author alexey
 */
public class ProxyServer extends UnicastRemoteObject implements ServerRemote{
    static Server server;

    public ProxyServer() throws RemoteException {
        server = new Server();
        try {
            Registry registry = LocateRegistry.createRegistry(2099);
            registry.rebind("IPersonBook", this);
            System.out.println("Server has started...");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws RemoteException {
          ProxyServer proxyServer = new ProxyServer();
//        Person tmp = (Person) server.addPerson("TEST", "TEST");
//        server.addContact(tmp, "a@tvr", "+8 871 12");
//        tmp = (Person) server.addPerson("TEST2", "TEST2");
//        IContact con = server.addContact(tmp, "a2@tvr", "+8 871 15");
//        server.deleteContact(tmp);
//        server.printPersons();  
    }

    @Override
    public IPerson addPerson(String name, String address) throws RemoteException {
         return null;
//                 server.addPerson(name, address);
    }

    @Override
    public IContact deletePerson(Person person) {
        return null;
//                server.deletePerson(person);
    }

    @Override
    public IContact addContact(Person person, String email, String mobilePhone) throws RemoteException {
        return server.addContact(person, email, mobilePhone);
    }

    @Override
    public IContact deleteContact(Person person) throws RemoteException {
        return null;
////                server.deleteContact(person);
    }

//    @Override
//    public void printPersons() throws RemoteException {
//         server.printPersons();
//    }

    @Override
    public void printPersons() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
