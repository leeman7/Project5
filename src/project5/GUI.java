/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project5;

/*
 * GUI.java
 *
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ItemListener {
    private JPanel cards; //a panel that uses CardLayout
    private final static String vehicleMngmt = "Vehicle Managment";
    private final static String userMngmt = "User Management";
    private final static String transMngmt = "Transaction Management";
    
    // Vehicle managment components
    private JRadioButton showVehiclesRadioButton;
    private JRadioButton addVehicleRadioButton;
    private JRadioButton deleteVehicleRadioButton;
    private JRadioButton searchByVinRadioButton;
    private JRadioButton searchByPriceRadioButton;
    private JButton manageVehicles;
    
    // User management components
    private JRadioButton showUsersRadioButton;
    private JRadioButton addUserRadioButton;
    private JRadioButton deleteUserRadioButton;
    private JRadioButton updateUserRadioButton;
    private JButton manageUsers;
    
    // User management components
    private JRadioButton showTransRadioButton;
    private JRadioButton addTransRadioButton;
    private JButton manageTrans;
    
    

    public GUI() throws HeadlessException {
        super("Dealership");
        initializeGui();
        initializeEvents();
    }
    
    private void initializeGui() {
      
        this.setSize(500, 400);
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        Container pane = this.getContentPane();
        //pane.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        
        //Put the JComboBox in a JPanel to get a nicer look.
        JPanel comboBoxPane = new JPanel(); //use FlowLayout
        String comboBoxItems[] = { vehicleMngmt, userMngmt, transMngmt };
        
        JComboBox cb = new JComboBox(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);
        
        //Create the "cards".
        
        JPanel vehicleMngmtPanel = new JPanel(new GridLayout(0, 1));
        showVehiclesRadioButton =  new JRadioButton("Show all vehicles");
        showVehiclesRadioButton.setSelected(true);
        addVehicleRadioButton = new JRadioButton("Add a new vehicle");
        deleteVehicleRadioButton = new JRadioButton("Delete a vehicle");
        searchByVinRadioButton = new JRadioButton("Search vehicle by VIN");
        searchByPriceRadioButton = new JRadioButton("Search vehicles by price");
        
        ButtonGroup group = new ButtonGroup();
        group.add(showVehiclesRadioButton);
        group.add(addVehicleRadioButton);
        group.add(deleteVehicleRadioButton);
        group.add(searchByVinRadioButton);
        group.add(searchByPriceRadioButton);
        
        
        vehicleMngmtPanel.add(showVehiclesRadioButton);
        vehicleMngmtPanel.add(addVehicleRadioButton);
        vehicleMngmtPanel.add(deleteVehicleRadioButton);
        vehicleMngmtPanel.add(searchByVinRadioButton);
        vehicleMngmtPanel.add(searchByPriceRadioButton);
        
        manageVehicles = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        vehicleMngmtPanel.add(manageVehicles);
        
        
        // User management
        JPanel userMngmtPanel = new JPanel(new GridLayout(0, 1));
        showUsersRadioButton =  new JRadioButton("Show all users");
        showUsersRadioButton.setSelected(true);
        addUserRadioButton = new JRadioButton("Add a new user");
        deleteUserRadioButton = new JRadioButton("Delete a user");
        updateUserRadioButton = new JRadioButton("Update user");
        
        ButtonGroup userRadioGroup = new ButtonGroup();
        userRadioGroup.add(showUsersRadioButton);
        userRadioGroup.add(addUserRadioButton);
        userRadioGroup.add(deleteUserRadioButton);
        userRadioGroup.add(updateUserRadioButton);
        
        
        userMngmtPanel.add(showUsersRadioButton);
        userMngmtPanel.add(addUserRadioButton);
        userMngmtPanel.add(deleteUserRadioButton);
        userMngmtPanel.add(updateUserRadioButton);
        
        manageUsers = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        userMngmtPanel.add(manageUsers);

        // Transaction management
        JPanel transMngmtPanel = new JPanel(new GridLayout(0, 1));
        showTransRadioButton =  new JRadioButton("Show all sale transactions");
        showTransRadioButton.setSelected(true);
        addTransRadioButton = new JRadioButton("Add a new sale transaction");
        
        
        ButtonGroup transRadioGroup = new ButtonGroup();
        transRadioGroup.add(showTransRadioButton);
        transRadioGroup.add(addTransRadioButton);
        
        
        transMngmtPanel.add(showTransRadioButton);
        transMngmtPanel.add(addTransRadioButton);
        
        manageTrans = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        transMngmtPanel.add(manageTrans);
        
        
        cards.add(vehicleMngmtPanel, vehicleMngmt);
        cards.add(userMngmtPanel, userMngmt);
        cards.add(transMngmtPanel, transMngmt);
        
        this.add(comboBoxPane, BorderLayout.PAGE_START);
        this.add(cards, BorderLayout.CENTER);
    }
    
    private void initializeEvents() {
        manageVehicles.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) {
                if (showVehiclesRadioButton.isSelected())
                    showAllVehicles();
                else if (addVehicleRadioButton.isSelected())
                    addNewVehicle();
                else if (deleteVehicleRadioButton.isSelected())
                    deleteVehicle();
                else if (searchByVinRadioButton.isSelected())
                    searchVehicleByVin();
                else if (searchByPriceRadioButton.isSelected())
                    searchVehicleByPrice();
            }
        });
        manageUsers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showUsersRadioButton.isSelected())
                    showAllUsers();
                else if (addUserRadioButton.isSelected())
                    addNewUser();
                else if (deleteUserRadioButton.isSelected())
                    deleteUser();
                else if (updateUserRadioButton.isSelected())
                    updateUsers();
            }
        });
        manageTrans.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showTransRadioButton.isSelected())
                    showAllTrans();
                else if (addTransRadioButton.isSelected())
                    addTrans();
            }
        });
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }
    
    private void showAllVehicles() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "PRICE"};

        // Create some data
        String dataValues[][] = {
                    {"SDF34", "Honda", "Civic", "2006", "6999.00"},
                    {"DFGH3", "Ford", "Mustang", "2001", "3234.00"},
                    {"VBG21", "Toyota", "Corolla", "2009", "11000.00"},
                    {"GFG32", "Honda", "Accord", "2000", "4000.00"}
                };

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }
    
    
    private void addNewVehicle() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        panel.add(new JLabel("VIN"));
        panel.add(new JTextField(5));
        panel.add(new JLabel("Make"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Model"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Year"));
        panel.add(new JTextField(4));
        panel.add(new JLabel("Mileage"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Price"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Vehicle type:"));
        
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        panel.add(new JLabel("Extra info 1"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Extra info 2"));
        panel.add(new JTextField(20));
        
        JOptionPane.showMessageDialog(this, panel, "Add new vehicle", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void deleteVehicle() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(new JTextField(5));
        JOptionPane.showMessageDialog(this, panel, "Delete vehicle", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void searchVehicleByVin() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        panel.add(new JLabel("Enter VIN"));
        panel.add(new JTextField(5));
        JOptionPane.showMessageDialog(this, panel, "Search vehicle by VIN", JOptionPane.PLAIN_MESSAGE);
    }
    
    private void searchVehicleByPrice() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());
        
        panel.add(new JLabel("Minimum Price"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Maximum Price"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Vehicle type:"));
        
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);
        
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        
        panel.add(radiosPanel);
        
        JOptionPane.showMessageDialog(this, panel, "Search vehicles by price", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAllUsers() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"ID", "FIRST NAME", "LAST NAME"};

        String dataValues[][] = {
                {"777586", "Luke", "Skywalker"},
                {"134882", "Darth", "Vader"},
        };

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }

    private void addNewUser() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        panel.add(new JLabel("ID"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("First Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Last Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("User type:"));

        JRadioButton user = new JRadioButton("Customer");
        user.setSelected(true);
        JRadioButton emp = new JRadioButton("Employee");
        ButtonGroup group = new ButtonGroup();
        group.add(user);
        group.add(emp);

        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(user);
        radiosPanel.add(emp);

        panel.add(radiosPanel);

        String Options[] = {"Add User"};

        JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }

    private void deleteUser() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        panel.add(new JLabel("Enter ID"));
        panel.add(new JTextField(5));

        String Option[] = {"Delete User"};

        JOptionPane.showOptionDialog(this, panel, "Delete user", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Option, Option[0]);
    }

    private void updateUsers() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        panel.add(new JLabel("Enter ID"));
        panel.add(new JTextField(5));

        String Option[] = {"Update User"};

        JOptionPane.showOptionDialog(this, panel, "Search Users to update", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Option, Option[0]);
    }

    private void showAllTrans() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN","CUSTOMER ID", "EMPLOYEE ID", "DATE", "PRICE"};

        String dataValues[][] = {
                {"SDF34", "777586", "000022", "11/20/2015", "15000"},
                {"DF6S1", "134882", "000022","12/24/2014", "12000"},
        };

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        String Options[] = {"Show Transactions"};

        JOptionPane.showOptionDialog(this, topPanel, "Search results", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }

    private void addTrans() {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        panel.add(new JLabel("VIN"));
        panel.add(new JTextField(5));
        panel.add(new JLabel("Customer ID"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Employee ID"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Date"));
        panel.add(new JTextField(10));
        panel.add(new JLabel("Sale Price"));
        panel.add(new JTextField(7));

        String Options[] = {"Add Transaction"};

        JOptionPane.showOptionDialog(this, panel, "Add new transaction", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }
}