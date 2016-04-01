package exception;

public class CarNotFoundException extends Exception {

    public CarNotFoundException(String string) {
        super(string);
    }

    public CarNotFoundException() {
        super("Car with requested id not found");
    }
}
