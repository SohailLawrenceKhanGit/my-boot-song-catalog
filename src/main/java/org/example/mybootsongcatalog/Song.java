package org.example.mybootsongcatalog;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String artist;

    @Enumerated(EnumType.STRING)
    private MusicGenre genre;

    private boolean live;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    public Song(String name, String artist, MusicGenre genre, boolean live) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.live = live;
        this.rating = Rating.NOT_RATED;
    }

    // By default Hibernate requires a no-args constructor to build objects from DB result sets
    private Song() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return isLive() == song.isLive() && Objects.equals(getName(), song.getName()) && Objects.equals(getArtist(), song.getArtist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getArtist(), isLive());
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", genre=" + genre +
                ", live=" + live +
                ", rating=" + rating +
                '}';
    }

    public long getId() {
        return id;
    }

    public Song setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Song setName(String name) {
        this.name = name;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public Song setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Song setGenre(MusicGenre genre) {
        this.genre = genre;
        return this;
    }

    public boolean isLive() {
        return live;
    }

    public Song setLive(boolean live) {
        this.live = live;
        return this;
    }

    public Rating getRating() {
        return rating;
    }

    public Song setRating(Rating rating) {
        this.rating = rating;
        return this;
    }
}
