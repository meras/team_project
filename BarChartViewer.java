import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A frame to display a bar chart
 */
public class BarChartViewer extends JFrame {
    /**
     * Constructor
     */
    public BarChartViewer(RefList refereeList) {
        //Create the frame
        setTitle("Allocation numbers");
        //setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);

        BarChart chart = new BarChart(refereeList);
        add(chart);
        int width = refereeList.getRefList().size() * (chart.barWidth + chart.barGap) + (chart.barMargin * 2)-5;
        setSize(width, 300);
        setLocationRelativeTo(null);

        setVisible(true);

    }

    /**
     * graphics object
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
         * @param refereeList list of referees which contains allocation numbers
         */
        public BarChart(RefList refereeList) {
            this.refereeList = refereeList;

            for (Referee ref : this.refereeList.getRefList()) {
                if (ref.getNumAllocs() > maxValue) {
                    maxValue = ref.getNumAllocs();
                }
            }
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            //initial x coordinate to start drawing bars
            int barX = 20;
            //scale the chart
            int unit = Math.round((float) CHART_HEIGHT / maxValue);

            drawAxis(g2, unit);

            for (Referee ref : refereeList.getRefList()) {
                String id = ref.getRefID();
                int allocs = ref.getNumAllocs();

                //calculate bar height relative to the chart area
                int barHeight = unit * allocs;

                //todo will change to something better


                drawBar(g2, allocs, barX, CHART_HEIGHT + 30 - barHeight, barWidth, barHeight, id);
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
            int y = CHART_HEIGHT+30;

            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= maxValue; i++) {
                int x1 = barMargin / 2;
                int x2 = refereeList.getRefList().size()*(barWidth+barGap) + barGap + barMargin;
                g.drawLine(x1, y, x2, y);
                y -= unit;
            }
        }
    }
}
