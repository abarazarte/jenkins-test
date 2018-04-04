package cl.multicaja.prepago.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users-1.0.2")
public class UsersController102 extends UsersController101 {

  @RequestMapping(path="/{param}")
  public ResponseEntity common(@PathVariable("param") String msg) {
    Map<String, Object> map = new HashMap<>();
    map.put("success", true);
    map.put("msg", msg);
    map.put("version", "1.0.2");
    return new ResponseEntity(map, HttpStatus.OK);
  }

}
