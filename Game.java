import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game  extends JPanel implements Runnable, KeyListener{
private int key; 
private String UI;
private final JButton beginPracticeButton;
private final JButton higherLevelButton;
private final JButton standardLevelButton;
private final JCheckBox[] topicCheckBoxes;
private final JPanel topicOptionsPanel;
private final JScrollPane topicScrollPane;
private final JPanel subtopicOptionsPanel;
private final JScrollPane subtopicScrollPane;
private final List<JCheckBox> subtopicCheckBoxes = new ArrayList<>();
private final JButton backButton;
private final JButton continueButton;
private final JTextField answerField;
private String selectedLevel;
private List<String> selectedTopics = new ArrayList<>();
private List<String> selectedSubtopics = new ArrayList<>();
private static final String[] TOPIC_NAMES = {
   "Topic A: Space, time and motion",
   "Topic B: The particulate nature of matter",
   "Topic C: Wave behaviour",
   "Topic D: Fields",
   "Topic E: Nuclear and quantum physics"
};
private static final String[][] SUBTOPIC_NAMES = {
   {"A.1 Kinematics", "A.2 Forces and momentum", "A.3 Work, energy and power", "A.4 Rigid body mechanics", "A.5 Galilean and special relativity"},
   {"B.1 Thermal energy transfers", "B.2 Greenhouse effect", "B.3 Gas laws", "B.4 Thermodynamics"},
   {"C.1 Simple harmonic motion", "C.2 Wave model", "C.3 Wave phenomena", "C.4 Standing waves and resonance", "C.5 Doppler effect"},
   {"D.1 Gravitational fields", "D.2 Electric and magnetic fields", "D.3 Motion in electromagnetic fields", "D.4 Induction"},
   {"E.1 Structure of the atom", "E.2 Quantum physics", "E.3 Radioactive decay", "E.4 Fission", "E.5 Fusion and stars"}
};
private static final List<String> HL_ONLY_SUBTOPIC_NAMES = Arrays.asList(
   "A.4 Rigid body mechanics",
   "A.5 Galilean and special relativity",
   "B.4 Thermodynamics",
   "D.4 Induction",
   "E.2 Quantum physics"
);
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
   higherLevelButton.addActionListener(e -> showTopicSelection("HL"));
   add(higherLevelButton);

   standardLevelButton = new JButton("Standard Level (SL)");
   standardLevelButton.addActionListener(e -> showTopicSelection("SL"));
   add(standardLevelButton);
   higherLevelButton.setVisible(false);
   standardLevelButton.setVisible(false);

   topicCheckBoxes = new JCheckBox[5];
   topicOptionsPanel = new JPanel();
   topicOptionsPanel.setLayout(new BoxLayout(topicOptionsPanel, BoxLayout.Y_AXIS));
   for (int i = 0; i < topicCheckBoxes.length; i++) {
      topicCheckBoxes[i] = new JCheckBox(TOPIC_NAMES[i]);
      topicCheckBoxes[i].setFont(topicCheckBoxes[i].getFont().deriveFont(22f));
      topicCheckBoxes[i].setMargin(new Insets(8, 8, 8, 8));
      topicCheckBoxes[i].setHorizontalAlignment(SwingConstants.LEFT);
      topicCheckBoxes[i].setAlignmentX(Component.LEFT_ALIGNMENT);
      topicCheckBoxes[i].addItemListener(e -> updateContinueButton());
      topicCheckBoxes[i].setVisible(false);
      topicOptionsPanel.add(topicCheckBoxes[i]);
   }
   topicScrollPane = new JScrollPane(topicOptionsPanel);
   topicScrollPane.setBorder(BorderFactory.createEmptyBorder());
   topicScrollPane.setVisible(false);
   add(topicScrollPane);

   subtopicOptionsPanel = new JPanel();
   subtopicOptionsPanel.setLayout(new BoxLayout(subtopicOptionsPanel, BoxLayout.Y_AXIS));
   subtopicScrollPane = new JScrollPane(subtopicOptionsPanel);
   subtopicScrollPane.setBorder(BorderFactory.createEmptyBorder());
   subtopicScrollPane.setVisible(false);
   add(subtopicScrollPane);

   backButton = new JButton("Back");
   backButton.addActionListener(e -> {
      if (UI.equals("subtopicSelection")) {
         showTopicSelection();
      } else {
         showConfiguration();
      }
   });
   backButton.setVisible(false);
   add(backButton);

   continueButton = new JButton("Continue");
   continueButton.addActionListener(e -> {
      if (UI.equals("topicSelection")) {
         showSubtopicSelection();
      } else if (UI.equals("subtopicSelection")) {
         startPractice();
      }
   });
   continueButton.setEnabled(false);
   continueButton.setVisible(false);
   add(continueButton);

   answerField = new JTextField();
   answerField.setFont(answerField.getFont().deriveFont(20f));
   answerField.setVisible(false);
   add(answerField);

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
   int scrollWidth = Math.max(0, Math.min(640, getWidth() - 40));
   int scrollY = 80;
   int controlsY = getHeight() - 65;
   int scrollHeight = Math.max(0, controlsY - scrollY - 15);
   topicScrollPane.setBounds(
      (getWidth() - scrollWidth) / 2,
      scrollY,
      scrollWidth,
      scrollHeight
   );
   subtopicScrollPane.setBounds(
      (getWidth() - scrollWidth) / 2,
      scrollY,
      scrollWidth,
      scrollHeight
   );
   answerField.setBounds((getWidth() - scrollWidth) / 2, 245, scrollWidth, 42);
   backButton.setBounds(centerX - 105, controlsY, buttonWidth, buttonHeight);
   continueButton.setBounds(centerX + 105, controlsY, buttonWidth, buttonHeight);
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
       case "topicSelection":
           g2d.setFont(new Font("Arial", Font.BOLD, 30));
           g2d.setColor(Color.BLACK);
           String topicHeading = "Choose Topics for Physics " + selectedLevel;
           FontMetrics topicHeadingMetrics = g2d.getFontMetrics();
           g2d.drawString(topicHeading, (getWidth() - topicHeadingMetrics.stringWidth(topicHeading)) / 2, 40);
           g2d.setFont(new Font("Arial", Font.PLAIN, 18));
           String topicInstructions = "Select one or more topics to practice";
           FontMetrics topicInstructionsMetrics = g2d.getFontMetrics();
           g2d.drawString(topicInstructions, (getWidth() - topicInstructionsMetrics.stringWidth(topicInstructions)) / 2, 67);
           break;
       case "subtopicSelection":
           g2d.setFont(new Font("Arial", Font.BOLD, 30));
           g2d.setColor(Color.BLACK);
           String subtopicHeading = "Choose Subtopics for Physics " + selectedLevel;
           FontMetrics subtopicHeadingMetrics = g2d.getFontMetrics();
           g2d.drawString(subtopicHeading, (getWidth() - subtopicHeadingMetrics.stringWidth(subtopicHeading)) / 2, 40);
           g2d.setFont(new Font("Arial", Font.PLAIN, 18));
           String subtopicInstructions = "Only subtopics from your selected topics are shown";
           FontMetrics subtopicInstructionsMetrics = g2d.getFontMetrics();
           g2d.drawString(subtopicInstructions, (getWidth() - subtopicInstructionsMetrics.stringWidth(subtopicInstructions)) / 2, 67);
           break;
       case "practice":
           g2d.setFont(new Font("Arial", Font.BOLD, 24));
           g2d.setColor(Color.BLACK);
           g2d.drawString(question1.getQuestionText(), 100, 200);
           g2d.setFont(new Font("Arial", Font.PLAIN, 18));
           g2d.drawString("Your answer:", 100, 232);
           break;
   }
}

