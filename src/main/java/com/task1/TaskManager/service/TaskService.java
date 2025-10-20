package com.task1.TaskManager.service;

import com.task1.TaskManager.model.Task;
import com.task1.TaskManager.model.TaskExecution;
import com.task1.TaskManager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Optional<Task> getTask(String id) {
        return repo.findById(id);
    }

    public List<Task> findByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    public Task saveTask(Task task) {
        if (!isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected!");
        }
        return repo.save(task);
    }

    public void deleteTask(String id) {
        repo.deleteById(id);
    }

    public TaskExecution runTask(String id) throws Exception {
        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!isCommandSafe(task.getCommand())) {
            throw new IllegalArgumentException("Unsafe command detected!");
        }

        TaskExecution exec = new TaskExecution();
        exec.setStartTime(new Date());

        // Execute shell command locally (mock of Kubernetes job)
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", task.getCommand());
        Process p = pb.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        p.waitFor();

        exec.setEndTime(new Date());
        exec.setOutput(output.toString());
        task.getTaskExecutions().add(exec);
        repo.save(task);
        return exec;
    }

    // Very simple allowlist check
    private boolean isCommandSafe(String cmd) {
        String lower = cmd.toLowerCase();
        String[] dangerous = {"rm", "sudo", "reboot", "shutdown", ":", ">", "<", "|", "&", ";"};
        for (String d : dangerous) {
            if (lower.contains(d)) return false;
        }
        return true;
    }
}
