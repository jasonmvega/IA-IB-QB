import javax.swing.*;
import java.awt.*;

public class Application {

    public static void main(String[] args) {

        JFrame frame = new JFrame("IB Physics Question Bank");

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("IB Physics Question Bank");

        JButton startButton = new JButton("Start Practice");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        panel.add(title);
        panel.add(startButton);

        frame.add(panel);

        frame.setVisible(true);
    }
}