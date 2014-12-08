/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPersinTest;

import IPerson.Person;
import IPerson.*;
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
public class PersonTest {
    
    private IPerson person;
    private Contact contact;
    private Contact otherContact;
    
    @Before
    public void SetUp(){
        this.person = new Person("Alexey", "Stachek 72-14");
        this.contact = new Contact("test@test.ru", "+78922551237");
        otherContact = new Contact("test2@test.ru", "+79062551237");
    }
    
    @Test
    public void equalsPersons(){
        IPerson otherPerson = new Person("Alexey", "Stachek 72-14");
        assertTrue(person.equals(otherPerson));
    }
    
    @Test
    public void updatePersonNullValues(){
        person.updateName("Vladimir");
        assertEquals(new Person("Vladimir", null).getName(), person.getName());
    }
    
    @Test
    public void addPersonContact(){
        assertEquals(otherContact, person.addContact(otherContact.getEmail(), otherContact.getMobilePhone()));
    }
    
    @Test
    public void removePersonContact(){
        person.addContact(otherContact.getEmail(), otherContact.getMobilePhone());
        assertEquals(otherContact, person.removeContact(1));
    }
    
    @Test
    public void updatePersonContact(){
        person.addContact(otherContact.getEmail(), otherContact.getMobilePhone());
        person.updateContact(1, "test6@list.ru", "+7 911 3254413");
        assertTrue(person.getContacts().containsValue(new Contact("test6@list.ru", "+7 911 3254413")));
    }
    
    @Test(expected = NoContactFoundException.class)
    public void addNonExistingContact(){
        Contact contact = new Contact("www", null);
        person.updateContact(1, "test3@list.ru", "+7 911 3254413");
        assertEquals(Arrays.asList(new Contact("test3@list.ru", "+7 911 3254413")), person.getContacts());
    }
    
}
