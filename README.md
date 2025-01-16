# Byte-Me
A CLI-based food ordering system in Java using OOPs principles and collections, implementing GUI with swing, file I/O management, and JUnit testing.The system allows customer and admin roles to place orders, manage their cart, view history, cancel and refund items, review items etc

# PROJECT OVERVIEW:
This is a console based interface that replicates a real life online food ordering system.

# INSTALLATION:
Ensure you have Java installed then compile and run the program using the following commands on your terminal
cd your/path/src
javac *.java
java Main

# USAGE:
customer
-registers a new account
-performs functionalities like browse menu, cart operations, etc.

admin
-registers a new account
-performs functionalities like menu management, order management, etc.

# CLASSES:
Main
Admin_Canteen
Customer_Canteen
Order
Cart
Menu
InvalidLoginException
Order_Frame
Canteen_Frame
Frame_Main
Testing

# COLLECTION FRAMEWORK USED:
-HashMap for login_id and password for easier lookup and better time complexity
-HashMap for login_id and customer object
-HashMap for Menu for easier lookups and better time complexity
-TreeMap for sorting menu according to price for better time complexity
-Queue for first in first out processing
-HashMap and value as ArrayList for storing multiple special requests and reviews

# ASSUMPTIONS:
-first in first out processing of orders when admin updates the status
-to place an order it must be added to cart first and then checked out
-search functionality takes input as the name of the item, not a key word

# JUNIT TESTING:
JUnit is used for unit testing the various functionalities of the system. The `Testing` class contains test methods to verify the behavior of the `Cart` class. The following JUnit annotations and assertions are used:
- `@BeforeEach` for setting up the test environment
- `@AfterEach` for cleaning up after tests
- `@Test` for marking test methods
- Assertions like `assertEquals`, `assertNull`, and `assertTrue` to validate the expected outcomes

# FILE HANDLING:
File handling is used to manage user data and order information. The following operations are performed:
- Reading and writing login credentials
- Storing and retrieving customer orders
- Logging special requests and reviews

# GUI:
The graphical user interface (GUI) is implemented using Java Swing. The following classes are used to create the GUI:
- `Order_Frame` for managing order-related operations
- `Canteen_Frame` for managing canteen-related operations
- `Frame_Main` for the main application window

