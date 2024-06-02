public record Position(int sentence, int index) {
    public Position prev(){
        return new Position(sentence, index - 1);
    }
}
