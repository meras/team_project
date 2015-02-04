import javax.swing.*;
import java.awt.*;

/**
 * A frame to display a bar chart representing allocation numbers of all the referees
 */
public class BarChartViewer extends JFrame {

    /**
     * Creates a frame where the width is scaled to the size of the referee list
     * and paints a bar chart
     *
     * @param refereeList object which contains Referee instances
     */
    public BarChartViewer(RefList refereeList) {
        //Create the frame
        setTitle("Allocation numbers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        BarChart chart = new BarChart(refereeList);
        add(chart);

        int width = refereeList.getRefereeCount() * (chart.BAR_WIDTH + chart.BAR_GAP) + (chart.BAR_MARGIN * 2) - chart.BAR_GAP;
        setSize(width, 310);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * The component that draws the bar chart
     */
    public class BarChart extends JComponent {
        private final int CHART_HEIGHT = 220;   // The height of the chart area
        private final int BAR_WIDTH = 30;   // The width of each bar
        private final int BAR_GAP = 5;  // The width of the gap between the bars
        private final int BAR_MARGIN = 20;  // TODO What is this number? What is its purpose?
        private static RefList refereeList; // The list of the referee whose informatoin will be displayed
        private int maxValue;   // The highest number of referee allocations

        /**
         * Draws a bar chart using allocation numbers
         *
         * @param refereeList list of referees which contains allocation numbers
         */
        public BarChart(RefList refereeList) {
            this.refereeList = refereeList;

            for (Referee ref : this.refereeList) {
                if (ref.getNumAllocs() > maxValue) {
                    maxValue = ref.getNumAllocs();
                }
            }
        }

        /**
        * TODO not sure what this does or what to say about it
        */
        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            //scale the chart depending on the highest number of match allocations
            int unit = Math.round((float) CHART_HEIGHT / maxValue);

            //draw the grey unit lines
            drawAxis(g2, unit);

            //initial x coordinate to start drawing bars
            int barX = BAR_MARGIN;
            for (Referee ref : refereeList) {
                String id = ref.getRefID();
                int allocNum = ref.getNumAllocs();

                //calculate bar height relative to the chart area
                int barHeight = unit * allocNum;

                drawBar(g2, allocNum, barX, CHART_HEIGHT + 30 - barHeight, BAR_WIDTH, barHeight, id);
                barX += BAR_WIDTH + BAR_GAP;
            }
        }

        /**
         * Draws a bar with the number of allocations and the referee id
         *
         * @param heading   displays number of allocations above the bar
         * @param x         the x coordinate
         * @param y         the y coordinate
         * @param BAR_WIDTH the width of the bar which is fixed
         * @param barHeight the height of the bar which depends on the number of allocations
         * @param id        Referee ID to displayed below the bar
         */
        private void drawBar(Graphics2D g, int heading, int x, int y, int BAR_WIDTH, int barHeight, String id) {
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, BAR_WIDTH, barHeight);
            g.setColor(Color.BLACK);
            g.draw(new Rectangle(x, y, BAR_WIDTH, barHeight));
            g.drawString("" + heading, x + 5, y - 5);
            g.drawString(id, x, y + barHeight + 15);
        }

        /**
         * Draws gray lines behind the bar chart
         *
         * @param g    graphics component
         * @param unit the relative pixel distance between each unit
         */
        private void drawAxis(Graphics2D g, int unit) {
            int y = CHART_HEIGHT + 30;

            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= maxValue; i++) {
                int x1 = BAR_MARGIN / 2;
                int x2 = refereeList.getRefereeCount() * (BAR_WIDTH + BAR_GAP) + BAR_GAP + BAR_MARGIN;
                g.drawLine(x1, y, x2, y);
                y -= unit;
            }
        }
    }
}
