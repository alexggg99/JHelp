/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPersonTest;

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
        IContact con = person.addContact("test2@test.ru", "+79062551237");
        person.removeContact(con.getId());
        assertTrue(person.getContacts().isEmpty());
    }
    
    @Test
    public void updatePersonContact(){
        IContact con = person.addContact("test2@test.ru", "+79062551237");
        person.updateContact(con.getId(), "test6@list.ru", "+7 911 3254413");
        assertTrue(person.getContacts().contains(new Contact("test6@list.ru", "+7 911 3254413")));
    }
    
    @Test
    public void addNonExistingContact(){
        Contact contact = new Contact("www", null);
        person.updateContact(contact.getId(), "test3@list.ru", "+7 911 3254413");
        assertTrue(person.getContacts().isEmpty());
    }
    
}
