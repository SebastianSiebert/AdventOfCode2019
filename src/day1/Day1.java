import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day1 {

    private static ArrayList<Float> masses = new ArrayList<Float>();

    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            return;

        readFile(args[0]);
        puzzle1();
        puzzle2();
    }

    private static void readFile(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        while ((st = br.readLine()) != null) {
            masses.add(Float.parseFloat(st));
        }
    }

    private static void puzzle1() {
        int fuelNeeded = 0;

        for (float mass : masses) {
            fuelNeeded += calculateFuel(mass);
        }

        System.out.println("Fuel needed (Part 1): " + fuelNeeded);
    }

    private static void puzzle2() {
        int fuelNeeded = 0;

        for (float mass : masses) {
            int fuel = calculateFuel(mass);
            while (fuel > 0) {
                fuelNeeded += fuel;
                fuel = calculateFuel(fuel);
            }
        }

        System.out.println("Fuel needed (Part 2): " + fuelNeeded);
    }

    private static int calculateFuel(float mass) {
        int fuel = (int) Math.floor(mass / 3.0f) - 2;
        return fuel;
    }
}

