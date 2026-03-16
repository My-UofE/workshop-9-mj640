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

    public String toFormattedString() {
        LocalDate postDate = LocalDate.ofEpochDay(this.date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String result = "\n------------------  Post " + postID + "  -------------------" + 
        "\nAuthor: " + author + 
        "\nDate: " + postDate.format(formatter) + 
        "\nSubject: " + subject + "\n" + 
        "----  Message:  -------------------------------\n" +  
        message + 
        "\n-----------------------------------------------\n";
        return result;
    }

    public void saveAsTextFile(String filename) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(filename)); // opens/creates the file
        out.write(this.toString()); // writes the formatted post string
        out.close(); // saves the file and closes it
    }

    // 'getter' methods
    public int getPostID() {
        return(postID);
    }

    public String getAuthor() {
        return(author);
    }

    public String getSubject() {
        return(subject);
    }

    public String getMessage() {
        return(message);
    }

    public int getDate() {
        return(date);
    }
}