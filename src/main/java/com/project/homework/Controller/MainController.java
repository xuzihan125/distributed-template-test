package com.project.homework.Controller;

import com.project.homework.Repository.ProductRepository;
import com.project.homework.Repository.UserRepository;
import com.project.homework.model.Product;
import com.project.homework.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/v1")
public class MainController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @PostMapping(path = "/addUser") // Map ONLY POST Requests
  public @ResponseBody
  String addNewUser(@RequestParam String name
          , @RequestParam String password) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    User n = new User();
    n.setUsername(name);
    n.setPassword(password);
    userRepository.save(n);
    return "Saved";
  }

  @PostMapping(path = "/addProduct") // Map ONLY POST Requests
  public @ResponseBody
  Product addProduct(@RequestParam String name
          , @RequestParam Integer num) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request

    Product product = new Product();
    product.setName(name);
    product.setNum(num);
    return productRepository.save(product);
  }

  @PostMapping(path = "/buyProduct") // Map ONLY POST Requests
  public @ResponseBody
  Boolean addProduct(@RequestParam String name) {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    Product product = productRepository.findById(name).orElse(null);
    if (product != null && product.getNum() > 0) {
      product.setNum(product.getNum() - 1);
      productRepository.save(product);
      return true;
    }
    return false;

  }

  @GetMapping(path = "/all")
  public @ResponseBody
  Iterable<User> getAllUsers() {
    return userRepository.findAll();
  }
}
