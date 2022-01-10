package org.example.mybootsongcatalog.archive;

import org.example.mybootsongcatalog.Song;

import java.util.List;

/*
 * An interface IS an abstract class but...
 * an interface can ONLY have abstract methods
 * whilst an abstract class can have fields and concrete methods
 */
public interface SongRepo {

    Song add(Song song);

    List<Song> findAll();
}
