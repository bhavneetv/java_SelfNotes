// Purpose: Handles console menus and user interaction for the SelfNotes app.
import java.util.List;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final NoteManager noteManager = NoteManager.getInstance();
    private static final NoteCreatorContext noteCreatorContext = new NoteCreatorContext();

    public static void main(String[] args) {
        noteManager.addObserver(new NotificationService());
        boolean running = true;

        while (running) {
            showMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
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

    private static void showMainMenu() {
        System.out.println();
        System.out.println("=== SelfNotes ===");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean registered = noteManager.registerUser(username, password);
        if (registered) {
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Username already exists.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = noteManager.loginUser(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Login successful.");
        userMenu(user);
    }

    private static void userMenu(User user) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println();
            System.out.println("=== Notes Menu ===");
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Delete Note");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = readInt();
            switch (choice) {
                case 1:
                    addNote(user);
                    break;
                case 2:
                    viewNotes(user);
                    break;
                case 3:
                    deleteNote(user);
                    break;
                case 4:
                    loggedIn = false;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void addNote(User user) {
        System.out.print("Enter subject (example: English, Physics): ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) {
            System.out.println("Subject cannot be empty.");
            return;
        }

        System.out.print("Enter note title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }

        System.out.println("Choose note creation method:");
        System.out.println("1. Type Text Note");
        System.out.println("2. Upload .txt or .pdf");
        System.out.print("Choose an option: ");
        int method = readInt();

        if (method == 1) {
            noteCreatorContext.setStrategy(new ManualNoteStrategy());
        } else if (method == 2) {
            noteCreatorContext.setStrategy(new FileNoteStrategy());
        } else {
            System.out.println("Invalid note creation method.");
            return;
        }

        NoteContentData contentData = noteCreatorContext.createContent(scanner);
        if (contentData == null) {
            System.out.println("Note was not created.");
            return;
        }

        noteManager.addNote(user, subject, title, contentData);
        System.out.println("Note shared successfully.");
    }

    private static void viewNotes(User user) {
        List<String> subjects = noteManager.getSubjects();
        if (subjects.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("Available subjects:");
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println((i + 1) + ". " + subjects.get(i));
            }
            System.out.println("0. Back");
            System.out.print("Select subject: ");

            int subjectChoice = readInt();
            if (subjectChoice == 0) {
                return;
            }
            if (subjectChoice < 1 || subjectChoice > subjects.size()) {
                System.out.println("Invalid subject option.");
                continue;
            }

            String selectedSubject = subjects.get(subjectChoice - 1);
            viewNotesBySubject(selectedSubject);
        }
    }

    private static void viewNotesBySubject(String subject) {
        List<Note> notes = noteManager.getNotesBySubject(subject);
        if (notes.isEmpty()) {
            System.out.println("No notes found for this subject.");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("Subject: " + subject);
            for (int i = 0; i < notes.size(); i++) {
                Note note = notes.get(i);
                System.out.println(
                        (i + 1)
                                + ". "
                                + note.getTitle()
                                + " | uploader: "
                                + note.getUploaderName()
                                + " | type: "
                                + note.getFileType()
                                + " | id: "
                                + note.getId()
                );
            }
            System.out.println("0. Back");
            System.out.print("Select note: ");

            int noteChoice = readInt();
            if (noteChoice == 0) {
                return;
            }
            if (noteChoice < 1 || noteChoice > notes.size()) {
                System.out.println("Invalid note option.");
                continue;
            }

            Note selectedNote = notes.get(noteChoice - 1);
            openNote(selectedNote);
        }
    }

    private static void openNote(Note note) {
        if ("pdf".equalsIgnoreCase(note.getFileType())) {
            if (note.getFilePath() == null || note.getFilePath().isEmpty()) {
                System.out.println("PDF path is missing.");
                return;
            }
            try {
                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Desktop open is not supported on this system.");
                    System.out.println("PDF path: " + note.getFilePath());
                    return;
                }
                Desktop.getDesktop().open(new File(note.getFilePath()));
                System.out.println("Opened PDF: " + note.getFilePath());
            } catch (IOException e) {
                System.out.println("Could not open PDF: " + e.getMessage());
            }
            return;
        }

        System.out.println("----------------------------------");
        System.out.println("Title: " + note.getTitle());
        System.out.println("Subject: " + note.getSubject());
        System.out.println("Uploader: " + note.getUploaderName());
        System.out.println("Content:");
        System.out.println(note.getContent());
        System.out.println("----------------------------------");
    }

    private static void deleteNote(User user) {
        System.out.print("Enter note id to delete: ");
        int noteId = readInt();

        int result = noteManager.deleteNote(user, noteId);
        if (result == 1) {
            System.out.println("Note deleted successfully.");
        } else if (result == 2) {
            System.out.println("You can only delete notes uploaded by your account.");
        } else {
            System.out.println("Note id not found.");
        }
    }

    private static int readInt() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
