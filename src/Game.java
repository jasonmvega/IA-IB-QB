package src;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends JPanel implements Runnable {
    private BufferedImage back;
    private char screen = 'S';
    private JButton start, HL, SL;
    private JButton backButton, continueButton, showTimerLabel;
    private JLabel startLabel, levelLabel, selectionLabel, questionLabel, explanationLabel, wrongLabel, formatLabel;
    private JPanel topicSelectionPanel;
    private JScrollPane topicSelectionScroll;
    private JCheckBox[] topicCheckBoxes;
    private JCheckBox[][] subtopicCheckBoxes;
    private JTextField answerField;
    private int difficulty, currentQuestionIndex;
    private boolean updatingSelections, timerRunning;
    private double time;
    private boolean stopwatchVisible = false;
    private long startTime;
    private long elapsedTime;
    private Question q;
    private final List<Question> questions = new ArrayList<>();
    private final List<String> selectedSubtopics = new ArrayList<>();
    private final Topic[] topics = {
        new Topic("A", "Space, time and motion", new String[] {
            "A.1 Kinematics",
            "A.2 Forces and momentum",
            "A.3 Work, energy and power"
        },
            "A.4 Rigid body mechanics",
            "A.5 Galilean and special relativity"),
        new Topic("B", "The particulate nature of matter", new String[] {
            "B.1 Thermal energy transfers",
            "B.2 Greenhouse effect",
            "B.3 Gas laws"
        },
            "B.4 Thermodynamics"),
        new Topic("C", "Wave behaviour", new String[] {
            "C.1 Simple harmonic motion",
            "C.2 Wave model",
            "C.3 Wave phenomena"
        },
            "C.4 Standing waves and resonance",
            "C.5 Doppler effect"),
        new Topic("D", "Fields", new String[] {
            "D.1 Gravitational fields",
            "D.2 Electric and magnetic fields"
        },
            "D.3 Motion in electromagnetic fields",
            "D.4 Induction"),
        new Topic("E", "Nuclear and quantum physics", new String[] {
            "E.1 Structure of the atom",
            "E.2 Quantum physics",
            "E.3 Radioactive decay",
            "E.4 Fission",
            "E.5 Fusion and stars"
        })
    };

    public Game() {
        this.setLayout(null);
        

        start = new JButton("Start");
        HL = new JButton("HL Physics");
        SL = new JButton("SL Physics");
        backButton = new JButton("Back");
        continueButton = new JButton("Start practice");
        startLabel = new JLabel();
        levelLabel = new JLabel();
        selectionLabel = new JLabel();
        time=0;
        answerField = new JTextField();
        questionLabel = new JLabel();
        explanationLabel = new JLabel();
        wrongLabel = new JLabel();
        formatLabel = new JLabel("Please include format using: (Answer) (Unit)");
        showTimerLabel = new JButton("Show Timer");
        createTopicSelector();
        setSwing();
        ActionListeners();
        addSwing();
        updateScreen();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setSwing();
            }
        });
        new Thread(this).start();
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

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D twoDgraph = (Graphics2D) g;

        if (back == null) 
            back = (BufferedImage)(createImage(getWidth(), getHeight()));
        
        Graphics g2d = back.createGraphics();
        
       super.paintComponent(g);

        if (q != null && q.getQuestion() != null) {
        g2d.drawString(q.getQuestion(), 30, 100);
}
        g2d.clearRect(0, 0, getSize().width, getSize().height);

