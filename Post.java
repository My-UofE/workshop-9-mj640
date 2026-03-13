import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Post implements Serializable {

    // Attributes
    private static int idCounter = 0;
    private int postID;
    private String author;
    private String subject;
    private String message;
    private int date;

    public Post(String author, String subject, String message) {
        this(author, subject, message, null);
    }

    public Post(String author, String subject, String message, LocalDate date) {
        this.postID = ++idCounter;
        this.author = author;
        this.subject = subject;
        this.message = message;
        if (date == null) {
            this.date = (int)LocalDate.now().toEpochDay();
        } else {
            this.date = (int)date.toEpochDay();
        }
    }

    public String toString() {
        String result = String.format("Post[postID=%d, author=\"%s\", subject=\"%s\", message=\"%s\", date=%d]", 
                                postID, author, subject, message.replace("\n", "\\n"), date);
        return result;
    }

}