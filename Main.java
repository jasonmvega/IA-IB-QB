import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame{

public Main () {

super("IB Question Bank");

setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

Game play = new Game();

((Component) play).setFocusable(true);

setBackground(Color.BLUE);

getContentPane().add(play);

setVisible(true);

setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


}

public static void main(String[] args) {

Main run = new Main();

 


}
}