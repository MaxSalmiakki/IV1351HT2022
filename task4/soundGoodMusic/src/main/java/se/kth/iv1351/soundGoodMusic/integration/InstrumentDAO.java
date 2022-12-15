package se.kth.iv1351.soundGoodMusic.integration;


import java.time.LocalDate;
import java.time.LocalTime;

import java.sql.Connection;


import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.DriverManager;

import java.sql.PreparedStatement;


import java.util.*;
import java.text.*;


import se.kth.iv1351.soundGoodMusic.model.Instrument;

public class InstrumentDAO {
    private static final String INSTRUMENT_TABLE = "instrument";
    private static final String INSTRUMENT_SERIAL_ID_COLUMN = "serial_instrument_id";
    private static final String INSTRUMENT_BRAND_COLUMN = "brand";
    private static final String INSTRUMENT_TYPE_COLUMN = "instrument_type";
    private static final String INSTRUMENT_AVAILABILITY_COLUMN = "isAvailable";
    private static final String INSTRUMENT_PRICE_COLUMN = "price";

    private static final String STUDENT_PAYMENT_TABLE = "student_payment";
    private static final String STUDENT_PAYMENT_ID_COLUMN = "student_payment_id";
    private static final String STUDENT_PAYMENT_STUDENT_ID_COLUMN = "student_id";
    private static final String STUDENT_PAYMENT_DISCOUNT_COLUMN = "discount";

    private static final String INSTRUMENT_RENTAL_TABLE = "instrument_rental";
    private static final String INSTRUMENT_RENTAL_ID_COLUMN = "rental_id";
    private static final String INSTRUMENT_RENTAL_STARTING_DATE_COLUMN = "starting_date";
    private static final String INSTRUMENT_RENTAL_ENDING_DATE_COLUMN = "ending_date";
    private static final String INSTRUMENT_RENTAL_PAYMENT_FK_COLUMN = STUDENT_PAYMENT_ID_COLUMN;
    private static final String INSTRUMENT_RENTAL_SERIAL_INSTRUMENT_ID_FK_COLUMN = INSTRUMENT_SERIAL_ID_COLUMN;
    private static final String INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN = "active";

    private Connection connection;

    /*

    
    List instruments

    STEPS:
    1) LIST ALL INSTRUMENTS WHERE TYPE = INSTRUMENT_TYPE AND AVAILABLE_BIT = 1 [listInstruments]
    

    

    Rent instrument

    1) CHECK IF NUMBER OF ACTIVE STUDENT RENTALS IS MORE THAN 2 => RETURN ERROR IF SO  [getStudentRentalsNumber]
    2) CHECK IF THE SPECIFIED INSTRUMENT IS AVAILABLE => RETURN ERROR IF SO [checkAvailability]
    3) GET STUDENT PAYMENT ID FROM BASED ON THE STUDENT KEY [getStudentPaymentId]
    4) SET AVAILABILITY BIT TO 0 // [updateInstrumentStatus]
    5) INSERT INTO RENTAL, STARTING DATE, ENDING DATE, ACTIVE BIT, STUDENT PAYMENT AND ETC [createRental]
    6) DONE


    */

    private PreparedStatement listInstruments;
    private PreparedStatement createRental;
    private PreparedStatement setRentalStatusToFalse;
    private PreparedStatement getStudentRentalsNumber;
    private PreparedStatement checkAvailability;
    private PreparedStatement getStudentPaymentId;
    private PreparedStatement showAllActiveRentals;
    private PreparedStatement showAllExpiredRentals;
    private PreparedStatement checkIfExists;
    private PreparedStatement checkIfActiveRental;
    private PreparedStatement getRentalId;
    private PreparedStatement updateInstrumentStatusToFalse;
    private PreparedStatement updateInstrumentStatusTotrue;

