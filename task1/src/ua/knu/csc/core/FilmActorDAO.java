package ua.knu.csc.core;

import java.util.List;
import java.util.LinkedList;

import java.sql.*;

public class FilmActorDAO {
    private final Connection connection;
    private final Statement statement;

    public FilmActorDAO(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        statement.close();
        connection.close();
    }

    public void addFilmActor(int filmId, int actorId) throws SQLException {
        String sql = "INSERT INTO film_actor VALUES " +
                "(" + filmId + ", " + actorId + ");";

        statement.executeUpdate(sql);
    }

    public void deleteFilmActor(int filmId, int actorId) throws SQLException {
        String sql = "DELETE FROM film_actor " +
                "WHERE film_id = " + filmId + " AND actor_id = " + actorId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public List<Integer> getActorIds(int filmId) throws SQLException {
        String sql = "SELECT actor_id " +
                "FROM film_actor " +
                "WHERE film_id = " + filmId + ";";

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            LinkedList<Integer> actorIds = new LinkedList<>();

            do {
                actorIds.addFirst(resultSet.getInt("actor_id"));
            } while (resultSet.next());

            return actorIds;
        } else {
            throw new SQLException("Not found.");
        }
    }

    public List<Integer> getFilmIds(int actorId) throws SQLException {
        String sql = "SELECT film_id " +
                "FROM film_actor " +
                "WHERE actor_id = " + actorId + ";";

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            LinkedList<Integer> filmIds = new LinkedList<>();

            do {
                filmIds.addFirst(resultSet.getInt("film_id"));
            } while (resultSet.next());

            return filmIds;
        } else {
            throw new SQLException("Not found.");
        }
    }
}
