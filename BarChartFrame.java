import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;

/**
 * A frame to display a bar chart
 */
public class BarChartFrame extends JFrame{
    private int[] testNums;

    /**
     * Constructor
     */
    public BarChartFrame() {
        //setup for testing
        testNums = new int[12];
        Random randInt = new Random();
        for (int i = 0; i < testNums.length; i++) {
            testNums[i] = randInt.nextInt(15);
        }

        //Create the frame

        setTitle("Bar chart report");
        setSize(375, 250);
        //TODO change to DISLAY_ON_CLOSE when integrating with the project
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BarChart chart = new BarChart(testNums);
        add(chart);

        setVisible(true);

    }

    /**
     * graphics object
     */


    public class BarChart extends JComponent {
        private int barWidth = 25;
        private int[] elements;

        public BarChart(int[] elements) {
            this.elements = elements;
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            //g2.draw(new Line2D.Double(0, 100, 500, 100));
            int x = 10;
            for (int i = 0; i < elements.length; i++) {
                int barHeight = elements[i]*10;
                drawBar(g2,elements[i], x, 220-barHeight, barWidth, barHeight);
                x += 30;
            }
        }

        private void drawBar(Graphics2D g, int heading, int x, int y, int w, int h) {
            g.draw(new Rectangle(x, y, w, h));
            g.drawString("" + heading, x + 5, y - 5);

        }
    }

    /**
     * print out test array TODO remove this method
     */
    public void printNums() {
        for (int i = 0; i < testNums.length; i++) {
            System.out.println(testNums[i]);
        }
    }
}
