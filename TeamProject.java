/**
 * The main class
 */
public class TeamProject {
    public static void main(String[] args) {
        System.out.println("Go Goldilocks!");

        BarChartViewer a = new BarChartViewer();
        a.printNums();
        LittleGUI b = new LittleGUI();
        b.setVisible(true);

    }
}
