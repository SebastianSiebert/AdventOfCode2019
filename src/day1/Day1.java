import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class Day1 {
    public static void main(String[] args) throws Exception {
        if (args.length == 0)
            return;

        puzzle1(args[0]);
    }

    private static void puzzle1(String fileName) throws Exception {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        int fuelNeeded = 0;

        while((st = br.readLine()) != null) {
            float mass = Float.parseFloat(st);
            fuelNeeded += calculateFuel(mass);
        }

        System.out.println("Fuel needed (Part 1): " + fuelNeeded);
    }


    private static int calculateFuel(float mass) {
        int fuel = (int)Math.floor(mass/3.0f) - 2;
        return fuel;
    }
}

