/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.io.Serializable;

public interface IContact extends Serializable{
	int getId ();
	String getContact();
        IContact updateContact(String email, String mobilePhone);
        String getEmail();
        String getMobilePhone();
}
