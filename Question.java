public class Question {
    
    private int id;
    private String level;
    private String topic;
    private String subtopic;
    private String questionText;
    private String answerText;
    private String explanationText;
    private int marks;

public Question(int id, String level, String topic, String subtopic, String questionText, String answerText, String explanationText, int marks) {
        this.id = id;
        this.level = level;
        this.topic = topic;
        this.subtopic = subtopic;
        this.questionText = questionText;
        this.answerText = answerText;
        this.explanationText = explanationText;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLevel() {
        
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }
    public String getSubtopic() {
        return subtopic;
    }
    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }
    public String getQuestionText() {
        return questionText;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public String getAnswerText() {
        return answerText;
    }
    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
    public String getExplanationText() {
        return explanationText;
    }
    public void setExplanationText(String explanationText) {
        this.explanationText = explanationText;
    }
    public int getMarks() {
        return marks;
    }
    public void setMarks(int marks) {
        this.marks = marks;
    }
}
