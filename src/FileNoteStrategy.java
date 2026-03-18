// Purpose: Creates note data by reading supported files from a path.
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class FileNoteStrategy implements NoteCreationStrategy {
    @Override
    public NoteContentData createContent(Scanner scanner) {
        System.out.print("Enter file path: ");
        String filePathInput = scanner.nextLine();
        try {
            Path filePath = Path.of(filePathInput);
            if (!Files.exists(filePath)) {
                System.out.println("File not found.");
                return null;
            }

            String fileName = filePath.getFileName().toString().toLowerCase();
            if (fileName.endsWith(".txt")) {
                String content = Files.readString(filePath);
                return new NoteContentData(content, filePath.toString(), "txt");
            }

            if (fileName.endsWith(".pdf")) {
                return new NoteContentData(null, filePath.toString(), "pdf");
            }

            System.out.println("Only .txt and .pdf files are supported.");
            return null;
        } catch (IOException e) {
            System.out.println("Unable to read file: " + e.getMessage());
            return null;
        }
    }
}
