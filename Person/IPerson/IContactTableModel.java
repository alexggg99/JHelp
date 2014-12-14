/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author alexey
 */
public class IContactTableModel extends AbstractTableModel{

    private String[] columnNames = {"id",
        "email",
        "mobile"};
    
    private List<IContact> data = new ArrayList<IContact>();
    private Client client;
    private Person person = null;

    public IContactTableModel(Client client){
        this.client = client;
        //this.data = (ArrayList<IContact>) client.getPersonContacts(person);
    }
    
    public void setData(int personId) throws RemoteException{
        person = (Person) client.findPerson(personId);
        if(person != null){
            data = client.getPersonContacts(person);
        }
    }
    
    public void addEmptyRow() throws RemoteException {
        //client.addPerson(name, address);
        //setupData();
        data.add(new Contact(-1," ", " "));
        fireTableRowsInserted(data.size() + 1, data.size() + 1);
//        isRowEditable.add(true);
//        rowFlagEditDeleteAdd.add(2);
    }
    
    public void saveRow(int rowIndex) throws RemoteException {
        int contactId = (int)getValueAt(rowIndex, 0);
        String email = (String) getValueAt(rowIndex, 1);
        String mobile = (String) getValueAt(rowIndex, 2);
        if(contactId == -1)
            client.addPersonContact(person, email, mobile);
        if(contactId != -1)
            client.updatePersonContact(person.getId(), email, mobile, contactId);
        
    }
    
    public void saveTable() throws RemoteException {
        int rowCount = getRowCount();
        for (int i = 0; i < rowCount; i++) {
            saveRow(i);
        }
        setData(person.getId());
//        rowFlagEditDeleteAdd.clear();
//        isRowEditable.clear();
//        setUpTableUneditable();
    }
    
    public void deleteRow(int rowIndex) throws RemoteException{
        if(rowIndex == -1)
            return;
        int contactId = (int) getValueAt(rowIndex, 0);
        client.removePersonContact(person, contactId);
        setData(person.getId());
    }
    
    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        //id cell is not editable by default
        if (col == 0) {
            return false;
        }
        return true;
    }
    
    
    public Class getColumnClass(int c) {
        //return getValueAt(0, c).getClass();
        return String.class;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object[] arr = data.toArray();
        IContact contact = (IContact) arr[rowIndex];
        switch (columnIndex) {
            case 0:
                return contact.getId();
            case 1:
                return contact.getEmail();
            case 2:
                return contact.getMobilePhone();
            default:
                return null;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        Object[] arr = data.toArray();
        IContact contact = (Contact) arr[row];
        switch (col) {
            case 1:
                contact.setEmail(value.toString());
                break;
            case 2:
                contact.setMobilePhone(value.toString());
                break;
//                default:return null;
        }

    }
    
}
