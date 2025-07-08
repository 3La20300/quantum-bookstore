/**
 * Quantum Bookstore System

 * @author Mostafa
 * @version 1.0
 */

import java.util.*;
import java.time.LocalDate;

// Abstract base class for all book types
abstract class Book {
    protected String isbn;
    protected String title;
    protected String author;
    protected int year;
    protected double price;
    
    public Book(String isbn, String title, String author, int year, double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
    
    // Abstract methods to be implemented by subclasses
    public abstract String getBookType();
    public abstract boolean isPurchasable();
    public abstract double processPurchase(int quantity, String email, String address) throws Exception;
    
    // Getters
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    
    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ") - ISBN: " + isbn;
    }
}

// Paper book implementation
class PaperBook extends Book {
    private int stock;
    
    public PaperBook(String isbn, String title, String author, int year, double price, int stock) {
        super(isbn, title, author, year, price);
        this.stock = stock;
    }
    
    @Override
    public String getBookType() {
        return "Paper Book";
    }
    
    @Override
    public boolean isPurchasable() {
        return stock > 0;
    }
    
    @Override
    public double processPurchase(int quantity, String email, String address) throws Exception {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (stock < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + stock + ", Requested: " + quantity);
        }
        
        stock -= quantity;
        double totalAmount = price * quantity;
        
        // Send to shipping service
        ShippingService.ship(this, quantity, address);
        
        System.out.println("Quantum book store: Paper book purchase processed. Remaining stock: " + stock);
        return totalAmount;
    }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

// EBook implementation
class EBook extends Book {
    private String fileType;
    
    public EBook(String isbn, String title, String author, int year, double price, String fileType) {
        super(isbn, title, author, year, price);
        this.fileType = fileType;
    }
    
    @Override
    public String getBookType() {
        return "EBook";
    }
    
    @Override
    public boolean isPurchasable() {
        return true; // EBooks are always available
    }
    
    @Override
    public double processPurchase(int quantity, String email, String address) throws Exception {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        double totalAmount = price * quantity;
        
        // Send to mail service
        MailService.sendEBook(this, quantity, email);
        
        System.out.println("Quantum book store: EBook purchase processed and sent to email: " + email);
        return totalAmount;
    }
    
    public String getFileType() { return fileType; }
}

// Showcase book implementation
class ShowcaseBook extends Book {
    
    public ShowcaseBook(String isbn, String title, String author, int year, double price) {
        super(isbn, title, author, year, price);
    }
    
    @Override
    public String getBookType() {
        return "Showcase/Demo Book";
    }
    
    @Override
    public boolean isPurchasable() {
        return false; // Showcase books are not for sale
    }
    
    @Override
    public double processPurchase(int quantity, String email, String address) throws Exception {
        throw new UnsupportedOperationException("Showcase books are not for sale");
    }
}

// Service classes (no implementation required as per requirements)
class ShippingService {
    public static void ship(PaperBook book, int quantity, String address) {
        System.out.println("Quantum book store: Shipping " + quantity + " copies of '" + 
                         book.getTitle() + "' to address: " + address);
    }
}

class MailService {
    public static void sendEBook(EBook book, int quantity, String email) {
        System.out.println("Quantum book store: Sending " + quantity + " copies of EBook '" + 
                         book.getTitle() + "' (" + book.getFileType() + ") to email: " + email);
    }
}

// Main bookstore class
class QuantumBookstore {
    private Map<String, Book> inventory;
    
    public QuantumBookstore() {
        this.inventory = new HashMap<>();
    }
    
    // Add a book to inventory
    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
        System.out.println("Quantum book store: Added " + book.getBookType() + " - " + book.toString());
    }
    
    // Remove and return outdated books
    public List<Book> removeOutdatedBooks(int yearsThreshold) {
        List<Book> removedBooks = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        
        Iterator<Map.Entry<String, Book>> iterator = inventory.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Book> entry = iterator.next();
            Book book = entry.getValue();
            
            if (currentYear - book.getYear() > yearsThreshold) {
                removedBooks.add(book);
                iterator.remove();
                System.out.println("Quantum book store: Removed outdated book - " + book.toString());
            }
        }
        
        return removedBooks;
    }
    
    // Buy a book
    public double buyBook(String isbn, int quantity, String email, String address) throws Exception {
        Book book = inventory.get(isbn);
        
        if (book == null) {
            throw new RuntimeException("Book with ISBN " + isbn + " not found in inventory");
        }
        
        if (!book.isPurchasable()) {
            throw new RuntimeException("Book " + book.getTitle() + " is not available for purchase");
        }
        
        double totalAmount = book.processPurchase(quantity, email, address);
        System.out.println("Quantum book store: Purchase completed. Total amount: $" + totalAmount);
        
        return totalAmount;
    }
    
    // Get book by ISBN
    public Book getBook(String isbn) {
        return inventory.get(isbn);
    }
    
    // Get all books
    public Collection<Book> getAllBooks() {
        return inventory.values();
    }
    
    // Display inventory
    public void displayInventory() {
        System.out.println("Quantum book store: Current Inventory:");
        for (Book book : inventory.values()) {
            String stockInfo = "";
            if (book instanceof PaperBook) {
                stockInfo = " (Stock: " + ((PaperBook) book).getStock() + ")";
            } else if (book instanceof EBook) {
                stockInfo = " (FileType: " + ((EBook) book).getFileType() + ")";
            }
            System.out.println("  " + book.getBookType() + " - " + book.toString() + stockInfo);
        }
    }
}

