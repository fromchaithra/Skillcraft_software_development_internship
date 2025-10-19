import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessingGame extends JFrame implements ActionListener {
    private final Random random = new Random();
    private int randomNumber;
    private int attempts;
    
    private JTextField guessField;
    private JLabel messageLabel;
    private JButton guessButton, resetButton;

    public GuessingGame() {
        setTitle("🎯 SkillCraft - Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);

        randomNumber = random.nextInt(100) + 1;
        attempts = 0;

        // Top label
        JLabel title = new JLabel("Guess the Number (1 - 100)", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.add(new JLabel("Enter your guess: "));
        
        guessField = new JTextField();
        centerPanel.add(guessField);
        
        guessButton = new JButton("Guess");
        resetButton = new JButton("Reset");
        centerPanel.add(guessButton);
        centerPanel.add(resetButton);
        add(centerPanel, BorderLayout.CENTER);

        // Message label
        messageLabel = new JLabel("Start guessing...", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        add(messageLabel, BorderLayout.SOUTH);

        guessButton.addActionListener(this);
        resetButton.addActionListener(this);

        getContentPane().setBackground(new Color(240, 248, 255));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            try {
                int userGuess = Integer.parseInt(guessField.getText());
                attempts++;
                if (userGuess < 1 || userGuess > 100) {
                    messageLabel.setText("❌ Enter a number between 1 and 100!");
                } else if (userGuess == randomNumber) {
                    messageLabel.setText("🎉 Correct! You guessed in " + attempts + " attempts!");
                    guessButton.setEnabled(false);
                } else if (userGuess < randomNumber) {
                    messageLabel.setText("📉 Too low! Try again.");
                } else {
                    messageLabel.setText("📈 Too high! Try again.");
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ Please enter a valid number!");
            }
        } else if (e.getSource() == resetButton) {
            randomNumber = random.nextInt(100) + 1;
            attempts = 0;
            guessField.setText("");
            messageLabel.setText("Game reset. Start guessing!");
            guessButton.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GuessingGame::new);
    }
}
