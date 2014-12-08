/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.util.*;

/**
 *
 * @author alexey
 */
public class Server{
    private Set<IPerson> persons;

    public Server() {
        this.persons = Collections.synchronizedSet(new TreeSet<IPerson>());
    }
    
    public IPerson addPerson(String name, String address){
        IPerson newPerson = new Person(name, address);
        persons.add(newPerson);
        return newPerson;
    }
    
    public boolean deletePerson(String name, String address){
        IPerson person = new Person(name, address);
        return persons.remove(person);
    }
    
    public IPerson updatePerson(int personId, String name, String address){
        IPerson currPerson = null;
        for(IPerson p : persons){
            if(p.getId() == personId)
                currPerson = p;
        }
        if(currPerson != null){
            currPerson.updateName(name);
            currPerson.updateAddress(address);
        }
        return currPerson;
    }
    
    public IContact addPersonContact(IPerson person, String email, String mobilePhone){
        IPerson currPerson = findPerson(person);
        if(currPerson != null){
            currPerson.addContact(email, mobilePhone);
        }
        return null;
    }
    
    public HashMap<Integer, IContact> getPersonContacts(IPerson person){
        return findPerson(person).getContacts();
    }
    
    public IContact removePersonContact(IPerson person, int contactId){
//        IContact contact = persons.remove(person);
//        persons.put(person, new Contact(null, null));
        IPerson currPerson = findPerson(person);
        if(currPerson != null){
            IContact contact = currPerson.removeContact(contactId);
            return contact;
        }
        return null;
    }
    
    public Set<IPerson> getPersons(){
        return persons;
    }
    
    public IContact updatePersonContact(int personId, String email, String address, int id){
        IPerson currPerson = findPerson(personId);
        if(currPerson != null){
            IContact contact = currPerson.updateContact(id, email, address);
            return contact;
        }
        return null;
    }
    
    public IPerson findPerson(IPerson person){
        Iterator<IPerson> it = persons.iterator();
        while(it.hasNext()){
            IPerson tmp = it.next();
            if(tmp == person){
                return tmp;
            }
        }
        return null;
    }
    
    public IPerson findPerson(int personId){
        Iterator<IPerson> it = persons.iterator();
        while(it.hasNext()){
            IPerson tmp = it.next();
            if(tmp.getId() == personId){
                return tmp;
            }
        }
        return null;
    }
   
}
