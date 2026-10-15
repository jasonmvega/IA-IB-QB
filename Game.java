import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

public class Game extends JPanel implements Runnable {
    private BufferedImage back;
    private char screen = 'S';
    private JButton start, HL, SL;
    private JLabel startLabel, levelLabel;
    private int difficulty;

    public Game() {
        new Thread(this).start();
        this.setLayout(null);
        

        start = new JButton("Start");
        HL = new JButton("HL Physics");
        SL = new JButton("SL Physics");
        startLabel = new JLabel();
        levelLabel = new JLabel();
        setSwing();
        ActionListeners();
        addSwing();
    }

    @SuppressWarnings("static-access")
    public void run() {
        try {
            while(true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch(Exception e) {
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D twoDgraph = (Graphics2D) g;

        if (back == null) 
            back = (BufferedImage)(createImage(getWidth(), getHeight()));
        
        Graphics g2d = back.createGraphics();
        
        g2d.clearRect(0, 0, getSize().width, getSize().height);
        
   screen(g2d);
        
        twoDgraph.drawImage(back, null, 0, 0);
    }


   public void screen(Graphics g2d){
      switch(screen){
         case 'S':
            addSwing();
            setSwing();
            ActionListeners();
            HL.setVisible(false);
            break;
         case 'A':
            HL.setVisible(true);
            start.setVisible(false);
            startLabel.setVisible(false);
            break;
         case 'Q':
      }
   }

   public void setSwing(){
      start.setBounds(getWidth() / 2 - 100, getHeight() / 2 - 100, 200, 50);
      start.setHorizontalAlignment(JLabel.CENTER);
      startLabel.setBounds(0, -200, getWidth(), getHeight());
      startLabel.setFont(new Font("Arial", Font.BOLD, 50));
      startLabel.setForeground(Color.BLACK);
      startLabel.setText("Welcome to the Unofficial IB Question Bank!");
      startLabel.setHorizontalAlignment(JLabel.CENTER);
      HL.setBounds(getWidth() / 2 - 100, getHeight() / 2 - 50, 200, 50);
      HL.setHorizontalAlignment(JLabel.CENTER);

   }

   public void ActionListeners(){
        start.addActionListener(e -> { screen = 'A';});
        HL.addActionListener(e -> { difficulty = 1; screen = 'Q';});
        SL.addActionListener(e -> { difficulty = 2; screen = 'Q';});
   }

   public void addSwing(){
        this.add(start);
        this.add(startLabel);
        this.add(HL);
        this.add(SL);
   }
}