// Test class
class QuantumBookstoreFullTest {
    public static void main(String[] args) {
        QuantumBookstore bookstore = new QuantumBookstore();
        
        System.out.println("=== Quantum Bookstore Test Suite ===\n");
        
        // Test 1: Adding books
        System.out.println("1. Testing Adding Books:");
        PaperBook paperBook1 = new PaperBook("978-0134685991", "Effective Java", "Joshua Bloch", 2017, 45.99, 10);
        PaperBook paperBook2 = new PaperBook("978-0596009205", "Head First Design Patterns", "Eric Freeman", 2004, 39.99, 5);
        EBook ebook1 = new EBook("978-0321356680", "Effective Java Digital", "Joshua Bloch", 2017, 35.99, "PDF");
        EBook ebook2 = new EBook("978-0134757599", "Java Concurrency in Practice", "Brian Goetz", 2006, 42.99, "EPUB");
        ShowcaseBook showcase1 = new ShowcaseBook("978-0134494166", "Clean Code Demo", "Robert Martin", 2008, 0.00);
        
        bookstore.addBook(paperBook1);
        bookstore.addBook(paperBook2);
        bookstore.addBook(ebook1);
        bookstore.addBook(ebook2);
        bookstore.addBook(showcase1);
        
        System.out.println();
        bookstore.displayInventory();
        
        // Test 2: Buying books
        System.out.println("\n2. Testing Book Purchases:");
        try {
            // Buy paper book
            double amount1 = bookstore.buyBook("978-0134685991", 2, "customer@email.com", "123 Main St, City");
            System.out.println("Purchase amount: $" + amount1);
            
            // Buy ebook
            double amount2 = bookstore.buyBook("978-0321356680", 1, "customer@email.com", "123 Main St, City");
            System.out.println("Purchase amount: $" + amount2);
            
        } catch (Exception e) {
            System.out.println("Quantum book store: Error - " + e.getMessage());
        }
        
        // Test 3: Error handling
        System.out.println("\n3. Testing Error Handling:");
        try {
            // Try to buy showcase book
            bookstore.buyBook("978-0134494166", 1, "customer@email.com", "123 Main St, City");
        } catch (Exception e) {
            System.out.println("Quantum book store: Error - " + e.getMessage());
        }
        
        try {
            // Try to buy more than available stock
            bookstore.buyBook("978-0596009205", 10, "customer@email.com", "123 Main St, City");
        } catch (Exception e) {
            System.out.println("Quantum book store: Error - " + e.getMessage());
        }
        
        try {
            // Try to buy non-existent book
            bookstore.buyBook("978-9999999999", 1, "customer@email.com", "123 Main St, City");
        } catch (Exception e) {
            System.out.println("Quantum book store: Error - " + e.getMessage());
        }
        
        // Test 4: Removing outdated books
        System.out.println("\n4. Testing Outdated Book Removal:");
        List<Book> removedBooks = bookstore.removeOutdatedBooks(15); // Remove books older than 15 years
        System.out.println("Quantum book store: Removed " + removedBooks.size() + " outdated books");
        
        System.out.println("\n5. Final Inventory:");
        bookstore.displayInventory();
        
        // Test 5: Extensibility demonstration
        System.out.println("\n6. Demonstrating Extensibility:");
        // Example of how to add a new book type without modifying existing code
        AudioBook audioBook = new AudioBook("978-1234567890", "The Art of Programming", "Donald Knuth", 2020, 29.99, "MP3", 480);
        bookstore.addBook(audioBook);
        
        try {
            double amount = bookstore.buyBook("978-1234567890", 1, "customer@email.com", "123 Main St, City");
            System.out.println("Purchase amount: $" + amount);
        } catch (Exception e) {
            System.out.println("Quantum book store: Error - " + e.getMessage());
        }
        
        System.out.println("\nQuantum book store: Test suite completed successfully!");
    }
}

// Example of extensibility - new book type can be added without modifying existing code
class AudioBook extends Book {
    private String audioFormat;
    private int durationMinutes;
    
    public AudioBook(String isbn, String title, String author, int year, double price, String audioFormat, int durationMinutes) {
        super(isbn, title, author, year, price);
        this.audioFormat = audioFormat;
        this.durationMinutes = durationMinutes;
    }
    
    @Override
    public String getBookType() {
        return "Audio Book";
    }
    
    @Override
    public boolean isPurchasable() {
        return true;
    }
    
    @Override
    public double processPurchase(int quantity, String email, String address) throws Exception {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        
        double totalAmount = price * quantity;
        
        // Send to audio service (hypothetical)
        AudioService.sendAudioBook(this, quantity, email);
        
        System.out.println("Quantum book store: AudioBook purchase processed and sent to email: " + email);
        return totalAmount;
    }
    
    public String getAudioFormat() { return audioFormat; }
    public int getDurationMinutes() { return durationMinutes; }
}

// Hypothetical audio service for demonstration
class AudioService {
    public static void sendAudioBook(AudioBook book, int quantity, String email) {
        System.out.println("Quantum book store: Sending " + quantity + " copies of AudioBook '" + 
                         book.getTitle() + "' (" + book.getAudioFormat() + ", " + 
                         book.getDurationMinutes() + " minutes) to email: " + email);
    }
}