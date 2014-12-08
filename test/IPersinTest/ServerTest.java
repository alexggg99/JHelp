/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPersinTest;

import IPerson.Contact;
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
        person = new Person("Sergey", "Pushkin");
        server.addPerson("Sergey", "Pushkin");
    }
    
    @Test
    public void addPersonTest(){
        server.addPerson("Sergey2", "Pushkin2");
        assertTrue(server.getPersons().contains(new Person("Sergey2", "Pushkin2")));
    }
    
    @Test
    public void deletePersonTest(){
        server.deletePerson("Sergey", "Pushkin");
        assertFalse(server.getPersons().contains(new Person("Sergey", "Pushkin")));
    }
    
    @Test
    public void updatePersonTest(){
        IPerson newPerson = new Person("name12", "address");
        assertEquals(newPerson ,server.updatePerson(1, "name12", "address"));
    }
    
    @Test
    public void addContactToPerson(){
        server.addPersonContact(person, "test@123", "9899055");
        assertTrue(person.getContacts().containsValue(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void removeContact(){
        server.addPersonContact(person, "test@123", "9899055");
        server.removePersonContact(person, 1);
        assertFalse(person.getContacts().containsValue(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void updatePersonContact(){
        server.addPersonContact(person, "test@123", "9899055");
        server.updatePersonContact(1, "54ret@me", "+8 911 12", 1);
        boolean t = person.getContacts().containsValue(new Contact("54ret@me", "+8 911 12"));
        assertTrue(t);
    }
}
