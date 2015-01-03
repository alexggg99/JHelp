package jhelp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Client class provides users's interface of the application.
 */
public class Client extends JFrame implements JHelp {
    JTextField version;
    JTextField ip;
    JTextField port;
    /**
     * Static constant for serialization
     */
    public static final long serialVersionUID = 1234;
    /**
     * Programm properties
     */
    private Properties prop;
    /**
     * Private Data object presents informational data.
     */
    private Data data;

    
    Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    /**
     * Constructor with parameters.
     *
     * @param args Array of {@link String} objects. Each item of this array can
     * define any client's property.
     */
    public Client(String[] args) {
        System.out.println("Client: constructor");
        prop = new Properties();
        try {
            prop.load(new FileInputStream(new File("config.ini")));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Method for application start
     *
     * @param args agrgument of command string
     */
    static public void main(String[] args) throws IOException, ClassNotFoundException {
        Client client = new Client(args);
        if (client.connect() == JHelp.OK) {
            client.run();
        }
        
    }

    /**
     * Method define main job cycle
     */
    public void run() throws IOException, ClassNotFoundException {
        System.out.println("Client: run");
        buildInterface();
        loadProps();
    }

    //TextAria with result
    JTextArea textArea;
    JTextField id;
    JButton jButton4;
    JButton jButton5;
    
    public void buildInterface() {
        setLocation(50, 100);
        setSize(790, 600);
        setTitle("JHelp");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //mainmenu
        Container c = getContentPane();
        c.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem exit = new JMenuItem();
        final JTabbedPane jtabs = new JTabbedPane();
        jtabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        exit.setText("Exit");
        exit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        disconnect();
                    }
                }
        //new DemoWindowAdapter()

        );
        file.add(exit);
        JMenu edit = new JMenu("Edit");
        JMenu settings = new JMenu("Settings");
        settings.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                jtabs.setSelectedIndex(1); 
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        
        JMenu help = new JMenu("Help");
        help.addMenuListener(new MenuListener() {

            @Override
            public void menuSelected(MenuEvent e) {
                jtabs.setSelectedIndex(2); 
            }

            @Override
            public void menuDeselected(MenuEvent e) {
            }

            @Override
            public void menuCanceled(MenuEvent e) {
            }
        });
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(settings);
        menuBar.add(help);
        setJMenuBar(menuBar);

        //set body part
        c.setLayout(new BorderLayout());
        
        JPanel mainTab = new JPanel();
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        mainTab.add(p1);
        mainTab.add(p2);
        GroupLayout layout = new GroupLayout(p1);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        p1.setLayout(layout);
        JPanel settingsTab = new JPanel();
        JPanel helpTab = new JPanel();
        jtabs.addTab("Main", mainTab);
        jtabs.addTab("Settings", settingsTab);
        jtabs.addTab("Help", helpTab);
        c.add(jtabs, BorderLayout.CENTER);
        JLabel term = new JLabel("Term");
        JLabel def = new JLabel("Definitions:");
        JTextField jSearch = new JTextField(50);
        jSearch.addKeyListener(new ClientListener(this, jSearch, textArea, id));
        JButton sButton = new JButton("FIND");
        sButton.setActionCommand("SELECT");
        sButton.addActionListener(new ClientListener(this, jSearch, textArea, id));

        //adding components to layout
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(term)
                        .addComponent(def))
                .addComponent(jSearch)
                .addComponent(sButton)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(term)
                        .addComponent(jSearch)
                        .addComponent(sButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(def)
        );
        textArea = new JTextArea(27, 56);
        //hidden field with id
        id = new JTextField();
        id.setVisible(false);
        JButton jButton1 = new JButton("Add");
        jButton1.setActionCommand("INSERT");
        jButton1.addActionListener(new ClientListener(this, jSearch , textArea, id));
        JButton jButton2 = new JButton("Edit");
        jButton2.setActionCommand("UPDATE");
        jButton2.addActionListener(new ClientListener(this, jSearch, textArea, id));
        JButton jButton3 = new JButton("Delete");
        jButton3.setActionCommand("DELETE");
        jButton3.addActionListener(new ClientListener(this, jSearch, textArea, id));
        jButton4 = new JButton("Next");
        jButton4.setEnabled(false);
        jButton4.setActionCommand("NEXT");
        jButton4.addActionListener(new ClientListener(this, jSearch, textArea, id));
        jButton5 = new JButton("Previos");
        jButton5.setEnabled(false);
        jButton5.setActionCommand("PREVIOS");
        jButton5.addActionListener(new ClientListener(this, jSearch, textArea, id));

        GroupLayout layout1 = new GroupLayout(p2);
        layout1.setAutoCreateContainerGaps(true);
        layout1.setAutoCreateGaps(true);
        p2.setLayout(layout1);

        //adding components to layout1
        layout1.setHorizontalGroup(
                layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout1.createSequentialGroup()
                        .addComponent(textArea)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                        .addGroup(layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton1, GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton2, GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton3, GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton4, GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton5, GroupLayout.Alignment.TRAILING))
                )
        );
        layout1.setVerticalGroup(
                layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout1.createSequentialGroup()
                        .addGroup(layout1.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout1.createSequentialGroup()
                                        .addComponent(textArea))
                                .addGroup(layout1.createSequentialGroup()
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3)
                                        .addComponent(jButton4)
                                        .addComponent(jButton5)
                                )))
        );
        layout1.linkSize(SwingConstants.HORIZONTAL, jButton1, jButton2, jButton3, jButton4, jButton5);
        
        ///////////////
        JPanel tmp = new JPanel();
        settingsTab.add(tmp);
        GridLayout tmpGrid = new GridLayout(3, 2);
        tmpGrid.setHgap(0);
        tmpGrid.setVgap(10);
        tmp.setLayout(tmpGrid);
        JLabel label1 = new JLabel("Version ");
        JLabel label2 = new JLabel("IP ");
        JLabel label3 = new JLabel("PORT ");
        version = new JTextField(20);
        ip = new JTextField(20);
        port = new JTextField(20);
        label1.setLabelFor(version);
        label2.setLabelFor(ip);
        label3.setLabelFor(port);
