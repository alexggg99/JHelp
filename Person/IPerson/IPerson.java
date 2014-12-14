/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IPerson extends Serializable, Comparable<Object>{
	String getName ();
	int getId ();
	String getAddress();
        void updateName(String name);
        void updateAddress(String address);
        IContact addContact(String Email, String Mobile);
        IContact removeContact(int id);
        IContact updateContact(int id, String Email, String Mobile);
        List<IContact> getContacts();
}
