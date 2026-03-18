// Purpose: Represents a shared note with subject, uploader, content, and file metadata.
public class Note {
    private final int id;
    private final String title;
    private final String subject;
    private final String uploaderName;
    private final String content;
    private final String filePath;
    private final String fileType;

    public Note(int id, String title, String subject, String uploaderName, String content, String filePath, String fileType) {
        this.id = id;
        this.title = title;
        this.subject = subject;
        this.uploaderName = uploaderName;
        this.content = content;
        this.filePath = filePath;
        this.fileType = fileType;
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

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }
}
