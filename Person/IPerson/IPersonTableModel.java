/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

class IPersonTableModel extends AbstractTableModel {

    private boolean DEBUG = false;
    Client client;

    private String[] columnNames = {"id",
        "Name",
        "Address"};

    private List<IPerson> data;
    ArrayList<Boolean> isRowEditable;
    //1 - delete   2 - add    3 - edit   0 - default state
    ArrayList<Integer> rowFlagEditDeleteAdd;

    public IPersonTableModel(Client client) {
        super();
        try {
            this.client = client;
            setupData();
            isRowEditable = new ArrayList<>(); 
            rowFlagEditDeleteAdd = new ArrayList<>();
            setUpTableUneditable();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    private void setUpTableUneditable() {
        for (int i = 0; i < data.size(); i++) {
            isRowEditable.add(false);
            rowFlagEditDeleteAdd.add(0);
        }
    }

    public void setupData() throws RemoteException {
        data = client.getPersons();
    }

    public void refresh() throws RemoteException {
        setupData();
        rowFlagEditDeleteAdd.clear();
        isRowEditable.clear();
        setUpTableUneditable();
    }

    public void addEmptyRow() throws RemoteException {
        //client.addPerson(name, address);
        //setupData();
        data.add(new Person(-1," ", " "));
        fireTableRowsInserted(data.size() + 1, data.size() + 1);
        isRowEditable.add(true);
        rowFlagEditDeleteAdd.add(2);
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public void saveRow(int rowIndex) throws RemoteException {
        int personId = (int)getValueAt(rowIndex, 0);
        String name = (String) getValueAt(rowIndex, 1);
        String address = (String) getValueAt(rowIndex, 2);
        if(personId == -1)
            client.addPerson(name, address);
        if(personId != -1)
            client.updatePerson(personId , name, address);
        
    }

    public void saveTable() throws RemoteException {
        int rowCount = getRowCount();
        for (int i = 0; i < rowCount; i++) {
            saveRow(i);
        }
        setupData();
        rowFlagEditDeleteAdd.clear();
        isRowEditable.clear();
        setUpTableUneditable();
    }

    public void setRowEditable(int selectedRow) throws RemoteException {
        // if no row selected
        if(selectedRow != -1) { 
            isRowEditable.set(selectedRow, true);
            rowFlagEditDeleteAdd.set(selectedRow, 3);
        }
    }

    public void deleteRow(int rowIndex) throws RemoteException{
        int personId = (int) getValueAt(rowIndex, 0);
        client.deletePerson(personId);
        setupData();
        rowFlagEditDeleteAdd.clear();
        isRowEditable.clear();
        setUpTableUneditable();
    }
    
    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Object[] arr = data.toArray();
        IPerson person = (IPerson) arr[row];
        switch (col) {
            case 0:
                return person.getId();
            case 1:
                return person.getName();
            case 2:
                return person.getAddress();
            default:
                return null;
        }

    }
    
    //get personId from selectedRow
    public int getPersonId(int selectedRowId){
        return (int)getValueAt(selectedRowId, 0);
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        //return getValueAt(0, c).getClass();
        return String.class;
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        //id cell is not editable by default
        if (col == 0) {
            return false;
        }
        return isRowEditable.get(row);
    }

    private void setCellEditable(int row, int col) {
        //id cell is not editable by default
        if (col == 0) {
            return;
        }
        isRowEditable.set(row, true);
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        Object[] arr = data.toArray();
        IPerson person = (IPerson) arr[row];
        switch (col) {
            case 1:
                person.updateName(value.toString());
                break;
            case 2:
                person.updateAddress(value.toString());
                break;
//                default:return null;
        }

        fireTableCellUpdated(row, col);
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
//                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}
