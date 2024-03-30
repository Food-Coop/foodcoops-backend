package de.dhbw.foodcoop.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.dhbw.foodcoop.warehouse.application.deadline.DeadlineService;

@SpringBootApplication
@EnableScheduling
public class FoodcoopWarehouseApplication {

	@Autowired
	private DeadlineService deadlineService;
	
    public static void main(String[] args) {
        SpringApplication.run(FoodcoopWarehouseApplication.class, args);
    }
    
    @Scheduled(fixedDelay = 1000 * 60 * 3, initialDelay = 1000 *2)
    public void updateDeadlineIfNescessary()  {
    	deadlineService.updateDeadline();
}
}
