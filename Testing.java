import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Testing {
    private Cart cart;
    private Admin_Canteen admin;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        admin = new Admin_Canteen();
        cart = new Cart(admin);
    }

    @Test
    void out_of_stock() {
        cart.addItem("Roti", 2, 10);

        String output = outputStream.toString().trim();
        assertEquals("Item is already sold out", output);

        assertNull(cart.getCartMap().get("Roti"));
    }

    @Test
    void verify_price() {
        cart.addItem("Pasta", 2, 50);

        assertEquals(2, cart.getCartMap().get("Pasta"));
        assertEquals(50, cart.getPriceMap().get("Pasta"));

        cart.total_price();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Total Price: 100"));
    }

    @Test
    void modify_qty() {
        cart.addItem("Pizza", 1, 200);
        cart.modifyQty("Pizza", 3);

        assertEquals(3, cart.getCartMap().get("Pizza"));

        cart.total_price();
        String output = outputStream.toString().trim();
        assertTrue(output.contains("Total Price: 600"));
    }

    @Test
    void negative_qty() {
        cart.addItem("Burger", -1, 40);
        assertNull(cart.getCartMap().get("Burger"));

        String output = outputStream.toString().trim();
        assertEquals("Quantity is negative", output);

        outputStream.reset();

        cart.addItem("Burger", 1, 40);
        cart.modifyQty("Burger", -2);

        assertEquals(1, cart.getCartMap().get("Burger"));

        output = outputStream.toString().trim();
        assertTrue(output.contains("Quantity is negative"));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}
