import org.junit.Assert;
import org.junit.Test;
import java.io.*;

class UniqAppTest {
    public static String equalFiles(String expName) throws IOException {
        File file2 = new File (expName);
        FileReader fR = new FileReader(file2);
        BufferedReader bR = new BufferedReader(fR);
        String line;
        StringBuilder file = new StringBuilder();
        while((line = bR.readLine()) != null){
            file.append(line);
        }
        return file.toString();
    }
    @Test
    public static void main(String[] args) throws IOException {
        UniqApp.starter("-i;-c;-o;outTest;inTest".split(";"));
        Assert.assertEquals( equalFiles("outExp"),equalFiles("outTest"));
        UniqApp.starter("-u;-o;outTest2;inTest;".split(";"));
        Assert.assertEquals( equalFiles("outExp2"),equalFiles("outTest2"));
        UniqApp.starter("-c;-s;14;-o;outTest3;inTest;".split(";"));
        Assert.assertEquals( equalFiles("outExp3"),equalFiles("outTest3"));
    }

}
