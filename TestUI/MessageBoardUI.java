import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import messageboard.*;

public class MessageBoardUI  {

    private Scanner scanner;
    public MessageBoard board;
    
    public MessageBoardUI() {
        scanner = new Scanner(System.in);
        String boardName = null;

        while (boardName == null || boardName.isBlank()) {
            System.out.print("Enter a name for the board e.g. Java Q&A: ");
            boardName = scanner.nextLine();
        }
        board = new MessageBoard(boardName);
    }

    public void displayPost(int PostID){
        System.out.println(board.getFormattedPost(PostID));
    }

    public static int dateInput(String userInput) {
        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(userInput, dateFormat);
            return (int)date.toEpochDay(); 
        }
        catch (java.time.format.DateTimeParseException ex) {
            System.out.println("Invalid date!");
        }
        return -1;
        }

    public int getChoice(int min, int max) {
        int choice = -1;
        while (choice == -1) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                scanner.nextLine();
                choice = -1;
                System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.nextLine();
        return choice;
    }   


    public void mainMenu() {
        int choice = -1;
        while (choice != 4) {
            System.out.println("\n************** MAIN MENU: ******************");
            System.out.println("\nWelcome to the " + board.getBoardName() + " MesageBoard!");
            System.out.println("\nWhat would you like to do?\n");
            System.out.println("**** Options: ******************************");

            System.out.println("1. View/Search posts");
            System.out.println("2. Add post");
            System.out.println("3. Backup/Restore");
            System.out.println("4. Exit");

            choice = getChoice(1,4);

            switch (choice) {
                case 1: viewSearchMenu();  break;
                case 2: addPostMenu();     break;
                case 3: backupRestoreMenu(); break;
                case 4: System.out.println("Goodbye!"); break;
                default: System.out.println("Not a valid choice.");
            }
        }
        return;
    }

    public void addPostMenu() {
        String author="";
        String subject="";
        String message="";

        while (author == null || author.isBlank()) {
            System.out.print("Enter author: ");
            author = scanner.nextLine();
        }

        while (subject == null || subject.isBlank()) {
            System.out.print("Enter subject: ");
            subject = scanner.nextLine();
        }

        while (message == null || message.isBlank()) {
            System.out.println("Enter message (end with empty line): ");
            message = "";
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                message += line+"\n";
            }
            message = message.substring(0, message.length() - 1);
        }        

        System.out.println("\n************** Your Post: **********************");
        System.out.println("Author: " + author);
        System.out.println("Subject: " + subject);
        System.out.println("-------------------Message:-----------------------\n" + message);
        System.out.println("*** Options: ************************************");

        System.out.println("1. Post 2; Cancel;");
        
        int choice = getChoice(1,2);

        if (choice == 2) return; 

        try {
            board.addPost(author, subject, message);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid post. Could not add to message board.");
        }
    }  

    public void viewSearchMenu() {
        int choice = -1;
        while (choice != 4) {
            System.out.println("\n****************** VIEW POSTS *******************");
            System.out.println("\nWhat would you like to do?\n");
            System.out.println("***** Options: ***********************************");
            System.out.println("1. View all posts");
            System.out.println("2. Search posts by subject");
            System.out.println("3. Search posts by date");
            System.out.println("4. Back to main menu");

            choice = getChoice(1,4);
            if (choice == 4) return;

            else if (choice == 1) postViewer(board.getPostIDs(), 0, "All posts");
            else if (choice == 2) {
                String searchTerm = "";
                while (searchTerm == null || searchTerm.isBlank()) {
                    System.out.print("Enter subject keyword: ");
                    searchTerm = scanner.nextLine();
                }
                postViewer(board.searchPostsBySubject(searchTerm), 0, "posts with subject keyword(s):"+searchTerm);
            }
            else if (choice == 3) {
                int startDate = -1;
                int endDate = -1;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                while (startDate == -1) {
                    System.out.print("Enter start date (dd/mm/yyyy): ");
                    startDate = dateInput(scanner.nextLine());
                }
                while (endDate == -1) {
                    System.out.print("Enter end date (dd/mm/yyyy): ");
                    endDate = dateInput(scanner.nextLine());
                }
                postViewer(board.searchPostsByDate(startDate,endDate), 0, "posts in date range: "+
                    LocalDate.ofEpochDay(startDate).format(formatter)+"-"+LocalDate.ofEpochDay(endDate).format(formatter));
            }
        }
    }

    public void postViewer(int[] postIDs, int index, String query) {
        
        if (postIDs.length==0) {
            System.out.println("***********************************");
            System.out.println("\nNo posts!\n");
            System.out.println("Press enter to return to Main Menu.\n");           
            System.out.println("***********************************");
            scanner.nextLine();
            return;
        }

        int choice = -1;
        while (choice != 4) {
            System.out.println("\n******************* VIEW POST *******************");
            displayPost(postIDs[index]);
            System.out.println("*************************************************");
            System.out.println("Viewing "+query);
            System.out.println("Post " + (index+1) + " of " + postIDs.length); ;
            System.out.println("**** Options: ************************************");
            System.out.println("1. Forwards; 2. Backwards;");
            System.out.println("3. Export post to file; 4. Back to menu;");
            System.out.println("*************************************************");
        
            choice = getChoice(1,4);

            if (choice == 4) return;

            switch (choice) {
                case 1: if (index+1 < postIDs.length) { index++; } break;
                case 2: if (index > 0) { index--; } break;
                case 3: savePostAsTextFile(postIDs[index]); break;
                default: ;
            }
        }
    }
    


    public void backupRestoreMenu() {

        System.out.println("\n**************** BACKUP & RESTORE ****************");
        System.out.println("\nWhat would you like to do?\n");
        System.out.println("**** Options: ************************************");
        System.out.println("1. Backup message board");
        System.out.println("2. Restore message board");
        System.out.println("3. Go Back");

        int choice = getChoice(1,3);

        switch (choice) {
            case 1:  saveMessageBoard(); break;
            case 2:  loadMessageBoard(); break;
            case 3: return;
            default: ;
        }     
        
        return;
    }

    public void saveMessageBoard() {
        try { board.saveMessageBoard("backup.ser");
              System.out.print("Backup completed. Press enter to continue.");
              scanner.nextLine(); 
            } 
        catch (IOException ex) {ex.printStackTrace();}
    }

    public void loadMessageBoard(){
        try { board.loadMessageBoard("backup.ser"); 
            System.out.print("Backup loaded. Press enter to continue.");
            scanner.nextLine(); 
            }
        catch (IOException ex) {ex.printStackTrace();}
        catch (ClassNotFoundException ex) {ex.printStackTrace();}
    }

    public void savePostAsTextFile(int postID){
        String filename="";
        while (filename == null || filename.isBlank()) {
            System.out.print("Enter filename: ");
            filename = scanner.nextLine();
        }
        try {
            board.savePostAsTextFile(postID, filename);
            System.out.print("Saved file. Press enter to continue.");
            scanner.nextLine();

        } catch (IOException ex) {ex.printStackTrace();} 
    }


}