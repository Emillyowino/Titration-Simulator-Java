import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Chemistry {
    public static void main(String[] args) {
        System.out.println("ACID-BASE TITRATION SIMULATION");
        System.out.println("Please enter all concentrations in moles per liter and volume in ml.");

        // Getting user input while validating it.
        Scanner myObj = new Scanner(System.in);
        double acidConcentration, acidVolume, baseConcentration, maxBaseVolume, stepSize;

        do {
            System.out.print("Enter acid concentration (mol/L): ");
            acidConcentration = myObj.nextDouble();
            if (acidConcentration <= 0) {
                System.out.println("Value must be positive. Try again.");
            }
        } while (acidConcentration <= 0);

        do {
            System.out.print("Enter acid volume (ml): ");
            acidVolume = myObj.nextDouble();
            if (acidVolume <= 0) {
                System.out.println("Value must be positive. Try again.");
            }
        } while (acidVolume <= 0);

        do {
            System.out.print("Enter base concentration (mol/L): ");
            baseConcentration = myObj.nextDouble();
            if (baseConcentration <= 0) {
                System.out.println("Value must be positive. Try again.");
            }
        } while (baseConcentration <= 0);

        do {
            System.out.print("Enter final base volume (ml): ");
            maxBaseVolume = myObj.nextDouble();
            if (maxBaseVolume <= 0) {
                System.out.println("Value must be positive. Try again.");
            }
        } while (maxBaseVolume <= 0);

        do {
            System.out.print("Enter increment for each addition (ml): ");
            stepSize = myObj.nextDouble();
            if (stepSize <= 0) {
                System.out.println("Value must be positive. Try again.");
            }
        } while (stepSize <= 0);

        TitrationSimulator simulator = new TitrationSimulator(acidConcentration, acidVolume, baseConcentration, maxBaseVolume, stepSize);
        simulator.runSimulator();
        double equilibriumVolume = simulator.findEquivalencePoint();
        System.out.printf("Equivalence point near %.2f ml of Base \n", equilibriumVolume);
        // Displaying the titration curve
        SwingUtilities.invokeLater(() -> TitrationGraph.showTitrationCurve(simulator.volumes, simulator.pHs, equilibriumVolume));
        myObj.close();
    }
}
