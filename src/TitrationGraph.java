import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TitrationGraph extends JPanel {

    private ArrayList<Double> x, y;
    private double xMax, yMax = 14;
    private double equilibriumVolume;

    public TitrationGraph(ArrayList<Double> x, ArrayList<Double> y, double equilibriumVolume) {
        this.x = x;
        this.y = y;
        this.equilibriumVolume = equilibriumVolume;
        this.xMax = x.get(x.size() - 1);
    }

    // Takes 2 parameters, volume and pH then graphs the pH-Volume curve;
    public static void showTitrationCurve(ArrayList<Double> volumes, ArrayList<Double> pHs, double equilibriumVolume) {
        JFrame frame = new JFrame("Titration Curve");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TitrationGraph(volumes, pHs, equilibriumVolume));
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

        // Graph drawing panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth(), h = getHeight();
        int margin = 50;

        // axes
        g2.drawLine(margin, h - margin, w - margin, h - margin); // x-axis
        g2.drawLine(margin, margin, margin, h - margin); // y-axis

        // labels
        g2.drawString("Volume (mL)", w / 2 - 30, h - 15);
        g2.drawString("pH", 15, margin - 10);

        // scale and plot
        g2.setColor(Color.BLUE);
        for (int i = 0; i < x.size() - 1; i++) {
            int x1 = (int) (margin + (x.get(i) / xMax) * (w - 2 * margin));
            int y1 = (int) (h - margin - (y.get(i) / yMax) * (h - 2 * margin));
            int x2 = (int) (margin + (x.get(i + 1) / xMax) * (w - 2 * margin));
            int y2 = (int) (h - margin - (y.get(i + 1) / yMax) * (h - 2 * margin));
            g2.drawLine(x1, y1, x2, y2);
        }

        int eqX = (int) (margin + (equilibriumVolume / xMax) * (w - 2 * margin));
        int eqY = (int) (h - margin - (7.0 / yMax) * (h - 2 * margin)); // pH 7
        g2.setColor(Color.RED);
        g2.fillOval(eqX - 4, eqY - 4, 8, 8);
        g2.drawString("Equivalence", eqX + 8, eqY - 8);

    }
}
