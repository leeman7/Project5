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
import java.util.ArrayList;
import javax.swing.*;

public class GUI extends JFrame implements ItemListener {
    private JPanel cards; //a panel that uses CardLayout
    private Database database;

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
        database = new Database();
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

        // Vehicle Buttons
        ButtonGroup group = new ButtonGroup();
        group.add(showVehiclesRadioButton);
        group.add(addVehicleRadioButton);
        group.add(deleteVehicleRadioButton);
        group.add(searchByVinRadioButton);
        group.add(searchByPriceRadioButton);
        
        // Vehicle Management Buttons
        vehicleMngmtPanel.add(showVehiclesRadioButton);
        vehicleMngmtPanel.add(addVehicleRadioButton);
        vehicleMngmtPanel.add(deleteVehicleRadioButton);
        vehicleMngmtPanel.add(searchByVinRadioButton);
        vehicleMngmtPanel.add(searchByPriceRadioButton);
        
        manageVehicles = new JButton("Go");
        //manageVehicles.setActionCommand("OUCH!");
        vehicleMngmtPanel.add(manageVehicles);

        //User management
        JPanel userMngmtPanel = new JPanel(new GridLayout(0, 1));
        showUsersRadioButton =  new JRadioButton("Show all users");
        showUsersRadioButton.setSelected(true);
        addUserRadioButton = new JRadioButton("Add a new user");
        deleteUserRadioButton = new JRadioButton("Delete a user");
        updateUserRadioButton = new JRadioButton("Update user");

        //User Buttons
        ButtonGroup userRadioGroup = new ButtonGroup();
        userRadioGroup.add(showUsersRadioButton);
        userRadioGroup.add(addUserRadioButton);
        userRadioGroup.add(deleteUserRadioButton);
        userRadioGroup.add(updateUserRadioButton);
        
        //User Management buttons
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

