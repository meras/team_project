import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * A frame to display a bar chart
 */
public class BarChartViewer extends JFrame {
    private final int HEIGHT = 300;
    private int WIDTH = 450;
    private int[] testNums;

    /**
     * Constructor
     */
    public BarChartViewer() {
        //setup for testing
        testNums = new int[12];
        Random randInt = new Random();
        for (int i = 0; i < testNums.length; i++) {
            testNums[i] = randInt.nextInt(15);
        }

        //Create the frame

        setTitle("Bar chart report");
        setSize(450, 300);
        System.out.println(getWidth());
        setResizable(false);
        //TODO change to DISLAY_ON_CLOSE when integrating with the project
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BarChart chart = new BarChart(testNums, HEIGHT, WIDTH);
        add(chart);

        setVisible(true);

    }

    /**
     * print out test array TODO remove this method
     */
    public void printNums() {
        for (int i = 0; i < testNums.length; i++) {
            System.out.println(testNums[i]);
        }
    }

    /**
     * graphics object
     */
    public class BarChart extends JComponent {
        private final int CHART_HEIGHT = 220;
        private int barWidth = 30;
        private int barGap = 5;
        private int[] elements;
        private int maxValue;
        private int viewerHeight;

        public BarChart(int[] elements, int height, int width) {
            this.elements = elements;
            for (int i : elements) {
                if (i > maxValue) {
                    maxValue = i;
                }
            }
            viewerHeight = height;

        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //g2.draw(new Line2D.Double(0, 100, 500, 100));

            g.setColor(Color.WHITE);
            g.fillRect(10, 10, 400, 300);

            drawAxis(g2);

            int x = 10;
            for (int i = 0; i < elements.length; i++) {
                // calculate bar height relative to the chart area
                int barHeight = Math.round(CHART_HEIGHT * ((float) elements[i] / maxValue));
                drawBar(g2, elements[i], x, viewerHeight - 50 - barHeight, barWidth, barHeight);
                x += barWidth + barGap;
            }
        }

        private void drawBar(Graphics2D g, int heading, int x, int y, int barWidth, int barHeight) {
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(Color.BLACK);
            //g.draw(new Rectangle(x, y, w, h));
            g.draw(new Rectangle2D.Double(x, y, barWidth, barHeight));
            g.drawString("" + heading, x + 5, y - 5);
            g.drawString("MM2", x, y + barHeight + 15);

        }

        private void drawAxis(Graphics2D g) {
            g.setColor(Color.BLACK);
            g.drawLine(10, 10, 10, 100);
        }
    }
}
