package org.example.mybootsongcatalog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/songs")
public class SongController {

    private SongService service;

    public SongController(SongService service) {
        this.service = service;
    }

    @GetMapping
    public String showSongsPage(Model model) {
        model.addAttribute("songs", service.findAll());
        return "songs";
    }
}
