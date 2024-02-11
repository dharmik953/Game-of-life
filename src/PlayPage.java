import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayPage {
    private static Set<JCheckBox[][]> set = new HashSet<>();
    private static List<JCheckBox[][]> list = new ArrayList<>();
    private static int row = GamePage.numOfRow + 2;
    private static int column = GamePage.numOfColumn + 2;
    private static int[][] naighbourMetrix = new int[row][column];
    private static int index = 0;

    protected void game() {
        GamePage.frame = new JFrame("InputPage");
        JCheckBox[][] checkboxes = new JCheckBox[row][column];

        GamePage.frame.setLayout(new GridLayout(GamePage.numOfRow + 3, GamePage.numOfColumn + 2));

        // Add checkboxes to the frame.
        for (int i = 0; i < GamePage.numOfRow + 2; i++) {
            for (int j = 0; j < GamePage.numOfColumn + 2; j++) {
                checkboxes[i][j] = new JCheckBox();
                GamePage.frame.add(checkboxes[i][j]);
            }
        }
        for (int i = 0; i < GamePage.numOfRow; i++) {
            for (int j = 0; j < GamePage.numOfColumn; j++) {
                if (GamePage.checkboxes[i][j].isSelected()) checkboxes[i + 1][j + 1].setSelected(true);
            }
        }

        showMatrix(checkboxes);
        GamePage.frame.add(prevButton());
        GamePage.frame.add(nextButton(checkboxes));

        GamePage.frame.pack();
        GamePage.frame.setVisible(true);
    }

    private static void showMatrix(JCheckBox[][] checkboxes) {
        // Add marked boxes
        for (int i = 0; i < checkboxes.length; i++) {
            for (int j = 0; j < checkboxes[0].length; j++) {
                int finalJ = j;
                int finalI = i;
                if (checkboxes[i][j].isSelected())
                    SwingUtilities.invokeLater(() -> checkboxes[finalI][finalJ].setSelected(true));
                else SwingUtilities.invokeLater(() -> checkboxes[finalI][finalJ].setSelected(false));
            }
        }
    }

    private static JButton prevButton() {
        JButton printButton = new JButton("Previous Generation");
        System.out.println("index--");
        printButton.addActionListener(e -> showMatrix(list.get(--index)));
        return printButton;
    }

    private static JButton nextButton(JCheckBox[][] checkboxes) {
        JButton printButton = new JButton("Next Generation");
        printButton.addActionListener(e -> {
            if (set.size() == index) {
                if (!set.contains(checkboxes)) {
                    index++;
                    getNewGeneration(checkboxes);
                } else {
                    System.out.println("It will last endlessly");
                    JLabel label = new JLabel("This will last endlessly");
                    GamePage.frame.add(label);
                    showTile(checkboxes, list);
                }
            } else {
                showMatrix(list.get(++index));
            }
        });
        return printButton;
    }

    private static void getNewGeneration(JCheckBox[][] checkboxes) {

        System.out.println("naighbour metrix");
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < column - 1; j++) {
                naighbourMetrix[i][j] = getNeighbour(i, j, checkboxes);
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                int numberOfNeighbours = naighbourMetrix[i][j];
                if ((numberOfNeighbours == 3 || numberOfNeighbours == 2) && checkboxes[i][j].isSelected())
                    checkboxes[i][j].setSelected(true);
                if (numberOfNeighbours == 3) checkboxes[i][j].setSelected(true);
            }
        }

        // Update the original checkboxes array
        for (int i = 0; i < row; i++) {
            System.arraycopy(checkboxes[i], 0, checkboxes[i], 0, GamePage.numOfColumn + 2);
        }

        showMatrix(checkboxes);
        set.add(checkboxes);
        list.add(checkboxes);
    }

    // returns the number of neighbor of {i,j} cell.
    private static int getNeighbour(int i, int j, JCheckBox[][] matrix) {
        int numberOfNeighbours = 0;

        int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] dir : directions) {
            int newRow = i + dir[0];
            int newCol = j + dir[1];
            if (GamePage.isValidCell(newRow, newCol) && matrix[newRow][newCol].isSelected()) {
                numberOfNeighbours++;
            }

        }
        return numberOfNeighbours;
    }

    // shows the tile when repetition occur.
    private static void showTile(JCheckBox[][] checkboxes, List<JCheckBox[][]> list) {
        int diff = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == checkboxes) {
                diff = Math.abs(index - i);
            }

        }

        JOptionPane.showMessageDialog(GamePage.frame, "It will repeat from last " + diff + " generations", "Error", JOptionPane.ERROR_MESSAGE);

        GamePage.frame.setSize(350, 350);
        GamePage.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("It will repeat from last " + diff + " generations");
    }

}
