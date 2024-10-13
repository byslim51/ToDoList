package org.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.toDoList.model.Tasks;

import java.util.*;

@Controller
public class IndexController {
    static Map<Integer, Tasks> tasksMap = new HashMap<>();
    int id = 1;  // Счетчик для уникальных ID задач
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
    @GetMapping("/createTask")
    public String createTask(Model model, HttpServletRequest request) {
        String taskId = UUID.randomUUID().toString();
        String taskDescription = request.getParameter("task");

        // Проверяем, что название задачи и описание не пусты
        if (taskDescription != null && !taskDescription.isEmpty() && name != null && !name.isEmpty()) {
            Tasks task = new Tasks();
            task.setPersonalUrl("/task/" + taskId); // Генерация уникального URL
            task.setId(id);  // Устанавливаем ID задачи
            task.setName(name); // Название задачи
            task.setTask(taskDescription); // Описание задачи

            // Добавляем задачу в Map
            tasksMap.put(id, task);
            id++;  // Увеличиваем счетчик ID
        }

        // Передаем обновленный список задач на главную страницу
        model.addAttribute("taskList", tasksMap.values());
        return "index.html";
    }

    // Страница с деталями задачи
    @GetMapping("/task/{taskId}")
    public String getTaskDetails(@PathVariable String taskId, Model model) {
        Tasks foundTask = null;

        // Ищем задачу по ее уникальному URL
        for (Tasks task : tasksMap.values()) {
            if (task.getPersonalUrl().equals("/task/" + taskId)) {
                foundTask = task;
                break;
            }
        }

        if (foundTask != null) {
            model.addAttribute("task", foundTask.getTask());
            model.addAttribute("taskName", foundTask.getName());
            return "taskDetails.html";  // Показываем страницу с деталями задачи
        } else {
            return "redirect:/";  // Если задача не найдена, возвращаемся на главную страницу
        }
    }

    // Метод удаления задачи
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable int taskId, Model model) {
        taskDelete(taskId);  // Удаляем задачу
        model.addAttribute("taskList", tasksMap.values());  // Обновляем список задач
        return "index.html";  // Возвращаемся на главную страницу
    }
}

