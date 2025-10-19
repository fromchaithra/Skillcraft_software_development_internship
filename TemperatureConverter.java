import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConverter extends JFrame implements ActionListener {
    private JComboBox<String> fromScale, toScale;
    private JTextField inputField, resultField;
    private JButton convertButton, resetButton;

    public TemperatureConverter() {
        setTitle("🌡️ SkillCraft - Temperature Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        // Title
        JLabel title = new JLabel("Temperature Converter", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input field
        panel.add(new JLabel("Enter Temperature:"));
        inputField = new JTextField();
        panel.add(inputField);

        // From scale
        panel.add(new JLabel("From:"));
        String[] scales = {"Celsius", "Fahrenheit", "Kelvin"};
        fromScale = new JComboBox<>(scales);
        panel.add(fromScale);

        // To scale
        panel.add(new JLabel("To:"));
        toScale = new JComboBox<>(scales);
        panel.add(toScale);

        // Result field
        panel.add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);

        add(panel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        convertButton = new JButton("Convert");
        resetButton = new JButton("Reset");
        buttonPanel.add(convertButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        convertButton.addActionListener(this);
        resetButton.addActionListener(this);

        getContentPane().setBackground(new Color(240, 248, 255));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton) {
            try {
                double inputTemp = Double.parseDouble(inputField.getText());
                String from = (String) fromScale.getSelectedItem();
                String to = (String) toScale.getSelectedItem();

                double result = convertTemperature(inputTemp, from, to);
                resultField.setText(String.format("%.2f", result) + " " + to);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠️ Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == resetButton) {
            inputField.setText("");
            resultField.setText("");
            fromScale.setSelectedIndex(0);
            toScale.setSelectedIndex(0);
        }
    }

    private double convertTemperature(double temp, String from, String to) {
        double result = temp;

        // Convert from source to Celsius first
        switch (from) {
            case "Fahrenheit" : result = (temp - 32) * 5 / 9;
                                break;
            case "Kelvin" : result = temp - 273.15;
                            break;
        }

        // Convert Celsius to target
        switch (to) {
            case "Fahrenheit" : result = (result * 9 / 5) + 32;
                                break;
            case "Kelvin" : result = result + 273.15;
                           break;
        }

        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TemperatureConverter::new);
    }
}
