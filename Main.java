public class Main {
    private static String[] rateWoerter;
    private static String[] hilfsWoerter;
    public static String rateWort;
    public static int wordIndex = 0;

    public Main() {
        PythonBackground.createList();
        rateWoerter = PythonBackground.rateWoerter();
        rateWort = rateWoerter[0];
        new UserInterface();

        while (true) {
            UserInterface.switchTimerState();
            UserInterface.timer();
        }
    }

    public static String[] getRateWoerter() {
        return rateWoerter;
    }

    public static String[] getHilfsWoerter() {
        hilfsWoerter = PythonBackground.hilfsWoerter(rateWort);
        return hilfsWoerter;
    }

    public static void main(String args[]) {
        new Main();
    }
}