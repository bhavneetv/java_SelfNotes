// Purpose: Holds and executes the selected note creation strategy at runtime.
import java.util.Scanner;

public class NoteCreatorContext {
    private NoteCreationStrategy strategy;

    public void setStrategy(NoteCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public NoteContentData createContent(Scanner scanner) {
        if (strategy == null) {
            return null;
        }
        return strategy.createContent(scanner);
    }
}
