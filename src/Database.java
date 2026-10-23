
package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    public void readDatabase() {

        String jdbcUrl = "jdbc:sqlite:physics.db";
        String sql = "SELECT id, level, topic, subtopic,question, correctAnswer, explanation FROM questions";

        try (Connection connection = DriverManager.getConnection(jdbcUrl);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                int id = result.getInt("id");
                String level = result.getString("level");
                String topic = result.getString("topic");
                String subtopic = result.getString("subtopic");
                String question = result.getString("question");
                String correctAnswer = result.getString("correctAnswer");
                String explanation = result.getString("explanation");

                System.out.println("ID: " + id + ", Level: " + level + ", Topic: " + topic + ", Subtopic: " + subtopic + ", Question: " + question + ", Correct Answer: " + correctAnswer + ", Explanation: " + explanation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
