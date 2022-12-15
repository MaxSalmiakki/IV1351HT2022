package se.kth.iv1351.soundGoodMusic.controller;

import se.kth.iv1351.soundGoodMusic.integration.*;
import se.kth.iv1351.soundGoodMusic.model.*;

import java.util.List;

public class Controller {
    private final InstrumentDAO instrumentDatabase;

    // Create  a new databases object
    public Controller() throws InstrumentDBException {
        instrumentDatabase = new InstrumentDAO();
    }

    public void rentInstrument(String instrumentId, String studentId) throws InstrumentException {
        String errorMsg = "Failed to rent instrument for: " + studentId;

        if (instrumentId.length() < 5 || studentId.length() < 14) {
            throw new InstrumentException(errorMsg);
        } try {
            instrumentDatabase.rentInstrument(instrumentId, studentId);
        } catch (Exception exception) {
            throw new InstrumentException(errorMsg, exception);
        }
    }


    public void terminateRental(String instrumentId, String studentId) throws InstrumentException {
        String errorMsg = "Failed to terminate rental of Instrument: " + instrumentId+" for student: "+studentId;

        if (instrumentId.length() < 5 || studentId.length() < 14) {
            throw new InstrumentException(errorMsg);
        }
        try {
            instrumentDatabase.terminateRental(instrumentId, studentId);
        } catch (Exception exception) {
            throw new InstrumentException(errorMsg, exception);
        }
    }

    public List<? extends InstrumentDTO> listInstruments(String instrumentType) throws InstrumentException {
        try {
            return (List<? extends InstrumentDTO>) instrumentDatabase.listInstruments(instrumentType);
        } catch (Exception exception) {
            throw new InstrumentException("Failed to list instruments", exception);
        }
    }
}