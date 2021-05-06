package ua.knu.csc.core;

import ua.knu.csc.entity.Actor;

import java.sql.*;

public class ActorDAO {
    private final Connection connection;
    private final Statement statement;

    public ActorDAO(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        statement.close();
        connection.close();
    }

    public void addActor(int actorId, String forename, String surname) throws SQLException {
        String sql = "INSERT INTO actor VALUES " +
                "(" + actorId + ", '" + forename + "', '" + surname + "');";

        statement.executeUpdate(sql);
    }

    public void updateActor(int actorId, String forename, String surname) throws SQLException {
        String sql = "UPDATE actor " +
                "SET forename = '" + forename + "', surname = '" + surname + "' " +
                "WHERE actor_id = " + actorId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public void deleteActor(int actorId) throws SQLException {
        String sql = "DELETE FROM actor WHERE actor_id = '" + actorId + "';";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public Actor getActor(int actorId) throws SQLException {
        String sql = "SELECT forename, surname " +
                "FROM actor " +
                "WHERE actor_id = " + actorId + ";";

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            Actor actor = new Actor();

            actor.setActorId(actorId);
            actor.setForename(resultSet.getString("forename"));
            actor.setSurname(resultSet.getString("surname"));

            return actor;
        } else {
            throw new SQLException("Not found.");
        }
    }
}
