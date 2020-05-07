import org.junit.Assert;
import org.junit.Test;
import java.io.*;

class UniqAppTest {
    public static String FileToStr(String expName) throws IOException {
        File file2 = new File(expName);
        FileReader fR = new FileReader(file2);
        BufferedReader bR = new BufferedReader(fR);
        String line;
        StringBuilder file = new StringBuilder();
        while ((line = bR.readLine()) != null) {
            file.append(line);
        }
        return file.toString();
    }

    @Test
    public static void main(String[] args) throws IOException {
        UniqApp.starter(new String[]{"-i", "-c", "-o", "outTest", "inTest"});
        Assert.assertEquals(FileToStr("outExp"), FileToStr("outTest"));
        UniqApp.starter(new String[]{"-u", "-o", "outTest2", "inTest"});
        Assert.assertEquals(FileToStr("outExp2"), FileToStr("outTest2"));
        UniqApp.starter(new String[]{"-c", "-s", "14", "-o", "outTest3", "inTest"});
        Assert.assertEquals(FileToStr("outExp3"), FileToStr("outTest3"));
    }

}
