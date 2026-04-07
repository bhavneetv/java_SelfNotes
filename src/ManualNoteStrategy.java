// Strategy Pattern: concrete strategy for typed/manual note content.
public class ManualNoteStrategy implements NoteCreationStrategy {
    private final String text;

    public ManualNoteStrategy(String text) {
        this.text = text;
    }

    @Override
    public NoteContentData createContent() {
        return new NoteContentData(text, null, "txt");
    }
}
