package org.example.mybootsongcatalog;

import org.example.mybootsongcatalog.archive.SongRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ContainerConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testContainerIsConfiguredCorrectly() {
        assertNotNull(applicationContext.getBean(SongRepo.class));
        assertNotNull(applicationContext.getBean(SongService.class));
    }
}
