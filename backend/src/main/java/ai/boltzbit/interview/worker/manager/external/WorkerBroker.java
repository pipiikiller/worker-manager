/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.external;

import ai.boltzbit.interview.worker.manager.model.Worker;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static ai.boltzbit.interview.worker.manager.model.WorkerStatus.*;
import static ai.boltzbit.interview.worker.manager.model.WorkerType.CPU;
import static ai.boltzbit.interview.worker.manager.model.WorkerType.GPU;

@Service
public class WorkerBroker {
    /***
     * DO NOT CHANGE THIS CLASS.
     * This class simulates external worker broker on cloud for the supply of VMs.
     * This provides the true status of workers.
     */

    private List<Worker> workList = new ArrayList<>(); // the list of registered workers
    private List<String> startList = new ArrayList<>();
    private List<String> stopList = new ArrayList<>();

    public WorkerBroker() {
        init();
    }

    private void init() {
        // DO NOT CHANGE THIS. initial registration of worker(VM) on cloud
        this.workList.add(new Worker("cpu-worker1", CPU, OFF));
        this.workList.add(new Worker("cpu-worker2", CPU, BUSY));
        this.workList.add(new Worker("gpu-worker1", GPU, OFF));
        this.workList.add(new Worker("gpu-worker2", GPU, OFF));
    }

    @Scheduled(fixedRate = 5000)
    private void updateWorkers() {
        // DO NOT CHANGE THIS. This code simulates external change of worker status in the worker list.
        // Note the state of the workers may change every 5 seconds
        workList.stream().forEach(worker -> {
            switch (worker.status) {
                case BUSY:
                    if (Math.random() > 0.975) {
                        worker.status = OUT_OF_SERVICE;
                    } else if (Math.random() > 0.5) {
                        worker.status = IDLE;
                    }
                    break;
                case IDLE:
                    if (Math.random() > 0.975) {
                        worker.status = OUT_OF_SERVICE;
                    } else if (Math.random() > 0.8){
                        worker.status = BUSY;
                    }
                    break;
                case OFF:
                    if (startList.contains(worker.id)) {
                        if (Math.random() > 0.05) {
                            worker.status = ON;
                        } else if (Math.random() > 0.5) {
                            worker.status = OUT_OF_SERVICE;
                        }
                    }
                    break;
                case ON:
                    if (stopList.contains(worker.id)) {
                        if (Math.random() > 0.05) {
                            worker.status = OFF;
                        } else if (Math.random() > 0.5) {
                            worker.status = OUT_OF_SERVICE;
                        }
                    } else if (Math.random() > 0.5) {
                            worker.status = IDLE;
                    }
            }
        });
    }

    public void requestStart(String workerID) {
        // DO NOT CHANGE THIS. This code simulates requests to cloud to start a registered VM.
        startList.add(workerID);
    }

    public void requestStop(String workerID) {
        // DO NOT CHANGE THIS. This code simulates requests to cloud to stop a registered VM.
        stopList.add(workerID);
    }

    public List<Worker> getWorkerList() {
        // DO NOT CHANGE THIS. This code reports list of registered VMs.
        return new ArrayList<>(this.workList);
    }
}
