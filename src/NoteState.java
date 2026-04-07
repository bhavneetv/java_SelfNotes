// State Pattern: interface for note state behavior.
public interface NoteState {
    String getName();
    String read(Note note, User viewer);
}
