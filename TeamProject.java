/**
 * The main class
 */
public class TeamProject {
    public static void main(String[] args) {
        System.out.println("Go Goldilocks!");
		
		MainGUI main = new MainGUI();
		main.setVisible(true);		
        BarChartViewer a = new BarChartViewer();
        a.printNums();
        LittleGUI b = new LittleGUI();
        b.setVisible(true);

    }
}
