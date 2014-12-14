/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.util.Objects;

/**
 *
 * @author alexey
 */
public class Contact implements IContact{
    private String email, mobilePhone;
    private int id;
    private static int count = 0; 
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getContact() {
        return mobilePhone + '/' + email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Contact(String email, String mobilePhone) {
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.id = ++count;
    }
    
    public Contact(int id ,String email, String mobilePhone) {
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getMobilePhone() {
        return mobilePhone;
    }

    @Override
    public String toString() {
        return "email= " + email + ", mobilePhone= " + mobilePhone;
    }

    @Override
    public IContact updateContact(String email, String mobilePhone) {
        setEmail(email);
        setMobilePhone(mobilePhone);
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.email);
        hash = 23 * hash + Objects.hashCode(this.mobilePhone);
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
        final Contact other = (Contact) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.mobilePhone, other.mobilePhone)) {
            return false;
        }
        return true;
    }

}
