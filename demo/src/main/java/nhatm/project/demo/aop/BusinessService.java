package nhatm.project.demo.aop;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    // Phương thức nghiệp vụ thông thường
    public String processData(String data) throws InterruptedException {
        System.out.println(">>> [Service] Đang xử lý data: " + data);
        // Giả lập xử lý chậm
        TimeUnit.MILLISECONDS.sleep(500); 
        return "Đã xử lý: " + data;
    }

    // Phương thức nhạy cảm, cần bảo mật
    public void deleteData(int id) throws InterruptedException {
        System.out.println(">>> [Service] ĐANG XÓA data: " + id);
        // Giả lập xử lý chậm
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println(">>> [Service] ĐÃ XÓA data: " + id);
    }
}
