package com.osrs.controller;

import com.osrs.common.JsonResponse;
import com.osrs.common.JwtUtil;
import com.osrs.entity.User;
import com.osrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public JsonResponse login(@RequestBody Map<String, String> credentials){
        String username = credentials.get("username");
        String password = credentials.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        User user = userService.login(username, password);
        if(user == null){
            return new JsonResponse(400, "Login failed", null);
        }else if(!user.getPassword().equals(password)){
            return new JsonResponse(400, "Wrong password", null);
        }else{
            String token = JwtUtil.generateToken(username);
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("token", token);
            data.put("user", user);
            return new JsonResponse(200, "Login successful", data);
        }
    }
    @GetMapping("/info")
    public JsonResponse getInfo(@RequestParam("token") String token){
        String username = JwtUtil.verifyToken(token);
        if(username == null){
            return new JsonResponse(400, "Invalid token", null);
        }
        User user = userService.getUserInfo(username);
        if(user == null){
            return new JsonResponse(400, "User not found", null);
        }
        return new JsonResponse(200, "User found", user );
    }
    @PostMapping("/logout")
    public JsonResponse logout(@RequestParam("token") String token){
        // 这里你可以添加使token失效的逻辑
        // 例如，你可以将token添加到一个黑名单中，或者直接从数据库中删除
        // 注意，这取决于你的JWT实现方式和你的业务需求

        // 假设你有一个方法可以使token失效
        boolean isTokenInvalidated = JwtUtil.invalidateToken(token);
        if(isTokenInvalidated){
            return new JsonResponse(200, "Logout successful", null);
        }else{
            return new JsonResponse(400, "Logout failed", null);
        }
    }
    @PostMapping("/register")
    public JsonResponse register(@RequestBody User user){
        User existingUser = userService.getUserInfo(user.getUsername());
        if(existingUser != null){
            return new JsonResponse(400, "User already exists", null);
        }
        int isRegistered = userService.register(user);
        if(isRegistered ==1){
            return new JsonResponse(200, "Registration successful", null);
        }else{
            return new JsonResponse(400, "Registration failed", null);
        }
    }
}
