package hexlet.code.repository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import hexlet.code.model.Url;

public class UrlRepository extends BaseRepository {
    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO Url (name, createdAt) VALUES (?,?)";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, url.getName());
            preparedStatement.setTimestamp(2, url.getCreatedAt());
            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> find(String name) throws SQLException {
        var sql = "SELECT * FROM Url WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var id = resultSet.getInt("id");
                var createdAt = resultSet.getTimestamp("createdAt");
                var url = new Url(name, createdAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Optional<Url> findById(int id) throws SQLException {
        var sql = "SELECT * FROM Url WHERE id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            var resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");
                var url = new Url(name, createdAt);
                url.setId(id);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        var sql = "SELECT * FROM Url";
        try (var conn = dataSource.getConnection();
             var preparedStatement = conn.prepareStatement(sql)) {
            var resultSet = preparedStatement.executeQuery();
            var result = new ArrayList<Url>();
            while (resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("createdAt");
                var url = new Url(name, createdAt);
                url.setId(id);
                result.add(url);
            }
            return result;
        }
    }
}
