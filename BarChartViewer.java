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

    public BarChartViewer(RefList refereeList) {
        //Create the frame
        setTitle("Allocation numbers");
        setSize(460, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        BarChart chart = new BarChart(refereeList);
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
        private final  int[] elements;
        private RefList refereeList;
        private int maxValue;

        public BarChart(int[] elements) {
            this.elements = elements;
            for (int i : elements) {
                if (i > maxValue) {
                    maxValue = i;
                }
            }
        }

        //todo consider using a list for elements

        /**
         * Draws a bar chart using allocation numbers
         * @param refereeList list of referees which contains allocation numbers
         */
        public BarChart(RefList refereeList) {
            this.refereeList = refereeList;
            elements = new int[refereeList.getRefList().size()];

            for (int i = 0; i < elements.length; i++) {
                elements[i] = refereeList.getRefAtIndex(i).getNumAllocs();
                if (elements[i] > maxValue) {
                    maxValue = elements[i];
                }
            }
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            int barX = 20;
            int unit = Math.round((float) CHART_HEIGHT / maxValue);

            drawAxis(g2, unit);

            String id = "123";
            // calculate the size of one unit relative to chart area
            //for (int element : elements) {
            for (int i = 0; i < elements.length; i++) {
                //// calculate bar height relative to the chart area
//                int barHeight = unit * element;
                int barHeight = unit * elements[i];

                //todo will change to something better
                id = refereeList.getRefAtIndex(i).getRefID();

//                drawBar(g2, element, barX, CHART_HEIGHT + 30 - barHeight, barWidth, barHeight, id);
                drawBar(g2, elements[i], barX, CHART_HEIGHT + 30 - barHeight, barWidth, barHeight, id);
                barX += barWidth + barGap;
            }
        }

        private void drawBar(Graphics2D g, int heading, int x, int y, int barWidth, int barHeight, String id) {
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(Color.BLACK);
            g.draw(new Rectangle(x, y, barWidth, barHeight));
            g.drawString("" + heading, x + 5, y - 5);
            g.drawString(id, x, y + barHeight + 15);
        }

        /**
         * Draws gray lines behind the bar chart
         * @param g graphics component
         * @param unit the relative pixel distance between each unit
         */
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