if (screen == 'Q' && stopwatchVisible) {
    addTimer(g2d);
}
        
        twoDgraph.drawImage(back, null, 0, 0);
            }
    

   private void updateScreen(){
      switch(screen){
         case 'S':
            start.setVisible(true);
            startLabel.setVisible(true);
            HL.setVisible(false);
            SL.setVisible(false);
            levelLabel.setVisible(false);
            selectionLabel.setVisible(false);
            backButton.setVisible(false);
            continueButton.setVisible(false);
            topicSelectionScroll.setVisible(false);
            answerField.setVisible(false);
            explanationLabel.setVisible(false);
            wrongLabel.setVisible(false);
            formatLabel.setVisible(false);
            showTimerLabel.setVisible(false);
            break;
         case 'A':
            start.setVisible(false);
            startLabel.setVisible(false);
            HL.setVisible(true);
            SL.setVisible(true);
            levelLabel.setVisible(true);
            selectionLabel.setVisible(false);
            backButton.setVisible(false);
            continueButton.setVisible(false);
            topicSelectionScroll.setVisible(false);
            answerField.setVisible(false);
            break;
         case 'T':
            start.setVisible(false);
            startLabel.setVisible(false);
            HL.setVisible(false);
            SL.setVisible(false);
            levelLabel.setVisible(false);
            selectionLabel.setText("Select topics and subtopics for "
                + (difficulty == 1 ? "HL" : "SL") + " practice:");
            selectionLabel.setVisible(true);
            backButton.setText("Back to difficulty");
            backButton.setVisible(true);
            continueButton.setText("Start practice");
            continueButton.setVisible(true);
            topicSelectionScroll.setVisible(true);
            answerField.setVisible(false);
            break;
         case 'Q':
            start.setVisible(false);
            startLabel.setVisible(false);
            HL.setVisible(false);
            SL.setVisible(false);
            levelLabel.setVisible(false);
            topicSelectionScroll.setVisible(false);
            selectionLabel.setVisible(true);
            selectionLabel.setText("Selected topics: " + getSelectedTopicCodes());
            backButton.setVisible(true);
            backButton.setText("Next question");
            continueButton.setVisible(true);
            answerField.setVisible(true);
            formatLabel.setVisible(true);
            showTimerLabel.setVisible(true);
            questionLabel.setText(
            q != null && q.getQuestion() != null
          ? q.getQuestion()
         : "No question loaded."
            );
            continueButton.setText("Show explanation");
            questionLabel.setVisible(true);
            break;
            case 'F':
        start.setVisible(false);
        startLabel.setVisible(false);
        HL.setVisible(false);
        SL.setVisible(false);
        levelLabel.setVisible(false);
        topicSelectionScroll.setVisible(false);
        selectionLabel.setText("Practice completed!");
        selectionLabel.setVisible(true);
        backButton.setText("Back to topics");
        backButton.setVisible(true);
        continueButton.setVisible(false);
        answerField.setVisible(false);
        questionLabel.setVisible(false);
        explanationLabel.setVisible(false);
        wrongLabel.setVisible(false);
        formatLabel.setVisible(false);
        showTimerLabel.setVisible(false);
            break;
      }
   }

   public void setSwing(){
      int width = getWidth();
      int height = getHeight();
      int buttonWidth = Math.min(200, Math.max(0, width - 40));
      int buttonHeight = Math.min(50, Math.max(0, (height - 20) / 2));
      int buttonX = (width - buttonWidth) / 2;
      int maxButtonY = Math.max(0, height - buttonHeight);
      int startY = Math.max(0, Math.min(height / 2 - 100, maxButtonY));
showTimerLabel.setBounds(30, 30, 130, 35);
showTimerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
      explanationLabel.setBounds(30, 300, width - 60, 200);
      explanationLabel.setFont(new Font("Arial", Font.PLAIN, 20));
      wrongLabel.setBounds(30, 500, width - 60, 50);
      wrongLabel.setFont(new Font("Arial", Font.PLAIN, 20));
      formatLabel.setBounds(30, 550, width - 60, 50);
      formatLabel.setFont(new Font("Arial", Font.PLAIN, 20));
      start.setBounds(buttonX, startY, buttonWidth, buttonHeight);
      start.setHorizontalAlignment(JLabel.CENTER);
      startLabel.setBounds(0, -200, width, height);
      startLabel.setFont(new Font("Arial", Font.BOLD, 50));
      startLabel.setForeground(Color.BLACK);
      startLabel.setText("Welcome to the Unofficial IB Question Bank!");
      startLabel.setHorizontalAlignment(JLabel.CENTER);
      levelLabel.setBounds(0, -100, width, height);
      levelLabel.setFont(new Font("Arial", Font.BOLD, 50));
      levelLabel.setForeground(Color.BLACK);
      levelLabel.setText("Select a difficulty level:");
      levelLabel.setHorizontalAlignment(JLabel.CENTER);
      selectionLabel.setBounds(0, 20, width, 60);
      selectionLabel.setFont(new Font("Arial", Font.BOLD, 30));
      selectionLabel.setForeground(Color.BLACK);
      selectionLabel.setHorizontalAlignment(JLabel.CENTER);
      answerField.setBounds((width - 300) / 2, 600, 300, 50);
      answerField.setHorizontalAlignment(JTextField.CENTER);
    questionLabel.setBounds(30, 100, width - 60, 150);
    questionLabel.setFont(new Font("Arial", Font.PLAIN, 22));
    questionLabel.setForeground(Color.BLACK);
      int gap = Math.min(10, Math.max(0, height - 2 * buttonHeight));
      int groupHeight = 2 * buttonHeight + gap;
      int maxGroupY = Math.max(0, height - groupHeight);
      int groupY = Math.max(0, Math.min(height / 2 + 55, maxGroupY));
      HL.setBounds(buttonX, groupY, buttonWidth, buttonHeight);
      HL.setHorizontalAlignment(JLabel.CENTER);
      SL.setBounds(buttonX, groupY + buttonHeight + gap, buttonWidth, buttonHeight);
      SL.setHorizontalAlignment(JLabel.CENTER);

      int selectorTop = Math.min(90, Math.max(0, height / 4));
      int actionButtonY = Math.max(0, height - buttonHeight - 40);
      int selectorBottom = Math.max(selectorTop, actionButtonY - 15);
      topicSelectionScroll.setBounds(
          20,
          selectorTop,
          Math.max(0, width - 40),
          Math.max(0, selectorBottom - selectorTop));
      int actionButtonWidth = Math.max(0, Math.min(200, (width - 60) / 2));
      int actionButtonX = Math.max(0, (width - actionButtonWidth * 2 - 20) / 2);

if (screen == 'Q') {
    backButton.setBounds(
        actionButtonX,
        actionButtonY,
        actionButtonWidth,
        buttonHeight
    );

    continueButton.setBounds(
        actionButtonX + actionButtonWidth + 20,
        actionButtonY,
        actionButtonWidth,
        buttonHeight
    );
} else {
    backButton.setBounds(
        actionButtonX,
        actionButtonY,
        actionButtonWidth,
        buttonHeight
    );

    continueButton.setBounds(
        actionButtonX + actionButtonWidth + 20,
        actionButtonY,
        actionButtonWidth,
        buttonHeight
    );
}

   }

   public void addTimer(Graphics g2d) {
    long currentElapsed = elapsedTime;

    if (timerRunning) {
        currentElapsed += System.currentTimeMillis() - startTime;
    }

    double seconds = currentElapsed / 1000.0;

    g2d.setColor(Color.BLACK);
    g2d.setFont(new Font("Arial", Font.BOLD, 20));
    g2d.drawString(
        "Time: " + new DecimalFormat("0.00").format(seconds) + " s",
        30,
        90
    );
}
 