private void showConfiguration() {
   UI = "configuration";
   beginPracticeButton.setVisible(false);
   higherLevelButton.setVisible(true);
   standardLevelButton.setVisible(true);
   setTopicSelectionVisible(false);
   topicScrollPane.setVisible(false);
   backButton.setVisible(false);
   continueButton.setVisible(false);
   subtopicScrollPane.setVisible(false);
   answerField.setVisible(false);
   repaint();
}

    public void checkAnswer() {
        String userInput = answerField.getText();
        try {
            int userAnswer = Integer.parseInt(userInput);
            if (userAnswer == q.getAnswer()) {
                correct();
                answerField.setText("");
                score ++;
                q = new Question(difficulty.getValue());
                if (timedMode) {
                    addTime();
                }
            } else if (userAnswer == q.getAnswer() + 1 || userAnswer == q.getAnswer() - 1) {
                if (timedMode) {
                    subTime();
                }
                JOptionPane.showMessageDialog(this, "So close! The correct answer is " + q.getAnswer() + ".");
                answerField.setText("");
                q = new Question(difficulty.getValue());
            } else {
                if (timedMode) {
                    subTime();
                }
                JOptionPane.showMessageDialog(this, "Incorrect. The correct answer is " + q.getAnswer() + ".");
                answerField.setText("");
                q = new Question(difficulty.getValue());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
        }
    }

private void showTopicSelection(String level) {
   selectedLevel = level;
   showTopicSelection();
}

private void showTopicSelection() {
   UI = "topicSelection";
   higherLevelButton.setVisible(false);
   standardLevelButton.setVisible(false);
   setTopicSelectionVisible(true);
   topicScrollPane.setVisible(true);
   backButton.setVisible(true);
   continueButton.setVisible(true);
   subtopicScrollPane.setVisible(false);
   answerField.setVisible(false);
   updateContinueButton();
   repaint();
}

private void showSubtopicSelection() {
   selectedTopics.clear();
   for (int i = 0; i < topicCheckBoxes.length; i++) {
      if (topicCheckBoxes[i].isSelected()) {
         selectedTopics.add(String.valueOf((char) ('A' + i)));
      }
   }

   List<String> previouslySelectedSubtopics = new ArrayList<>(selectedSubtopics);
   subtopicCheckBoxes.clear();
   subtopicOptionsPanel.removeAll();
   for (int i = 0; i < topicCheckBoxes.length; i++) {
      if (!topicCheckBoxes[i].isSelected()) {
         continue;
      }
      JLabel topicLabel = new JLabel(TOPIC_NAMES[i]);
      topicLabel.setFont(topicLabel.getFont().deriveFont(Font.BOLD));
      topicLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      subtopicOptionsPanel.add(topicLabel);

      for (String subtopicName : SUBTOPIC_NAMES[i]) {
         if ("SL".equals(selectedLevel) && HL_ONLY_SUBTOPIC_NAMES.contains(subtopicName)) {
            continue;
         }
         JCheckBox subtopicCheckBox = new JCheckBox(subtopicName);
         subtopicCheckBox.setFont(subtopicCheckBox.getFont().deriveFont(20f));
         subtopicCheckBox.setMargin(new Insets(6, 8, 6, 8));
         subtopicCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
         subtopicCheckBox.setSelected(previouslySelectedSubtopics.contains(subtopicName));
         subtopicCheckBox.addItemListener(e -> updateContinueButton());
         subtopicCheckBoxes.add(subtopicCheckBox);
         subtopicOptionsPanel.add(subtopicCheckBox);
      }
      subtopicOptionsPanel.add(Box.createVerticalStrut(8));
   }
   subtopicOptionsPanel.revalidate();
   subtopicOptionsPanel.repaint();
   UI = "subtopicSelection";
   setTopicSelectionVisible(false);
   topicScrollPane.setVisible(false);
   backButton.setVisible(true);
   continueButton.setVisible(true);
   subtopicScrollPane.setVisible(true);
   answerField.setVisible(false);
   updateContinueButton();
   repaint();
}

private void startPractice() {
   selectedSubtopics.clear();
   for (JCheckBox subtopicCheckBox : subtopicCheckBoxes) {
      if (subtopicCheckBox.isSelected()) {
         selectedSubtopics.add(subtopicCheckBox.getText());
      }
   }
   UI = "practice";
   answerField.setText("");
   answerField.setVisible(true);
   setTopicSelectionVisible(false);
   topicScrollPane.setVisible(false);
   backButton.setVisible(false);
   continueButton.setVisible(false);
   subtopicScrollPane.setVisible(false);
   repaint();
   answerField.requestFocusInWindow();
}

private void setTopicSelectionVisible(boolean visible) {
   for (JCheckBox topicCheckBox : topicCheckBoxes) {
      topicCheckBox.setVisible(visible);
   }
}

private void updateContinueButton() {
   if (UI.equals("topicSelection")) {
      for (JCheckBox topicCheckBox : topicCheckBoxes) {
         if (topicCheckBox.isSelected()) {
            continueButton.setEnabled(true);
            return;
         }
      }
   } else if (UI.equals("subtopicSelection")) {
      for (JCheckBox subtopicCheckBox : subtopicCheckBoxes) {
         if (subtopicCheckBox.isSelected()) {
            continueButton.setEnabled(true);
            return;
         }
      }
   }
   continueButton.setEnabled(false);
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