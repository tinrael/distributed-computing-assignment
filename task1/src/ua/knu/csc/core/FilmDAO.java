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

    public void addFilm(int filmId, String title, String country, int year) throws SQLException {
        String sql = "INSERT INTO film VALUES " +
                "(" + filmId + ", '" + title + "', '" + country + "', " + year + ");";

        statement.executeUpdate(sql);
    }

    public void addFilm(Film film) throws SQLException {
        addFilm(film.getFilmId(), film.getTitle(), film.getCountry(), film.getYear());
    }

    public void updateFilm(int filmId, String title, String country, int year) throws SQLException {
        String sql = "UPDATE film " +
                "SET title = '" + title + "', country = '" + country + "', year = " + year + " " +
                "WHERE film_id = " + filmId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public void updateFilm(Film film) throws SQLException {
        updateFilm(film.getFilmId(), film.getTitle(), film.getCountry(), film.getYear());
    }

    public void deleteFilm(int filmId) throws SQLException {
        String sql = "DELETE FROM film WHERE film_id = " + filmId + ";";

        int rowsAffectedCount = statement.executeUpdate(sql);

        if (rowsAffectedCount == 0) {
            throw new SQLException("Not found.");
        }
    }

    public Film getFilm(int filmId) throws SQLException {
        String sql = "SELECT title, country, year " +
                "FROM film " +
                "WHERE film_id = " + filmId + ";";

        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            Film film = new Film();

            film.setFilmId(filmId);
            film.setTitle(resultSet.getString("title"));
            film.setCountry(resultSet.getString("country"));
            film.setYear(resultSet.getInt("year"));

            return film;
        } else {
            throw new SQLException("Not found.");
        }
    }
}
