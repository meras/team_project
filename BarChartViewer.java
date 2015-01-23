import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * A frame to display a bar chart
 */
public class BarChartViewer extends JFrame {
    private int[] testNums;

    /**
     * Constructor
     */
    public BarChartViewer() {
        //setup values for testing
        testNums = new int[12];
        Random randInt = new Random();
        for (int i = 0; i < testNums.length; i++) {
            testNums[i] = randInt.nextInt(15);
        }

        //Create the frame
        setTitle("Allocation numbers");
        setSize(460, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);


        BarChart chart = new BarChart(testNums);
        add(chart);

        setVisible(true);

    }

    /**
     * print out test array TODO remove this method
     */
    public void printNums() {
        for (int testNum : testNums) {
            System.out.println(testNum);
        }
    }

    /**
     * graphics object
     */
    public class BarChart extends JComponent {
        private final int CHART_HEIGHT = 220;
        private final int barWidth = 30;
        private final int barGap = 5;
        private final int[] elements;
        private int maxValue;

        public BarChart(int[] elements) {
            this.elements = elements;
            for (int i : elements) {
                if (i > maxValue) {
                    maxValue = i;
                }
            }

        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            int barX = 20;
            int unit = Math.round((float) CHART_HEIGHT / maxValue);

            drawAxis(g2, unit);

            // calculate the size of one unit relative to chart area
            for (int element : elements) {
                //// calculate bar height relative to the chart area
                int barHeight = unit * element;

                drawBar(g2, element, barX, CHART_HEIGHT + 30 - barHeight, barWidth, barHeight);
                barX += barWidth + barGap;
            }
        }

        private void drawBar(Graphics2D g, int heading, int x, int y, int barWidth, int barHeight) {
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.draw(new Rectangle(x, y, barWidth, barHeight));
            g.drawString("" + heading, x + 5, y - 5);
            g.drawString("MM2", x, y + barHeight + 15);
        }

        private void drawAxis(Graphics2D g, int unit) {
            int lineHeight = CHART_HEIGHT+30;

            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= maxValue; i++) {
                g.drawLine(10, lineHeight, elements.length*(barWidth+barGap)+30, lineHeight);
                lineHeight -= unit;
            }
        }
    }
}
