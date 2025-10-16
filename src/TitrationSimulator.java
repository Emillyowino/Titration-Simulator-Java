import java.util.ArrayList;

public class TitrationSimulator {
    private double acidConcentration, acidVolume, baseConcentration, maxBaseVolume, stepSize;

    public ArrayList<Double> volumes = new ArrayList<>();
    public ArrayList<Double> pHs = new ArrayList<>();

    public TitrationSimulator(double acidConcentration, double acidVolume,
    double baseConcentration, double maxBaseVolume, double stepSize) {
        this.acidConcentration = acidConcentration;
        this.acidVolume = acidVolume;
        this.baseConcentration = baseConcentration;
        this.maxBaseVolume = maxBaseVolume;
        this.stepSize = stepSize;
    }

    /* Helper function: Takes in 2 arguments; concentration and volume of an acid
    or base and returns the moles of the acid or base */
    private double calculateMoles(double concentration, double volume) {
        double moles = concentration * (volume / 1000);
        return moles;
    }
    // Find where pH changes fastest (equivalence)
    public double findEquivalencePoint() {
        int equivalencePoint = 0;
        double slope = 0;
        double maxSlope = 0;
        for (int i = 1; i < volumes.size(); i++) {
            slope = Math.abs((pHs.get(i) - pHs.get(i - 1)) / (volumes.get(i) - volumes.get(i - 1)));
            if (slope > maxSlope) {
                maxSlope = slope;
                equivalencePoint = i;
            }
        }
        return volumes.get(equivalencePoint);
    }

    public void runSimulator() {
        double pH = 0;
        double baseMoles = 0;
        double excessHydrogenIons = 0;
        double excessOH = 0;
        String previousRegion = "Acidic";
        String region = "";

        System.out.printf("pH Transition Detected: \nBase volume [ml] | pH:   | Region\n");
        System.out.println("--------------------------------");

        double acidMoles = calculateMoles(acidConcentration, acidVolume);

        for (double baseVolume = 0; baseVolume <= maxBaseVolume; baseVolume += stepSize) {
            baseMoles = calculateMoles(baseConcentration, baseVolume);

            if (baseMoles < acidMoles) {
                region = "Acidic";
                excessHydrogenIons = (acidMoles - baseMoles) / ((acidVolume + baseVolume) / 1000);
                pH = - Math.log10(excessHydrogenIons);
            } else if (baseMoles == acidMoles) {
                region = "Neutral point";
                pH = 7.0;
            } else {
                region = "Basic";
                excessOH = (baseMoles - acidMoles) / ((acidVolume + baseVolume) / 1000);
                excessOH = (Math.pow(10, -14) / excessOH);
                pH = -Math.log10(excessOH);
            }

            // Clamping pH values within 0 - 14 scale range
            if (pH < 0.0) {
                pH = 0;
            } else if (pH > 14.00) {
                pH = 14;
            }

            volumes.add(baseVolume);
            pHs.add(pH);

            if(!region.equals(previousRegion)) {
                System.out.printf("%-16.2f | %-6.2f | %s\n", baseVolume, pH, region);
                previousRegion = region;
            } else if (baseVolume == 0) {
                System.out.printf("%-16.2f | %-6.2f | Initial strong acid\n", baseVolume, pH);
            }
        }
    }
}