    private void initializeEvents(){
        manageVehicles.addActionListener(e->{
            if (showVehiclesRadioButton.isSelected())
                showAllVehicles();
            else if (addVehicleRadioButton.isSelected()) {
                try {
                    addNewVehicle();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (deleteVehicleRadioButton.isSelected()) {
                try {
                    deleteVehicle();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (searchByVinRadioButton.isSelected()) {
                try {
                    searchVehicleByVin();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (searchByPriceRadioButton.isSelected()) {
                try {
                    searchVehicleByPrice();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
        });
        manageUsers.addActionListener(e->{
            if (showUsersRadioButton.isSelected()) {
                try {
                    showAllUsers();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (addUserRadioButton.isSelected()) {
                try {
                    addNewUser();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (deleteUserRadioButton.isSelected()) {
                try {
                    deleteUser();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
            else if (updateUserRadioButton.isSelected()) {
                try {
                    updateUsers();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
        });
        manageTrans.addActionListener(e -> {
            if (showTransRadioButton.isSelected()) {
                try {
                    showAllTrans();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            } else if (addTransRadioButton.isSelected()) {
                try {
                    addTrans();
                } catch (BadInputException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }
    
    private String getSelection(JPanel panel) throws BadInputException{
        for (Component comp : panel.getComponents()){
            if(comp instanceof JRadioButton){
                if (((JRadioButton) comp).isSelected()){
                    try {
                        return ((JRadioButton) comp).getText();
                    } catch (Exception e) {
                        throw new BadInputException("Unable to determine Vehicle Type");
                    }
                }
            }
        }
        return "Invalid Type.";
    }

    private int getType(String type){
        if(type.equals("Passenger Car"))
            return 1;
        if(type.equals("Truck"))
            return 2;
        if(type.equals("Motorcycle"))
            return 3;
        if(type.equals("Customer"))
            return 1;
        if(type.equals("Employee"))
            return 2;
        else
            return -1;
    }

    private void showAllVehicles() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "MILEAGE", "PRICE"};

        // Create some data
        ArrayList<String[]> data = database.showAllVehicles();

        if(data.size() > 0){
            String dataValues[][]={};
            for(int i = 0; i < data.size(); i++){
                dataValues[i] = data.get(i);
            }
            // Create a new table instance
            JTable table = new JTable(dataValues, columnNames);
            table.setFillsViewportHeight(true);

            // Add the table to a scrolling pane
            JScrollPane scrollPane = new JScrollPane(table);
            topPanel.add(scrollPane, BorderLayout.CENTER);
        }
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }

    private void addNewVehicle() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(500, 400);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

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
        
        JLabel ex1 = new JLabel("BodyStyle");
        panel.add(ex1);
        panel.add(new JTextField(20));
        JLabel ex2 = new JLabel("N/A");
        panel.add(ex2);
        panel.add(new JTextField(20));

        car.addActionListener(e -> {
            ex1.setText("BodyStyle");
            ex2.setText("N/A");
        });
        motorcycle.addActionListener(e -> {
            ex1.setText("Type");
            ex2.setText("Eng. Disp.");
        });
        truck.addActionListener(e -> {
            ex1.setText("Max Load");
            ex2.setText("Length");
        });
        
        String options[]={"Add Vehicle", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);


        if(opt == 0) {
            //get all necessary components
            ArrayList<String> texts = new ArrayList<>();
            int vehicleType = -1;
            for (Component comp : panel.getComponents()) {
                if(comp instanceof JPanel) {
                    String type = getSelection(((JPanel) comp));
                    vehicleType = getType(type);
                    break;
                }
                if (comp instanceof JTextField)
                    texts.add(((JTextField) comp).getText());
            }

            database.addNewVehicle(vehicleType, texts.get(0), texts.get(1), texts.get(2), texts.get(3), texts.get(4), texts.get(5), texts.get(6), texts.get(7));
            JOptionPane.showMessageDialog(this, "Vehicle Successfully Added");
        }
    }

    private void deleteVehicle() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add Vehicle Text fields
        panel.add(new JLabel("Enter VIN"));
        panel.add(new JTextField(5));

        // Print Options
        String Options[] = {"Delete Vehicle", "Cancel"};
        int opt= JOptionPane.showOptionDialog(this, panel, "Delete vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if(opt == 0){
            Component comps[] = panel.getComponents();
            if(database.deleteVehicle(((JTextField)comps[1]).getText())){
                JOptionPane.showMessageDialog(this, "Vehicle Successfully Deleted.");
            }
        }
    }

    
    private void searchVehicleByVin() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add Vehicle Text fields
        panel.add(new JLabel("Enter VIN"));
        panel.add(new JTextField(5));

        // Print Options
        String Options[] = {"Show Vehicle", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Search vehicle by VIN", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if (opt == 0){
            JPanel results = new JPanel();
            results.setSize(400, 300);
            results.setLayout(new BorderLayout());
            getContentPane().add(results);
            
            //get all necessary components
            Component comps[] = panel.getComponents();

            // Create columns names
            String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "MILEAGE", "PRICE"};

            ArrayList<String[]> data = database.searchVehicle(((JTextField)comps[1]).getText());

            if(data.size() > 0){
                String dataValues[][]={};

                for(int i = 0; i < data.size(); i++){
                   dataValues[i] = data.get(i);
                }

                // Create a new table instance
                JTable table = new JTable(dataValues, columnNames);
                table.setFillsViewportHeight(true);

                //create scroll pane to hold search contents
                JScrollPane scrollPane = new JScrollPane(table);
                results.add(scrollPane, BorderLayout.CENTER);
            }

            JOptionPane.showMessageDialog(this, results, "Search results", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private void searchVehicleByPrice() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add Vehicle Text fields
        panel.add(new JLabel("Minimum Price"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Maximum Price"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("Vehicle type:"));

        // Add Car/Truck/Motorcyle Button Selection
        JRadioButton car = new JRadioButton("Passenger Car");
        car.setSelected(true);
        JRadioButton truck = new JRadioButton("Truck");
        JRadioButton motorcycle = new JRadioButton("Motorcycle");
        ButtonGroup group = new ButtonGroup();
        group.add(car);
        group.add(truck);
        group.add(motorcycle);

        // Add organized button panel
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(car);
        radiosPanel.add(truck);
        radiosPanel.add(motorcycle);
        panel.add(radiosPanel);

        // Print Options
        String Options[] = {"Show Vehicles", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Search vehicles by price", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if (opt == 0){
            JPanel results = new JPanel();
            results.setSize(400, 300);
            results.setLayout(new BorderLayout());
            getContentPane().add(results);

            //get all necessary components
            Component comps[] = panel.getComponents();
            int vehicleType = -1;

            for (Component comp : comps) {
                if(comp instanceof JPanel) {
                    String type = getSelection(((JPanel) comp));
                    vehicleType = getType(type);
                    break;
                }
            }

            // Create columns names
            String columnNames[] = {"VIN", "MAKE", "MODEL", "YEAR", "MILEAGE", "PRICE"};

            ArrayList<String[]> data = database.showVehiclesByPrice(((JTextField)comps[1]).getText(), ((JTextField)comps[3]).getText(), vehicleType);

            if(data.size() > 0){
                String dataValues[][]={};
                
                for(int i = 0; i < data.size(); i++){
                   dataValues[i] = data.get(i);
                }

                // Create a new table instance
                JTable table = new JTable(dataValues, columnNames);
                table.setFillsViewportHeight(true);

                // Add the table to a scrolling pane
                JScrollPane scrollPane = new JScrollPane(table);
                results.add(scrollPane, BorderLayout.CENTER);
            }
            JOptionPane.showMessageDialog(this, results, "Search results", JOptionPane.PLAIN_MESSAGE);
        }
    }

    
    /**
     *
     */
    private void showAllUsers() throws BadInputException {
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

        // Print Options
        String Options[] = {"Show Users", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, topPanel, "Search results", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        /*if (opt == 0){
            for (User u : users) {
                u.print();
            }
        } else {

        }*/
    }

    /**
     *
     */
    private void addNewUser() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add User Text Fields
        panel.add(new JLabel("ID"));
        panel.add(new JTextField(6));
        panel.add(new JLabel("First Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Last Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("User type:"));

        // Add Customer/Employee button selection
        JRadioButton user = new JRadioButton("Customer");
        user.setSelected(true);
        JRadioButton emp = new JRadioButton("Employee");
        ButtonGroup group = new ButtonGroup();
        group.add(user);
        group.add(emp);

        // Add organized button panel
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(user);
        radiosPanel.add(emp);
        panel.add(radiosPanel);

        // Print Options
        String Options[] = {"Add User", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        /*if (opt == 0) {
            ArrayList<String> text = new ArrayList<>();
            for (Component comp : this.getComponents()){

            }

            JOptionPane.showMessageDialog(null, "Successfully Added new user!", "Success", JOptionPane.PLAIN_MESSAGE);
        } else {

        }*/
    }

    /**
     *
     */
    private void deleteUser() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add User Text Fields
        panel.add(new JLabel("Enter ID"));
        panel.add(new JTextField(5));

        // Print Options
        String Option[] = {"Delete User", "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Delete user", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Option, Option[0]);
        //JOptionPane.showMessageDialog(null, "Successfully Added new user!", "Success", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     *
     */
    private void updateUsers() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add User Text fields
        panel.add(new JLabel("Enter ID"));
        panel.add(new JTextField(5));

        // Print Options
        String Options[] = {"Update User", "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Search Users to update", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }

    /**
     *
     */
    private void showAllTrans() throws BadInputException {
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

        // Print Options
        String Options[] = {"Show Transactions", "Cancel"};
        JOptionPane.showOptionDialog(this, topPanel, "Search results", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }

    /**
     *
     */
    private void addTrans() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add Transaction text fields
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

        // Print Options
        String Options[] = {"Add Transaction", "Cancel"};
        JOptionPane.showOptionDialog(this, panel, "Add new transaction", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }
}