/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Set;
/**
 *
 * @author alexey
 */
public class ProxyServer extends UnicastRemoteObject implements ServerRemote{
    static Server server;

    public ProxyServer() throws RemoteException {
        server = new Server();
        try {
            Registry registry = LocateRegistry.createRegistry(2050);
            registry.rebind("IPerson", this);
            System.out.println("Server has started...");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws RemoteException {
          ProxyServer proxyServer = new ProxyServer();
    }

    @Override
    public IPerson addPerson(String name, String address) throws RemoteException {
         return server.addPerson(name, address);
    }

    @Override
    public boolean deletePerson(String name, String address) throws RemoteException {
        return server.deletePerson(name, address);
    }

    @Override
    public IPerson updatePerson(int personId, String name, String address) throws RemoteException {
        return server.updatePerson(personId, name, address);
    }

    @Override
    public Set<IPerson> getPersons() {
        return server.getPersons();
    }

    @Override
    public IContact addPersonContact(Person person, String email, String mobilePhone) throws RemoteException {
        return server.addPersonContact(person, email, mobilePhone);
    }

    @Override
    public IContact updatePersonContact(int personId, String email, String address, int id) throws RemoteException {
        return server.updatePersonContact(personId, email, address, id);
    }

    @Override
    public IContact removePersonContact(Person person, int contactId) throws RemoteException {
        return server.removePersonContact(person, contactId);
    }

    @Override
    public HashMap<Integer, IContact> getPersonContacts(IPerson person) throws RemoteException {
        return server.getPersonContacts(person);
    }
    
    
}
