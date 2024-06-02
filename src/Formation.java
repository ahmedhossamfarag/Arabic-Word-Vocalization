import java.util.Arrays;
public class Formation {

    public static void format(Word word){
        // remove prefix & suffix
        removePrefix(word);
        removeSuffix(word);

        // get possible matches
        if (word.length() >= Sources.map.length)
            return;

        Position[] possibleMatches = Sources.map[word.length()];

        if (possibleMatches == null || possibleMatches.length == 0)
            return;

        // compute matches
        Match[] matches = new Match[possibleMatches.length];
        for (int i = 0; i < matches.length; i++) {
            matches[i] = match(word, possibleMatches[i]);
        }

        // keep top matches
        matches = keepTop(matches);

        // has no previous word
        if(word.prevWord == null){
            word.format = format(word, getWord(matches[0].position()));
            return;
        }

        // match previous word
        for(Match match: matches){
            if(match.position().index() > 0){
                Word prev = getWord(match.position().prev());
                if(matchPrev(word, prev)){
                    word.format = format(word, getWord(match.position()));
                    return;
                }
            }
        }

        // no prev match
        word.format = format(word, getWord(matches[0].position()));
    }

    private static String format(Word word, Word from) {
        String prefix = "", suffix = "";
        if(word.prefix != null)
            prefix = Sources.prefixLetters.get(word.prefix);
        if(word.suffix != null)
            suffix = Sources.suffixLetters.get(word.suffix);
        return prefix + format(word.src, from.format) + suffix;
    }

    private static String format(String src, String format) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            if(Sources.isSign(format.charAt(i))){
                formatted.append(format.charAt(i));
            }else{
                formatted.append(src.charAt(0));
                src = src.substring(1);
            }
        }
        return formatted.toString();
    }


    public static Word getWord(Position position){
        return Sources.wordMap[position.sentence()][position.index()];
    }

    public static Match[] keepTop(Match[] matches){
        int top = Math.min(3, matches.length);
        Arrays.sort(matches, (m1, m2) -> Double.compare(m2.value(), m1.value()));
        return Arrays.copyOfRange(matches, 0, top);
    }



    public static void removePrefix(Word word){
        if(word.length() <= 3)
            return;
        for (String prefix : Sources.prefixLetters.keySet()){
            if(word.src.startsWith(prefix) && word.length() - prefix.length() >= 3){
                word.prefix = prefix;
                word.src = word.src.substring(prefix.length());
                return;
            }
        }
    }

    public static void removeSuffix(Word word){
        if(word.length() <= 3)
            return;
        for (String suffix : Sources.suffixLetters.keySet()){
            if(word.src.endsWith(suffix) && word.length() - suffix.length() >= 3){
                word.suffix = suffix;
                word.src = word.src.substring(0, word.length() - suffix.length());
                return;
            }
        }
    }

    public static Match match(Word word, Position position){
        String src = word.src;
        String with = getWord(position).src;
        double value = 0;
        for (int i = 0; i < src.length(); i++) {
            value += match(src.charAt(i), with.charAt(i));
        }
        return new Match(position, value);
    }

    public static double match(char c1, char c2){
        if(c1 == c2)
            return 1;
        return 0.5;
    }


    private static boolean matchPrev(Word word, Word prev) {
        return word.prevWord.src.equals(prev.src);
    }
}
