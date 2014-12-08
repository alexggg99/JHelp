/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JUnitTest;

import IPerson.Person;
import IPerson.Contact;
import IPerson.IContact;
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
public class ContactTest {
    
    private IContact contact;
    
    @Before
    public void SetUp(){
        this.contact = new Contact("test@test.ru", "+78922551237");
    }
    
    @Test
    public void updateContact(){
        contact.updateContact("test2@test.ru", "+79113601412");
        assertEquals(new Contact("test2@test.ru", "+79113601412"), contact);
    }
    
    @Test
    public void testMobile(){
        assertEquals("+78922551237", contact.getMobilePhone());
    }
    
    @Test
    public void testEmail(){
        assertEquals("test@test.ru", contact.getEmail());
    }
    
}
