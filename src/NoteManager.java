import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NoteManager {
    // Singleton Pattern: one shared instance for the full app.
    private static NoteManager instance;

    private final List<Note> notes;
    private final List<NoteObserver> observers;

    // Factory Pattern: centralized note construction.
    private final NoteFactory noteFactory;
    private int nextId;

    private NoteManager() {
        notes = new ArrayList<>();
        observers = new ArrayList<>();
        noteFactory = new NoteFactory();
        nextId = 1;
    }

    public static NoteManager getInstance() {
        if (instance == null) {
            instance = new NoteManager();
        }
        return instance;
    }

    public void addObserver(NoteObserver observer) {
        observers.add(observer);
    }

    public Note addNote(User user, String subject, String title, NoteContentData data) {
        Note note = noteFactory.createNote(
                nextId++,
                title,
                subject,
                user.getUsername(),
                data.getContent(),
                data.getFilePath(),
                data.getFileType()
        );
        notes.add(note);
        notifyObservers("Added", note);
        return note;
    }

    public boolean changeNoteState(User user, int noteId, NoteState nextState) {
        Note note = findById(noteId);
        if (note == null || !note.isOwner(user)) {
            return false;
        }
        note.changeState(nextState);
        notifyObservers("State changed to " + note.getStateName(), note);
        return true;
    }

    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }

    public List<String> getSubjects() {
        Set<String> subjects = new LinkedHashSet<>();
        for (Note note : notes) {
            subjects.add(note.getSubject());
        }
        return new ArrayList<>(subjects);
    }

    public List<Note> getNotesBySubject(String subject) {
        List<Note> result = new ArrayList<>();
        for (Note note : notes) {
            if (note.getSubject().equalsIgnoreCase(subject)) {
                result.add(note);
            }
        }
        return result;
    }

    private Note findById(int noteId) {
        for (Note note : notes) {
            if (note.getId() == noteId) {
                return note;
            }
        }
        return null;
    }

    private void notifyObservers(String action, Note note) {
        for (NoteObserver observer : observers) {
            observer.update(action, note);
        }
    }
}
