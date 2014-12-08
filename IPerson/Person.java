/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import IPerson.Contact;
import IPerson.IPerson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author alexey
 */
public class Person implements IPerson {

    private String name, address;
    private int id;
    private volatile static int count = 0;
    private HashMap<Integer, IContact> contacts;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
        synchronized (this) {
            this.id = ++count;
        }
        contacts = new HashMap<Integer, IContact>();
    }

    @Override
    public String toString() {
        return "Name= " + name + ", address= " + address;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.address);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }

    @Override
    public void updateName(String name) {
        this.name = name;
    }

    @Override
    public void updateAddress(String address) {
        this.address = address;
    }

    public HashMap<Integer, IContact> getContacts() {
        return contacts;
    }

    @Override
    public IContact addContact(String Email, String Mobile) {
        IContact contact = new Contact(Email, Mobile);
        Integer id = Integer.valueOf(contacts.size() + 1);
        this.contacts.put(id, contact);
        return contact;
    }

    @Override
    public IContact removeContact(int id) {
        return contacts.remove(id);
    }

    @Override
    public IContact updateContact(int id, String Email, String Mobile) {
        if(!contacts.containsKey(id)){
            throw new NoContactFoundException("No such contact found");
        }
        IContact cont = contacts.get(id);
        cont.updateContact(Email, Mobile);
        return cont;
    }

    @Override
    public int compareTo(Object o) {
        IPerson otherPerson = null;
        if (o == null) {
            return -1;
        }
        if(o instanceof IPerson){
            otherPerson = (IPerson)o;
        }
        return this.getName().compareTo(otherPerson.getName());
    }

}
