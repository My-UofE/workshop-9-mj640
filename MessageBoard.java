import java.util.*;
import java.time.LocalDate;
import java.io.*;
import java.time.format.DateTimeFormatter;


public class MessageBoard implements Serializable {
    private List<Post> posts;
    private String boardName;

    public MessageBoard(String boardName) {
        this.boardName = boardName;
        this.posts = new ArrayList<>();
    }

    public int[] getPostIDs() {
        int[] ids = new int[posts.size()];

        for (int i = 0; i < posts.size(); i++) {
            ids[i] = posts.get(i).getPostIDs;
        }

        return ids;
    }

    public int addPost(String author, String subject, String message) throws IllegalArgumentException {
        if (author == null || author.isEmpty()
            || subject == null || subject.isEmpty()
            || message == null || message.isEmpty) {
                throw new IllegalArgumentException("Author, subject, and message must not be empty.")
            }
        
        Post newPost = new Post(author, subject, message);
        posts.add(newPost);
        
        return newPost.getPostID;
    }

    public void deletePost(int postID) throws IDInvalidException {
        // searches for the post to be deleted
        Post postToDelete = null;

        for (Post p : posts) {
            if (p.getPostID() == postID) {
                postToDelete = p;
                break;
            }
        }

        // checks if the post was found
        if (postToDelete == null) {
            throw new IDInvalidException("Post " + postID + " could not be found.")
        }

        // if post to be deleted is found
        posts.remove(postToDelete);   
    }

    public int[] searchPostsBySubject(String keyword) {
        // creates an empty array to store all the filtered posts
        ArrayList<Post> filteredPosts = new ArrayList<>();

        // search through posts
        for (Post p : posts) {
            String subject = p.getSubject().toLowerCase();
            if (subject.contains(keyword.toLowerCase())) {
                filteredPosts.add(p);
            }
        }

        // save all the post IDs of the filtered posts
        int[] IDs = new int[filteredPosts.size()];

        for (int i = 0; i < filteredPosts.size(); i++) {
            IDs[i] = filteredPosts.get(i).getPostID();
        }

        // returns an array of post IDs that match the search criteria
        return IDs;
    }

    public int[] searchPostsByDate(int startDate, int endDate) {
        // creates an empty array to store all the filtered posts
        ArrayList<Post> filteredPosts = new ArrayList<>();

        // search through posts
        for (Post p : posts) {
            if (p.getDate() >= startDate && p.getDate() <= endDate) {
                filteredPosts.add(p)
            }
        }

        // save all the post IDs of the filtered posts
        int[] IDs = new int[filteredPosts.size()];

        for (int i = 0; i < filteredPosts.size(); i++) {
            IDs[i] = filteredPosts.get(i).getPostID();
        }

        // returns an array of post IDs that match the search criteria
        return IDs;
    }

    public String getFormattedPost(int postID) throws IDInvalidException {
        Post post = null;

        // finds the post using the postID argument
        for (Post p : posts) {
            if (p.getPostID == postID) {
                post = p;
                break;
            }
        }

        // checks if the post was found
        if (post == null) {
            throw new IDInvalidException("Post " + postID + " does not exist.")
        }

        // gets formatted post
        String formattedPost = post.toFormattedString;
        
        // returns the formatted post
        return formattedPost;
    }

    public void saveMessageBoard(String filename) throws IOException {
        // saves each post in a file
        for (Post p : posts) {
            p.saveAsTextFile(filename);
        }
    }

    public void loadMessageBoard(String filename) throws IOException {
        posts.clear(); // Clear current posts
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = in.readLine()) != null) {
            // Parse the line to extract post details (similar to previous parsing logic)
            String[] parts = line.substring(line.indexOf('[') + 1, line.indexOf(']')).split(", (?=\\w+=");
            String author = parts[1].split("=")[1].replaceAll("^\"|\"$", "");
            String subject = parts[2].split("=")[1].replaceAll("^\"|\"$", "");
            String message = parts[3].split("=")[1].replaceAll("^\"|\"$", "").replace("\\n", "\n");
            int date = Integer.parseInt(parts[4].split("=")[1]);
            LocalDate postDate = LocalDate.ofEpochDay(date);
            Post post = new Post(author, subject, message, postDate);
            posts.add(post);
        }
        in.close();
    }

    public void savePostAsTextFile(int postID, String filename) throws IDInvalidException, IOException {
        Post postToSave = null;

        // finds the post using the postID argument
        for (Post p : posts) {
            if (p.getPostID == postID) {
                postToSave = p;
                break;
            }
        }

        // checks if the post was found
        if (postToSave == null) {
            throw new IDInvalidException("Post " + postID + " could not be found.")
        }
    }
}