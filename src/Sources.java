import java.util.*;

public class Sources {
    public static char[] signs;
    public static HashMap<String, String> prefixLetters;
    public static HashMap<String, String> suffixLetters;

    public static Word[][] wordMap;

    public static Position[][] map;

    public static void initialize(){
        signs = new char[]{'\u064E', '\u064F', '\u0650','\u0651', '\u064B', '\u064C', '\u064D','\u0652'};
        prefixLetters = new HashMap<>();
        prefixLetters.put("ال","ال");
        suffixLetters = new HashMap<>();
        suffixLetters.put("ه","هُ");
        suffixLetters.put("ها","هَا");
        suffixLetters.put("هما","هُمَا");
        suffixLetters.put("هم","هُم");
        suffixLetters.put("هن","هُنّ");
    }

    public static boolean isSign(char c){
        return Arrays.binarySearch(signs, c) != -1;
    }
}
