package com.breno.taskmanager.controller;

import com.breno.taskmanager.exception.TaskNotFoundException;
import com.breno.taskmanager.model.Task;
import com.breno.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/taskmanager")
    Task newTask(@RequestBody Task newTask) {
        return taskRepository.save(newTask);
    }

    @GetMapping("/taskmanager")
    List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/taskmanager/{id}")
    Task getTaskById(@PathVariable Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()->new TaskNotFoundException(id));
    }



    @PutMapping("/taskmanager/{id}")
    Task updateTask(@RequestBody Task newTask, @PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTask(newTask.getTask());
                    task.setResponsible(newTask.getResponsible());
                    task.setStatus(newTask.getStatus());
                    task.setDeadline(newTask.getDeadline());
                    return taskRepository.save(task);
                }).orElseThrow(()-> new TaskNotFoundException(id));
    }

    @DeleteMapping("/taskmanager/{id}")
    String deleteTask(@PathVariable Long id) {
        if(!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
        return "Task with "+id+" has been deleted success.";
    }

}
