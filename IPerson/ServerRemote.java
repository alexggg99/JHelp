/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;
import java.rmi.*;
import java.util.HashMap;
import java.util.Set;
/**
 *
 * @author alexey
 */
public interface ServerRemote extends Remote{
    IPerson addPerson(String name, String address) throws RemoteException;
    IContact updatePerson(IPerson person, String name, String address) throws RemoteException;
    IContact deletePerson(Person person) throws RemoteException;
    Set<IPerson> getPersons();
    IContact addPersonContact(Person person, String email, String mobilePhone) throws RemoteException;
    IContact updatePersonContact(IPerson person, String email, String address, int id);
    IContact removePersonContact(Person person, int contactId) throws RemoteException;
    HashMap<Integer, IContact> getPersonContacts(IPerson person);
}
