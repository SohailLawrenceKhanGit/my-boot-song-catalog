package org.example.mybootsongcatalog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepo extends JpaRepository<Song, Long> {

    /*
     * DERIVED QUERIES
     * The proxy will attempt to implement this method provided I adhere to some rules:
     * - If the method starts with findBy/getBy/readBy then a select query is assumed
     * - If the word that follows By matches a field name then the query will include a where clause
     * - And and Or are recognised keywords
     * This list of rules goes on...
     * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
     */
    Optional<Song> findByNameAndArtistAndLive(String name, String artist, boolean live);

    List<Song> findByNameContaining(String partialName);
}
