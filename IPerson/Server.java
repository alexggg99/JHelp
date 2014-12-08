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
    
    public IPerson addPerson(IPerson person){
        persons.add(person);
        return person;
    }
    
    public boolean deletePerson(IPerson person){
        return persons.remove(person);
    }
    
    public IPerson updatePerson(IPerson person, String name, String address){
        IPerson currPerson = findPerson(person);
        if(currPerson != null){
            currPerson.updateName(name);
            currPerson.updateAddress(address);
        }
        return currPerson;
    }
    
    public IContact addContact(IPerson person, String email, String mobilePhone){
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
    
    public IContact updatePersonContact(IPerson person, String email, String address, int id){
        IPerson currPerson = findPerson(person);
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
    
//    public void printPersons(){
//        for (Map.Entry<Person, Contact> entrySet : persons.entrySet()) {
//                Person key = entrySet.getKey();
//                System.out.print(key);
//                Contact value = entrySet.getValue();
//                System.out.println(" { " + value + "}");
//            }
//    }
}
