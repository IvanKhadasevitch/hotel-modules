package by.khadasevich.hotel.singleton;

public class SingletonException  extends RuntimeException  {
    public SingletonException(String message) {
        super(message);
    }

    public SingletonException(String message, Throwable cause) {
        super(message, cause);
    }
}
