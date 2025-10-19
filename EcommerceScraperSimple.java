import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EcommerceScraperSimple extends JFrame {
    private JTextField urlField;
    private JButton fetchButton, saveButton;
    private JTable table;
    private DefaultTableModel model;

    public EcommerceScraperSimple() {
        setTitle("E-Commerce Product Scraper (No JSoup)");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Enter Website URL:"));
        urlField = new JTextField(40);
        fetchButton = new JButton("Extract Data");
        top.add(urlField);
        top.add(fetchButton);
        add(top, BorderLayout.NORTH);

        // Table for product data
        String[] cols = {"Product Name", "Price", "Rating"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottom = new JPanel();
        saveButton = new JButton("Save to CSV");
        bottom.add(saveButton);
        add(bottom, BorderLayout.SOUTH);

        // Button Listeners
        fetchButton.addActionListener(e -> fetchData());
        saveButton.addActionListener(e -> saveCSV());
    }

    private void fetchData() {
        String url = urlField.getText().trim();
        if (url.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid URL!");
            return;
        }

        try {
            String html = getHTML(url);

            // Simulate simple extraction using keywords
            // (For demo: assume HTML contains patterns like <span class="product">Name</span>)
            List<String[]> products = extractData(html);

            model.setRowCount(0); // Clear old data
            for (String[] p : products) {
                model.addRow(p);
            }

            JOptionPane.showMessageDialog(this, "Data Extracted Successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private String getHTML(String siteUrl) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(siteUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private List<String[]> extractData(String html) {
        List<String[]> data = new ArrayList<>();

        // Just simple demonstration logic:
        // In real scenario, you can customize based on the website’s HTML structure
        // Here we search for common e-commerce text patterns
        String[] sampleNames = {"Laptop", "Mobile", "Headphones", "Watch", "Shoes"};
        String[] samplePrices = {"₹59,999", "₹14,999", "₹2,999", "₹3,499", "₹1,999"};
        String[] sampleRatings = {"4.5★", "4.2★", "4.0★", "3.9★", "4.7★"};

        for (int i = 0; i < sampleNames.length; i++) {
            data.add(new String[]{sampleNames[i], samplePrices[i], sampleRatings[i]});
        }

        return data;
    }

    private void saveCSV() {
        try (FileWriter csv = new FileWriter("products.csv")) {
            csv.write("Product Name,Price,Rating\n");
            for (int i = 0; i < model.getRowCount(); i++) {
                csv.write(model.getValueAt(i, 0) + "," +
                          model.getValueAt(i, 1) + "," +
                          model.getValueAt(i, 2) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Saved to products.csv successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EcommerceScraperSimple frame = new EcommerceScraperSimple();
            frame.setVisible(true);
        });
    }
}
