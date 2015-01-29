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

        int width = refereeList.getRefereeCount() * (chart.barWidth + chart.barGap) + (chart.barMargin * 2) - chart.barGap;
        setSize(width, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * The component that draws the bar chart
     */
    public class BarChart extends JComponent {
        private final int CHART_HEIGHT = 220;
        private final int barWidth = 30;
        private final int barGap = 5;
        private final int barMargin = 20;
        private final RefList refereeList;
        private int maxValue;

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

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            //scale the chart
            int unit = Math.round((float) CHART_HEIGHT / maxValue);

            //draw the grey unit lines
            drawAxis(g2, unit);

            //initial x coordinate to start drawing bars
            int barX = barMargin;
            for (Referee ref : refereeList) {
                String id = ref.getRefID();
                int allocNum = ref.getNumAllocs();

                //calculate bar height relative to the chart area
                int barHeight = unit * allocNum;

                drawBar(g2, allocNum, barX, CHART_HEIGHT + 30 - barHeight, barWidth, barHeight, id);
                barX += barWidth + barGap;
            }
        }

        /**
         * Draws a bar with the number of allocations and the referee id
         *
         * @param heading   displays number of allocations above the bar
         * @param x         the x coordinate
         * @param y         the y coordinate
         * @param barWidth
         * @param barHeight
         * @param id        Referee ID to displayed below the bar
         */
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
         *
         * @param g    graphics component
         * @param unit the relative pixel distance between each unit
         */
        private void drawAxis(Graphics2D g, int unit) {
            int y = CHART_HEIGHT + 30;

            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= maxValue; i++) {
                int x1 = barMargin / 2;
                int x2 = refereeList.getRefereeCount() * (barWidth + barGap) + barGap + barMargin;
                g.drawLine(x1, y, x2, y);
                y -= unit;
            }
        }
    }
}
