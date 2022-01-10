package org.example.mybootsongcatalog;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongApiController {

    private SongService service;

    public SongApiController(SongService service) {
        this.service = service;
    }

    @ExceptionHandler(NoSuchSongException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleNoSuchSongException() {}

    @ExceptionHandler(DuplicateSongException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void handleDuplicateSongException() {}

    @GetMapping
    public List<Song> getAllSongs(
            @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> size) {

        // findAll is overloaded so that we can pass in a Pageable object
        return service.findAll(page.orElse(0), size.orElse(5));
    }

    @GetMapping("/{id}") // <!-- the path specified here is appended to the class-level path
    public Song getSongById(@PathVariable long id) throws NoSuchSongException {
        return service.findById(id);
    }

    @PostMapping
    public Song addNewSong(@RequestBody Song song) throws DuplicateSongException {
        return service.add(song);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable long id, @RequestBody Song song) throws NoSuchSongException {
        song.setId(id); // <!-- ensure the Song object has its ID set
        return service.update(song);
    }

    @DeleteMapping("/{id}")
    public Song deleteSongById(@PathVariable long id) throws NoSuchSongException {
        return service.deleteById(id);
    }
}