public void checkAnswer() {
    String userInput = answerField.getText().trim();

    if (q == null) {
        return;
    }

    try {
        if (userInput.equals(q.getCorrectAnswer().trim())) {
            answerField.setText("");
            explanationLabel.setText(q.getExplanation());
            explanationLabel.setVisible(true);
            wrongLabel.setForeground(Color.GREEN);
            wrongLabel.setText("Correct!");
            wrongLabel.setVisible(true);
    
        } else {
            wrongLabel.setForeground(Color.RED);
            wrongLabel.setText("Incorrect. Try again.");
            wrongLabel.setVisible(true);
        }

    } catch (NumberFormatException e) {
        questionLabel.setText("Please enter a valid whole number.");
    }
}

   public void ActionListeners(){
        start.addActionListener(e -> { screen = 'A'; updateScreen();});
        answerField.addActionListener(e -> checkAnswer());
showTimerLabel.addActionListener(e -> {
    stopwatchVisible = !stopwatchVisible;

    if (stopwatchVisible) {
        if (!timerRunning) {
            startTime = System.currentTimeMillis();
            timerRunning = true;
        }

        showTimerLabel.setText("Hide Timer");
    } else {
        showTimerLabel.setText("Show Timer");
    }

    repaint();
});
     HL.addActionListener(e -> selectDifficulty(1));
        SL.addActionListener(e -> selectDifficulty(2));

backButton.addActionListener(e -> {
    if (screen == 'F') {
        screen = 'S';
        q = null;
        setSwing();
        updateScreen();
        repaint();

    } else if (screen == 'T') {
        screen = 'A';
        setSwing();
        updateScreen();

    } else if (screen == 'Q') {
        currentQuestionIndex++;

        if (currentQuestionIndex < questions.size()) {
            q = questions.get(currentQuestionIndex);

            // Clear feedback from the previous question
            explanationLabel.setText("");
            explanationLabel.setVisible(false);
            wrongLabel.setText("");
            wrongLabel.setVisible(false);
            answerField.setText("");

            screen = 'Q';

        } else {
            q = null;
            screen = 'F';
        }

        setSwing();
        updateScreen();
        repaint();
    }
});



continueButton.addActionListener(e -> {
    if (screen == 'T') {
        updateSelectedSubtopics();

        if (selectedSubtopics.isEmpty()) {
            selectionLabel.setText("Select at least one subtopic to continue.");
            return;
        }

        loadPracticeQuestions();

        if (questions.isEmpty()) {
            selectionLabel.setText("No questions found for those subtopics.");
            return;
        }

        screen = 'Q';
        setSwing();
        updateScreen();

    } else if (screen == 'Q' && q != null) {
        explanationLabel.setText(q.getExplanation());
        explanationLabel.setVisible(true);
    }
});
   }

  private void loadPracticeQuestions() {
    Database db = new Database();
    List<Question> allQuestions = db.readDatabase();

    questions.clear();

    for (Question question : allQuestions) {
        String databaseSubtopic = question.getSubtopic();

        if (databaseSubtopic == null) {
            continue;
        }

        // Get the code, such as "A.1", from the database
        databaseSubtopic = databaseSubtopic.trim().split("\\s+")[0];

        for (String selected : selectedSubtopics) {
            // Get the code, such as "A.1", from the checkbox selection
            String selectedCode = selected.trim().split("\\s+")[0];

            if (databaseSubtopic.equalsIgnoreCase(selectedCode)) {
                questions.add(question);
                break;
            }
        }
    }

    currentQuestionIndex = 0;

    if (!questions.isEmpty()) {
        q = questions.get(currentQuestionIndex);
        questionLabel.setText(q.getQuestion());
    } else {
        q = null;
        questionLabel.setText(
            "No questions found for the selected subtopics."
        );
    }

    questionLabel.setVisible(true);
    repaint();
}

   public void addSwing(){
        this.add(start);
        this.add(startLabel);
        this.add(HL);
        this.add(SL);
        this.add(levelLabel);
        this.add(selectionLabel);
        this.add(backButton);
        this.add(continueButton);
        this.add(topicSelectionScroll);
        this.add(answerField);
        this.add(questionLabel);
        this.add(explanationLabel);
        this.add(wrongLabel);
        this.add(formatLabel);
        this.add(showTimerLabel);
   }

   private void createTopicSelector() {
       topicSelectionPanel = new JPanel(new GridBagLayout());
       topicSelectionPanel.setBackground(Color.WHITE);
       topicCheckBoxes = new JCheckBox[topics.length];
       subtopicCheckBoxes = new JCheckBox[topics.length][];

       for (int topicIndex = 0; topicIndex < topics.length; topicIndex++) {
           Topic topic = topics[topicIndex];
           topicCheckBoxes[topicIndex] = new JCheckBox(
               topic.getCode() + ": " + topic.getName());
           topicCheckBoxes[topicIndex].setFont(topicCheckBoxes[topicIndex].getFont().deriveFont(Font.BOLD));
           topicCheckBoxes[topicIndex].setOpaque(true);
           topicCheckBoxes[topicIndex].setBackground(Color.WHITE);
           subtopicCheckBoxes[topicIndex] = new JCheckBox[topic.getSubtopics().length];

           final int selectedTopicIndex = topicIndex;
           topicCheckBoxes[topicIndex].addActionListener(e -> {
               if (!updatingSelections) {
                   updatingSelections = true;
                   for (int subtopicIndex = 0;
                           subtopicIndex < subtopicCheckBoxes[selectedTopicIndex].length;
                           subtopicIndex++) {
                       if (isSubtopicAvailable(selectedTopicIndex, subtopicIndex)) {
                           subtopicCheckBoxes[selectedTopicIndex][subtopicIndex].setSelected(
                               topicCheckBoxes[selectedTopicIndex].isSelected());
                       }
                   }
                   updatingSelections = false;
               }
           });

           String[] subtopics = topic.getSubtopics();
           for (int subtopicIndex = 0; subtopicIndex < subtopics.length; subtopicIndex++) {
               JCheckBox subtopicCheckBox = new JCheckBox(subtopics[subtopicIndex]);
               subtopicCheckBox.setOpaque(true);
               subtopicCheckBox.setBackground(Color.WHITE);
               subtopicCheckBoxes[topicIndex][subtopicIndex] = subtopicCheckBox;
               subtopicCheckBox.addActionListener(e -> {
                   if (!updatingSelections) {
                       updateTopicCheckBox(selectedTopicIndex);
                   }
               });
           }
       }

       topicSelectionScroll = new JScrollPane(topicSelectionPanel);
       topicSelectionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       topicSelectionScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       populateTopicSelector();
   }

   private void populateTopicSelector() {
       topicSelectionPanel.removeAll();
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.gridx = 0;
       constraints.weightx = 1;
       constraints.fill = GridBagConstraints.HORIZONTAL;
       constraints.anchor = GridBagConstraints.WEST;

       for (int topicIndex = 0; topicIndex < topics.length; topicIndex++) {
           boolean hasAvailableSubtopic = false;
           for (int subtopicIndex = 0; subtopicIndex < subtopicCheckBoxes[topicIndex].length;
                   subtopicIndex++) {
               JCheckBox subtopicCheckBox = subtopicCheckBoxes[topicIndex][subtopicIndex];
               if (isSubtopicAvailable(topicIndex, subtopicIndex)) {
                   hasAvailableSubtopic = true;
               } else {
                   subtopicCheckBox.setSelected(false);
               }
           }

           if (!hasAvailableSubtopic) {
               continue;
           }

           constraints.gridy = topicSelectionPanel.getComponentCount();
           constraints.insets = new Insets(8, 8, 2, 8);
           topicSelectionPanel.add(topicCheckBoxes[topicIndex], constraints);

           for (int subtopicIndex = 0; subtopicIndex < subtopicCheckBoxes[topicIndex].length;
                   subtopicIndex++) {
               if (isSubtopicAvailable(topicIndex, subtopicIndex)) {
                   constraints.gridy = topicSelectionPanel.getComponentCount();
                   constraints.insets = new Insets(1, 32, 1, 8);
                   topicSelectionPanel.add(subtopicCheckBoxes[topicIndex][subtopicIndex], constraints);
               }
           }
           updateTopicCheckBox(topicIndex);
       }

       constraints.gridy = topicSelectionPanel.getComponentCount();
       constraints.weighty = 1;
       topicSelectionPanel.add(Box.createVerticalGlue(), constraints);
       topicSelectionPanel.revalidate();
       topicSelectionPanel.repaint();
   }

   private boolean isSubtopicAvailable(int topicIndex, int subtopicIndex) {
       return difficulty == 1 || !topics[topicIndex].isHigherLevelOnly(subtopicIndex);
   }

   private void selectDifficulty(int selectedDifficulty) {
       difficulty = selectedDifficulty;
       populateTopicSelector();
       screen = 'T';
       setSwing();
       updateScreen();
   }

   private void updateTopicCheckBox(int topicIndex) {
       boolean allSelected = true;
       for (int subtopicIndex = 0; subtopicIndex < subtopicCheckBoxes[topicIndex].length;
               subtopicIndex++) {
           if (!isSubtopicAvailable(topicIndex, subtopicIndex)) {
               continue;
           }
           JCheckBox subtopicCheckBox = subtopicCheckBoxes[topicIndex][subtopicIndex];
           if (!subtopicCheckBox.isSelected()) {
               allSelected = false;
               break;
           }
       }
       updatingSelections = true;
       topicCheckBoxes[topicIndex].setSelected(allSelected);
       updatingSelections = false;
   }

   private void updateSelectedSubtopics() {
       selectedSubtopics.clear();
       for (int topicIndex = 0; topicIndex < topics.length; topicIndex++) {
           String[] subtopics = topics[topicIndex].getSubtopics();
           for (int subtopicIndex = 0; subtopicIndex < subtopics.length; subtopicIndex++) {
               if (subtopicCheckBoxes[topicIndex][subtopicIndex].isSelected()) {
                   selectedSubtopics.add(subtopics[subtopicIndex]);
               }
           }
       }
   }

   private String getSelectedTopicCodes() {
       List<String> selectedTopicCodes = new ArrayList<>();
       for (int topicIndex = 0; topicIndex < topics.length; topicIndex++) {
           String topicPrefix = topics[topicIndex].getCode() + ".";
           for (String subtopic : selectedSubtopics) {
               if (subtopic.startsWith(topicPrefix)) {
                   selectedTopicCodes.add(topics[topicIndex].getCode());
                   break;
               }
           }
       }
       return String.join(", ", selectedTopicCodes);
   }

   public List<String> getSelectedSubtopics() {
       return Collections.unmodifiableList(new ArrayList<>(selectedSubtopics));
   }
}