package src;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game extends JPanel implements Runnable {
    private BufferedImage back;
    private char screen = 'S';
    private JButton start, HL, SL;
    private JButton backButton, continueButton;
    private JLabel startLabel, levelLabel, selectionLabel;
    private JPanel topicSelectionPanel;
    private JScrollPane topicSelectionScroll;
    private JCheckBox[] topicCheckBoxes;
    private JCheckBox[][] subtopicCheckBoxes;
    private JTextField answerField;
    private int difficulty;
    private boolean updatingSelections;
    private Question q;
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
        answerField = new JTextField();
        Database db = new Database();
        db.readDatabase();
        q=new Question();
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


    public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D twoDgraph = (Graphics2D) g;

        if (back == null) 
            back = (BufferedImage)(createImage(getWidth(), getHeight()));
        
        Graphics g2d = back.createGraphics();
        
        g2d.clearRect(0, 0, getSize().width, getSize().height);
        
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
            backButton.setVisible(false);
            continueButton.setVisible(false);
            answerField.setVisible(true);
            g2d.drawString("Question: " + q.getQuestion(), 50, 50);
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
      backButton.setBounds(
          screen == 'Q' || screen == 'P' ? buttonX : actionButtonX,
          actionButtonY,
          screen == 'Q' || screen == 'P' ? buttonWidth : actionButtonWidth,
          buttonHeight);
      continueButton.setBounds(
          actionButtonX + actionButtonWidth + 20,
          actionButtonY,
          actionButtonWidth,
          buttonHeight);

   }

   public void checkAnswer(){
    String userInput = answerField.getText();
    try{
        int userAnswer = Integer.parseInt(userInput);
        if (userAnswer == q.getAnswer()) {
            answerField.setText("");
            q=new Question(difficulty.getValue());
   }

   public void ActionListeners(){
        start.addActionListener(e -> { screen = 'A'; updateScreen();});
        HL.addActionListener(e -> selectDifficulty(1));
        SL.addActionListener(e -> selectDifficulty(2));
        backButton.addActionListener(e -> {
            if (screen == 'T') {
                screen = 'A';
            } else if (screen == 'Q') {
                screen = 'T';
            }
            setSwing();
            updateScreen();
        });
        continueButton.addActionListener(e -> {
            updateSelectedSubtopics();
            if (selectedSubtopics.isEmpty()) {
                selectionLabel.setText("Select at least one subtopic to continue.");
                return;
            }
            screen = 'Q';
            setSwing();
            updateScreen();
        });
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