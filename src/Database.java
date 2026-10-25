
package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public List<Question> readDatabase() {

        List<Question> questions = new ArrayList<>();

        String jdbcUrl = "jdbc:sqlite:physics.db";

        String sql = "SELECT id, level, topic, subtopic, question, " +
                     "correctAnswer, explanation FROM questions";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {

                Question q = new Question();

                q.setId(result.getInt("id"));
                q.setLevel(result.getString("level"));
                q.setTopic(result.getString("topic"));
                q.setSubtopic(result.getString("subtopic"));
                q.setQuestion(result.getString("question"));
                q.setCorrectAnswer(result.getString("correctAnswer"));
                q.setExplanation(result.getString("explanation"));

                questions.add(q);
            }

            System.out.println("Loaded " + questions.size() + " questions.");

        } catch (SQLException e) {
            System.out.println("Error loading questions from database:");
            e.printStackTrace();
        }

        return questions;
    }
}
