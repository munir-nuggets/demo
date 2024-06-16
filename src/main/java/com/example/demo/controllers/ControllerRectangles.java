package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Model.Rectangle;
import com.example.demo.Model.RectangleRepo;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/rectangles")
public class ControllerRectangles {

    @Autowired
    private RectangleRepo rectangleRepository;

    @GetMapping("/view")
    public String listRectangles(Model model) {
        System.out.println("rectangles/view opened");
        List<Rectangle> rectangles = rectangleRepository.findAll();
        model.addAttribute("rectangles", rectangles);
        return "showAll";
    }

    @PostMapping("/add")
    public String postMethodName(@RequestParam Map<String, String> newRect, HttpServletResponse response) {
        String name = newRect.get("name");
        int width = Integer.parseInt(newRect.get("width"));
        int height = Integer.parseInt(newRect.get("height"));
        String color = newRect.get("color");
        Rectangle rectangle = new Rectangle(name, width, height, color);
        rectangleRepository.save(rectangle);
        return "redirect:/rectangles/view";
    }

    @GetMapping("/delete/{id}")
    public String deleteRectangle(@PathVariable Long id) {
        rectangleRepository.deleteById(id);
        return "redirect:/rectangles/view";
    }

    
    @GetMapping("/edit/{id}")
    public String editRectangle(@PathVariable Long id) {
        // Rectangle rectangle = rectangleRepository.getReferenceById(id);
        return "editRectangle";
    }

    @GetMapping("/display/{id}")
    public String viewRectangleDetails(@PathVariable Long id, Model model) {
        Rectangle rectangle = rectangleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rectangle Id:" + id));
        model.addAttribute("rectangle", rectangle);
        return "rectangleDetails";
    }

    @PostMapping("/update")
    public String updateRectangle(@ModelAttribute("rectangle") Rectangle rectangle) {
        Rectangle existingRectangle = rectangleRepository.findById(rectangle.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid rectangle Id:" + rectangle.getId()));
        existingRectangle.setName(rectangle.getName());
        existingRectangle.setWidth(rectangle.getWidth());
        existingRectangle.setHeight(rectangle.getHeight());
        existingRectangle.setColor(rectangle.getColor());
        rectangleRepository.save(rectangle);
        return "redirect:/rectangles/view";
    }
}