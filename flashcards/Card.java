package flashcards;

public class Card {
    private final String term;
    private String definition;
    private int cntMistakes;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public Card(String term, String definition, int cntMistakes) {
        this.term = term;
        this.definition = definition;
        this.cntMistakes = cntMistakes;
    }

    public void incrementMistake() {
        cntMistakes++;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setCntMistakes(int cntMistakes) {
        this.cntMistakes = cntMistakes;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getCntMistakes() {
        return cntMistakes;
    }
}
