import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SourceLoader {
    public static void load(Path path) throws IOException {
        // read file
        List<String> sentences = Files.readAllLines(path);

        // initialize maps
        Word[][] wordMap = new Word[sentences.size()][];
        HashMap<Integer, ArrayList<Position>> positionsMap = new HashMap<>();

        // split sentences/words
        for (int i = 0; i < sentences.size(); i++) {
            String[] words = sentences.get(i).split(" ");

            wordMap[i] = new Word[words.length];
            for (int j = 0; j < words.length; j++) {
                wordMap[i][j] = splitWord(words[j]);
                addPosition(wordMap[i][j].length(), new Position(i, j), positionsMap);
            }
        }

        // set sources
        Sources.wordMap = wordMap;

        int maxPos = getMaxPos(positionsMap);
        Sources.map = new Position[maxPos + 1][];
        for (int length : positionsMap.keySet()) {
            Sources.map[length] = positionsMap.get(length).toArray(Position[]::new);
        }
    }

    private static int getMaxPos(HashMap<Integer, ArrayList<Position>> positionsMap) {
        int max = 0;
        for (int length : positionsMap.keySet()) {
            max = Math.max(max, length);
        }
        return max;
    }


    private static void addPosition(int length, Position position, HashMap<Integer, ArrayList<Position>> positionsMap) {
        if (!positionsMap.containsKey(length))
            positionsMap.put(length, new ArrayList<>());
        positionsMap.get(length).add(position);
    }

    private static Word splitWord(String word) {
        StringBuilder src = new StringBuilder();

        // remove signs
        for (int i = 0; i < word.length(); i++) {
            if (!Sources.isSign(word.charAt(i))) {
                src.append(word.charAt(i));
            }
        }

        // initialize word
        Word outWord = new Word(src.toString());
        outWord.format = word;

        // remove prefix&suffix
        removePrefix(outWord);
        removeSuffix(outWord);

        return outWord;
    }

    public static void removePrefix(Word word) {
        if (word.length() <= 3)
            return;
        for (String prefix : Sources.prefixLetters.keySet()) {
            if (word.src.startsWith(prefix) && word.length() - prefix.length() >= 3) {
                word.prefix = prefix;
                word.src = word.src.substring(prefix.length());
                word.format = removePrefix(word.format, prefix);
                return;
            }
        }
    }

    private static String removePrefix(String format, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            format = format.substring(1);
            while (Sources.isSign(format.charAt(0)))
                format = format.substring(1);
        }
        return format;
    }

    public static void removeSuffix(Word word) {
        if (word.length() <= 3)
            return;
        for (String suffix : Sources.suffixLetters.keySet()) {
            if (word.src.endsWith(suffix) && word.length() - suffix.length() >= 3) {
                word.suffix = suffix;
                word.src = word.src.substring(0, word.length() - suffix.length());
                word.format = removeSuffix(word.format, suffix);
                return;
            }
        }

    }

    private static String removeSuffix(String format, String suffix) {
        for (int i = suffix.length()-1; i >= 0; i--) {
            while (Sources.isSign(format.charAt(format.length()-1)))
                format = format.substring(0, format.length()-1);
            format = format.substring(0, format.length()-1);
        }
        return format;
    }
}