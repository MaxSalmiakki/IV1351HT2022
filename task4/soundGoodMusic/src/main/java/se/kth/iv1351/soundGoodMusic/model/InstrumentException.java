package se.kth.iv1351.soundGoodMusic.model;


public class InstrumentException extends Exception {


    public InstrumentException(String errorSource) {
        super(errorSource);
    }

    public InstrumentException(String source, Throwable mainErrorSource) {
        super(source, mainErrorSource);
    }
}
