/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IPerson;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author alexey
 */
public class ProxyClient extends javax.swing.JFrame {

    // Variables declaration - do not modify                     
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JScrollPane jScrollPaneUpper;
    private javax.swing.JScrollPane jScrollPaneLower;
    private javax.swing.JPanel jPanalGap;
    JTable tableUpper;
    JTable tableLower;
    public Client client;
    IPersonTableModel personModel;
    IContactTableModel contactModel;
    // End of variables declaration    

    public ProxyClient() {
        try{
            client = new Client();
            initComponents();
        }catch(RemoteException ex){
            JOptionPane.showMessageDialog(null, 
                    "Cant find server! \n" + 
                    ex.toString());
        }
    }

    private void initComponents() {

        personModel = new IPersonTableModel(client);

        tableUpper = new JTable(personModel);
        tableUpper.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        //personModel.saveRow(((JTable) e.getSource()).getSelectedRow());
                        personModel.saveTable();
                        personModel.fireTableDataChanged();
                    } catch (RemoteException ex) {
                    }
                }
            }
        });
        tableUpper.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int personId = personModel.getPersonId(tableUpper.getSelectedRow());
                if (personId == -1) {
                    return;
                }
                try {
                    contactModel.setData(personId);
                } catch (RemoteException ex) {
                    System.out.println(ex.getMessage());
                }
                contactModel.fireTableDataChanged();
            }

        });
        tableUpper.setRowSelectionInterval(0, 0);
        tableUpper.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableUpper.getColumnModel().getColumn(0).setMinWidth(20);
        tableUpper.getColumnModel().getColumn(0).setMaxWidth(20);
        tableUpper.getColumnModel().getColumn(0).setPreferredWidth(20);

        contactModel = new IContactTableModel(client);
        tableLower = new JTable(contactModel);
        tableLower.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        //personModel.saveRow(((JTable) e.getSource()).getSelectedRow());
                        contactModel.saveTable();
                        contactModel.fireTableDataChanged();
                    } catch (RemoteException ex) {
                    }
                }
            }
        });
        tableLower.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableLower.getColumnModel().getColumn(0).setMinWidth(20);
        tableLower.getColumnModel().getColumn(0).setMaxWidth(20);
        tableLower.getColumnModel().getColumn(0).setPreferredWidth(20);
        //load first person if exist
        int fstPerId = (int) personModel.getValueAt(0, 0);
        if (fstPerId > -1) {
            try {
                contactModel.setData(fstPerId);
            } catch (RemoteException ex) {
            }
        }

        jPanalGap = new JPanel();

        Icon addContact = new ImageIcon("add.png");
        JLabel addLable = new JLabel(addContact);
        jPanalGap.add(addLable);
        jPanalGap.setLayout(new FlowLayout(FlowLayout.LEFT));
        addLable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    contactModel.addEmptyRow();
                } catch (RemoteException ex) {
                }
            }
        });

        Icon deleteContact = new ImageIcon("delete.gif");
        JLabel deleteLable = new JLabel(deleteContact);
        jPanalGap.add(deleteLable);
        deleteLable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (tableLower.getSelectedRow() == -1) {
                        return;
                    }
                    contactModel.deleteRow(tableLower.getSelectedRow());
                } catch (RemoteException ex) {
                }
                contactModel.fireTableDataChanged();
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPaneUpper = new javax.swing.JScrollPane(tableUpper);
        jScrollPaneLower = new javax.swing.JScrollPane(tableLower);
        jScrollPaneUpper.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPaneLower.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu1.setText("File");

        jMenuItem1.setText("Open");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Exit");
        jMenu1.add(jMenuItem2);
        jMenuItem2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(EXIT_ON_CLOSE);
            }

        });

        jMenu2.setText("Edit");

        jMenuItem3.setText("Add");
        jMenu2.add(jMenuItem3);
        jMenuItem3.setActionCommand("Add");
        jMenuItem3.addActionListener(new ActionButtomAdapter(this));

        jMenuItem4.setText("Edit");
        jMenu2.add(jMenuItem4);
        jMenuItem4.setActionCommand("Edit");
        jMenuItem4.addActionListener(new ActionButtomAdapter(this));

        jMenuItem5.setText("Delete");
        jMenu2.add(jMenuItem5);
        jMenuItem5.setActionCommand("Delete");
        jMenuItem5.addActionListener(new ActionButtomAdapter(this));

        jMenu3.setIcon(new ImageIcon("refresh.jpg"));
        jMenu3.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                try {
                    personModel.refresh();
                } catch (RemoteException ex) {
                }
                personModel.fireTableDataChanged();
                tableUpper.setRowSelectionInterval(0, 0);
                //add contacts after refreshing
                int fstPerId = (int) personModel.getValueAt(0, 0);
                if (fstPerId > -1) {
                    try {
                        contactModel.setData(fstPerId);
                        contactModel.fireTableDataChanged();
                    } catch (RemoteException ex) {}
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });

        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPaneUpper, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addComponent(jPanalGap)
                .addComponent(jScrollPaneLower)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneUpper, javax.swing.GroupLayout.PREFERRED_SIZE, 160, 800)
                        //                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanalGap, 25, 25, 25)
                        .addComponent(jScrollPaneLower, javax.swing.GroupLayout.PREFERRED_SIZE, 100, 800))
        );

        pack();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProxyClient().setVisible(true);
            }
        });
    }

}
