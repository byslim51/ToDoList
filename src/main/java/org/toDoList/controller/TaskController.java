package org.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.toDoList.model.Task;
import org.toDoList.model.TaskStatus;

import java.util.*;

@Controller
public class TaskController {
    static Map<Integer, Task> tasksMap = new HashMap<>();
    List<TaskStatus> statusList = new ArrayList<>();
    int id = 1;
    String name;


    public void setStatusList() {
        statusList.add(TaskStatus.NEW);
        statusList.add(TaskStatus.IN_PROGRESS);
        statusList.add(TaskStatus.COMPLETE);
    }

    // Метод для удаления задачи по ID
    public void taskDelete(int id) {
        tasksMap.remove(id);
    }

    // Главная страница - передаем список задач
    @GetMapping("/")
    public String getTaskMenu(Model model) {
        model.addAttribute("taskList", tasksMap.values());
        return "index.html";
    }

    // Страница создания меню
    @GetMapping("/createMenu")
    public String createMenu(Model model,HttpServletRequest request) {
        model.addAttribute("userMap", UserController.getUserMap().values());
        name = request.getParameter("name");
        return "createMenu.html";
    }

    // Метод для создания задачи
    @PostMapping("/createTask")
    public String createTask(Model model, HttpServletRequest request) {
        String taskId = UUID.randomUUID().toString();
        String taskDescription = request.getParameter("task");
        String userName = request.getParameter("userType");

            Task task = new Task();
            task.setPersonalUrl("/task/" + taskId);
            task.setId(id);
            task.setName(name);
            task.setTask(taskDescription);
            task.setUserName(userName);
            task.setStatus(TaskStatus.NEW);

            tasksMap.put(id, task);
            id++;

        model.addAttribute("taskList", tasksMap.values());
        return "redirect:/";
    }

    @PostMapping("/updateTask/{taskHref}")
    public String updateTask(@PathVariable String taskHref, @RequestParam String task_name, @RequestParam String task_description, Model model, HttpServletRequest request) {
        Task foundTask = null;


        for (Task task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskHref)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask != null) {
            // Обновляем название и описание задачи
            foundTask.setName(task_name);
            foundTask.setTask(task_description);
            foundTask.setStatus(TaskStatus.valueOf(request.getParameter("status")));
            foundTask.setUserName(request.getParameter("userType"));
        }

        return "redirect:/"; // Перенаправляем на главную страницу
    }


    @GetMapping("/task/{taskHref}")
    public String getTaskDetails(@PathVariable String taskHref, Model model) {
        Task foundTask = null;

        for (Task task : tasksMap.values()) {
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
        if (statusList.isEmpty()) {
            setStatusList();
        }
        Task thisTask = null;
        for (Task task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskHref)) {
                thisTask = task;
                break;
            }
        } if (thisTask != null) {
            model.addAttribute("task_name", thisTask.getName());
            model.addAttribute("task_description", thisTask.getTask());
            model.addAttribute("task_url", thisTask.getPersonalUrl().replace("/task/", ""));
            model.addAttribute("userMap" ,UserController.getUserMap().values());
            model.addAttribute("statusList", statusList);
        }

        return "changeTaskDescription.html";
    }
}

