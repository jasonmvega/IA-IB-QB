import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage; 
import java.awt.event.*; 

public class Game  extends JPanel implements Runnable, KeyListener{
private BufferedImage back;

private int key; 
private Question question1 = new Question(1, "Easy", "Math", "Algebra", "What is 2 + 2?", "4", "Adding two and two gives four.", 1);

public Game() {
new Thread(this).start();

this.addKeyListener(this);
key =-1; 
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
public void paint(Graphics g){
Graphics2D twoDgraph = (Graphics2D) g; 
if( back ==null)
back=(BufferedImage)( (createImage(getWidth(), getHeight()))); 
Graphics g2d = back.createGraphics();
g2d.clearRect(0,0,getSize().width, getSize().height);

g2d.setColor(Color.WHITE);
g2d.setFont(new Font("Arial", Font.PLAIN, 20));
g2d.drawString("Question ID: " + question1.getId(), 50, 50);
g2d.drawString("Level: " + question1.getLevel(), 50, 70);
g2d.drawString("Topic: " + question1.getTopic(), 50, 90);
g2d.drawString("Subtopic: " + question1.getSubtopic(), 50, 110);
g2d.drawString("Question: " + question1.getQuestionText(), 50, 130);    
        
twoDgraph.drawImage(back, null, 0, 0);
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
}
//DO NOT DELETE
@Override
public void keyReleased(KeyEvent e) {
}

}