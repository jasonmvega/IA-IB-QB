import java.sql.*;

public class Database {

    public static Question getQuestion(int id) {
        String sql = """
            SELECT id, topic, question_text,
                   option_a, option_b, option_c, option_d,
                   correct_answer, explanation
            FROM questions
            WHERE id = ?
            """;

        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Question(
                        rs.getInt("id"),
                        rs.getString("topic"),
                        rs.getString("question_text"),
                        new String[]{
                            rs.getString("option_a"),
                            rs.getString("option_b"),
                            rs.getString("option_c"),
                            rs.getString("option_d")
                        },
                        rs.getString("correct_answer"),
                        rs.getString("explanation")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}