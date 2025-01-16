import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Canteen_Frame implements ActionListener {

    private Map<String, Menu> canteen_menu;
    JFrame frame=new JFrame();
    JButton button;
    Canteen_Frame() {
        frame.setTitle("Canteen Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(0xDB7093));
        frame.setResizable(false);

        JLabel label = new JLabel(" Welcome to BYTE ME!");
        label.setFont(new Font("Serif", Font.BOLD, 35));
        label.setForeground(Color.WHITE);
        label.setBounds(275, 190, 400, 50);
        frame.add(label);

        button=new JButton("View orders");
        button.setFont(new Font("Serif", Font.BOLD, 15));
        button.setOpaque(true);
       // button.setForeground(new Color(255,255,255));
        button.setBackground(new Color(230,190,255));
        button.setBounds(380,510,150,40);
        button.addActionListener(this);
        button.setFocusable(false);
        frame.add(button);

        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        label.setBorder(border);


        this.canteen_menu = new HashMap<>();
        loadMenuFromFile("canteen_data.txt");


        String[][] data = loadMenuData();
        String[] columnNames = {"Item", "Category", "Price", "Availability"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(130, 300, 700, 170);
        table.setFont(new Font("Serif", Font.PLAIN, 15));

        table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 24));
        table.getTableHeader().setBackground(new Color(200, 200, 200));
        table.getTableHeader().setForeground(Color.WHITE);

        frame.add(scrollPane);
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        frame.setVisible(true);
    }

    private void loadMenuFromFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String item = parts[0].trim();
                    String category = parts[1].trim();
                    String availability = parts[2].trim();
                    int price = Integer.parseInt(parts[3].trim());


                    canteen_menu.put(item, new Menu(category, availability, price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[][] loadMenuData() {
        String[][] data = new String[canteen_menu.size()][4];
        int i = 0;
        for (Map.Entry<String, Menu> entry : canteen_menu.entrySet()) {
            Menu menu = entry.getValue();
            data[i][0] = entry.getKey();
            data[i][1] = menu.getCategory();
            data[i][2] = "Rs." + menu.getPrice();
            data[i][3] = menu.getAvailable();
            i++;
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button) {
            frame.dispose();
            Order_Frame order_frame = new Order_Frame();
        }
    }
}



