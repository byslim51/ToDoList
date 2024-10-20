package org.toDoList.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.toDoList.model.User;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    static Map<Integer, User> userMap = new HashMap<>();
    int id = 1;

    @GetMapping("/usersList")
    public String getUsersMenu(Model model) {
        model.addAttribute("userList", userMap.values());
        return "usersList.html";
    }

    @GetMapping("/userCreateMenu")
    public String userCreateMenu(Model model, HttpServletRequest request) {
        return "userCreateMenu.html";
    }

    @PostMapping("/createUser")
    public String createUser(Model model, HttpServletRequest request) {
        int age = Integer.parseInt(request.getParameter("age"));
        String name = request.getParameter("name");

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        userMap.put(id, user);
        id++;

        model.addAttribute("userList", userMap.values());
        return "redirect:/usersList";
    }

    public static Map<Integer, User> getUserMap() {
        return userMap;
    }
}
