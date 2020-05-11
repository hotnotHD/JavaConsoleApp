import java.io.*;

public class UniqApp {

    public static void starter(String[] input) throws IOException {
        FlagCont flags = new FlagCont();
        try {
            for (String i : input) {
                flags(i, flags);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input of arguments");
        }
        try {
            File file = !flags.outputFileS.isEmpty() ? new File(flags.outputFileS) : null;
            FileWriter fW = file != null ? new FileWriter(file) : null;
            BufferedWriter bW = fW != null ? new BufferedWriter(fW) : new BufferedWriter(new OutputStreamWriter(System.out));
            File file2 = !flags.inputFileS.isEmpty() ? new File(flags.inputFileS) : null;
            FileReader fR = file2 != null ? new FileReader(file2) : null;
            BufferedReader bR = fR != null ? new BufferedReader(fR) : new BufferedReader(new InputStreamReader(System.in));
            uniqBase(bW, bR, flags);
            bW.close();
            bR.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public static void flags(String inputF, FlagCont flags) {
        switch (inputF) {
            case "-i":
                flags.register = true;
                return;
            case "-u":
                flags.uniq = true;
                return;
            case "-c":
                flags.repeatNum = true;
                return;
            case "-s":
                flags.ignoreIntb = true;
                return;
            case "-o":
                flags.outputFile = true;
                return;
        }
        if (flags.ignoreIntb) {
            try {
                flags.ignoreInt = Integer.parseInt(inputF);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input");
            }
            flags.ignoreIntb = false;
            return;
        }
        if (flags.outputFile) {
            flags.outputFileS = inputF;
            flags.outputFile = false;
            return;
        }
        if (inputF.charAt(0) != '-') {
            flags.inputFileS = inputF;
            return;
        }
        throw new IllegalArgumentException();
    }

    private static void uniqBase(BufferedWriter bW, BufferedReader bR, FlagCont flags) throws IOException {
        boolean skip = false;
        boolean lastString = true;
        int count = 1;
        String line1 = "";
        String line2 = reader(bR);
        if (line2 == null) {
            skip = true;
            line1 = null;
        }
        while (lastString) {
            if (line1 == null) {
                line1 = " ";
                lastString = false;
            } else line1 = reader(bR);
            if (!skip && line1 != null) {
                if (flags.register) {
                    line1 = line1.toLowerCase();
                    line2 = line2.toLowerCase();
                }
                int b = line1.length();
                int a = line2.length();
                int ignore = flags.ignoreInt;
                if (ignore > b || ignore > a) {
                    ignore = Math.min(a, b);
                }
                if (line1.substring(ignore).equals(line2.substring(ignore))) count += 1;
                else {
                    if (!flags.uniq || count == 1) {
                        if (flags.repeatNum && !line2.isEmpty()) writer(bW, count + " " + line2);
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

    private static String reader(BufferedReader bR) throws IOException {
        String read = bR.readLine();
        if (read == null || read.equals("-e")) return null;
        else return read;
    }

    public static void main(String[] args) throws IOException {
        starter(args);
    }
}