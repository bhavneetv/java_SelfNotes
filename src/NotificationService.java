// Purpose: Observer implementation that prints note activity notifications.
public class NotificationService implements NoteObserver {
    @Override
    public void update(String action, User user, Note note) {
        System.out.println(
                "Notification: "
                        + action
                        + " note '"
                        + note.getTitle()
                        + "' in subject "
                        + note.getSubject()
                        + " by "
                        + note.getUploaderName()
        );
    }
}
