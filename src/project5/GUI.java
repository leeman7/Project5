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
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;
import javax.swing.*;

/**
 * GUI sets all the elements necessary for our GUI interface setup
 * and all the necessary panels used in generating the GUI.
 */
public class GUI extends JFrame implements ItemListener {
    private JPanel cards; //a panel that uses CardLayout
    private Database database;
    private PrintWriter writer;
    private StringWriter error;
    private FileHandler handle;
    private Logger log;

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

    /**
     * Handler Exception for if GUI fails to be initialized properly
     * upon startup.
     * @throws HeadlessException
     * @throws IOException
     */
    public GUI() throws HeadlessException{
        super("Dealership");
        database = new Database();
        try{
            log = initializeLogger();
        }catch(IOException e){
            JOptionPane.showMessageDialog(this, "Internal System Error.");
            System.exit(ERROR);
        }
        initializeGui();
        initializeEvents();
    }

    /**
     * Initializes the GUI's Logger, FileHandler, and String Writer
     * these will all be used to write all user caused exceptions
     * @return log Logger object
     * @throws IOException
     */
    private Logger initializeLogger() throws IOException{
        //initialize logger
        Logger log = Logger.getAnonymousLogger();

        //create logs.txt file
        writer = new PrintWriter("logs.txt");
        writer.close();

        //initialize fileHandler
        handle= new FileHandler("logs.txt");
        log.addHandler(handle);
        SimpleFormatter format = new SimpleFormatter();
        handle.setFormatter(format);

        log.addHandler(handle);

         error = new StringWriter();

        return log;
    }

    /*
     * Handler function for toString BadInputException
     * returns a stacktrace error if the string was not able to be
     * converted from the user input.
     * @param ex
     * @return error.toString()
     *
    private String convertExceptionToString(BadInputException ex){
        ex.printStackTrace(new PrintWriter(error));
        return error.toString();
    }*/