//        JButton jApply = new JButton("Apply");
        tmp.add(label1);
        tmp.add(version);
        tmp.add(label2);
        tmp.add(ip);
        tmp.add(label3);
        tmp.add(port);
//        settingsTab.add(jApply);
        //////////////////
        JTextPane jHelp = new JTextPane();
        jHelp.setContentType("text/html");
        jHelp.setEditable(false); 
        jHelp.setText(
                "Приложение JHelp представляет собой информационно-справочную систему, "
                        + "реализованную в виде многопользовательского сетевого приложения с доступом к базе данных. "
                        + "В зависимости от конкретного наполнения базы данных приложение может служить, "
                        + "например, толковым словарём вида «термин – определение» "
                        + "или словарём иностранных слов вида «иностранное слово – перевод». "
                        + "В этом случае пользователь приложения JHelp может запросить в зависимости "
                        + "от наполнения базы данных либо определение некоторого термина, "
                        + "либо перевод иностранного слова. Кроме этого, "
                        + "при наличии соответствующей реализации пользователь приложения может редактировать содержание базы данных, "
                        + "т.е. добавлять в неё новые записи, редактировать записи и удалять их из базы данных."
                );
        helpTab.add(jHelp);
        JScrollPane JHelpScrollPane = new JScrollPane(jHelp);
        helpTab.setLayout(new GridLayout());
        helpTab.add(JHelpScrollPane);
        
        setVisible(true);
    }

    public void loadProps(){
        version.setText(String.valueOf(prop.getProperty("version")));
        ip.setText(String.valueOf(prop.getProperty("ip")));
        port.setText(String.valueOf(prop.getProperty("port")));
    }
    
    /**
     * Method set connection to default server with default parameters
     *
     * @return error code
     */
    public int connect() throws IOException {
        try{
            String host = String.valueOf(prop.getProperty("ip"));
            int port = Integer.valueOf(prop.getProperty("port"));
            clientSocket = new Socket(host, port);        
            output = new ObjectOutputStream(clientSocket.getOutputStream());
            input = new ObjectInputStream(clientSocket.getInputStream());
            return JHelp.OK;
        }catch(ConnectException ex){
            // create a jframe
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Cant connect to database !");
            System.exit(0);
            return JHelp.ERROR;
        } 
    }

    /**
     * Method set connection to server with parameters defines by argument
     * <code>args</code>
     *
     * @return error code
     */
    public int connect(String[] args) {
        System.out.println("Client: connect");
        return JHelp.ERROR;
    }

    /**
     * Method gets data from data source
     *
     * @param data initial object (template)
     * @return new object
     */
    //Коллекция значений 
    List<Item> values = new LinkedList<Item>();
    ListIterator<Item> t;
    public Data getData(Data data) {
        System.out.println("Client: getData " + data);
        Data result;
        try {
            if(data.getKey() != null)
                output.writeObject(data);
                result = (Data) input.readObject();
                //clean TextArea
                textArea.setText(null);
                values.clear();
                jButton4.setEnabled(false);
                jButton5.setEnabled(false);
                //Если в базе ничего не нашлось возврощаем null
                if(result == null){
                    return null;
                }
                if(data.getOperation() != JHelp.DELETE ){
                    //fill in array with values
                    for(Item i : result.getValues()){
                        values.add(i);
                    }
                    t = values.listIterator();
                    //Добовляем полученные значения в интерфейс
                    if(t.hasNext()){
                        Item temp = (Item)t.next();
                        textArea.append(temp.getItem());
                        id.setText(String.valueOf(temp.getId()));
                    }    
                    if(result.getValues().length > 1){
                        jButton4.setEnabled(true);
                    }
                }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
      } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
            
        return null;
    }

    /**
     * Method change value in TextArea
     *
     * @return error code
     */
    public void switchNext(){
        if(t.hasNext()){
            textArea.setText(null);
            Item nextItem = (Item) t.next();
            textArea.append(nextItem.getItem());
            id.setText(String.valueOf(nextItem.getId()));
        }
        //если конец LinkedList
        if(!t.hasNext()){
            jButton4.setEnabled(false);
            Item temp = t.previous();
            jButton5.setEnabled(true);
        }
    }
    
            /**
     * Method change value in TextArea
     *
     * @return error code
     */
    public void switchPrevious(){
        if(t.hasPrevious()){
            textArea.setText(null);
            Item previousItem = (Item) t.previous();
            textArea.append(previousItem.getItem());
            id.setText(String.valueOf(previousItem.getId()));
        }
        //Если начало LinkedList
        if(!t.hasPrevious()){
            jButton5.setEnabled(false);
            Item temp = t.next();
            jButton4.setEnabled(true);
        }
    }
    /**
     * Method disconnects client and server
     *
     * @return error code
     */
    public int disconnect() {
        System.out.println("Client: disconnect");
        try {
            clientSocket.close();
            dispose();
            
        } catch (IOException ex) {
            return JHelp.ERROR;
        }
        System.exit(-1);
        return JHelp.DISCONNECT;
    }
    
}
