/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.rmi.RemoteException;

/**
 *
 * @author alexey
 */
public class Test {
    public static void main(String[] args) throws RemoteException {
          Client client = new Client();
        Person tmp = (Person) client.addPerson("TEST", "TEST");
//        client.addContact(tmp, "a@tvr", "+8 871 12");
//        tmp = (Person) server.addPerson("TEST2", "TEST2");
//        IContact con = server.addContact(tmp, "a2@tvr", "+8 871 15");
//        server.deleteContact(tmp);
        client.printPersons();  
    }
}
