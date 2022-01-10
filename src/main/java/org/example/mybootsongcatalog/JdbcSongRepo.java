package org.example.mybootsongcatalog;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Repository
@Primary
public class JdbcSongRepo implements SongRepo {

    private DataSource dataSource;

    public JdbcSongRepo(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public Song add(Song song) {
        try {
            var connection = getConnection();
            var statement = connection.prepareStatement(
                    "insert into songs (name, artist, genre, live, rating) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, song.getName());
            statement.setString(2, song.getArtist());
            statement.setString(3, song.getGenre().name());
            statement.setBoolean(4, song.isLive());
            statement.setString(5, song.getRating().name());
            statement.execute();
            var resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                song.setId(resultSet.getLong("id"));
            } else {
                throw new RuntimeException("Song ID not generated");
            }
            connection.close();
            return song;
        } catch (SQLException e) {
            // by re-throwing as a RuntimeException we're not forced to handle it
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Song> findAll() {
        var songs = new LinkedList<Song>();
        try {
            var connection = getConnection();
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("select * from songs");
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var artist = resultSet.getString("artist");
                var genre = MusicGenre.valueOf(resultSet.getString("genre"));
                var live = resultSet.getBoolean("live");
                var rating = Rating.valueOf(resultSet.getString("rating"));
                var song = new Song(name, artist, genre, live);
                song.setId(id);
                song.setRating(rating);
                songs.add(song);
            }
            connection.close();
            return songs;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
