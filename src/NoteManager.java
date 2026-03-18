// Purpose: Singleton service that manages users, shared notes, and note operations.
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NoteManager {
    private static NoteManager instance;
    private final Map<String, User> users;
    private final List<Note> sharedNotes;
    private final List<NoteObserver> observers;
    private final NoteFactory noteFactory;
    private int noteIdCounter;

    private NoteManager() {
        users = new HashMap<>();
        sharedNotes = new ArrayList<>();
        observers = new ArrayList<>();
        noteFactory = new NoteFactory();
        noteIdCounter = 1;
    }

    public static NoteManager getInstance() {
        if (instance == null) {
            instance = new NoteManager();
        }
        return instance;
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        return true;
    }

    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            return null;
        }
        if (!user.checkPassword(password)) {
            return null;
        }
        return user;
    }

    public Note addNote(User user, String subject, String title, NoteContentData noteContentData) {
        Note note = noteFactory.createNote(
                noteIdCounter++,
                title,
                subject,
                user.getUsername(),
                noteContentData.getContent(),
                noteContentData.getFilePath(),
                noteContentData.getFileType()
        );
        sharedNotes.add(note);
        notifyObservers("Added", user, note);
        return note;
    }

    public List<String> getSubjects() {
        Set<String> subjects = new LinkedHashSet<>();
        for (Note note : sharedNotes) {
            subjects.add(note.getSubject());
        }
        return new ArrayList<>(subjects);
    }

    public List<Note> getNotesBySubject(String subject) {
        List<Note> result = new ArrayList<>();
        for (Note note : sharedNotes) {
            if (note.getSubject().equalsIgnoreCase(subject)) {
                result.add(note);
            }
        }
        return result;
    }

    public int deleteNote(User user, int noteId) {
        Iterator<Note> iterator = sharedNotes.iterator();
        while (iterator.hasNext()) {
            Note note = iterator.next();
            if (note.getId() == noteId) {
                if (!note.getUploaderName().equals(user.getUsername())) {
                    return 2;
                }
                iterator.remove();
                notifyObservers("Deleted", user, note);
                return 1;
            }
        }
        return 0;
    }

    public void addObserver(NoteObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers(String action, User user, Note note) {
        for (NoteObserver observer : observers) {
            observer.update(action, user, note);
        }
    }
}
