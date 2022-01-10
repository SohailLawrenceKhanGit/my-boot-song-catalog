package org.example.mybootsongcatalog;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

// @Service is a more descriptive version of @Component
@Service
@Transactional(readOnly = true)
public class SongService {

    private SongRepo repo;

    public SongService(SongRepo repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = false, rollbackFor = DuplicateSongException.class)
    public Song add(Song song) throws DuplicateSongException {
        if (repo.findByNameAndArtistAndLive(song.getName(), song.getArtist(), song.isLive()).isPresent()) {
            throw new DuplicateSongException();
        }
        return repo.save(song);
    }

    @Transactional(readOnly = false, rollbackFor = NoSuchSongException.class)
    public Song update(Song song) throws NoSuchSongException {
        if (repo.existsById(song.getId())) {
            // the JpaRepo will do an update IF the Song has a valid ID
            return repo.save(song);
        } else {
            throw new NoSuchSongException();
        }
    }

    @Transactional(readOnly = false, rollbackFor = NoSuchSongException.class)
    public Song deleteById(long id) throws NoSuchSongException {
        if (repo.existsById(id)) {
            var song = repo.findById(id);
            repo.deleteById(id);
            return song.get();
        } else {
            throw new NoSuchSongException();
        }
    }

    public List<Song> findAll() {
        return repo.findAll();
    }

    public List<Song> findAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).toList();
    }

    public List<Song> findByPartialName(String partialName) {
        return repo.findByNameContaining(partialName);
    }

    public Song findById(long id) throws NoSuchSongException {
        return repo.findById(id).orElseThrow(() -> new NoSuchSongException());
    }
}
