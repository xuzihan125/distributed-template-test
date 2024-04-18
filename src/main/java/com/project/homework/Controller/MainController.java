package com.project.homework.Controller;

import com.project.homework.Repository.UserRepository;
import com.project.homework.common.BaseResponse;
import com.project.homework.common.ErrorCode;
import com.project.homework.common.ResultUtils;
import com.project.homework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
public class MainController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(path = "/addUser") // Map ONLY POST Requests
  public @ResponseBody
  BaseResponse<String> addNewUser(@RequestBody User user) {
    Optional<User> byId = userRepository.findById(user.getUsername());
    if(byId.isPresent()){
      return ResultUtils.error(ErrorCode.PARAMS_ERROR, "this username is already taken");
    }
    userRepository.save(user);
    return ResultUtils.success("success");
  }

  @PostMapping(path="/login")
  public @ResponseBody BaseResponse<String> login(@RequestBody User user){
    if(user.getUsername()==null || user.getPassword()==null){
      return ResultUtils.success("");
    }
    Optional<User> check = userRepository.findById(user.getUsername());
    String result = check.map(u -> u.getPassword().equals(user.getPassword()) ? "success":"").orElse("");
    return ResultUtils.success(result);
  }

}
