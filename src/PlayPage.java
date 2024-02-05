import javax.swing.*;
import java.awt.*;

public class PlayPage {
    protected void game(){
        GamePage.frame = new JFrame("InputPage");

        JCheckBox[][] checkboxes = new JCheckBox[GamePage.numOfRow + 2][GamePage.numOfColumn + 2];


        GamePage.frame.setLayout(new GridLayout(GamePage.numOfRow + 3, GamePage.numOfColumn + 2));
        // Add checkboxes to the frame.
        for (int i = 0; i < GamePage.numOfRow + 2; i++) {
            for (int j = 0; j < GamePage.numOfColumn + 2; j++) {
                checkboxes[i][j] = new JCheckBox();
                GamePage.frame.add(checkboxes[i][j]);
            }
        }

        //add marked boxes
        for (int i = 0; i < GamePage.numOfRow ; i++) {
            for (int j = 0; j < GamePage.numOfColumn ; j++) {
                if (GamePage.checkboxes[i][j].isSelected()){
                   checkboxes[i+1][j+1].setSelected(true);
                }

            }
        }

//        GamePage.frame.add(checkboxes);
        GamePage.frame.pack();
        GamePage.frame.setVisible(true);
    }
}
