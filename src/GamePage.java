import javax.swing.*;
import java.awt.*;

public class GamePage {
    static int numOfRow = 0;
    static int numOfColumn = 0;
    static JCheckBox[][] checkboxes;
    static JFrame frame;

    protected void inputPage() {
        JLabel labelRow = new JLabel("Enter Number Of Row: ");
        JLabel labelCol = new JLabel("Enter Number Of Column: ");

        frame = new JFrame("InputPage");
        frame.setLayout(new GridLayout(3, 2));
        JTextField rowField = new JTextField();
        JTextField colField = new JTextField();


        JButton printButton = new JButton(" Select Life");


        printButton.addActionListener(e -> {
            numOfColumn = Integer.parseInt(colField.getText());
            numOfRow = Integer.parseInt(rowField.getText());
            frame.setVisible(false);
            playGame();
        });

        frame.add(labelCol);
        frame.add(colField);
        frame.add(labelRow);
        frame.add(rowField);

        frame.add(printButton);

        frame.pack();
        frame.setVisible(true);
    }

    protected void playGame() {


        checkboxes = new JCheckBox[numOfRow][numOfColumn];

        // Create the frame and set its layout
        frame = new JFrame("Checkbox Matrix");
        frame.setLayout(new GridLayout(numOfRow, numOfColumn));


        // Add checkboxes to the frame
        for (int i = 0; i < numOfRow; i++) {
            for (int j = 0; j < numOfColumn; j++) {
                checkboxes[i][j] = new JCheckBox();
                frame.add(checkboxes[i][j]);
            }
        }

        // Add a button to print selected checkboxes
        JButton printButton = getButton(checkboxes);
        frame.setLayout(new GridLayout(numOfRow + 1, numOfColumn));
        frame.add(printButton);

        // Display the frame
        frame.pack();
        frame.setVisible(true);

    }

    private static JButton getButton(JCheckBox[][] checkboxes) {
        JButton printButton = new JButton("Play");
        printButton.addActionListener(e -> {
            System.out.println("Selected checkboxes:");
            for (int i = 0; i < numOfRow; i++) {
                for (int j = 0; j < numOfColumn; j++) {
                    if (checkboxes[i][j].isSelected()) {
                        System.out.println("(" + i + ", " + j + ")");
                    }
                }
            }

            PlayPage page = new PlayPage();
            page.game();
        });
        return printButton;
    }

    public static boolean isValidCell(int row, int col) {
        return row >= 0 && row < numOfRow && col >= 0 && col < numOfColumn;
    }
}
