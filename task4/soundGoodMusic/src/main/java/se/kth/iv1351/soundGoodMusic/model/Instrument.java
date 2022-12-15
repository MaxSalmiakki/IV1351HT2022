package se.kth.iv1351.soundGoodMusic.model;

public class Instrument implements InstrumentDTO {
    private String serial_instrument_id;
    private String brand;
    private int price;
    private Boolean isAvailable;
    private String instrument_type;

    // Constructor for the instrument class
    public Instrument(String serial_instrument_id, String instrument_type, String brand, int price, String isAvailable) {
        this.serial_instrument_id = serial_instrument_id;
        this.instrument_type = instrument_type;
        this.brand = brand;
        this.price = price;
        if (isAvailable.equals("1")) {
            this.isAvailable = true;
        } else{
            this.isAvailable = false;
        }

    }

    // Returns the instruemnt's unique id
    public String getInstrumentSerialId() {
        return serial_instrument_id;
    }

    // Returns the instrument's brand
    public String getBrand() {
        return brand;
    }

    // Tells us what type of instrument it is.
    public String getType() {
        return instrument_type;
    }
    // Returns whether the instrument is available or not
    public boolean getAvailability() {
        return isAvailable;
    }

    // Returns the rent price of teh instrument
    public int getPrice() {
        return price;
    }

    // Return our own custom string.
    @Override
    public String toString() {
        return "(Instrument: " +'\''+
                "brand: '" + brand + '\'' +
                ", type: "+instrument_type+ '\'' +
                ", price:" + price + '\'' +
                ", instrumentSerialID: '" + serial_instrument_id + '\'' +
                ", availability: "+isAvailable+')'+'\n';
    }
}
