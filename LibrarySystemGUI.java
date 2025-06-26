import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Book Class
class Book {
    String title;
    boolean isIssued = false;

    Book(String title) {
        this.title = title;
    }

    public String toString() {
        return title + (isIssued ? " (Issued)" : " (Available)");
    }
}

// User Class
class User {
    String name;
    ArrayList<Book> borrowedBooks = new ArrayList<>();

    User(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

// Library Class
class Library {
    ArrayList<Book> books = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
    }

    void addUser(User user) {
        users.add(user);
    }

    void issueBook(Book book, User user) {
        if (!book.isIssued) {
            book.isIssued = true;
            user.borrowedBooks.add(book);
        }
    }

    void returnBook(Book book, User user) {
        if (book.isIssued) {
            book.isIssued = false;
            user.borrowedBooks.remove(book);
        }
    }
}

// GUI + Main Class
public class LibrarySystemGUI extends JFrame {
    DefaultListModel<Book> bookModel = new DefaultListModel<>();
    DefaultListModel<User> userModel = new DefaultListModel<>();
    JList<Book> bookList = new JList<>(bookModel);
    JList<User> userList = new JList<>(userModel);

    JTextField bookField = new JTextField(10);
    JTextField userField = new JTextField(10);

    Library library = new Library();

    public LibrarySystemGUI() {
        setTitle("Library Management");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panels
        JPanel top = new JPanel();
        top.add(new JLabel("Book:"));
        top.add(bookField);
        JButton addBook = new JButton("Add Book");
        top.add(addBook);

        top.add(new JLabel("User:"));
        top.add(userField);
        JButton addUser = new JButton("Add User");
        top.add(addUser);

        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2));
        center.add(new JScrollPane(bookList));
        center.add(new JScrollPane(userList));
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton issue = new JButton("Issue");
        JButton ret = new JButton("Return");
        bottom.add(issue);
        bottom.add(ret);
        add(bottom, BorderLayout.SOUTH);

        // Actions
        addBook.addActionListener(e -> {
            String title = bookField.getText().trim();
            if (!title.isEmpty()) {
                Book book = new Book(title);
                library.addBook(book);
                bookModel.addElement(book);
                bookField.setText("");
            }
        });

        addUser.addActionListener(e -> {
            String name = userField.getText().trim();
            if (!name.isEmpty()) {
                User user = new User(name);
                library.addUser(user);
                userModel.addElement(user);
                userField.setText("");
            }
        });

        issue.addActionListener(e -> {
            Book book = bookList.getSelectedValue();
            User user = userList.getSelectedValue();
            if (book != null && user != null && !book.isIssued) {
                library.issueBook(book, user);
                bookList.repaint();
            }
        });

        ret.addActionListener(e -> {
            Book book = bookList.getSelectedValue();
            User user = userList.getSelectedValue();
            if (book != null && user != null && book.isIssued) {
                library.returnBook(book, user);
                bookList.repaint();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySystemGUI().setVisible(true));
    }
}

