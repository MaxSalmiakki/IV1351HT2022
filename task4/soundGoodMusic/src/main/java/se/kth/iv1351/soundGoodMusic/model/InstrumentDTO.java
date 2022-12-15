package se.kth.iv1351.soundGoodMusic.model;

public interface InstrumentDTO {
    public String getInstrumentSerialId();

    public boolean getAvailability();

    public String getType();

    public String getBrand();

    public int getPrice();
}