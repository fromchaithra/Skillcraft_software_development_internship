import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SudokuSolver extends JFrame {
    private static final int SIZE = 9;
    private JTextField[][] cells = new JTextField[SIZE][SIZE];
    private JButton solveButton, clearButton;

    public SudokuSolver() {
        setTitle("Sudoku Solver - SkillCraft Task 03");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("SansSerif", Font.BOLD, 20);

        // Create 9x9 text fields
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(font);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));

                // Add light blue shading for 3x3 boxes
                if (((i / 3) + (j / 3)) % 2 == 0)
                    cells[i][j].setBackground(new Color(220, 235, 250));
                else
                    cells[i][j].setBackground(Color.WHITE);

                gridPanel.add(cells[i][j]);
            }
        }

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        solveButton = new JButton("Solve Sudoku");
        clearButton = new JButton("Clear");
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        add(new JLabel("Enter Sudoku Puzzle (0 or blank for empty cells):", SwingConstants.CENTER), BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        solveButton.addActionListener(this::solveSudoku);
        clearButton.addActionListener(e -> clearGrid());
    }

    private void solveSudoku(ActionEvent e) {
        int[][] board = new int[SIZE][SIZE];

        // Read grid input
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = cells[i][j].getText().trim();
                if (text.isEmpty()) board[i][j] = 0;
                else board[i][j] = Integer.parseInt(text);
            }
        }

        // Solve Sudoku
        if (solve(board)) {
            // Display solved Sudoku
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setForeground(Color.BLUE);
                }
            }
            JOptionPane.showMessageDialog(this, "Sudoku Solved Successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No Solution Exists!");
        }
    }

    private boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) return true;
                            board[row][col] = 0; // backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        // Row & Column check
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        // 3x3 sub-grid check
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num)
                    return false;
            }
        }
        return true;
    }

    private void clearGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j].setText("");
                cells[i][j].setForeground(Color.BLACK);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuSolver frame = new SudokuSolver();
            frame.setVisible(true);
        });
    }
}
