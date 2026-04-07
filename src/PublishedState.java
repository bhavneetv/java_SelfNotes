// State Pattern: published notes are visible to everyone.
public class PublishedState implements NoteState {
    @Override
    public String getName() {
        return "Published";
    }

    @Override
    public String read(Note note, User viewer) {
        return note.getDisplayContent();
    }
}
