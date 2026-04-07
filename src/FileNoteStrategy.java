// Strategy Pattern: concrete strategy for file-based note content.
public class FileNoteStrategy implements NoteCreationStrategy {
    private final String filePath;

    public FileNoteStrategy(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public NoteContentData createContent() {
        String lower = filePath.toLowerCase();
        String fileType = lower.endsWith(".pdf") ? "pdf" : "txt";
        String content = fileType.equals("txt") ? "Content loaded from: " + filePath : null;
        return new NoteContentData(content, filePath, fileType);
    }
}
