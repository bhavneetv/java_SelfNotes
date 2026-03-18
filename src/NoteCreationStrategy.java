// Purpose: Strategy interface for different note content creation methods.
import java.util.Scanner;

public interface NoteCreationStrategy {
    NoteContentData createContent(Scanner scanner);
}
