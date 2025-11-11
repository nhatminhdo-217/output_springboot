package nhatm.project.demo.ioc_di;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {

  /**
   * Cách làm này Class bị phụ thuộc vào dependency
   * Khi dependency thay đổi thì bị liên lụy tới cả Class
   */
  // private HelloResponseService service = new HelloResponseService();

  @Autowired
  HelloResponseService service;

  private HelloResponseService resService;

  public HelloController(HelloResponseService resService) { // Constructor-based Injection
    this.resService = resService;
  }

  public void setHelloResponseService(HelloResponseService resService) { // Setter-based Injection
    this.resService = resService;
  }

  @GetMapping("/hello")
  public ResponseEntity<HelloResponse> getHelloResponse() {
    HelloResponse res = new HelloResponse("Hello world from Spring Boot");
    res.setMessage("This have been change");
    service.changeHelloResponseMsg(res, "What is this");
    return ResponseEntity.ok(res);
  }

}
