public class Note {
    private final int id;
    private final String title;
    private final String subject;
    private final String uploaderName;
    private final String content;
    private final String filePath;
    private final String fileType;

    // State Pattern: each Note has a state object.
    private NoteState state;

    public Note(int id, String title, String subject, String uploaderName, String content, String filePath, String fileType) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.uploaderName = uploaderName;
        this.content = content;
        this.filePath = filePath;
        this.fileType = fileType;
        this.state = new DraftState();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getUploaderName() {
        return uploaderName;
    }

    public String getFileType() {
        return fileType;
    }

    public boolean isOwner(User user) {
        return user != null && uploaderName.equals(user.getUsername());
    }

    public void changeState(NoteState nextState) {
        if (nextState != null) {
            state = nextState;
        }
    }

    public String getStateName() {
        return state.getName();
    }

    public String read(User viewer) {
        return state.read(this, viewer);
    }

    public String getDisplayContent() {
        if ("pdf".equalsIgnoreCase(fileType)) {
            return "PDF path: " + filePath;
        }
        return content;
    }
}
