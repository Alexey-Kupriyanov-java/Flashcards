package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardsStore {
    IOSystem io;
    private final List<Card> cards;

    public CardsStore(IOSystem io) {
        this.io = io;
        cards = new ArrayList<>();
    }

    public void addCard() {
        String term;
        String definition;

        io.output("The card:");
        term = io.input();
        if (findByTerm(term) != null) {
            io.output(String.format("The card \"%s\" already exists.\n", term));
            return;
        }

        io.output("The definition of the card:");
        definition = io.input();
        if (findByDefinition(definition) != null) {
            io.output(String.format("The definition \"%s\" already exists.\n", definition));
            return;
        }
        cards.add(new Card(term, definition));
        io.output(String.format("The pair (\"%s\":\"%s\") has been added.\n", term, definition));
    }

    public void removeCard() {
        String term;
        Card card;

        io.output("Which card?");
        term = io.input();

        card = findByTerm(term);
        if (cards.remove(card)) {
            io.output("The card has been removed.");
        } else {
            io.output(String.format("Can't remove \"%s\": there is no such card.", term));
        }
    }

    public void importFromFile(String fileName) {
        int count = 0;

        try (Scanner fs = new Scanner(new File(fileName))) {
            while (fs.hasNextLine()) {
                String term = fs.nextLine();
                String definition = fs.nextLine();
                int cntMistakes = Integer.parseInt(fs.nextLine());
                Card card = findByTerm(term);
                if (card == null) {
                    cards.add(new Card(term, definition, cntMistakes));
                } else {
                    card.setDefinition(definition);
                    card.setCntMistakes(cntMistakes);
                }
                count++;
            }
            io.output(count + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            io.output("File not found.");
        }
    }

    public void exportToFile(String fileName) {
        int count = 0;

        try (PrintWriter printer = new PrintWriter(fileName)) {
            for (Card card : cards) {
                printer.println(card.getTerm());
                printer.println(card.getDefinition());
                printer.println(card.getCntMistakes());
                count++;
            }
            io.output(count + " cards have been saved.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ask() {
        io.output("How many times to ask?");
        int count = Integer.parseInt(io.input());
        int index = (int) (Math.random() * cards.size());
        for (int i = 0; i < count; i++) {
            Card card = cards.get(index);
            io.output(String.format("Print the definition of \"%s\":", card.getTerm()));
            String answer = io.input();
            String output;

            if (answer.equals(card.getDefinition())) {
                output = "Correct!";
            } else {
                output = String.format("Wrong. The right answer is \"%s\"", card.getDefinition());
                card.incrementMistake();
                Card existingCard = findByDefinition(answer);
                if (existingCard != null) {
                    output += String.format(", but your definition is correct for \"%s\".", existingCard.getTerm());
                } else {
                    output += ".";
                }
            }
            io.output(output);
        }
    }

    public void showHardestCards() {
        List<Card> hardestCards = new ArrayList<>();
        int hardestCount = 0;
        for (Card card : cards) {
            if (card.getCntMistakes() > hardestCount) {
                hardestCards.clear();
                hardestCards.add(card);
                hardestCount = card.getCntMistakes();

            } else if (card.getCntMistakes() == hardestCount && hardestCount != 0) {
                hardestCards.add(card);
            }
        }

        if (hardestCards.size() == 0) {
            io.output("There are no cards with errors.");
        } else if (hardestCards.size() == 1) {
            io.output(String.format("The hardest card is \"%s\". You have %d errors answering it.",
                    hardestCards.get(0).getTerm(), hardestCount));
        } else {
            StringBuilder sb = new StringBuilder("The hardest cards are ");
            for (Card card : hardestCards) {
                sb.append("\"").
                        append(card.getTerm()).
                        append("\",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","))
                    .append(". You have ")
                    .append(hardestCount)
                    .append(" errors answering them");
            io.output(sb.toString());
        }
    }

    public void resetStats() {
        for (Card card : cards) {
            card.setCntMistakes(0);
        }
        io.output("Card statistics have been reset.");
    }

    private Card findByTerm(String term) {
        for (Card card : cards) {
            if (term.equals(card.getTerm())) {
                return card;
            }
        }
        return null;
    }

    private Card findByDefinition(String definition) {
        for (Card card : cards) {
            if (definition.equals(card.getDefinition())) {
                return card;
            }
        }
        return null;
    }
}
