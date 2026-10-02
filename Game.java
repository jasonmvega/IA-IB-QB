import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class Game  extends JPanel implements Runnable, KeyListener{
private int key; 
private String UI;
private final JButton beginPracticeButton;
private final JButton higherLevelButton;
private final JButton standardLevelButton;
private String selectedLevel;
private Question question1 = new Question(1, "Easy", "Math", "Algebra", "What is 2 + 2?", "4", "Adding two and two gives four.", 1);

public Game() {
   key = -1;
   UI = "mainMenu";
   setLayout(null);
   setFocusable(true);
   addKeyListener(this);

   beginPracticeButton = new JButton("Begin Practice");
   beginPracticeButton.addActionListener(e -> showConfiguration());
   add(beginPracticeButton);

   higherLevelButton = new JButton("Higher Level (HL)");
   higherLevelButton.addActionListener(e -> startPractice("HL"));
   add(higherLevelButton);

   standardLevelButton = new JButton("Standard Level (SL)");
   standardLevelButton.addActionListener(e -> startPractice("SL"));
   add(standardLevelButton);
   higherLevelButton.setVisible(false);
   standardLevelButton.setVisible(false);

   new Thread(this).start();
}
public void run()
   {
    try
    {
    while(true)
    {
       Thread.currentThread().sleep(5);
            repaint();
         }
      }
    catch(Exception e)
      {
      }
  }
@Override
protected void paintComponent(Graphics g) {
   super.paintComponent(g);
   beginPractice(g);
}

@Override
public void doLayout() {
   int buttonWidth = 190;
   int buttonHeight = 44;
   int centerX = (getWidth() - buttonWidth) / 2;
   beginPracticeButton.setBounds(
      centerX,
      getHeight() / 2 + 55,
      buttonWidth,
      buttonHeight
   );
   higherLevelButton.setBounds(centerX - 105, getHeight() / 2 + 55, buttonWidth, buttonHeight);
   standardLevelButton.setBounds(centerX + 105, getHeight() / 2 + 55, buttonWidth, buttonHeight);
}

public void beginPractice(Graphics g2d) {
   switch(UI){
       case "mainMenu":
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.BLACK);
            FontMetrics titleMetrics = g2d.getFontMetrics();
            String title = "Welcome to the Unofficial IB Physics Question Bank!";
            g2d.drawString(title, (getWidth() - titleMetrics.stringWidth(title)) / 2, getHeight() / 2);
            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            break;
       case "configuration":
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            g2d.setColor(Color.BLACK);
            String heading = "Configure Your Practice";
            FontMetrics headingMetrics = g2d.getFontMetrics();
            g2d.drawString(heading, (getWidth() - headingMetrics.stringWidth(heading)) / 2, getHeight() / 2 - 35);
            g2d.setFont(new Font("Arial", Font.PLAIN, 20));
            String subject = "Select your Physics level";
            FontMetrics subjectMetrics = g2d.getFontMetrics();
            g2d.drawString(subject, (getWidth() - subjectMetrics.stringWidth(subject)) / 2, getHeight() / 2 + 10);
           break;
       case "practice":
           g2d.setFont(new Font("Arial", Font.BOLD, 24));
           g2d.setColor(Color.BLACK);
           g2d.drawString("Practice Mode", 100, 100);
           g2d.drawString("Physics " + selectedLevel, 100, 150);
           g2d.drawString("Question: " + question1.getQuestionText(), 100, 200);
           g2d.drawString("Answer: " + question1.getAnswerText(), 100, 250);
           g2d.drawString("Explanation: " + question1.getExplanationText(), 100, 300);
           break;
   }
}

private void showConfiguration() {
   UI = "configuration";
   beginPracticeButton.setVisible(false);
   higherLevelButton.setVisible(true);
   standardLevelButton.setVisible(true);
   repaint();
}

private void startPractice(String level) {
   selectedLevel = level;
   UI = "practice";
   higherLevelButton.setVisible(false);
   standardLevelButton.setVisible(false);
   repaint();
   requestFocusInWindow();
}

//DO NOT DELETE
@Override
public void keyTyped(KeyEvent e) {
// TODO Auto-generated method stub
}
//DO NOT DELETE
@Override
public void keyPressed(KeyEvent e) {
// TODO Auto-generated method stub
key= e.getKeyCode();
System.out.println(key);
if (UI.equals("mainMenu") && key == KeyEvent.VK_P) {
   showConfiguration();
}
}
//DO NOT DELETE
@Override
public void keyReleased(KeyEvent e) {
}

}