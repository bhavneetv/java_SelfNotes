// State Pattern: draft notes are private to uploader.
public class DraftState implements NoteState {
    @Override
    public String getName() {
        return "Draft";
    }

    @Override
    public String read(Note note, User viewer) {
        if (!note.isOwner(viewer)) {
            return "Draft note: only uploader can read this note.";
        }
        return note.getDisplayContent();
    }
}
