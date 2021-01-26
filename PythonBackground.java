import org.python.util.PythonInterpreter;

public class PythonBackground {
    private static PythonInterpreter pyInterp = new PythonInterpreter();
    private static boolean statusReady = false;
    private static int listLength = 0;

    public static void createList() {
        pyInterp.exec("import os");
        pyInterp.exec("from random import shuffle");

        pyInterp.exec("list = []");
        pyInterp.exec(
                      "def createList():                                               " + "\n" +
                      "    global list                                                 " + "\n" +
                      "    try:                                                        " + "\n" +
                      "        file = open(os.getcwd() + '/WordList.txt', 'r')         " + "\n" +
                      "        lines = file.readlines()                                " + "\n" +
                      "        for i in range(lines.__len__()):                        " + "\n" +
                      "             list.append(lines[i].replace('\\n', '').split(','))" + "\n" +
                      "        file.close()                                            " + "\n" +
                      "        shuffle(list)                                           " + "\n" +
                      "        list.append(['Kein weiteres Wort!'])                    " + "\n" +
                      "        success = True                                          " + "\n" +
                      "    except:                                                     " + "\n" +
                      "        success = False                                         " + "\n" +
                      "    return success                                              "
        );

        pyInterp.exec("success = createList()");
        pyInterp.exec("createList = None");

        statusReady = (Boolean) pyInterp.get("success").__tojava__(Boolean.class);
        pyInterp.exec("success = None");
    }
    
    public static String[] rateWoerter() {
        if (statusReady) {
            int len = getListLength();
            String[] woerter = new String[len];

            for (int i = 0; i < len; i++) {
                pyInterp.exec(String.format("currElement = list[%d][0]", i));
                woerter[i] = pyInterp.get("currElement").toString();
            }
            pyInterp.exec("currElement = None");
            return woerter;
        }
        
        return new String[]{"Keine Wortliste vorhanden!"};
    }

    public static String[] hilfsWoerter(String rateWort) {
        if (statusReady) {
            pyInterp.exec(String.format("rateWort = list[%d][0]", Main.wordIndex));
            if (pyInterp.get("rateWort").toString().equals(rateWort)) {
                pyInterp.exec(String.format("helpCount = list[%d].__len__()", Main.wordIndex));
                int helpCount = (int) pyInterp.get("helpCount").__tojava__(int.class);
                String[] woerter = new String[helpCount - 1];
                for (int i = 1; i < helpCount; i++) {
                    pyInterp.exec(String.format("currElement = list[%d][%o]", Main.wordIndex, i));
                    woerter[i - 1] = pyInterp.get("currElement").toString();
                }
                pyInterp.exec("currElement = None");
                pyInterp.exec("helpCount = None");
                pyInterp.exec("rateWort = None");
                return woerter;
            }
            return new String[]{"Keine Hilfe vorhanden!"};
        }

        return new String[]{"Keine Wortliste vorhanden!"};
    }

    public static int getListLength() {
        if (statusReady && listLength == 0) {
            pyInterp.exec("len = list.__len__()");
            listLength = (int) pyInterp.get("len").__tojava__(int.class);
            pyInterp.exec("len = None");
            return listLength;
        }

        return listLength;
    }
}