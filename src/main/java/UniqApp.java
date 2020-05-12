import java.io.*;

public class UniqApp {

    public static void starter(String[] input) throws IOException {
        FlagCont flags = new FlagCont(input);
        try {
            File file = flags.getOutputFileS() != null ? new File(flags.getOutputFileS()) : null;
            FileWriter fW = file != null ? new FileWriter(file) : null;
            BufferedWriter bW = fW != null ? new BufferedWriter(fW) : new BufferedWriter(new OutputStreamWriter(System.out));
            File file2 = flags.getInputFileS() != null ? new File(flags.getInputFileS()) : null;
            FileReader fR = file2 != null ? new FileReader(file2) : null;
            BufferedReader bR = fR != null ? new BufferedReader(fR) : new BufferedReader(new InputStreamReader(System.in));
            uniqBase(bW, bR, flags);
            bW.close();
            bR.close();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
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
                if (flags.getRegister()) {
                    line1 = line1.toLowerCase();
                    line2 = line2.toLowerCase();
                }
                int b = line1.length();
                int a = line2.length();
                int ignore = flags.getIgnoreInt();
                if (ignore > b || ignore > a) {
                    ignore = Math.min(a, b);
                }
                if (line1.substring(ignore).equals(line2.substring(ignore))) count += 1;
                else {
                    if (!flags.getUniq() || count == 1) {
                        if (flags.getRepeatNum() && !line2.isEmpty()) writer(bW, count + " " + line2);
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