package ua.knu.csc;

import ua.knu.csc.core.ActorDAO;
import ua.knu.csc.core.FilmActorDAO;
import ua.knu.csc.core.FilmDAO;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost/films";
        String user = "postgres";
        String password = "postgres";

        try {
            ActorDAO actorDAO = new ActorDAO(url, user, password);
            FilmActorDAO filmActorDAO = new FilmActorDAO(url, user, password);
            FilmDAO filmDAO = new FilmDAO(url, user, password);

            actorDAO.addActor(24, "Tom", "Cruise");
            filmDAO.addFilm(56, "Oblivion", "United States", 2013);
            filmActorDAO.addFilmActor(56, 24);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
