import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    // Singleton Pattern
    private static final NoteManager manager = NoteManager.getInstance();
    // Strategy Pattern
    private static final NoteCreatorContext context = new NoteCreatorContext();
    private static final User currentUser = new User("student1");

    public static void main(String[] args) {
        // Observer Pattern
        manager.addObserver(new NotificationService());

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt();

            switch (choice) {
                case 1:
                    uploadNote();
                    break;
                case 2:
                    viewNotesBySubject();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting SelfNotes.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("=== SelfNotes (Simple) ===");
        System.out.println("Logged in as: " + currentUser.getUsername());
        System.out.println("1. Upload Note");
        System.out.println("2. View Notes By Subject");
        System.out.println("3. Exit");
        System.out.print("Choose option: ");
    }

    private static void uploadNote() {
        System.out.print("Enter subject: ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) {
            System.out.println("Subject cannot be empty.");
            return;
        }

        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.println("Choose input type:");
        System.out.println("1. Manual text");
        System.out.println("2. File path");
        System.out.print("Choose option: ");
        int inputType = readInt();

        if (inputType == 1) {
            System.out.print("Enter note text: ");
            String text = scanner.nextLine();
            context.setStrategy(new ManualNoteStrategy(text));
        } else if (inputType == 2) {
            System.out.print("Enter file path (.txt/.pdf): ");
            String filePath = scanner.nextLine();
            context.setStrategy(new FileNoteStrategy(filePath));
        } else {
            System.out.println("Invalid input type.");
            return;
        }

        Note note = manager.addNote(currentUser, subject, title, context.createContent());

        // State Pattern
        System.out.println("Set note state:");
        System.out.println("1. Draft");
        System.out.println("2. Published");
        System.out.println("3. Archived");
        System.out.print("Choose option: ");
        int stateChoice = readInt();

        if (stateChoice == 2) {
            manager.changeNoteState(currentUser, note.getId(), new PublishedState());
        } else if (stateChoice == 3) {
            manager.changeNoteState(currentUser, note.getId(), new ArchivedState());
        }

        System.out.println("Note uploaded successfully.");
    }

    private static void viewNotesBySubject() {
        List<String> subjects = manager.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }

        System.out.println("Available subjects:");
        for (int i = 0; i < subjects.size(); i++) {
            System.out.println((i + 1) + ". " + subjects.get(i));
        }
        System.out.print("Select subject number: ");
        int subjectChoice = readInt();

        if (subjectChoice < 1 || subjectChoice > subjects.size()) {
            System.out.println("Invalid subject.");
            return;
        }

        String selectedSubject = subjects.get(subjectChoice - 1);
        List<Note> notes = manager.getNotesBySubject(selectedSubject);
        if (notes.isEmpty()) {
            System.out.println("No notes in this subject.");
            return;
        }

        System.out.println();
        System.out.println("Notes in subject: " + selectedSubject);
        for (Note note : notes) {
            System.out.println(
                    note.getId()
                            + ". "
                            + note.getTitle()
                            + " | type: "
                            + note.getFileType()
                            + " | state: "
                            + note.getStateName()
            );
            System.out.println("   Content: " + note.read(currentUser));
        }
    }

    private static int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}
