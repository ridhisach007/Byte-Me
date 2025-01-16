import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class Order_Frame extends JFrame {

    private Queue<Order> pending_orders;
    private Queue<Order> completed_orders;

    Order_Frame() {
        this.setTitle("Canteen Menu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setLayout(null);
        this.getContentPane().setBackground(new Color(190,140,210));
        this.setResizable(false);

        JLabel label = new JLabel("     Pending Orders! ");
        label.setFont(new Font("Serif", Font.BOLD, 35));
        label.setForeground(Color.WHITE);
        label.setBounds(275, 190, 350, 50);
        this.add(label);

        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        label.setBorder(border);

        this.pending_orders = new LinkedList<>();
        this.completed_orders = new LinkedList<>();
        loadMenuFromFile("order_data.txt");


        String[] columnNames = {"Order Name", "Customer ID", "Status","Quantity"};
        String[][] data = loadMenuData();
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(130, 300, 700, 170);
        table.setFont(new Font("Serif", Font.PLAIN, 15));

        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 24));
        table.getTableHeader().setBackground(new Color(200, 200, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        this.add(scrollPane);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);


        this.setVisible(true);


    }
    private void loadMenuFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String item_name = parts[0].trim();
                    String customer_name = parts[1].trim();
                    String status = parts[2].trim();
                    int quantity = Integer.parseInt(parts[3].trim());


                    pending_orders.add(new Order(item_name,customer_name,quantity));
                    //completed_orders.add(new Order(status,customer_name,quantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[][] loadMenuData() {
        String[][] data = new String[pending_orders.size()][4];
        int i = 0;
        for (Order order : pending_orders) {
            data[i][0] = order.getOrder_name();
            data[i][1] = order.getCustomerID();
            data[i][2] = order.getStatus();
            data[i][3] = "qty: "+ order.getQuantity();
            i++;
        }


        return data;
    }




}
