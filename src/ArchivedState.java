// State Pattern: archived notes cannot be opened.
public class ArchivedState implements NoteState {
    @Override
    public String getName() {
        return "Archived";
    }

    @Override
    public String read(Note note, User viewer) {
        return "Archived note: reading is disabled.";
    }
}
