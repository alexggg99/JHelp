/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPersonTest;

import IPerson.Contact;
import IPerson.IContact;
import IPerson.IPerson;
import IPerson.Person;
import IPerson.ProxyServer;
import IPerson.Server;
import java.rmi.RemoteException;
import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alexey
 */
public class ServerTest {
    Server server;
    IPerson person;
    
    @Before
    public void setUp() throws RemoteException {
        server = new Server();
        person = server.addPerson("Sergey", "Pushkin");
    }
    
    @Test
    public void addPersonTest(){
        server.addPerson("Sergey2", "Pushkin2");
        assertTrue(server.getPersons().contains(new Person("Sergey2", "Pushkin2")));
    }
    
    @Test
    public void deletePersonTest(){
        assertFalse(server.deletePerson(person.getId()));
    }
    
    @Test
    public void updateNotExistingPersonTest(){
        IPerson newPerson = new Person("name12", "address");
        assertEquals(null ,server.updatePerson(125, "name12", "address"));
    }
    
    @Test
    public void addContactToPersonTest(){
        server.addPersonContact(person, "test@123", "9899055");
        IPerson p = server.getPersons().get(0);
        assertTrue(p.getContacts().contains(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void removeContactTest(){
        server.addPersonContact(person, "test@123", "9899055");
        server.removePersonContact(person, 1);
        assertFalse(person.getContacts().contains(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void updatePersonContactTest(){
        IContact con = server.addPersonContact(person, "test@123", "9899055");
        server.updatePersonContact(con.getId(), "54ret@me", "+8 911 12", 1);
        IPerson p = server.getPersons().get(0);
        boolean t = p.getContacts().contains(new Contact("54ret@me", "+8 911 12"));
        assertTrue(t);
    }
    
    @Test
    public void findPersonTest(){
        IPerson person = server.addPerson("Sergey2", "Pushkin2");
        IPerson p = server.findPerson(person.getId());
        assertNotNull(p);
    }
}
