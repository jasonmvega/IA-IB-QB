import javax.swing.*;

public class Button extends JFrame{
    
    Button(){
        
        JButton button = new JButton("Click Me");
        button.setBounds(100, 100, 100, 50);

        this.add(button);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500,500);
        this.setVisible(true);
    }
}
