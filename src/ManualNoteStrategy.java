// Purpose: Creates note content from direct user text input.
import java.util.Scanner;

public class ManualNoteStrategy implements NoteCreationStrategy {
    @Override
    public NoteContentData createContent(Scanner scanner) {
        System.out.print("Enter note content: ");
        String content = scanner.nextLine();
        return new NoteContentData(content, null, "txt");
    }
}
