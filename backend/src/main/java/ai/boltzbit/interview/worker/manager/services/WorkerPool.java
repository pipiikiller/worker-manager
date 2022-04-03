/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.services;

import ai.boltzbit.interview.worker.manager.external.WorkerBroker;
import ai.boltzbit.interview.worker.manager.model.Worker;
import ai.boltzbit.interview.worker.manager.model.WorkerStatus;
import ai.boltzbit.interview.worker.manager.model.WorkerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ai.boltzbit.interview.worker.manager.model.WorkerType.CPU;
import static ai.boltzbit.interview.worker.manager.model.WorkerType.GPU;

@Service
public class WorkerPool {
    /***
     * [Boltzbit] implement the services to
     *  - request workers
     *  - stop idle workers
     *  - monitor the latest status of workers.
     */

    @Autowired
    WorkerBroker workerBroker; // use the broker class to start and stop workers on cloud

    private List<Worker> workList;

    public WorkerPool() {
        workList = new ArrayList<>();
    }

    Logger logger = Logger.getLogger(WorkerPool.class.getName());

    public Worker getWorkerWithID(String id) {
        if (id == null && id.isEmpty()) {
            return null;
        }

        if (workList.isEmpty()) {
            workList.addAll(workerBroker.getWorkerList());
        }

        Optional<Worker> optionalWorker = this.workList.stream()
                .filter(worker -> worker.id.equalsIgnoreCase(id))
                .findFirst();

        return optionalWorker.isPresent() ? optionalWorker.get() : null;
    }

    public List<Worker> getWorkersWithType(WorkerType workerType) {
        if (workList.isEmpty()) {
            workList.addAll(workerBroker.getWorkerList());
        }
        return this.workList.stream()
                .filter(worker -> worker.workerType == workerType)
                .collect(Collectors.toList());
    }


    public List<Worker> getWorkersWithTypeAndStatus(WorkerType workerType, WorkerStatus status) {
        if (workList.isEmpty()) {
            workList.addAll(workerBroker.getWorkerList());
        }

        return this.workList.stream()
                .filter(worker -> worker.status == status)
                .filter(worker -> worker.workerType == workerType)
                .collect(Collectors.toList());
    }


    public List<Worker> getAllWorkers() {
        return this.workList;
    }

    public void stopIdleWorkers(WorkerStatus status){
        List<Worker> idleWorkerList = getAllWorkers()
                .stream()
                .filter(worker -> worker.status == WorkerStatus.IDLE)
                .collect(Collectors.toList());

        if(idleWorkerList.isEmpty()){
            return;
        }

        /**
         * need to tweak worker broker to stop idle workers
         */
        idleWorkerList
                .stream()
                .forEach(worker -> workerBroker.requestStop(worker.id));

    }

    // Finish up the requested service here.
    public Worker requestWorker(WorkerType workerType) {

        try {
            List<Worker> idleWorkerList = getWorkersWithTypeAndStatus(workerType, WorkerStatus.IDLE);

            if (idleWorkerList.isEmpty()) {
                List<Worker> offlineWorkerList = getWorkersWithTypeAndStatus(workerType, WorkerStatus.OFF);

                if (offlineWorkerList.isEmpty()) {
                    return null;
                }
                offlineWorkerList.stream().forEach(worker -> workerBroker.requestStart(worker.id));

                Worker worker = processWorkers(offlineWorkerList);
                if (worker != null) return worker;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        /**
         * Last try before returning null
         */

        List<Worker> workers = getWorkersWithTypeAndStatus(workerType, WorkerStatus.IDLE);
        return workers.isEmpty() ? null : workers.get(0);
    }

    public Worker processWorkers(List<Worker> offlineWorkerList) throws Exception {

        while(!offlineWorkerList.isEmpty()){

            Worker worker = offlineWorkerList.get(0);
            WorkerStatus status = getWorkerStatus(worker.id);
            if (status == WorkerStatus.IDLE) {
                return worker;
            } else if (status == WorkerStatus.OUT_OF_SERVICE) {
                offlineWorkerList.remove(worker);
            } else if(status == WorkerStatus.OFF){
                logger.log(Level.WARNING, "Requested workers are still OFFLINE.");
            }
        }
        return null;
    }

    public WorkerStatus getWorkerStatus(String workerId) throws Exception {

        Worker worker = getWorkerWithID(workerId);

        if (worker == null) {
            throw new Exception("Cannot find the worker with given: " + workerId);
        }

        return worker.status;
    }

}
