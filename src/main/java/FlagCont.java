import java.util.Objects;

public class FlagCont {
    public boolean register;
    public boolean uniq;
    public boolean repeatNum;
    public boolean ignoreIntb = false;
    public int ignoreInt = 0;
    public boolean outputFile = false;
    public String outputFileS = "";
    public String inputFileS = "";

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
