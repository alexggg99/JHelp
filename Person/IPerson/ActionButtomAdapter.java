/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alexey
 */
public class ActionButtomAdapter implements ActionListener{

    private ProxyClient proxyClient;

    public ActionButtomAdapter(ProxyClient proxyClient) {
        this.proxyClient = proxyClient;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        IPersonTableModel model =(IPersonTableModel) proxyClient.tableUpper.getModel();
//        switch((String)e.getActionCommand()){
//            case("Add"):
//                {
//                    
//                }
//            case("Edit"):
//                    try {
//                        //read row by row
//                        //and save data to server
//                        model.edit();
//                    } catch (RemoteException ex) {
//                        System.out.println("Test");
//                    };
//            case("Delete"):    
//        }
        if(e.getActionCommand() == "Add"){
            try {
                model.addEmptyRow();
            } catch (RemoteException ex) {}
        }
        if(e.getActionCommand() == "Edit"){
            try {
//              read row by row
//              and save data to server
                int rowIndex = proxyClient.tableUpper.getSelectedRow();
                if(rowIndex == -1) return;
                model.setRowEditable(rowIndex);
            } catch (RemoteException ex) {};
        }
        if(e.getActionCommand() == "Delete"){
            try {
                int rowIndex = proxyClient.tableUpper.getSelectedRow();
                if(rowIndex == -1) return;
                model.deleteRow(rowIndex);
                model.fireTableDataChanged();
            } catch (RemoteException ex) {};
        }
    }
    
}
