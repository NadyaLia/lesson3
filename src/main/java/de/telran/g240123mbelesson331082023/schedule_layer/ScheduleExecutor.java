package de.telran.g240123mbelesson331082023.schedule_layer;

import de.telran.g240123mbelesson331082023.domain.entity.Product;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.JpaProduct;
import de.telran.g240123mbelesson331082023.domain.entity.jpa.Task;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaProductRepository;
import de.telran.g240123mbelesson331082023.repository.jpa.JpaTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
@EnableAsync
public class ScheduleExecutor {
    @Autowired
    private JpaTaskRepository repository;

    @Autowired
    private JpaProductRepository productRepository;
    private static Logger logger = LoggerFactory.getLogger(ScheduleExecutor.class);

  /*  @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        Task task = new Task("Fixed delay task");
        logger.info(task.getDescription());
        repository.save(task);
    }*/

   /* @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        Task task = new Task("Fixed delay task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

/*    @Scheduled(fixedDelay = 5000)
    public void fixedDelayTask() {
        Task task = new Task("Fixed delay task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedRate = 5000)
    public void fixedRateTask() {
        Task task = new Task("Fixed rate task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

   /* @Scheduled(fixedRate = 5000)
    public void fixedRateTask() {
        Task task = new Task("Fixed rate task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

   /* @Scheduled(fixedRate = 5000)
    @Async
    public void fixedRateTask() {
        Task task = new Task("Fixed rate task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedRate = 5000, initialDelay = 15000)
    @Async
    public void initialDelayTask() {
        Task task = new Task("Initial delay task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    // fixedDelay = 7200000 - two hours
  /*  @Scheduled(fixedDelayString = "PT07S")
    public void anotherDelayFormatTask() {
        Task task = new Task("Another delay format task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(fixedDelayString = "${interval}")
    public void delayInPropertyTask() {
        Task task = new Task("Delay in property task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*@Scheduled(cron = "${cron-interval}")
    public void cronExpressionTask() {
        Task task = new Task("Cron expression task");
        logger.info(task.getDescription());
        repository.save(task);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }*/

    public static void taskSchedulerTask(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        scheduler.schedule(() -> logger.info(task.getDescription()),
                new CronTrigger("0,10,20,30,40,50 * * * * *"));
    }

    public static void taskSchedulerTaskWithInstant(Task task) {
        TaskScheduler scheduler = new DefaultManagedTaskScheduler();
        Instant instant = Instant.now().plusSeconds(20);
        scheduler.schedule(() -> logger.info(task.getDescription()), instant);
    }

    // 1. Реализовать вывод в консоль каждые 30 секунд списка последних пяти выполненных задач.
    //        Время выполнения предыдущей задачи не должно влиять на старт следующей.
    @Scheduled(fixedRate = 30000)
    public void displayLastFiveTasks() {
        List<Task> lastFiveTasks = repository.displayLastFiveTasks();
        System.out.println(lastFiveTasks);
    }

    // 2. Реализовать вывод в консоль последнего добавленного в БД товара.
    //        Вывод должен производиться в 15 и 45 секунд каждой минуты.
    //        Задача должна быть сохранена в БД.
    @Scheduled(cron = "15,45 * * * * *")
    public void displayLastAddedProduct() {
        Product lastProduct = repository.displayLastAddedProduct();
        logger.info("Last added product: " + lastProduct.getName());
        Task newTask = new Task("Displayed last added product");
        repository.save(newTask);
    }
}

