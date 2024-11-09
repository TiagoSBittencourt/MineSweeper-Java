import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class MineSweeper {
    private static class Tile extends JButton {
        int r;
        int c;
        int bombsNear;

        public Tile(int r, int c) {
            this.r = r;
            this.c = c;
            this.bombsNear = 0;
        }
        public Tile(int r, int c, int bombsNear) {
            this.r = r;
            this.c = c;
            this.bombsNear = bombsNear;
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
    ArrayList<Tile> bombsList;

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
                tile.setFont(new Font("Arial Unicode Ms", Font.PLAIN, 45));
                tile.setText(""); // Default
                boardPanel.add(tile);

                // Mouse Listener (This part track the mouse when is on this tile)
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        Tile tile = (Tile) e.getSource();

                        // Left Click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (tile.getText().isEmpty()) {
                                if (bombsList.contains(tile)) {
                                    revealMines();
                                    JOptionPane.showMessageDialog(frame, "Game Over!");
                                    disableBoard();
                                }
                                else {
                                    tile.setText(String.valueOf(tile.bombsNear)); // Placeholder for no adjacent mines
                                    tile.setEnabled(false); // Disable the tile
                                    tile.setBackground(Color.LIGHT_GRAY); // Mark as revealed
                                }
                            }
                        }
                        // Obs: BUTTON3 "wheel"

                        // Right Click (BUTTON3)
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            if (tile.getText().isEmpty()) {
                                tile.setText("🚩"); // Place flag (this block -> if(bombsList.contains(tile)))
                            }
                            else if (tile.getText().equals("🚩")) {
                                tile.setText(""); // Remove flag
                            }
                        }
                    }
                });
            }
        }
        frame.setVisible(true); // This is to prevent the bug of loading the screen before the components

        plantMines();
    }

    // Random mine placement
    void plantMines() {
        bombsList = new ArrayList<>();
        int mineCount = 10; // For example, 10 mines
        Random rand = new Random();

        while (bombsList.size() < mineCount) {
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);
            Tile tile = mineBoard[randRow][randCol];

            if (!bombsList.contains(tile)) {
                bombsList.add(tile);
            }
        }
        enumerateTiles();
    }

    private void enumerateTiles() {
        int[] sideTiles = {-1, 0, 1};
        int r;
        int c;
        for (Tile bombTile : bombsList) {
            r = bombTile.r;
            c = bombTile.c;
            for (int difR : sideTiles) {
                for (int difC : sideTiles) {
                    int newR = r + difR; // New row index
                    int newC = c + difC; // New column index


                    // Ensure the neighboring tile is within bounds
                    if (newR >= 0 && newR < rows && newC >= 0 && newC < cols) {
                        mineBoard[newR][newC].bombsNear++;
                    }
                }
            }
        }
    }

    // Reveals all the mines when the game is over
    void revealMines() {
        for (Tile tile : bombsList) {
            tile.setText("💣");
        }
    }

    // Disables all tiles after game is over
    void disableBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                mineBoard[r][c].setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new MineSweeper();
    }
}