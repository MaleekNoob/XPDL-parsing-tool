
import java.util.Random;
import java.util.Scanner;

public class BPMN {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file name (e.g., myfile.bpmn): ");
        String fileName = scanner.nextLine(); // Get the file name from the user

        // Simulate random cycle time calculation
        double cycleTime = generateRandomCycleTime();
        System.out.println("cycle time: " + cycleTime + " minutes");

        scanner.close();
    }

    public static double generateRandomCycleTime() {
        Random rand = new Random();
        double cycleTime = rand.nextDouble() * 100;
        return cycleTime;
    }
}
