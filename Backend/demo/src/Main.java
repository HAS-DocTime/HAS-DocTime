import java.sql.Time;

public class Main {
    public static void main(String[] args) {
        Time time = new Time(9,0,0);
        System.out.println(time);
        time.setMinutes(time.getMinutes()+45);
        System.out.println(time);
        time.setMinutes(time.getMinutes()+45);
        System.out.println(time);
        System.out.println("Hello world!");
    }
}