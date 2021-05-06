package ua.knu.csc.core;

import ua.knu.csc.entity.Film;

import java.sql.*;

public class FilmDAO {
    private final Connection connection;
    private final Statement statement;

    public FilmDAO(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void stop() throws SQLException {
        statement.close();
        connection.close();
    }

    public void addFilm(int filmId, String title, String country) throws SQLException {
        String sql = "INSERT INTO film VALUES " +
                "(" + filmId + ", '" + title + "', '" + country + "');";

        statement.executeUpdate(sql);
    }

    public void updateFilm(int filmId, String title, String country) throws SQLException {
        String sql = "UPDATE film " +
                "SET title = '" + title + "', country = '" + country + "' " +
                "WHERE film_id = " + filmId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public void deleteFilm(int filmId) throws SQLException {
        String sql = "DELETE FROM film WHERE film_id = " + filmId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public Film getFilm(int filmId) throws SQLException {
        String sql = "SELECT title, country " +
                "FROM film " +
                "WHERE film_id = " + filmId + ";";

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            Film film = new Film();

            film.setFilmId(filmId);
            film.setTitle(resultSet.getString("title"));
            film.setCountry(resultSet.getString("country"));

            return film;
        } else {
            throw new SQLException("Not found.");
        }
    }
}
