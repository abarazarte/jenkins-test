package cl.multicaja.prepago.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
