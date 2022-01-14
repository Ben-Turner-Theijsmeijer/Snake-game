package Snake_Game;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
    
    GameFrame(){
        GamePannel panel = new GamePannel();
        this.add(panel);
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
