import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UniqApp {
    private static boolean register;
    private static boolean uniq;
    private static boolean repeatNum;
    private static boolean ignoreIntb = false;
    private static int ignoreInt = 0;
    private static boolean outputFile = false;
    private static String outputFileS = "";
    private static boolean inputFile = false;
    private static String inputFileS = "";

    public static void starter(String[] input) throws IOException {
        Pattern pattern = Pattern.compile(
                "(-i)?\\s?(-u)?\\s?(-c)?\\s?(-s \\d+)?\\s?(-o \\w+)?\\s?(\\w+)?");
        if (!Pattern.matches(String.valueOf(pattern), String.join(" ", input))) {
            System.out.println("Invalid enter, check flag entry order");
            throw new IllegalArgumentException("Invalid enter, check flag entry order");
        }
        for (String i: input){
            flags(i);
        }
        File file = !outputFileS.isEmpty() ? new File(outputFileS): null;
        FileWriter fW = file != null ? new FileWriter(file): null;
        BufferedWriter bW = fW != null ? new BufferedWriter(fW): null;
        File file2 = !inputFileS.isEmpty() ? new File (inputFileS):null;
        FileReader fR = file2 != null ? new FileReader(file2): null;
        BufferedReader bR = fR != null ? new BufferedReader(fR): null;
        uniqBase(bW,bR);
        if(bW!=null) bW.close();
        if(bR!=null) bR.close();
        register = false;
        uniq = false;
        repeatNum = false;
        ignoreIntb = false;
        ignoreInt = 0;
        outputFile = false;
        outputFileS = "";
        inputFile = false;
        inputFileS = "";
    }

    public static void flags(String inputF) {
        switch (inputF) {
            case "-i": register = true;
                break;
            case "-u": uniq = true;
                break;
            case "-c": repeatNum = true;
                break;
            case "-s": ignoreIntb = true;
                break;
            case "-o": outputFile = true;
                break;
        }
        Pattern p = Pattern.compile("\\d+");
        if (Pattern.matches(String.valueOf(p), inputF) && ignoreIntb) {
            ignoreInt = Integer.parseInt(inputF);
        }
        Pattern p2 = Pattern.compile("\\w+");
        if (Pattern.matches(String.valueOf(p2), inputF)){
            if (!outputFile){
                inputFileS = inputF;
                inputFile = true;
            }
            if(outputFile && outputFileS.isEmpty()) {
                outputFileS = inputF;
                outputFile = false;
            }
        }
    }

    private static void uniqBase(Object ... bW) throws IOException {
        BufferedWriter bW1 = bW[0]!= null ? (BufferedWriter) bW[0] : null;
        BufferedReader bR1 = bW[1]!= null ?(BufferedReader)bW[1]:null;
        boolean skip = false;
        int count = 1;
        String line1 = "";
        String line2 = reader(bR1, inputFile);
        if (line2 == null) {
            skip = true;
            line1 = null;
        }
        boolean lastString = true;
        while (lastString){
            if (line1 == null) {
                line1 = " ";
                lastString = false;
            } else line1 = reader(bR1, inputFile);
            if(!skip && line1 !=null) {
                if (register) {
                    line1 = line1.toLowerCase();
                    line2 = line2.toLowerCase();
                }
                int b = line1.length();
                int a = line2.length();
                int ignore = ignoreInt;
                if (ignore > b || ignore > a) {
                    ignore = Math.min(a, b);
                }
                if (line1.substring(ignore).equals(line2.substring(ignore))) count += 1;
                else {
                    if (!uniq || count == 1) {
                        if (repeatNum && !line2.isEmpty()) writer(bW1, !outputFileS.isEmpty(), count + " " + line2);
                        else writer(bW1, !outputFileS.isEmpty(), line2);
                    }
                    count = 1;
                }
                line2 = line1;
            }
        }
    }

    private static void writer(BufferedWriter bW, Boolean out, String line) throws IOException {
        if (out){
            bW.write(line);
            bW.newLine();
        } else {
            System.out.println(line);
        }
    }

    private static String  reader (BufferedReader bR,Boolean in) throws IOException {
        if (in){
            return bR.readLine();
        } else {
            Scanner inb = new Scanner(System.in);
            System.out.println("Enter you line, to stop typing enter -esc");
            String text = inb.nextLine();
            if (text.equals("-esc")) text = null;
            return text;
        }
    }

    public static void main(String[] args) throws IOException {
        starter(args);
    }
}