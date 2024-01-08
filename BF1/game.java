import java.io.File;

public class game {
    public static void main(String[] args) {
        String outPutFileName = "res1";
        String fileName = "Result\\"+ outPutFileName +".txt";
        File file = new File(fileName);
        //file.delete();
        long startTime = System.currentTimeMillis(); 
        for (int i = 0; i < 1000; i++) {
            BeeFarming.run(new String[]{outPutFileName});
        }
        long endTime   = System.currentTimeMillis(); 
        long TotalTime = endTime - startTime;       
        System.out.println("time: " + TotalTime / 1000f + "s");
        System.exit(0);
    }
}
