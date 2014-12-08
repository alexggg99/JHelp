/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.net.MalformedURLException;
import java.rmi.*;

/**
 *
 * @author alexey
 */
public class Client implements ServerRemote{
    ServerRemote server;
    
    public Client(){
        try{
            server = (ServerRemote) Naming.lookup("rmi://localhost:2099/IPersonBook");
            System.out.println("Server conected..");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public IPerson addPerson(String name, String address) throws RemoteException {
        return server.addPerson(name, address);
    }

    @Override
    public IContact deletePerson(Person person) throws RemoteException {
        return server.deletePerson(person);
    }

    @Override
    public IContact addContact(Person person, String email, String mobilePhone) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IContact deleteContact(Person person) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printPersons() throws RemoteException {
        server.printPersons();
    }
    
}
