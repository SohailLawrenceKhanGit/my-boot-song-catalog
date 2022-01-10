package org.example.mybootsongcatalog;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

// @Repository is a more descriptive version of @Component
@Repository
public class InMemorySongRepo implements SongRepo {

    private List<Song> songs;

    public InMemorySongRepo() {
        songs = new LinkedList<>();
    }

    @Override
    public Song add(Song song) {
        songs.add(song);
        return song;
    }

    @Override
    public List<Song> findAll() {
        return songs;
    }
}
