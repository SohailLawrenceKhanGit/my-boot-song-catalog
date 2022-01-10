package org.example.mybootsongcatalog;

import org.example.mybootsongcatalog.archive.SongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class App implements CommandLineRunner {

//	@Autowired
//	private DataSource dataSource;

	@Autowired
	private SongService service;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * We're forced to override this method because we implemented CommandLineRunner.
		 * It will be executed on startup and enables us to execute initialisation code.
		 *
		 * When using JpaRepos and an embedded database Hibernate will create and drop the schema for us.
		 */

//		var connection = dataSource.getConnection();
//		var statement = connection.createStatement();
//		statement.execute("create table if not exists songs ( " +
//				"id int auto_increment, " +
//				"name varchar, " +
//				"artist varchar, " +
//				"genre varchar, " +
//				"live boolean, " +
//				"rating varchar)");
//		connection.close();

		service.add(new Song("Red House", "Hendrix", MusicGenre.BLUES, false));
		service.add(new Song("Back in Black", "ACDC", MusicGenre.ROCK, false));
		service.add(new Song("Run to the Hills", "Iron Maiden", MusicGenre.METAL, false));
	}
}
