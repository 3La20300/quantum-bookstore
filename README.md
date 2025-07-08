# Quantum Bookstore

An extensible online bookstore system built in Java that supports multiple book types including paper books, eBooks, and showcase books. The system is designed with object-oriented principles to be easily extensible for new product types without modifying existing code.

## Features

- **Multiple Book Types**:
  - **Paper Books**: Physical books with stock management and shipping integration
  - **EBooks**: Digital books with file format support and email delivery
  - **Showcase Books**: Display-only books (not for sale)
  - **Extensible Design**: Easy to add new book types (see AudioBook example)

- **Inventory Management**:
  - Add books with ISBN, title, author, year, and price
  - Remove outdated books based on age threshold
  - Track stock levels for physical books

- **Purchase System**:
  - Buy books with quantity, email, and address
  - Automatic stock reduction and validation
  - Error handling for insufficient stock and invalid purchases
  - Integration with shipping and mail services

- **Extensibility**:
  - Abstract base class design allows new book types
  - Polymorphic purchase processing
  - Service layer for external integrations

## Architecture

The system uses object-oriented design principles:

- **Abstract Base Class**: `Book` class defines common properties and abstract methods
- **Inheritance**: `PaperBook`, `EBook`, and `ShowcaseBook` extend the base class
- **Polymorphism**: Each book type implements its own purchase logic
- **Service Layer**: Separate services for shipping (`ShippingService`) and email (`MailService`)

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line or terminal access

### Compilation
```bash
javac QuantumBookstore.java
```

### Execution
```bash
java QuantumBookstoreFullTest
```

## Sample Output

```
=== Quantum Bookstore Test Suite ===

1. Testing Adding Books:
Quantum book store: Added Paper Book - Effective Java by Joshua Bloch (2017) - ISBN: 978-0134685991
Quantum book store: Added Paper Book - Head First Design Patterns by Eric Freeman (2004) - ISBN: 978-0596009205
Quantum book store: Added EBook - Effective Java Digital by Joshua Bloch (2017) - ISBN: 978-0321356680
Quantum book store: Added EBook - Java Concurrency in Practice by Brian Goetz (2006) - ISBN: 978-0134757599
Quantum book store: Added Showcase/Demo Book - Clean Code Demo by Robert Martin (2008) - ISBN: 978-0134494166

Quantum book store: Current Inventory:
  Paper Book - Effective Java by Joshua Bloch (2017) - ISBN: 978-0134685991 (Stock: 10)
  Paper Book - Head First Design Patterns by Eric Freeman (2004) - ISBN: 978-0596009205 (Stock: 5)
  EBook - Effective Java Digital by Joshua Bloch (2017) - ISBN: 978-0321356680 (FileType: PDF)
  EBook - Java Concurrency in Practice by Brian Goetz (2006) - ISBN: 978-0134757599 (FileType: EPUB)
  Showcase/Demo Book - Clean Code Demo by Robert Martin (2008) - ISBN: 978-0134494166

2. Testing Book Purchases:
Quantum book store: Shipping 2 copies of 'Effective Java' to address: 123 Main St, City
Quantum book store: Paper book purchase processed. Remaining stock: 8
Quantum book store: Purchase completed. Total amount: $91.98
Purchase amount: $91.98
Quantum book store: Sending 1 copies of EBook 'Effective Java Digital' (PDF) to email: customer@email.com
Quantum book store: EBook purchase processed and sent to email: customer@email.com
Quantum book store: Purchase completed. Total amount: $35.99
Purchase amount: $35.99

3. Testing Error Handling:
Quantum book store: Error - Showcase books are not for sale
Quantum book store: Error - Insufficient stock. Available: 5, Requested: 10
Quantum book store: Error - Book with ISBN 978-9999999999 not found in inventory

4. Testing Outdated Book Removal:
Quantum book store: Removed outdated book - Head First Design Patterns by Eric Freeman (2004) - ISBN: 978-0596009205
Quantum book store: Removed outdated book - Java Concurrency in Practice by Brian Goetz (2006) - ISBN: 978-0134757599
Quantum book store: Removed outdated book - Clean Code Demo by Robert Martin (2008) - ISBN: 978-0134494166
Quantum book store: Removed 3 outdated books

6. Demonstrating Extensibility:
Quantum book store: Added Audio Book - The Art of Programming by Donald Knuth (2020) - ISBN: 978-1234567890
Quantum book store: Sending 1 copies of AudioBook 'The Art of Programming' (MP3, 480 minutes) to email: customer@email.com
Quantum book store: AudioBook purchase processed and sent to email: customer@email.com
Quantum book store: Purchase completed. Total amount: $29.99
Purchase amount: $29.99

Quantum book store: Test suite completed successfully!
```

## Code Structure

### Core Classes

1. **`Book` (Abstract Class)**
   - Base class with common properties (ISBN, title, author, year, price)
   - Abstract methods: `getBookType()`, `isPurchasable()`, `processPurchase()`

2. **`PaperBook`**
   - Extends `Book`
   - Includes stock management
   - Integrates with `ShippingService`

3. **`EBook`**
   - Extends `Book`
   - Includes file type (PDF, EPUB, etc.)
   - Integrates with `MailService`

4. **`ShowcaseBook`**
   - Extends `Book`
   - Not purchasable (display only)

5. **`QuantumBookstore`**
   - Main bookstore class
   - Manages inventory with HashMap
   - Handles adding, removing, and purchasing books

6. **`QuantumBookstoreFullTest`**
   - Comprehensive test suite
   - Demonstrates all functionality
   - Tests error handling and edge cases

### Service Classes

- **`ShippingService`**: Handles physical book shipping
- **`MailService`**: Handles digital book delivery
- **`AudioService`**: Example service for audio books (extensibility demo)

## Extensibility Example

The system demonstrates extensibility with the `AudioBook` class:

```java
class AudioBook extends Book {
    private String audioFormat;
    private int durationMinutes;
    
    // Implementation shows how to add new book types
    // without modifying existing code
}
```

## Key Design Principles

1. **Open/Closed Principle**: Open for extension, closed for modification
2. **Polymorphism**: Different book types handle purchases differently
3. **Encapsulation**: Each class manages its own data and behavior
4. **Abstraction**: Abstract base class defines common interface
5. **Single Responsibility**: Each class has one clear purpose

## Error Handling

The system includes comprehensive error handling for:
- Invalid purchase quantities
- Insufficient stock
- Non-existent books
- Attempts to purchase non-purchasable books
- Invalid parameters

## Future Enhancements

- Database integration for persistent storage
- REST API endpoints
- User authentication and authorization
- Order management system
- Payment processing integration
- Advanced search and filtering
- Shopping cart functionality
- Book reviews and ratings

## Requirements

- Java 8 or higher
- No external dependencies
- All functionality contained in single file

## Author

Created as a demonstration of extensible object-oriented design in Java.

## License

This project is for educational purposes.
