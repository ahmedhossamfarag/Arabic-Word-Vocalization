import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Sources.initialize();
        SourceLoader.load(Path.of("src/sourceSentences.txt"));
        String formatted = TextFormation.format("ضرب ضربه ضربهم قاتل يعمل تقاتل حتي يقرأ القلم");
        System.out.println(formatted);
    }
}