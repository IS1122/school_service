package com.example.school_service.controller;

import com.example.school_service.entity.Component;
import com.example.school_service.response.CustomResponse;
import com.example.school_service.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/component")
public class ComponentController {
    @Autowired
    ComponentService componentService;

    @GetMapping("/getAll")
    public CustomResponse getAll(){
        return componentService.getAll();
    }
    @GetMapping("/getById/{componentId}")
    public CustomResponse getById(@PathVariable Integer componentId){
        return componentService.getById(componentId);
    }
    @PostMapping("/addComponent")
    public CustomResponse addComponent(@RequestBody Component component){
        return componentService.addComponent(component);
    }
    @DeleteMapping("/deleteComponent/{componentId}")
    public CustomResponse deleteComponent(@PathVariable Integer componentId){
        return componentService.deleteComponent(componentId);
    }
}
