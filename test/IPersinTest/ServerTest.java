/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnitTest;

import IPerson.Contact;
import IPerson.IPerson;
import IPerson.Person;
import IPerson.Server;
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
    public void setUp() {
        server = new Server();
        person = new Person("Sergey", "Pushkin");
        server.addPerson(person);
    }
    
    @Test
    public void addPersonTest(){
        IPerson sergey2 = new Person("Sergey2", "Pushkin2");
        server.addPerson(sergey2);
        assertTrue(server.getPersons().contains(sergey2));
    }
    
    @Test
    public void deletePersonTest(){
        server.deletePerson(person);
        assertFalse(server.getPersons().contains(person));
    }
    
    @Test
    public void updatePersonTest(){
        IPerson newPerson = new Person("name", "address");
        assertEquals(newPerson ,server.updatePerson(person, "name", "address"));
    }
    
    @Test
    public void addContactToPerson(){
        server.addContact(person, "test@123", "9899055");
        assertTrue(person.getContacts().containsValue(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void removeContact(){
        server.addContact(person, "test@123", "9899055");
        server.removePersonContact(person, 1);
        assertFalse(person.getContacts().containsValue(new Contact("test@123", "9899055")));
    }
    
    @Test
    public void updatePersonContact(){
        server.addContact(person, "test@123", "9899055");
        server.updatePersonContact(person, "54ret@me", "+8 911 12", 1);
        assertTrue(person.getContacts().containsValue(new Contact("54ret@me", "+8 911 12")));
    }
}