    private void prepareStatements() throws SQLException {
        listInstruments = connection.prepareStatement("SELECT * FROM "+INSTRUMENT_TABLE+" WHERE "+INSTRUMENT_TYPE_COLUMN+" = ? AND "+INSTRUMENT_AVAILABILITY_COLUMN+" = '1'");
        checkAvailability = connection.prepareStatement("SELECT "+INSTRUMENT_AVAILABILITY_COLUMN+" FROM "+INSTRUMENT_TABLE+" WHERE "+INSTRUMENT_SERIAL_ID_COLUMN+" = ?");
        setRentalStatusToFalse = connection.prepareStatement("UPDATE "+INSTRUMENT_RENTAL_TABLE+" SET "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" = '0' WHERE " + INSTRUMENT_RENTAL_ID_COLUMN+" = ?");
        getStudentRentalsNumber = connection.prepareStatement("SELECT COUNT(*) FROM "+INSTRUMENT_RENTAL_TABLE+" WHERE "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" = '1' AND "+INSTRUMENT_RENTAL_PAYMENT_FK_COLUMN+" = ?");
        createRental = connection.prepareStatement("INSERT INTO "+INSTRUMENT_RENTAL_TABLE+" VALUES (?, ?, ?, ?, ?, '1')");
        getStudentPaymentId = connection.prepareStatement("SELECT "+STUDENT_PAYMENT_ID_COLUMN+" FROM "+STUDENT_PAYMENT_TABLE+" WHERE "+STUDENT_PAYMENT_STUDENT_ID_COLUMN+" = ?");
        checkIfActiveRental = connection.prepareStatement("SELECT "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" FROM "+INSTRUMENT_RENTAL_TABLE+" WHERE "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" = 1");
        getRentalId = connection.prepareStatement("SELECT "+INSTRUMENT_RENTAL_ID_COLUMN+" FROM "+INSTRUMENT_RENTAL_TABLE+" WHERE "+INSTRUMENT_RENTAL_SERIAL_INSTRUMENT_ID_FK_COLUMN+" = ? AND "+INSTRUMENT_RENTAL_PAYMENT_FK_COLUMN+" = ? AND "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" = '1'");


        updateInstrumentStatusToFalse = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE + " SET "+ INSTRUMENT_AVAILABILITY_COLUMN + " = '0' WHERE " + INSTRUMENT_RENTAL_SERIAL_INSTRUMENT_ID_FK_COLUMN + " = ?");
        updateInstrumentStatusTotrue = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE + " SET "+ INSTRUMENT_AVAILABILITY_COLUMN + " = '1' WHERE " + INSTRUMENT_RENTAL_SERIAL_INSTRUMENT_ID_FK_COLUMN + " = ?");

        showAllActiveRentals = connection.prepareStatement("SELECT * FROM "+ INSTRUMENT_TABLE + "WHERE "+ INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN + " = 1");
        showAllExpiredRentals = connection.prepareStatement("SELECT * FROM "+ INSTRUMENT_TABLE + "WHERE "+ INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN + " = 0");
        checkIfExists = connection.prepareStatement("SELECT COUNT (*) FROM "+INSTRUMENT_RENTAL_TABLE+" WHERE "+INSTRUMENT_RENTAL_IS_ACTIVE_COLUMN+" = 1 AND "+INSTRUMENT_RENTAL_PAYMENT_FK_COLUMN+" = ?");

    }


    /*
    Terminate rental

    1) GET STUDENT PAYMENT ID COLUMN
    2) GET RENTAL ID
    3) SET RENTAL'S ACTIVE BIT TO ZERO BASED ON STUDENT_PAYMENT_ID AND INSTRUMENT_SERIAL_ID [updateRentalStatus]
    4) SET AVAILABILITY BIT OF THE INSTRUMENT TO ONE [updateInstrumentStatus]
    5) COMMIT
    */
    public void terminateRental(String instrumentSerialId, String studentId) throws InstrumentDBException, SQLException {
        String errorOutput = "Could not terminate rent of Instrument: "+instrumentSerialId+" for Student: " + studentId;
        try{
            //1
            String studentPaymentId = getStudentPaymentId(studentId);

            // 2
            getRentalId.setString(1, instrumentSerialId);
            getRentalId.setString(2, studentPaymentId);
            
            ResultSet r = getRentalId.executeQuery();
            
            if (!r.next()) {
                handleException(errorOutput, null);
            }
            
            String rentalId = r.getString(1);

            // 3)
            setRentalStatusToFalse.setString(1, rentalId);
            int updateStatus =  setRentalStatusToFalse.executeUpdate();
            if (updateStatus != 1) {
                handleException(errorOutput, null);
            }

            // 4)
            updateInstrumentStatusTotrue.setString(1, instrumentSerialId);
            updateStatus = updateInstrumentStatusTotrue.executeUpdate();
            
            if (updateStatus != 1) {
                handleException(errorOutput, null);
            }
            // 5)
            connection.commit();

        } catch (SQLException exception) {
            handleException(errorOutput, exception);
        }
    }




    /*
    1) GET STUDENT PAYMENT_ID
    2) CHECK WHETHER THE RENTAL IS ALREADY TERMINATED OR NOT
    2) SET RENTAL'S ACTIVE BIT TO ZERO BASED ON STUDENT_PAYMENT_ID AND INSTRUMENT_SERIAL_ID [updateRentalStatus]
    3) SET AVAILABILITY BIT OF THE INSTRUMENT TO ONE [updateInstrumentStatus]


    updateInstrumentStatus = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE + " SET "+ INSTRUMENT_AVAILABILITY_COLUMN + " = ? WHERE " + INSTRUMENT_SERIAL_ID_COLUMN + " = ?");

    */


    public java.sql.Date getDatetype(LocalDate c) throws ParseException {

        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(c.toString());
        
        return (java.sql.Date) d1;
       
        
                
    }

    /*
    1) CHECK IF NUMBER OF ACTIVE STUDENT RENTALS IS MORE THAN 2 => RETURN ERROR IF SO  [getStudentRentalsNumber]
    2) CHECK IF THE SPECIFIED INSTRUMENT IS AVAILABLE => RETURN ERROR IF SO [checkAvailability]
    3) SET AVAILABILITY BIT TO 0 // [updateInstrumentStatus]
    4) GET STUDENT PAYMENT ID BASED ON THE STUDENT KEY [getStudentPaymentId]
    5) INSERT INTO RENTAL, STARTING DATE, ENDING DATE, ACTIVE BIT, STUDENT PAYMENT AND ETC [createRental]
    6) Commit
    
    updateInstrumentStatus = connection.prepareStatement("UPDATE " + INSTRUMENT_TABLE + " SET "+ INSTRUMENT_AVAILABILITY_COLUMN + " = ? WHERE " + INSTRUMENT_SERIAL_ID_COLUMN + " = ?");

    */
    public void rentInstrument(String instrumentSerialId, String studentId) throws InstrumentDBException, SQLException, ParseException {
        String errorMessage = "Failed to rent instrument for: " + studentId;
        try {
            //1, 2
            if ((checkInstrumentAvailability(instrumentSerialId) == false)) {
                
                handleException(errorMessage, null);
            }
            //System.out.println("Passed flag 1"); //GETS HERE

            if (checkStudentRentalLimits(studentId) == false) {
                
                handleException(errorMessage, null);
            }
            // 3
            // 4)
            

            updateInstrumentStatusToFalse.setString(1, instrumentSerialId);
            //System.out.println("Set to zero");
            
            
            
            /*
            RENT TBN02 200110117750ST
            */
            //System.out.println("Got here");
            int updateStatus = updateInstrumentStatusToFalse.executeUpdate();

            // Roll back if something goes wrong
            if (updateStatus != 1) {
                handleException(errorMessage, null);
                //System.out.println("Mess up 1");
               
            }
            //System.out.println("Checkpoint 1");

            

            // 5)
            String rentalId = getStamp();
            //LocalDate currentDate = LocalDate.now();
            //LocalDate yearLater = (LocalDate.now().plusDays(365));
            
            long m=System.currentTimeMillis();   
            java.sql.Date currentDate = new java.sql.Date(m);

            java.sql.Date dateYlater = java.sql.Date.valueOf((LocalDate.now().plusDays(365)));

            String studentPaymentId = getStudentPaymentId(studentId);
            
            //createRental = connection.prepareStatement("INSERT INTO "+INSTRUMENT_RENTAL_TABLE+" VALUES (?, ?, ?, ?, ?, ?)");
            createRental.setString(1, rentalId);
            createRental.setDate(2, currentDate);
            createRental.setDate(3, dateYlater);
            createRental.setString(4, studentPaymentId);
            createRental.setString(5, instrumentSerialId);


            // Roll back if something goes wrong
            updateStatus = createRental.executeUpdate();
            if (updateStatus != 1) {
                handleException(errorMessage, null);
                System.out.println("Mess up 2");
                
            }
            //System.out.println("Checkpoint 2");

            connection.commit();
            
        } catch (SQLException e) {
            //System.out.println("MESSED UP OUTER");
            handleException(errorMessage, e);
        }
    }

    

    
    private String getStudentPaymentId(String studentId) throws InstrumentDBException, SQLException {
        String errorMessage = "Could not get student payment id for Student: " + studentId;
        getStudentPaymentId.setString(1, studentId);
        ResultSet rentalIdResult = getStudentPaymentId.executeQuery();
        if (!rentalIdResult.next()) {
            handleException(errorMessage, null);
        }
        //System.out.println(rentalIdResult.getString(1));
        return rentalIdResult.getString(1);
    }

    private boolean checkStudentRentalLimits(String studentId) throws SQLException, InstrumentDBException {
        
        String rentalId = getStudentPaymentId(studentId);
        //System.out.println(rentalId);
        
        getStudentRentalsNumber.setString(1, rentalId);
        
        ResultSet result = getStudentRentalsNumber.executeQuery();

        //getStudentRentalsNumber.setInt();
        /*
        RENT TBN02 200110117750ST
        */
        
        if (result.next()) {
            //System.out.println("At least here");
            int number = result.getInt(1);
            if (number < 2) {
                //System.out.println("Case 1");
                return true;
            } else {
                //System.out.println("Case 2");
                return false;
            }
        } else {
            //System.out.println("Case 3");
            return false;
        }      
    }    

    // Returns a unique time stamp 
    private String getStamp() {
        LocalDate datum = LocalDate.now();
        LocalTime tid = LocalTime.now();
        String out = datum.toString()+tid.toString();
        return out;
    }

    public InstrumentDAO() throws InstrumentDBException {
        try {
            connectToInstrumentDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException sqlException) {
            throw new InstrumentDBException("Failed to connect to database .", sqlException);
        }
    }
    /* Display all available instruments of a certain type*/
    public List<Instrument> listInstruments(String InstrumentType) throws InstrumentDBException {
        String failureOutput = "Failed to list instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try {
            listInstruments.setString(1, InstrumentType);
            ResultSet result = listInstruments.executeQuery();
            while (result.next()) {
                instruments.add(new Instrument(result.getString(INSTRUMENT_SERIAL_ID_COLUMN),
                result.getString(INSTRUMENT_TYPE_COLUMN),
                result.getString(INSTRUMENT_BRAND_COLUMN),
                result.getInt(INSTRUMENT_PRICE_COLUMN),
                result.getString(INSTRUMENT_AVAILABILITY_COLUMN)));
            }
            connection.commit();
        } catch (SQLException sqlException) {
            handleException(failureOutput, sqlException);
        }
        return instruments;
    }

    
    //Commits the current transaction.
    public void commit() throws InstrumentDBException {
        try {
            connection.commit();
        } catch (SQLException exception) {
            handleException("Could not commit", exception);
        }
    }

    // Redacted out password for the obvious security reasons
    private void connectToInstrumentDB() throws ClassNotFoundException{
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IV1351Test", "postgres", PASSWORD);
            connection.setAutoCommit(false);
            System.out.println("Connected succesfully");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void handleException(String failureOutput, Exception source) throws InstrumentDBException {
        String finalFailureOutput = failureOutput;
        try {
            connection.rollback();
        } catch (SQLException exception) {
            finalFailureOutput = finalFailureOutput + 
            ". In addition could not rollback the transaction due to: " + exception.getMessage();
        }
        if (source != null) {
            throw new InstrumentDBException(failureOutput, source);
        } else {
            throw new InstrumentDBException(failureOutput);
        }
    }

    private boolean checkInstrumentAvailability(String instrumentSerialId) throws SQLException {
        checkAvailability.setString(1, instrumentSerialId);
        ResultSet result = checkAvailability.executeQuery();
        /*
        RENT TBN02 199808116134ST
        */
        if (result.next()) {
            int availability = result.getInt(1);
            if (availability == 1) {
                //System.out.println("Case 1");
                return true;
            } else {
                //System.out.println("Case 2");
                return false;
            }
        } else {
            //System.out.println("Case 3");
            return false;
        }
    }
}
