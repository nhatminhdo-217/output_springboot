package nhatm.project.demo.bean_life_cycle;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DBConnector {
    
    private boolean isConnected;

    public DBConnector() {
        System.out.println("1. Giai đoạn init. Gọi Constructor");
    }

    @PostConstruct
    public void connect() {
        System.out.println("4. Giai đoạn gọi @PostConstruct");
        System.out.println("Kết nối CSDL...........");
        this.isConnected = true;
        System.out.println("Đã kết nối");
    }

    public boolean isConnected() {
        return isConnected;
    }

    @PreDestroy
    public void disconnect() {
        System.out.println("6. Giai đoạn destruction, gọi @PreDestroy.");
        System.out.println("Đang ngắt kết nối CSDL..........");
        this.isConnected = false;
        System.out.println("Đã ngắt kết nối");
    }
}
