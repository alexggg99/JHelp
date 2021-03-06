/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;
import static IPerson.ProxyServer.server;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
/**
 *
 * @author alexey
 */
public interface ServerRemote extends Remote{
    IPerson addPerson(String name, String address) throws RemoteException;
    IPerson updatePerson(int personId, String name, String address) throws RemoteException;
    boolean deletePerson(int personId) throws RemoteException;
    List<IPerson> getPersons() throws RemoteException;
    IContact addPersonContact(Person person, String email, String mobilePhone) throws RemoteException;
    IContact updatePersonContact(int personId, String email, String address, int id) throws RemoteException;
    IContact removePersonContact(Person person, int contactId) throws RemoteException;
    List<IContact> getPersonContacts(IPerson person) throws RemoteException;
    public IPerson findPerson(int personId) throws RemoteException;
}
