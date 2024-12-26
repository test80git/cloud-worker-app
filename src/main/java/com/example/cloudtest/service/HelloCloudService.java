package com.example.cloudtest.service;

import com.example.cloudtest.dto.WorkerRequest;
import com.example.cloudtest.model.Worker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@SessionScope // Указывает, что экземпляр сервиса создается для каждой HTTP-сессии
@Slf4j
public class HelloCloudService {
    private final AtomicInteger count = new AtomicInteger(0);
    private static final LocalDateTime CREATEDATETIME = LocalDateTime.of(2024, 12, 25, 12, 00, 00);
    private final Map<Integer, Worker> map = new HashMap<>(20);

    public HelloCloudService() {
        map.put(count.incrementAndGet(), new Worker(count.get(), "Tom", "Cruise", LocalDate.of(1962, 07, 03), "USA",
                CREATEDATETIME, CREATEDATETIME));
        map.put(count.incrementAndGet(), new Worker(count.get(), "Katie", "Holmes", LocalDate.of(1978, 12, 18), "USA", CREATEDATETIME,
                CREATEDATETIME));
        map.put(count.incrementAndGet(), new Worker(count.get(), "Константин", "Хабенский", LocalDate.of(1972, 01, 11), "Ленинград",
                CREATEDATETIME,
                CREATEDATETIME));
        map.put(count.incrementAndGet(), new Worker(count.get(), "Сергей", "Безруков", LocalDate.of(1973, 10, 18), "Москва", CREATEDATETIME
                , CREATEDATETIME));
    }

    public Worker getWorker(Integer id) {

        if (!map.containsKey(id))
            return new Worker();
        return map.get(id);
    }

    public List<Worker> getAllWorkerLimitFive() {
        List<Worker> list = new ArrayList<>(map.values());
        return list.stream().skip(Math.max(0, list.size() - 5)) // Пропустить первые элементы, оставив последние 5
                .toList();
    }

    public Worker createWorker(WorkerRequest workerRequest) {
        int size = map.size();

        log.info("size map = {}", size);
        if (size > 100) {
            int temp = count.get();
            log.info("size map>100 = {}, count = {}", size, count);
            for (int i = 0; i < 50; i++) {
                map.remove(temp - 50 + i);
            }
        }
        Worker worker = new Worker();

        worker.setId(count.incrementAndGet());
        toEntity(workerRequest, worker);
        worker.setCreate(LocalDateTime.now());
        worker.setUpdate(LocalDateTime.now());
        log.info("size = {}, worker = {}", size, worker);
        map.put(count.get(), worker);
        return worker;
    }

    public Worker updateWorker(Integer id, WorkerRequest workerUpdate) {
        if (map.containsKey(id)) {
            Worker worker = map.get(id);
            toEntity(workerUpdate, worker);
            worker.setUpdate(LocalDateTime.now());
            log.info("worker = {}", worker);
            return worker;
        } else {
            return new Worker();
        }
    }

    private void toEntity(WorkerRequest workerUpdate, Worker worker) {
        worker.setName(workerUpdate.name());
        worker.setSurName(workerUpdate.surName());
        LocalDate dateOfBirth;
        try {
            dateOfBirth = workerUpdate.dateOfBirth();
        } catch (DateTimeException e) {
            dateOfBirth = LocalDate.of(1970, 1, 1);
            log.error(e.getMessage());
        }
        worker.setDateOfBirth(dateOfBirth);
        worker.setAddress(workerUpdate.address());
    }


    public ResponseEntity<?> deleteWorker(Integer id) {

        if (map.containsKey(id)) {
            map.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
