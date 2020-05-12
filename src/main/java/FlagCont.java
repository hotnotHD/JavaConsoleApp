import java.util.Objects;

public class FlagCont {
    private boolean register;
    private boolean uniq;
    private boolean repeatNum;
    private boolean ignoreIntb;
    private int ignoreInt;
    private boolean outputFile;
    private String outputFileS;
    private String inputFileS;

    public FlagCont (String[] inputF) {
        for (String i: inputF){
            if (ignoreIntb) {
                try {
                    this.ignoreInt = Integer.parseInt(i);
                    this.ignoreIntb = false;
                } catch (NumberFormatException e) {
                    System.err.println(e);
                }
            }
            switch (i) {
                case "-i":
                    this.register = true;
                    break;
                case "-u":
                    this.uniq = true;
                    break;
                case "-c":
                    this.repeatNum = true;
                    break;
                case "-s":
                    this.ignoreIntb = true;
                    break;
                case "-o":
                    this.outputFile = true;
                    break;
            }
            if (outputFile) {
                this.outputFileS = i;
                this.outputFile = false;
            }
            if (i.charAt(0) != '-') {
                this.inputFileS = i;
            }
        }
    }
    public boolean getRegister() {
        return register;
    }

    public boolean getUniq() {
        return uniq;
    }

    public boolean getRepeatNum() {
        return repeatNum;
    }

    public int getIgnoreInt() {
        return ignoreInt;
    }

    public String getInputFileS() {
        return inputFileS;
    }

    public String getOutputFileS() {
        return outputFileS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FlagCont)) return false;
        FlagCont flagCont = (FlagCont) o;
        return register == flagCont.register &&
                uniq == flagCont.uniq &&
                repeatNum == flagCont.repeatNum &&
                ignoreIntb == flagCont.ignoreIntb &&
                ignoreInt == flagCont.ignoreInt &&
                outputFile == flagCont.outputFile &&
                Objects.equals(outputFileS, flagCont.outputFileS) &&
                Objects.equals(inputFileS, flagCont.inputFileS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(register, uniq, repeatNum, ignoreIntb, ignoreInt, outputFile, outputFileS, inputFileS);
    }
}
