// Purpose: Carries note content and file details produced by creation strategies.
public class NoteContentData {
    private final String content;
    private final String filePath;
    private final String fileType;

    public NoteContentData(String content, String filePath, String fileType) {
        this.content = content;
        this.filePath = filePath;
        this.fileType = fileType;
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
