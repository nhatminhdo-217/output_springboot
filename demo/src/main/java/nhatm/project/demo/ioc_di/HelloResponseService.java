package nhatm.project.demo.ioc_di;

import org.springframework.stereotype.Service;

@Service
public class HelloResponseService {

  public void changeHelloResponseMsg(HelloResponse res, String msg) {
    res.setMessage(msg);
  }

  public void printHello() {
    System.err.println("Hello from Service");
  }
}
