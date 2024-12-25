package com.example.cloudtest.controller;

import com.example.cloudtest.dto.WorkerRequest;
import com.example.cloudtest.model.Worker;
import com.example.cloudtest.service.HelloCloudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
public class HelloCloudController {

    private final HelloCloudService service;

    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorker(@PathVariable Integer id) {
        log.info("getWorker() begin");
        Worker worker = service.getWorker(id);
        if (worker.getId() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(worker, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Worker>> getAllWorkerLimitFive() {
        log.info("getAll() begin");
        return new ResponseEntity<>(service.getAllWorkerLimitFive(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Worker> createWorker(@Valid @RequestBody WorkerRequest workerRequest) {
        log.info("createWorker() begin");
        return new ResponseEntity<>(service.createWorker(workerRequest), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateWorker(@PathVariable Integer id, @Valid @RequestBody WorkerRequest workerUpdate) {
        log.info("updateWorker() begin");
        Worker worker = service.updateWorker(id, workerUpdate);
        if (worker.getId() == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(worker, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable Integer id) {
        log.info("deleteWorker() begin");

        return service.deleteWorker(id);
    }
}
