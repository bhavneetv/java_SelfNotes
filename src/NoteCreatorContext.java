// Strategy Pattern: context that switches strategy at runtime.
public class NoteCreatorContext {
    private NoteCreationStrategy strategy;

    public void setStrategy(NoteCreationStrategy strategy) {
        this.strategy = strategy;
    }

    public NoteContentData createContent() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy is not set.");
        }
        return strategy.createContent();
    }
}
