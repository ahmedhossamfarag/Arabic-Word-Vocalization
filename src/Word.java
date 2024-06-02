public class Word {
    public String src;
    public String prefix;
    public String suffix;

    public String format;

    public Word prevWord;

    public Word(String src){
        this.src = src;
    }

    public int length(){
        return  src.length();
    }

    @Override
    public String toString() {
        return format;
    }
}
