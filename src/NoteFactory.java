// Purpose: Factory for creating Note objects in a centralized way.
public class NoteFactory {
    public Note createNote(int id, String title, String subject, String uploaderName, String content, String filePath, String fileType) {
        return new Note(id, title, subject, uploaderName, content, filePath, fileType);
    }
}
