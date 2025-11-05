import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ExperimentData {
    private static final String DATA_FILE = System.getProperty("user.dir") + "/experiments.json";
    private static final Gson gson = new Gson();

    public static class DataPoint {
        double volume;
        double pH;
        public DataPoint(double volume, double pH) {
            this.volume = volume;
            this.pH = pH;
        }
    }

    public static class Experiment {
        String name;
        List<DataPoint> data;
        public Experiment(String name, List<DataPoint> data) {
            this.name = name;
            this.data = data;
        }
    }

    public static void saveExperiment(Experiment exp) {
        System.out.println("Saving JSON to: " + DATA_FILE);
        try {
            List<Experiment> experiments = loadAll();
            System.out.println("Loaded " + experiments.size() + " previous experiments");
            experiments.add(exp);
            FileWriter writer = new FileWriter(DATA_FILE);
            gson.toJson(experiments, writer);
            writer.close();
            System.out.println("Experiment successfully saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Experiment> loadAll() {
        try {
            FileReader reader = new FileReader(DATA_FILE);
            List<Experiment> experiments = gson.fromJson(reader,
                    new TypeToken<List<Experiment>>(){}.getType());
            reader.close();
            return experiments != null ? experiments : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("No existing JSON file found, creating new list");
            return new ArrayList<>();
        }
    }
}
