import java.io.*;

public class UniqApp {

    public static void starter(String[] input) throws IOException {
        FlagCont gg = new FlagCont();
        try{
        for (String i: input){
            flags(i, gg);
        } } catch (IllegalArgumentException e){
            System.out.println("Invalid input of arguments");
        }
        try {
            File file = !gg.outputFileS.isEmpty() ? new File(gg.outputFileS) : null;
            FileWriter fW = file != null ? new FileWriter(file) : null;
            BufferedWriter bW = fW != null ? new BufferedWriter(fW) : new BufferedWriter(new OutputStreamWriter(System.out));
            File file2 = !gg.inputFileS.isEmpty() ? new File(gg.inputFileS) : null;
            FileReader fR = file2 != null ? new FileReader(file2) : null;
            BufferedReader bR = fR != null ? new BufferedReader(fR) : new BufferedReader(new InputStreamReader(System.in));
            uniqBase(bW, bR, gg);
            bW.close();
            bR.close();
        }catch(FileNotFoundException e){
            System.out.println("File Not Found");
        }
    }

    public static void flags(String inputF, FlagCont gg) {
        switch (inputF) {
            case "-i": gg.register = true;
                return;
            case "-u": gg.uniq = true;
                return;
            case "-c": gg.repeatNum = true;
                return;
            case "-s": gg.ignoreIntb = true;
                return;
            case "-o": gg.outputFile = true;
                return;
        }
        if (gg.ignoreIntb) {
            try {
                gg.ignoreInt = Integer.parseInt(inputF);
            }catch (NumberFormatException e){
                System.out.println("Wrong input");
            }
            gg.ignoreIntb = false;
            return;
        }
        if (gg.outputFile){
            gg.outputFileS = inputF;
            gg.outputFile = false;
            return;
        }
        if(inputF.charAt(0) != '-') {
            gg.inputFileS = inputF;
            return;
        }
        throw new IllegalArgumentException();
    }

    private static void uniqBase(BufferedWriter bW, BufferedReader bR, FlagCont gg) throws IOException {
        boolean skip = false;
        boolean lastString = true;
        int count = 1;
        String line1 = "";
        String line2 = reader(bR);
        if (line2 == null) {
            skip = true;
            line1 = null;
        }
        while (lastString){
            if (line1 == null) {
                line1 = " ";
                lastString = false;
            } else line1 = reader(bR);
            if(!skip && line1 !=null) {
                if (gg.register) {
                    line1 = line1.toLowerCase();
                    line2 = line2.toLowerCase();
                }
                int b = line1.length();
                int a = line2.length();
                int ignore = gg.ignoreInt;
                if (ignore > b || ignore > a) {
                    ignore = Math.min(a, b);
                }
                if (line1.substring(ignore).equals(line2.substring(ignore))) count += 1;
                else {
                    if (!gg.uniq || count == 1) {
                        if (gg.repeatNum && !line2.isEmpty()) writer(bW, count + " " + line2);
                        else writer(bW, line2);
                    }
                    count = 1;
                }
                line2 = line1;
            }
        }
    }

    private static void writer(BufferedWriter bW, String line) throws IOException {
            bW.write(line);
            bW.newLine();
    }

    private static String  reader (BufferedReader bR) throws IOException {
        String read = bR.readLine();
        if ( read == null || read.equals("-e")) return null;
            else return read;
    }

    public static void main(String[] args) throws IOException {
        starter(args);
    }
}