package cl.multicaja.prepago.controller;

import cl.multicaja.prepago.model.User;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users-1.0.0")
public class UsersController100 {

	@RequestMapping("/ping")
  public ResponseEntity ping() {
    Map<String, Object> map = new HashMap<>();
    map.put("success", true);
    map.put("impl", this.getClass().getSimpleName());
    return new ResponseEntity(map, HttpStatus.OK);
  }

  @RequestMapping(path="/{param}")
  public ResponseEntity common(@PathVariable("param") String msg) {
    Map<String, Object> map = new HashMap<>();
    map.put("success", true);
    map.put("msg", msg);
    map.put("version", "1.0.0");
    return new ResponseEntity(map, HttpStatus.OK);
  }

  @RequestMapping(path="/", method = RequestMethod.POST)
  public ResponseEntity newUser(@RequestBody String body) {
    User user = new Gson().fromJson(body, User.class);
    Map<String, Object> map = new HashMap<>();
    map.put("success", true);
    map.put("user", user);
    return new ResponseEntity(map, HttpStatus.OK);
  }
}
