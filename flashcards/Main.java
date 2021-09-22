package flashcards;

public class Main {

    public static void main(String[] args) {
        IOSystem io = new IOSystem();
        CardsStore cardsStore = new CardsStore(io);
        String importFileName;
        String exportFileName = null;

        boolean exit = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-import")) {
                importFileName = args[i + 1];
                cardsStore.importFromFile(importFileName);
            } else if (args[i].equals("-export")) {
                exportFileName = args[i + 1];
            }
        }

        while (!exit) {
            io.output("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            String action = io.input();
            String fileName;

            switch (action) {
                case "add":
                    cardsStore.addCard();
                    break;
                case "remove":
                    cardsStore.removeCard();
                    break;
                case "import":
                    io.output("File name:");
                    fileName = io.input();
                    cardsStore.importFromFile(fileName);
                    break;
                case "export":
                    io.output("File name:");
                    fileName = io.input();
                    cardsStore.exportToFile(fileName);
                    break;
                case "ask":
                    cardsStore.ask();
                    break;
                case "hardest card":
                    cardsStore.showHardestCards();
                    break;
                case "log":
                    io.saveLog();
                    break;
                case "reset stats":
                    cardsStore.resetStats();
                    break;
                case "exit":
                    exit = true;
                    io.output("Bye bye!");
                    if (exportFileName != null) {
                        cardsStore.exportToFile(exportFileName);
                    }
                    break;
            }
            io.output("");
        }
    }
}
