import java.io.*;
import java.time.LocalDate;

/**
 * The MessageBoardInterface provides methods for managing and interacting with a message board.
 * It allows adding, deleting, searching, displaying, and backing up posts.
 */
public interface MessageBoardInterface extends Serializable {

    /**
     * Retrieves an array of all post IDs on the message board.
     *
     * @return an array of integers representing the IDs of all posts
     *        on the message board or an empty array if there are no posts.
     */
    public int[] getPostIDs();

    /**
     * Adds a post to the message board with the specified author, subject, and message.
     * The current date will be used as the date of the post.
     *
     * @param author  the author of the post (must not be null or empty)
     * @param subject the subject of the post (must not be null or empty)
     * @param message the message content of the post (must not be null or empty)
     * @return the ID of the newly added post
     * @throws IllegalArgumentException if any of the parameters are invalid
     */
    public int addPost(String author, String subject, String message) throws IllegalArgumentException;

    /**
     * Deletes the post with the specified ID from the message board.
     *
     * @param postID the ID of the post to be deleted
     * @throws IDInvalidException if the post ID is invalid
     */
    public void deletePost(int postID) throws IDInvalidException;

    /**
     * Searches for posts with subjects that contain the specified keyword.
     *
     * @param keyword the keyword to search for in the subjects (case-insensitive)
     * @return an array of post IDs that match the search criteria
     */
    public int[] searchPostsBySubject(String keyword);

    /**
     * Searches for posts within the specified date range.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return an array of post IDs that match the search criteria 
     */
    public int[] searchPostsByDate(int startDate, int endDate);

    /**
     * Displays the formatted post with the specified ID.
     *
     * @param postID the ID of the post to be displayed
     * @throws IDInvalidException if the post ID is invalid
     */
    public String getFormattedPost(int postID) throws IDInvalidException;

    /**
     * Backs up the message board to a file.
     *
     * @throws IOException if an I/O error occurs during the backup process
     */
    public void saveMessageBoard(String filename) throws IOException;

    /**
     * Loads the message board state from a backup file.
     * 
     * The current contents of the message board will be replaced by the contents of the backup file.
     *
     * @throws IOException            if an I/O error occurs during the loading process
     * @throws ClassNotFoundException if the class of a serialized object cannot be found
     */
    public void loadMessageBoard(String filename) throws IOException, ClassNotFoundException;

    /**
     * Saves a post as formatted text to a file.
     *
     * @param postID   the ID of the post to save
     * @param filename the name of the file to save the messages to
     * @throws IDInvalidException if the post ID is invalid
     * @throws IOException if an I/O error occurs during the saving process
     */
    public void savePostAsTextFile(int postID, String filename)  throws IDInvalidException, IOException; 

}