package com.example.school_service.service;

import com.example.school_service.entity.Component;
import com.example.school_service.entity.Role;
import com.example.school_service.entity.User;
import com.example.school_service.repository.ComponentRepository;
import com.example.school_service.repository.UserRepository;
import com.example.school_service.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComponentService {
    @Autowired
    ComponentRepository componentRepository;
    @Autowired
    UserRepository userRepository;

    public CustomResponse addComponent(Component component) {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            Component newComponent = Component.builder().name(component.getName())
                    .description(component.getDescription())
                    .stock(component.getStock())
                    .price(component.getPrice())
                    .build();
            componentRepository.save(newComponent);
            return CustomResponse.builder().message("Added Successfully").data(newComponent).build();
        }
        else{
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse updateComponent(Component component, Integer componentId) {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            Optional<Component> componentFound = componentRepository.findById(componentId);
            if (componentFound.isPresent()) {
                componentFound.get().setDescription(component.getDescription());
                componentFound.get().setName(component.getName());
                componentFound.get().setPrice(component.getPrice());
                componentFound.get().setStock(component.getStock());
                componentRepository.save(componentFound.get());
                return CustomResponse.builder().data(componentFound).message("Updated Successfully").build();
            } else {
                return CustomResponse.builder().message("Component with id: " + componentId + " not found!").build();
            }
        }
        else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse deleteComponent(Integer componentId) {
        Optional<Component> component = componentRepository.findById(componentId);
        if (component.isPresent()) {
            componentRepository.delete(component.get());
            return CustomResponse.builder().data(component).message("Deleted Successfully").build();
        } else {
            return CustomResponse.builder().message("Component with id: " + componentId + " not found!").build();
        }
    }

    public CustomResponse getAll() {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            return CustomResponse.builder().data(componentRepository.findAll()).build();
        }
        else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse getById(Integer componentId) {
        return CustomResponse.builder().data(componentRepository.findById(componentId)).build();
    }
}
