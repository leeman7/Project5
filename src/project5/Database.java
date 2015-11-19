/*
 * Car Database Managment Software v0.1 
 * Developed for CS4354: Object Oriented Design and Implementation.
 * Copyright: Vangelis Metsis (vmetsis@txstate.edu)
 */

package project5;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.Float;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents a car Database software, providing some basic operations.
 * Available operations:
 * 1. Show all existing vehicles in the database.
 * 2. Add a new vehicle to the database. 
 * 3. Delete a vehicle from a database (given its VIN).
 * 4. Search for a vehicle (given its VIN).
 * 5. Show a list of vehicles within a given price range. 
 * 6. Show list of users.
 * 7. Add a new user to the database.
 * 8. Update user info (given their id).
 * 9. Sell a vehicle.
 * 10. Show a list of completed sale transactions        
 * 11. Exit program.
 * @author vangelis 
 */
public class Database {
    
    private final List<Vehicle> vehicleInventory; 
    private final List<User> users;
    private final List<SaleTransaction> transactions;
    private Scanner sc;
    private int userIdCounter = 1;

    /**
     * Default constructor. Initializes the inventory, users, and transactions
     * tables.
     */
    public Database() {
        this.vehicleInventory = new ArrayList<Vehicle>();
        this.users = new ArrayList<User>();
        this.transactions = new ArrayList<SaleTransaction>();
        sc = new Scanner(System.in);
    }
    

        /**
     * Constructor. Initializes the inventory, users, and transactions to given values.
     */
    public Database(List<Vehicle> vehicleInventory, List<User> users, List<SaleTransaction> transactions) {
        this.vehicleInventory = vehicleInventory;
        this.users = users;
        this.transactions = transactions;
        sc = new Scanner(System.in);
    }

