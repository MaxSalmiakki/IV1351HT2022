package se.kth.iv1351.soundGoodMusic.integration;

public class InstrumentDBException extends Exception {

    public InstrumentDBException(String errorSource) {
        super(errorSource);
    }

    public InstrumentDBException(String errorSource, Throwable mainErrorSource) {
        super(errorSource, mainErrorSource);
    }
}
