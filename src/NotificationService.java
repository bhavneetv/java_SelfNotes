// Observer Pattern: concrete observer that prints notifications.
public class NotificationService implements NoteObserver {
    @Override
    public void update(String action, Note note) {
        System.out.println("Notification: " + action + " | " + note.getTitle() + " | state: " + note.getStateName());
    }
}
