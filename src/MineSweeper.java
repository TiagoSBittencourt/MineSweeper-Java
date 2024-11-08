import javax.swing.*;
import java.awt.*;

public class MineSweeper {
    private static class Tile extends JButton {
        int r;
        int c;

        public Tile(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    int sideSize = 70; // Size of each tile

    int rows = 10;
    int cols = rows;
    int boardWidth = cols * sideSize;
    int boardHeight = rows * sideSize;

    // frame: The main window that holds all components
    JFrame frame = new JFrame("MineSweeper");
    // textLabel: Displays the game's title
    JLabel textLabel = new JLabel();
    // textPanel: A panel that contains the textLabel
    JPanel textPanel = new JPanel();
    // boardPanel: The main area where the grid of tiles
    JPanel boardPanel = new JPanel();

    // The board GRID
    Tile[][] mineBoard = new Tile[rows][cols];

    MineSweeper() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight); // Set window size
        frame.setLocationRelativeTo(null); // Center window
        frame.setResizable(false); // Disable resizing
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.BOLD, 25)); // Set label font
        textLabel.setHorizontalAlignment(JLabel.CENTER); // Center text
        textLabel.setText("MineSweeper"); // Set initial label text
        textLabel.setOpaque(true); // Make label background visible

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel); // Add label to the panel
        frame.add(textPanel, BorderLayout.NORTH); // Add panel to the top

        boardPanel.setLayout(new GridLayout(rows, cols)); // Create grid layout
        boardPanel.setBackground(Color.GRAY); // Set background color
        frame.add(boardPanel); // Add board panel to the frame

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Tile tile = new Tile(r, c);
                mineBoard[r][c] = tile;

                tile.setFocusable(false); // Disable focus
                tile.setMargin(new Insets(0, 0, 0, 0)); // Remove button margin
                tile.setFont(new Font("Arial", Font.PLAIN, 45));
                tile.setText("X"); // Default
                boardPanel.add(tile);
            }
        }
    }
}