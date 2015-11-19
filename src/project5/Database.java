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
    private int userIdCounter = 1;

    /**
     * Default constructor. Initializes the inventory, users, and transactions
     * tables.
     */
    public Database() {
        this.vehicleInventory = new ArrayList<Vehicle>();
        this.users = new ArrayList<User>();
        this.transactions = new ArrayList<SaleTransaction>();
    }
    

        /**
     * Constructor. Initializes the inventory, users, and transactions to given values.
     */
    public Database(List<Vehicle> vehicleInventory, List<User> users, List<SaleTransaction> transactions) {
        this.vehicleInventory = vehicleInventory;
        this.users = users;
        this.transactions = transactions;
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
        }
    }

    
    public void searchVehicle() {

        System.out.print("\nEnter VIN of vehicle to search for (string): ");
        String vin =
        
        List<Vehicle> matchingVehicle = new ArrayList<Vehicle>();
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                matchingVehicle.add(v);
                showVehicles(matchingVehicle);
                return;
            }
        }
        System.out.println("Vehicle with VIN " + vin + " not found in the database"); 
    }
    
    
    /**
     * This method allows the user to delete a vehicle from the inventory database.
     * @param sc The scanner object used to read user input.
     */
    public void deleteVehicle() {
        System.out.print("\nEnter VIN of vehicle to delete (string): ");
        String vin = ;
        
        for (Vehicle v : vehicleInventory) {
            if (v.getVin().equals(vin)) {
                vehicleInventory.remove(v);
                break;
            }
        }
    }
    
    /**
     * Auxilary private method to print out a list of vehicles in a formatted manner.
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
     * @param sc The scanner object used to read user input.
     */
    public void showVehiclesByPrice() throws BadInputException {

        float lowValue =
        if (lowValue < 0.0f)
            throw new BadInputException("Low price cannot be negative.");
        

        float highValue =
        if (highValue < 0.0f)
            throw new BadInputException("High price cannot be negative.");

        int vehicleType = ;
        if (vehicleType < 1 || vehicleType > 3)
            throw new BadInputException("Legal vehicle type values: 1-3.");
        
        ArrayList<Vehicle> matchingVehicles = new ArrayList<Vehicle>();
        for (Vehicle v : vehicleInventory) {
            if (v.getPrice() >= lowValue && v.getPrice() <= highValue) {
                if (vehicleType == 1 && v instanceof PassengerCar)
                    matchingVehicles.add(v);
                else if (vehicleType == 2 && v instanceof Truck)
                    matchingVehicles.add(v);
                else if (vehicleType == 3 && v instanceof Motorcycle)
                    matchingVehicles.add(v);
            }
        }
        
        if (matchingVehicles.size() == 0)
            ;
        else
            showVehicles(matchingVehicles);
    }

    /**
     * This method allows a new user to be added to the database.
     * @param sc The scanner object used to read user input.
     * @throws Database BadInputException
     */
    public void addNewUser(int userType, String id, String fname, String lname, String ph, String dl, String ex3, String ex4) throws BadInputException {

        if (userType < 1 || userType > 3)
            throw new BadInputException("Legal user type values: 1-2.");

        if (userType == 1) {
            String phoneNumber = ph;
            int dlnumber = Integer.parseInt(dl);
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");

            users.add(new Customer(userIdCounter++, fname, lname,
                    phoneNumber, dlnumber));
        } else if (userType == 2) {
            float monthlySalary = Float.parseFloat(ex3);
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");
            int bankAccNumber = Integer.parseInt(ex4);
            if (bankAccNumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");

            users.add(new Employee(userIdCounter++, fname, lname,
                    monthlySalary, bankAccNumber));
        }
    }

    /**
     * This method can be used to update a user's information, given their user ID.
     * @param sc The scanner object used to read user input.
     * @throws Database BadInputException
     */
    public void updateUser() throws BadInputException {

        int userID = ;
        
        User user = null;
        for (User u : users) {
            if (u.getId() == userID)
                user = u;
        }
        
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        String firstName = ;

        String lastName = ;
        
        if (user instanceof Customer) {
            String phoneNumber = ;

            int dlnumber = ;
            if (dlnumber < 0)
                throw new BadInputException("Driver license number cannot be negative.");
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            ((Customer)user).setPhoneNumber(phoneNumber);
            ((Customer)user).setDriverLicenceNumber(dlnumber);
            
        } else { //User is an employee
            float monthlySalary = ;
            if (monthlySalary < 0.0f)
                throw new BadInputException("Monthly salary cannot be negative.");

            int bankAccNumber = ;
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
    private ArrayList<String[]>  showUsers(List<User> users) {
        ArrayList<String[]> userList = new ArrayList<>();
        String user[] = {"","",""};

        for (User u : users) {
            user[0] = Integer.toString(u.id);
            user[1] = u.firstName;
            user[2] = u.lastName;

            userList.add(user);
        }

        return userList;
    }

    public ArrayList<String[]> showAllUsers() { return showUsers(users); }

    /**
     * This method is used to complete a vehicle sale transaction.
     * @throws Database BadInputException
     */
    public void sellVehicle() throws BadInputException {
        int customerId = ;
        //Check that the customer exists in database
        boolean customerExists = false;
        for (User u : users) {
            if (u.getId() == customerId)
                customerExists = true;
        }
        if (!customerExists) {
            return;
        }

        int employeeId = ;
        //Check that the employee exists in database
        boolean employeeExists = false;
        for (User u : users) {
            if (u.getId() == employeeId)
                employeeExists = true;
        }
        if (!employeeExists) {
            return;
        }

        String vin = ;
        //Check that the vehicle exists in database
        Vehicle v = findVehicle(vin);
        if (v == null) {
            return;
        }
        
        Date currentDate = new Date(System.currentTimeMillis());

        float price = ;
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
    private ArrayList<String[]> showSales(List<SaleTransaction> transactions) {
        ArrayList<String[]> salesList = new ArrayList<>();
        String transaction[] = {"","","","",""};

        for (SaleTransaction sale : transactions) {
            transaction[0] = Integer.toString(sale.customerId);
            transaction[1] = Integer.toString(sale.employeeId);
            transaction[2] = sale.vin;
            transaction[3] = String.valueOf(sale.date);
            transaction[4] = Float.toString(sale.salePrice);

            salesList.add(transaction);
        }

        return salesList;
    }

    public ArrayList<String[]> showAllSales() { return showSales(transactions); }
    
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
     * @param cds
     */
    public void writeDatabase() {
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
