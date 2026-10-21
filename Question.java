import java.awt.*;
import java.util.Random;

public class Question {
    private int a1, b1, c1, a2, b2, c2, a3, b3, c3, a4, b4;
    public int answer, difficulty;
    private Random random;
    public String message = "";

    public Question() {
        random = new Random();
        difficulty = 1;
        assignValues();
    }

    public Question(int difficulty) {
        this(); 
        setDifficulty(difficulty); 
    }


    public int getRandomNumberA() {
        if (difficulty == 1) {
            return random.nextInt(11);
        } else if (difficulty == 2) {
            return random.nextInt(31);
        } else {
            return random.nextInt(51);
        }
    }
    
    public int getRandomNumberS() {
        if (difficulty == 1) {
            return random.nextInt(11);
        } else if (difficulty == 2) {
            return random.nextInt(31);
        } else {
            return random.nextInt(51);
        }
    }

    public int getRandomNumberM() {
        if (difficulty == 1|| difficulty == 4) {
            return random.nextInt(9);
        } else if (difficulty == 2) {
            return random.nextInt(13);
        } else {
            return random.nextInt(16);
        }
    }

    public void getRandomNumberD() {
        if (difficulty == 1) {
            b4 = random.nextInt(6) + 1;
            a4 = b4 * (random.nextInt(8) + 2); 
        } else if (difficulty == 2) {
            b4 = random.nextInt(8) + 3; 
            a4 = b4 * (random.nextInt(10) + 3); 
        } else if (difficulty == 3) {
            b4 = random.nextInt(9) + 5;
            a4 = b4 * (random.nextInt(12) + 4); 
        } else {
            b4 = random.nextInt(10) + 7; 
            a4 = b4 * (random.nextInt(15) + 6); 
        }
    }



    public void getQuestionA(Graphics g2d, int x, int y) {
        if (difficulty <= 3) {
            message = "What is " + a1 + " + " + b1 + " ?";
            answer = a1 + b1;
        } else {
            message = "What is " + a1 + " + " + b1 + " + " + c1 + " ?";
            answer = a1 + b1 + c1;
        }
    }

    public void getQuestionS(Graphics g2d, int x, int y) {
        if (difficulty <= 3) {
            message = "What is " + a2 + " - " + b2 + " ?";
            answer = a2 - b2;
        } else {
            message = "What is " + a2 + " - " + b2 + " - " + c2 + " ?";
            answer = a2 - b2 - c2;
        }
    }

    public void getQuestionM(Graphics g2d, int x, int y) {
        if (difficulty <= 3) {
            message = "What is " + a3 + " × " + b3 + " ?";
            answer = a3 * b3;
        } else {
            message = "What is " + a3 + " × " + b3 + " × " + c3 + " ?";
            answer = a3 * b3 * c3;
        }
    }

    public void getQuestionD(Graphics g2d, int x, int y) {
        message = "What is " + a4 + " ÷ " + b4 + " ?";
        answer = a4 / b4; 
    }


    public int getAnswer() {
        return answer;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        assignValues();
    }

    public void assignValues() {
        a1 = getRandomNumberA();
        b1 = getRandomNumberA();
        c1 = getRandomNumberA();

        a2 = getRandomNumberS();
        b2 = getRandomNumberS();
        c2 = getRandomNumberS();

        if (b2 > a2) {
            int temp = a2;
            a2 = b2;
            b2 = temp;
        }
        if (c2 > b2) {
            int temp = b2;
            b2 = c2;
            c2 = temp;
        }
        if (b2 > a2) {
            int temp = a2;
            a2 = b2;
            b2 = temp;
        }

        a3 = getRandomNumberM();
        b3 = getRandomNumberM();
        c3 = getRandomNumberM();

        getRandomNumberD();
    }

}
