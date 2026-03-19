import messageboard.*;

public class TestMBUIApp {
    public static void main(String[] args) {
        MessageBoardUI mb = new MessageBoardUI();
        
        String n,s,m;
        n = "Eric";
        s = "Java IDE";
        m = "Can someone recommend a Java IDE?";
        mb.board.addPost(n,s,m);

        n = "Freya";
        s = "Java IDE";
        m = "I only code in C++ and I don't use an IDE :(";
        mb.board.addPost(n,s,m);

        n = "Gaz";
        s = "How to print";
        m = "How do you print in Java... print does not work!";
        mb.board.addPost(n,s,m);

        n = "Helen";
        s = "How to print";
        m = "System.out.println(\"Garth.... Have you tried Google?\")";
        mb.board.addPost(n,s,m);

        mb.mainMenu();
    }
    
}