    /**
     * Initialize GUI starts our GUI interface for users to use and interact
     * with. The following function sets the default settings for
     * our GUI interface and how it will appear to our user.
     */
    private void initializeGui() {
        this.setSize(500, 400);
        Dimension windowSize = this.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2);
        Container pane = this.getContentPane();
        //pane.setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handle.close();
            }
        });
        
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
        //userMngmtPanel.add(deleteUserRadioButton);
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

    /**
     * Initialize Events initializes our three panels used for
     * -Vehicles
     * -Users
     * -Transactions
     * The events are handled for each sub panel and what all state changes
     * occurs for each of those events.
     */
    private void initializeEvents(){
        manageVehicles.addActionListener(e->{
            if (showVehiclesRadioButton.isSelected()){
                showAllVehicles();
                log.info("User requested list of all vehicles");
            }
            else if (addVehicleRadioButton.isSelected()) {
                try {
                    addNewVehicle();
                    log.info("Employee added vehicle:\n   -> " + database.getLastVehicle());
                } catch (BadInputException ex) {
                    log.warning("Invalid information entered for function addNewVehicle");
                }
            }
            else if (deleteVehicleRadioButton.isSelected()) {
                try {
                    deleteVehicle();
                    log.info("Employee removed vehicle from System");
                } catch (BadInputException ex) {
                    log.warning("Invalid VIN entered for function deleteVehicle");
                }
            }
            else if (searchByVinRadioButton.isSelected()) {
                try {
                    searchVehicleByVin();
                    log.info("User searched for vehicle by VIN");
                } catch (BadInputException ex) {
                    log.warning("Invalid VIN entered for function searchVehicleByVin");
                }
            }
            else if (searchByPriceRadioButton.isSelected()) {
                try {
                    searchVehicleByPrice();
                    log.info("User searched for vehicles by price range\n");
                } catch (BadInputException ex) {
                    log.warning("Invalid information entered for function searchVehicleByPrice");
                }
            }
        });
        manageUsers.addActionListener(e->{
            if (showUsersRadioButton.isSelected()) {
                    showAllUsers();
                    log.info("User requested list of all users");
            }
            else if (addUserRadioButton.isSelected()) {
                try {
                    addNewUser();
                    log.info("Employee added new User to the system.");
                } catch (BadInputException ex) {
                    log.warning("Invalid information entered for function addUser");
                }
            }
            /*else if (deleteUserRadioButton.isSelected()) {
                try {
                    //deleteUser();
                } catch (BadInputException ex) {
                    log.warning(ex.getMessage().toUpperCase() + "\n" + convertExceptionToString(ex));
                }
            }*/
            else if (updateUserRadioButton.isSelected()) {
                try {
                    updateUsers();
                    log.info("Employee updated another User's information");
                } catch (BadInputException ex) {
                    log.warning("Invalid information entered for function updateUser");
                }
            }
        });
        manageTrans.addActionListener(e -> {
            if (showTransRadioButton.isSelected()) {
                    showAllTrans();
                    log.info("User requested list of all transactions");
            } else if (addTransRadioButton.isSelected()) {
                try {
                    addTrans();
                    log.info("Employee Sold Vehicle:");
                } catch (BadInputException ex) {
                    log.warning("Invalid information entered for function addTrans");
                }
            }
        });
    }

    /**
     * This functions lists the state changes for our GUI and then processes the event
     * based on the actions performed by the user within the GUI.
     * @param evt
     */
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String) evt.getItem());
    }

    /**
     * Helper function used handled the selections made by the user through
     * our GUI. This mostly is for the Radiobuttons when they are specifically
     * selected for either: VehicleType or UserType.
     * @param panel
     * @return
     * @throws BadInputException
     */
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

    /**
     * Helper function that we created to help handle different types of
     * vehicles and users. Theses a mostly used to verify that proper input
     * was used for the different types of each class.
     * @param type
     * @return
     */
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

    /**
     * The Show vehicles function displays all the vehicles in the current
     * database at the time.
     */
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
            String dataValues[][]= new String[data.size()][];

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

    /**
     * The Add Vehicle function adds a new vehicle entry to our Database
     * if the user submits wrong output the user should get an error.
     * @throws BadInputException
     */
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
        
        JLabel extraInfo1 = new JLabel("BodyStyle");
        panel.add(extraInfo1);
        panel.add(new JTextField(20));
        JLabel extraInfo2 = new JLabel("N/A");
        panel.add(extraInfo2);
        panel.add(new JTextField(20));

        car.addActionListener(e -> {
            extraInfo1.setText("BodyStyle");
            extraInfo2.setText("N/A");
        });
        motorcycle.addActionListener(e -> {
            extraInfo1.setText("Type");
            extraInfo2.setText("Eng. Disp.");
        });
        truck.addActionListener(e -> {
            extraInfo1.setText("Max Load");
            extraInfo2.setText("Length");
        });
        
        String options[]={"Add Vehicle", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);


        if(opt == 0) {
            //get all necessary components
            ArrayList<String> texts = new ArrayList<>();
            int vehicleType = -1;
            Component comps[] = panel.getComponents();
            for (Component comp : comps) {
                if((vehicleType == -1) && (comp instanceof javax.swing.JPanel)) {
                    String type = getSelection(((JPanel) comp));
                    vehicleType = getType(type);
                }
                if (comp instanceof javax.swing.JTextField)
                    texts.add(((JTextField) comp).getText());
            }

            database.addNewVehicle(vehicleType, texts.get(0), texts.get(1), texts.get(2), texts.get(3), texts.get(4), texts.get(5), texts.get(6), texts.get(7));
            JOptionPane.showMessageDialog(this, "Vehicle Successfully Added");
        }
    }

    /**
     * The Delete vehicle function prompts the user for a VIN of a vehicle
     * to delete from the database. If the VIN is found the vehicle is then
     * removed from the database using the GUI.
     * @throws BadInputException
     */
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

    /**
     * The Following function searches for a vehicle in our Database based on
     * the VIN provided by the user. If the vehicle VIN exists it will be displayed
     * via the GUI to the user.
     * @throws BadInputException
     */
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

            ArrayList<String[]> data = database.searchVehicle(((JTextField) comps[1]).getText());

            if(data.size() > 0){
                String dataValues[][]= new String[data.size()][];

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

    /**
     * The Following Functions searches for a Vehicle in the Database by a specific
     * price range. If a vehicle exists with a price in that range it will be displayed
     * through the GUI.
     * @throws BadInputException
     */
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
                String dataValues[][]= new String[data.size()][];
                
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
     * The following function displays all users in the Database via the GUI
     * interface.
     */
    private void showAllUsers() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"ID", "FIRST NAME", "LAST NAME"};

        // Create some data
        ArrayList<String[]> data = database.showAllUsers();

        String dataValues[][]= new String[data.size()][];
        for(int i = 0; i < data.size(); i++){
            dataValues[i] = data.get(i);
        }

        // Create a new table instance
        JTable table = new JTable(dataValues, columnNames);
        table.setFillsViewportHeight(true);

        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        ArrayList<String[]> users = database.showAllUsers();

        if (users.size() > 0) {
            String Values[][] = new String[users.size()][];
            for (int i = 0; i < users.size(); i++){
                Values[i] = users.get(i);
            }
            // Create a new table instance
            JTable tables = new JTable(Values, columnNames);
            tables.setFillsViewportHeight(true);

            // Add the able to a scrolling pane
            JScrollPane scrollPanes = new JScrollPane(tables);
            topPanel.add(scrollPanes, BorderLayout.CENTER);
        }
        // Print Results
        JOptionPane.showMessageDialog(this, topPanel, "Search results", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * This function adds a new user using the GUI interface to the database.
     * @throws BadInputException
     */
    private void addNewUser() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add Customer/Employee button selection
        panel.add(new JLabel("User type:"));
        JRadioButton customer = new JRadioButton("Customer");
        customer.setSelected(true);
        JRadioButton employee = new JRadioButton("Employee");
        ButtonGroup group = new ButtonGroup();
        group.add(customer);
        group.add(employee);

        // Add organized button panel
        JPanel radiosPanel = new JPanel(new FlowLayout());
        radiosPanel.add(customer);
        radiosPanel.add(employee);
        panel.add(radiosPanel);

        // Add User Text Fields
        panel.add(new JLabel("First Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Last Name"));
        panel.add(new JTextField(20));
        JLabel extraInfo1 = new JLabel("Phone");
        panel.add(extraInfo1);
        panel.add(new JTextField(20));
        JLabel extraInfo2 = new JLabel("Drivers License");
        panel.add(extraInfo2);
        panel.add(new JTextField(20));

        customer.addActionListener(e -> {
            extraInfo1.setText("Phone");
            extraInfo2.setText("Drivers License");
        });
        employee.addActionListener(e -> {
            extraInfo1.setText("Monthly Salary");
            extraInfo2.setText("Bank Account");
        });

        // Print Options
        String Options[] = {"Add User", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Add new vehicle", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if (opt == 0) {
            //get all necessary components
            ArrayList<String> texts = new ArrayList<>();
            int userType = -1;
            Component comps[] = panel.getComponents();
            for (Component comp : comps) {
                if((userType == -1) && (comp instanceof javax.swing.JPanel)) {
                    String type = getSelection(((JPanel) comp));
                    userType = getType(type);
                }
                if (comp instanceof javax.swing.JTextField)
                    texts.add(((JTextField) comp).getText());
            }

            database.addNewUser(userType, texts.get(0), texts.get(1), texts.get(2), texts.get(3));
            JOptionPane.showMessageDialog(this, "User Successfully Added");
        }
    }

    /**
     * The following function is the GUI implementation of deleting a user from our Database.
     * The user is prompted to enter an ID to search a user by, if the user is not found the GUI
     * will return an error message. If the user is found, it then proceeds to delete the user in the database
     * and return a success message to the user.
     * @throws BadInputException
     */
    /*private void deleteUser() throws BadInputException {
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
    }*/

    /**
     * The following function is the GUI implementation of updating an existing user within the Database
     * based on the ID passed to function. The update user function then requests what details to change for
     * that user in the Database. Once entered the information is updated in the backend.
     * @throws BadInputException
     */
    private void updateUsers() throws BadInputException {
        JPanel panel = new JPanel();
        panel.setSize(400, 300);
        getContentPane().add(panel);
        panel.setLayout(new FormLayout());

        // Add User Text fields
        panel.add(new JLabel("Enter ID"));
        panel.add(new JTextField(5));

        panel.add(new JLabel("First Name"));
        panel.add(new JTextField(20));
        panel.add(new JLabel("Last Name"));
        panel.add(new JTextField(20));

        JLabel ex3 = new JLabel("Phone");
        panel.add(ex3);
        panel.add(new JTextField(20));
        JLabel ex4 = new JLabel("Drivers License");
        panel.add(ex4);
        panel.add(new JTextField(20));

        // Add Customer/Employee button selection
        panel.add(new JLabel("User type:"));
        JRadioButton customer = new JRadioButton("Customer");
        customer.setSelected(true);
        JRadioButton employee = new JRadioButton("Employee");
        ButtonGroup group = new ButtonGroup();
        group.add(customer);
        group.add(employee);

        customer.addActionListener(e -> {
            ex3.setText("Phone");
            ex4.setText("Drivers License");
        });
        employee.addActionListener(e -> {
            ex3.setText("Monthly Salary");
            ex4.setText("Bank Account");
        });

        // Print Options
        String Options[] = {"Update User", "Cancel"};
        int opt = JOptionPane.showOptionDialog(this, panel, "Search Users to update", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if (opt == 1) {
            ArrayList<String> text = new ArrayList<>();
            int userType = -1;
            Component comps[] = panel.getComponents();
            for (Component comp : comps) {
                if ((userType == -1) && (comp instanceof javax.swing.JPanel)) {
                    if (((JRadioButton) comp).isSelected()) {
                        String type = getSelection(((JPanel) comp));
                        userType = getType(type);
                    }
                    if (comp instanceof javax.swing.JTextField)
                        text.add(((JTextField) comp).getText());
                }

                if (comp instanceof JTextArea)
                    text.add(((JTextField) comp).getText());
            }
            database.updateUsers(userType, text.get(0), text.get(1), text.get(2), text.get(3), text.get(4), text.get(5));
            JOptionPane.showMessageDialog(null, "Successfully Added new user!", "Success", JOptionPane.PLAIN_MESSAGE);
        }
    }

    /**
     * This function will show all the existing Transactions within the Database in a formatted table.
     * All the Transactions are stored in our ArrayList of Sales that should be displayed upon user request.
     */
    private void showAllTrans() {
        JPanel topPanel = new JPanel();
        topPanel.setSize(400, 300);
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Create columns names
        String columnNames[] = {"VIN","CUSTOMER ID", "EMPLOYEE ID", "DATE", "PRICE"};

        ArrayList<String[]> data = database.showAllSales();

        if (data.size() > 0) {
            String dataValues[][]= new String[data.size()][];

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
        // Print Options
        String Options[] = {"Show Transactions", "Cancel"};
        JOptionPane.showOptionDialog(this, topPanel, "Search results", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);
    }

    /**
     *This function a is the GUI implementation of adding a new transaction to our Database
     * @throws BadInputException
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
        int opt = JOptionPane.showOptionDialog(this, panel, "Add new transaction", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, Options, Options[0]);

        if (opt == 0) {
            ArrayList<String> text = new ArrayList<>();
            Component comps[] = panel.getComponents();
            for (Component comp : comps) {
                if (comp instanceof JTextField)
                    text.add(((JTextField) comp).getText());
            }
            database.sellVehicle(text.get(0), text.get(1), text.get(2), text.get(3));
            JOptionPane.showMessageDialog(null, "Successfully Added new user!", "Success", JOptionPane.PLAIN_MESSAGE);
        }
    }
}
