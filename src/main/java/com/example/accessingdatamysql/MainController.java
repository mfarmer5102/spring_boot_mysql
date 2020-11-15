package com.example.accessingdatamysql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@Controller // This means that this class is a Controller
@RequestMapping(path="/api") // This means URL's start with /api (after Application path)
public class MainController {

  @Autowired // This means to get the bean called dogRepository
         // Which is auto-generated by Spring, we will use it to handle the data
  private DogRepository dogRepository;

  @GetMapping(path="/all")
  public @ResponseBody Iterable<Dog> getAllDogs() {
    // This returns a JSON or XML with the dogs
    return dogRepository.findAll();
  }

  @GetMapping(path="/one")
  public @ResponseBody Iterable<Dog> getOneDog(@RequestParam String name) {
    // This returns a JSON or XML with the dogs
    return dogRepository.findByName(name);
  }

  @GetMapping(path="/id") // ex. http://localhost:8080/api/id?id=3
  public @ResponseBody Dog getById(@RequestParam Integer id) {
    // This returns a JSON or XML with the dogs
    Dog myDog = dogRepository.findByIdentifier(id);
    System.out.println(myDog);
    return myDog;
  }

  @PostMapping(path="/add")
  public @ResponseBody String addDog (@RequestBody Dog doggie) {
    Dog newDog = new Dog();
    newDog.setName(doggie.getName());
    newDog.setBreed(doggie.getBreed());
    dogRepository.save(newDog);
    return "Saved";
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Object> updateStudent(@RequestBody Dog dog, @PathVariable Integer id) {
    Optional<Dog> dogOptional = dogRepository.findById(id);
    if (!dogOptional.isPresent())
      return ResponseEntity.notFound().build();
    dog.setId(id);
    dogRepository.save(dog);
    return ResponseEntity.noContent().build();
  }
  
}