package org.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.toDoList.model.Tasks;

import javax.swing.*;
import java.util.*;

@Controller
public class IndexController {
    static Map<Integer, Tasks> tasksMap = new HashMap<>();
    int id = 1;
    String name;

    // Метод для удаления задачи по ID
    public void taskDelete(int id) {
        tasksMap.remove(id);
    }

    // Главная страница - передаем список задач
    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("taskList", tasksMap.values());
        return "index.html";
    }

    // Страница создания меню
    @GetMapping("/createMenu")
    public String createMenu(HttpServletRequest request) {
        name = request.getParameter("name");
        return "createMenu.html";
    }

    // Метод для создания задачи
    @PostMapping("/createTask")
    public String createTask(Model model, HttpServletRequest request) {
        String taskId = UUID.randomUUID().toString();
        String taskDescription = request.getParameter("task");

            Tasks task = new Tasks();
            task.setPersonalUrl("/task/" + taskId);
            task.setId(id);
            task.setName(name);
            task.setTask(taskDescription);

            tasksMap.put(id, task);
            id++;

        model.addAttribute("taskList", tasksMap.values());
        return "redirect:/";
    }

    @PostMapping("/updateTask/{taskHref}")
    public String updateTask(@PathVariable String taskHref, @RequestParam String task_name, @RequestParam String task_description, Model model) {
        Tasks foundTask = null;

        for (Tasks task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskHref)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask != null) {
            // Обновляем название и описание задачи
            foundTask.setName(task_name);
            foundTask.setTask(task_description);
        }

        return "redirect:/"; // Перенаправляем на главную страницу
    }


    @GetMapping("/task/{taskHref}")
    public String getTaskDetails(@PathVariable String taskHref, Model model) {
        Tasks foundTask = null;

        for (Tasks task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskHref)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask != null) {
            model.addAttribute("taskUrl", foundTask.getPersonalUrl());
            model.addAttribute("task", foundTask.getTask());
            model.addAttribute("taskName", foundTask.getName());
            return "taskDetails.html";
        } else {
            return "redirect:/";
        }
    }

    // Метод удаления задачи
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, Model model) {
        taskDelete(taskId);
        model.addAttribute("taskList", tasksMap.values());
        return "index.html";
    }

    @GetMapping("/changeTaskDescription")
    public String changeTaskDescription(@RequestParam String taskHref, Model model) {
        Tasks thisTask = null;
        for (Tasks task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskHref)) {
                thisTask = task;
                break;
            }
        } if (thisTask != null) {
            model.addAttribute("task_name", thisTask.getName());
            model.addAttribute("task_description", thisTask.getTask());
            model.addAttribute("task_url", thisTask.getPersonalUrl().replace("/task/", ""));
        }

        return "changeTaskDescription.html";
    }
}

