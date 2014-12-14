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
    private List<IPerson> persons;

    public Server() {
        //this.persons = Collections.synchronizedSet(new TreeSet<IPerson>());
        this.persons = Collections.synchronizedList(new ArrayList<IPerson>());
    }
    
    public IPerson addPerson(String name, String address){
        IPerson newPerson = new Person(name, address);
        persons.add(newPerson);
        return newPerson;
    }
    
    public boolean deletePerson(int personId){
        Iterator it = persons.iterator();
        while(it.hasNext()){
            if(((IPerson)it.next()).getId() == personId){
                it.remove();
                break;
            }
        }
        return ifContainId(personId);
    }
    
    public boolean ifContainId(int personId){
        for(IPerson p : getPersons())
            if(p.getId() == personId)
                return true;
        return false;
    }
    
    public IPerson updatePerson(int personId, String name, String address){
//        for(IPerson p : getPersons())
//            if(p.getId() != personId)
//                return addPerson(name, address);
//                return null;
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
        if(email.isEmpty() && mobilePhone.isEmpty()){
            return null;
        }
        IPerson currPerson = findPerson(person.getId());
        if(currPerson != null){
            currPerson.addContact(email, mobilePhone);
        }
        return null;
    }
    
    public List<IContact> getPersonContacts(IPerson person){
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
    
    public List<IPerson> getPersons(){
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
            if(tmp.equals(person)){
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
