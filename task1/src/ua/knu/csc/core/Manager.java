package ua.knu.csc.core;

import ua.knu.csc.entity.Actor;
import ua.knu.csc.entity.Film;

import java.sql.SQLException;

public class Manager {
    private final ActorDAO actorDAO;
    private final FilmDAO filmDAO;
    private final FilmActorDAO filmActorDAO;

    public Manager(String url, String user, String password) throws SQLException {
        actorDAO = new ActorDAO(url, user, password);
        filmDAO = new FilmDAO(url, user, password);
        filmActorDAO = new FilmActorDAO(url, user, password);
    }

    public void stop() throws SQLException {
        actorDAO.stop();
        filmDAO.stop();
        filmActorDAO.stop();
    }

    public void addActor(int actorId, String forename, String surname) throws SQLException {
        actorDAO.addActor(actorId, forename, surname);
    }

    public void addActor(Actor actor) throws SQLException {
        actorDAO.addActor(actor);
    }

    public void updateActor(int actorId, String forename, String surname) throws SQLException {
        actorDAO.updateActor(actorId, forename, surname);
    }

    public void updateActor(Actor actor) throws SQLException {
        actorDAO.updateActor(actor);
    }

    public void deleteActor(int actorId) throws SQLException {
        actorDAO.deleteActor(actorId);
    }

    public Actor getActor(int actorId) throws SQLException {
        return actorDAO.getActor(actorId);
    }

    public void addFilm(int filmId, String title, String country, int year) throws SQLException {
        filmDAO.addFilm(filmId, title, country, year);
    }

    public void addFilm(Film film) throws SQLException {
        filmDAO.addFilm(film);
    }

    public void updateFilm(int filmId, String title, String country, int year) throws SQLException {
        filmDAO.updateFilm(filmId, title, country, year);
    }

    public void updateFilm(Film film) throws SQLException {
        filmDAO.updateFilm(film);
    }

    public void deleteFilm(int filmId) throws SQLException {
        filmDAO.deleteFilm(filmId);
    }

    public Film getFilm(int filmId) throws SQLException {
        return filmDAO.getFilm(filmId);
    }

    public void addFilmActor(int filmId, int actorId) throws SQLException {
        filmActorDAO.addFilmActor(filmId, actorId);
    }

    public void deleteFilmActor(int filmId, int actorId) throws SQLException {
        filmActorDAO.deleteFilmActor(filmId, actorId);
    }
}
