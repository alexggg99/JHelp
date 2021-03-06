/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import static IPerson.ProxyServer.server;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author alexey
 */
public class Client implements ServerRemote, Remote{
    ServerRemote server;
    
    public Client() throws RemoteException{
        try{
            server = (ServerRemote) Naming.lookup("rmi://localhost:2050/IPerson");
            System.out.println("Server conected..");
//            System.out.println(getPersons());
//            server.addPerson("T", "M");
//            server.addPerson("A", "M");
//            System.out.println(getPersons());
//            server.deletePerson("T", "M");
//            System.out.println(getPersons());
//            addPersonContact(new Person("A", "M"), "bhh@pop", "+7 8892 223");
//            System.out.print(getPersons().iterator().next());
//            System.out.println(getPersons().iterator().next().getContacts());
//            removePersonContact(new Person("T", "M"), 1);
//            System.out.println(getPersons().iterator().next().getContacts());
        }catch(Exception ex){
            throw new RemoteException();
        }
    }

    public static void main(String[] args) throws RemoteException {
        Client client = new Client();
    }
    
    @Override
    public IPerson addPerson(String name, String address) throws RemoteException {
        return server.addPerson(name, address);
    }

    @Override
    public boolean deletePerson(int personId) throws RemoteException {
        return server.deletePerson(personId);
    }

    @Override
    public IPerson updatePerson(int personId, String name, String address) throws RemoteException {
        return server.updatePerson(personId, name, address);
    }

    @Override
    public List<IPerson> getPersons() throws RemoteException {
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
    public List<IContact> getPersonContacts(IPerson person) throws RemoteException {
        return server.getPersonContacts(person);
    }
    
    @Override
    public IPerson findPerson(int personId) throws RemoteException{
        return server.findPerson(personId);
    }
}
