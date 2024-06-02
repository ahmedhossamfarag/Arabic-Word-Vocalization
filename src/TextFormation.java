public class TextFormation {

    public static String format(String text){
        String[] words = text.split(" ");
        Word prevWord = null;
        StringBuilder textBuilder = new StringBuilder();
        for (String s : words) {
            Word word = new Word(s);
            word.prevWord = prevWord;
            Formation.format(word);
            textBuilder.append(word.format);
            textBuilder.append(' ');
            prevWord = word;
        }
        return textBuilder.toString();
    }

}