    /**
     * This method allows the user to enter a new vehicle to the inventory 
     * database.
     *param sc The scanner object used to read user input.
     * @throws Database BadInputException
     */
    public void addNewVehicle(int vehicleType, String vin, String make, String model, String Year, String Mileage, String Price, String ex1, String ex2) throws BadInputException {
        int year, mileage;
        float price;
        if (vehicleType < 1 || vehicleType > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");

        if (vin.length() > 10)
            throw new BadInputException("VIN should not be more that 10 characters long.");

        try {
            year = Integer.parseInt(Year);
            if (year < 1860)
                throw  new BadInputException("Year cannot be less than 1860.");

            mileage = Integer.parseInt(Mileage);
            if (mileage < 0)
                throw new BadInputException("Mileage cannot be negative.");

            price = Float.parseFloat(Price);
            if (price < 0.0f)
                throw new BadInputException("Price cannot be negative.");
        }catch(Exception e){
            throw new BadInputException("Invalid VIN, Mileage, or Price.");
        }

        if (vehicleType == 1) {
            String bodyStyle = ex1;
            PassengerCar car = new PassengerCar(vin, make, model, year, mileage, price, bodyStyle);
            vehicleInventory.add(car);
        } else if (vehicleType == 2) {
            try {
                float maxLoad = Float.parseFloat(ex1);
                if (maxLoad < 0.0f)
                    throw new BadInputException("Max load cannot be negative.");

                float tLength = Float.parseFloat(ex2);
                if (tLength < 0.0f)
                    throw new BadInputException("Truck length cannot be negative.");

                Truck tr = new Truck(vin, make, model, year, mileage, price, maxLoad, tLength);
                vehicleInventory.add(tr);
            }catch(Exception e){
                throw new BadInputException("Invalid Max load or length value.");
            }
        } else if (vehicleType == 3) {
            try{
                String type = ex1;
                int displacement = Integer.parseInt(ex2);
                if (displacement < 0.0f)
                    throw new BadInputException("Displacement cannot be negative.");

                Motorcycle mc = new Motorcycle(vin, make, model, year, mileage, price, type, displacement);
                vehicleInventory.add(mc);
            }catch(Exception e){
                throw new BadInputException("Invalid displacement value.");
            }
        } else {
            throw new BadInputException("Invalid Vehicle Type");
        }
    }

    /**
     *
     * @param vin
     * @return
     */
    public ArrayList<String[]> searchVehicle(String vin) {
        List<Vehicle> matchingVehicle = new ArrayList<>();
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                matchingVehicle.add(v);
                return showVehicles(matchingVehicle);
            }
        }
        return new ArrayList<>();
    }
    
    
    /**
     * This method allows the user to delete a vehicle from the inventory database.
     * @param vin The String object containing the car vin.
     */
    public boolean deleteVehicle(String vin) {
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                vehicleInventory.remove(v);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Auxilary private method to print out a list of vehicles in a formatted manner.
     * @param vehicles List object containing the list of cars in the database
     */
    private ArrayList<String[]> showVehicles(List<Vehicle> vehicles) {
        ArrayList<String[]> vehicleList = new ArrayList<>();
        String vehicle[] = {"","","","","",""};
        
        for (Vehicle v : vehicles) {
            vehicle[0] = v.vin;
            vehicle[1] = v.make;
            vehicle[2] = v.model;
            vehicle[3] = Integer.toString(v.year);
            vehicle[4] = Integer.toString(v.mileage);
            vehicle[5] = Float.toString(v.price);
            
            vehicleList.add(vehicle);
        }

        return vehicleList;
    }

    /**
     * This method prints out all the vehicle currently in the inventory, in a
     * formatted manner.
     */
    public ArrayList<String[]> showAllVehicles() {
        return showVehicles(vehicleInventory);
    }

    /**
     * This method allows the user to search for vehicles within a price range.
     * The list of vehicles found is printed out.
     * @param Low minimum price range
     * @param High maximum price range
     * @param vehicleType vehicle type
     */
    public ArrayList<String[]> showVehiclesByPrice(String Low, String High, int vehicleType) throws BadInputException {
        float high, low;

        try {
            low = Float.parseFloat(Low);
            if (low < 0.0f)
                throw new BadInputException("Low price cannot be negative.");

            high = Float.parseFloat(High);
            if (high < 0.0f)
                throw new BadInputException("High price cannot be negative.");
        }catch (Exception e){
            throw new BadInputException("Invalid Low or High price range.");
        }
        if (vehicleType < 1 || vehicleType > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");
        
        ArrayList<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        for (Vehicle v : vehicleInventory) {
            if (v.getPrice() >= low && v.getPrice() <= high) {
                if (vehicleType == 1 && v instanceof PassengerCar)
                    matchingVehicles.add(v);
                else if (vehicleType == 2 && v instanceof Truck)
                    matchingVehicles.add(v);
                else if (vehicleType == 3 && v instanceof Motorcycle)
                    matchingVehicles.add(v);
                else
                    throw new BadInputException("Invalid Vehicle Type");
            }
        }
        
        if (matchingVehicles.size() != 0)
            return showVehicles(matchingVehicles);
        else
            return new ArrayList<>();
    }

    /**
     * This method allows a new user to be added to the database.
     * @param
     * @throws Database BadInputException
     */
    public void addNewUser() throws BadInputException {
        System.out.println("Select user type:\n"
                + "1. Customer\n"
                + "2. Employee");
        int userType = sc.nextInt();
        if (userType < 1 || userType > 3)
            throw new BadInputException("Legal user type values: 1-2.");
        
        sc.nextLine();
        System.out.print("\nEnter first name (string): ");
        String firstName = sc.nextLine();
        
        System.out.print("\nEnter last name (string): ");
        String lastName = sc.nextLine();
        
        if (userType == 1) {
            System.out.print("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();
            
            System.out.print("\nEnter driver license number (int): ");
            int dlnumber = sc.nextInt();
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");

            users.add(new Customer(userIdCounter++, firstName, lastName, 
                    phoneNumber, dlnumber));
        } else if (userType == 2) {
            System.out.print("\nEnter monthly salary (float): ");
            float monthlySalary = sc.nextFloat();
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");
            
            System.out.print("\nEnter bank account number (int): ");
            int bankAccNumber = sc.nextInt();
            if (bankAccNumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            users.add(new Employee(userIdCounter++, firstName, lastName, 
                    monthlySalary, bankAccNumber));
        } else {
            System.out.println("Unknown user type. Please try again.");
        }
    }

    /**
     * This method can be used to update a user's information, given their user ID.
     * @param
     * @throws Database BadInputException
     */
    public void updateUser() throws BadInputException {
        System.out.print("\nEnter user ID: ");
        int userID = sc.nextInt();
        
        User user = null;
        for (User u : users) {
            if (u.getId() == userID)
                user = u;
        }
        
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        
        sc.nextLine();
        System.out.print("\nEnter first name (string): ");
        String firstName = sc.nextLine();
        
        System.out.print("\nEnter last name (string): ");
        String lastName = sc.nextLine();
        
        if (user instanceof Customer) {
            System.out.print("\nEnter phone number (string): ");
            String phoneNumber = sc.nextLine();
            
            System.out.print("\nEnter driver license number (int): ");
            int dlnumber = sc.nextInt();
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            ((Customer)user).setPhoneNumber(phoneNumber);
            ((Customer)user).setDriverLicenceNumber(dlnumber);
            
        } else { //User is an employee
            System.out.print("\nEnter monthly salary (float): ");
            float monthlySalary = sc.nextFloat();
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");
            
            System.out.print("\nEnter bank account number (int): ");
            int bankAccNumber = sc.nextInt();
            if (bankAccNumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            ((Employee)user).setMonthlySalary(monthlySalary);
            ((Employee)user).setBankAccountNumber(bankAccNumber);
        }
    }

    /**
     * Prints out a list of all users in the database.
     */
    public void showAllUsers() {
        System.out.println("---------------------------------------------------"
                + "------------------------------------------");
        System.out.format("| %10s | %9s | %12s | %12s | %25s          | %n", 
                "USER TYPE", "USER ID", "FIST NAME", "LAST NAME", "OTHER DETAILS");
        System.out.println("---------------------------------------------------"
                + "------------------------------------------");
        for (User u : users) {
            u.print();
        }
        System.out.println("---------------------------------------------------"
                + "------------------------------------------");
    }

    /**
     * This method is used to complete a vehicle sale transaction.
     * @param
     * @throws Database BadInputException
     */
    public void sellVehicle() throws BadInputException {
        System.out.print("\nEnter customer ID (int): ");
        int customerId = sc.nextInt();
        //Check that the customer exists in database
        boolean customerExists = false;
        for (User u : users) {
            if (u.getId() == customerId)
                customerExists = true;
        }
        if (!customerExists) {
            System.out.print("\nThe customer ID you have entered does not exist in the database.\n"
                    + "Please add the customer to the database first and then try again.");
            return;
        }
        
        System.out.print("\nEnter employee ID (int): ");
        int employeeId = sc.nextInt();
        //Check that the employee exists in database
        boolean employeeExists = false;
        for (User u : users) {
            if (u.getId() == employeeId)
                employeeExists = true;
        }
        if (!employeeExists) {
            System.out.print("\nThe employee ID you have entered does not exist in the database.\n"
                    + "Please add the employee to the database first and then try again.");
            return;
        }
        
        sc.nextLine();
        System.out.print("\nEnter VIN (string): ");
        String vin = sc.nextLine();
        //Check that the vehicle exists in database
        Vehicle v = findVehicle(vin);
        if (v == null) {
            System.out.print("\nThe vehicle with the VIN you are trying to sell "
                    + "does not exist in the database. Aborting transaction.");
            return;
        }
        
        Date currentDate = new Date(System.currentTimeMillis());
        
        System.out.print("\nEnter sale price (float): ");
        float price = sc.nextFloat();
        if (price < 0.0f)
            throw new BadInputException("Sale price cannot be negative.");
        
        SaleTransaction trans = new SaleTransaction(customerId, employeeId, vin, 
                currentDate, price);
        transactions.add(trans);
        vehicleInventory.remove(v); //Sold vehicles are automatically removed from the inventory.
        
        System.out.println("\nTransaction Completed!");
    }
    
    /**
     * Prints out a list of all recorded transactions.
     */
    public void showAllSales() {
        for (SaleTransaction sale : transactions) {
            System.out.println(sale.toString());
        }
    }
    
    /**
     * Auxiliary method used to find a vehicle in the database, given its
     * VIN number.
     * @param vin
     * @return The vehicle found, or otherwise null.
     */
    public Vehicle findVehicle(String vin) {
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin))
                return v;
        }
        return null;
    }
    
    /**
     * This method is used to read the database from a file, serializable objects.
     * @return A new Database object.
     */
    @SuppressWarnings("unchecked") // This will prevent Java unchecked operation warning when
    // convering from serialized Object to Arraylist<>
    public static Database readDatabase() {
        System.out.print("Reading database...");
        Database cds = null;

        // Try to read existing Database database from a file
        InputStream file = null;
        InputStream buffer = null;
        ObjectInput input = null;
        try {
            file = new FileInputStream("Database.ser");
            buffer = new BufferedInputStream(file);
            input = new ObjectInputStream(buffer);
            
            // Read serilized data
            List<Vehicle> vehicleInventory = (ArrayList<Vehicle>) input.readObject();
            List<User> users = (ArrayList<User>) input.readObject();
            List<SaleTransaction> transactions = (ArrayList<SaleTransaction>) input.readObject();
            cds = new Database(vehicleInventory, users, transactions);
            cds.userIdCounter = input.readInt();
            
            input.close();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (FileNotFoundException ex) {
            System.err.println("Database file not found.");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        System.out.println("Done.");
        
        return cds;
    }
    
    /**
     * This method is used to save the Database database as a 
     * serializable object.
     * @param
     */
    public void writeDatabase() {
        System.out.print("Writing database...");
        //serialize the database
        OutputStream file = null;
        OutputStream buffer = null;
        ObjectOutput output = null;
        try {
            file = new FileOutputStream("Database.ser");
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            
            output.writeObject(vehicleInventory);
            output.writeObject(users);
            output.writeObject(transactions);
            output.writeInt(userIdCounter);
            
            output.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        } finally {
            close(file);
        }
        System.out.println("Done.");
    }
    
    /**
     * Auxiliary convenience method used to close a file and handle possible
     * exceptions that may occur.
     * @param c
     */
    public static void close(Closeable c) {
        if (c == null) {
            return;
        }
        try {
            c.close();
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
}
