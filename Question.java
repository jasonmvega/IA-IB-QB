public class Question {
    public int id;
    public String topic;
    public String text;
    public String[] options;
    public String correctAnswer;
    public String explanation;

    public Question(int id, String topic, String text,
                    String[] options, String correctAnswer,
                    String explanation) {
        this.id = id;
        this.topic = topic;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
    }

    
}