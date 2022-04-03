/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.api;

import ai.boltzbit.interview.worker.manager.model.Worker;
import ai.boltzbit.interview.worker.manager.model.WorkerStatus;
import ai.boltzbit.interview.worker.manager.model.WorkerType;
import ai.boltzbit.interview.worker.manager.services.WorkerPool;
import ai.boltzbit.interview.worker.manager.utils.JSONHelper;
import ai.boltzbit.interview.worker.manager.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/worker")
public class WorkerController {

    @Autowired
    private WorkerPool workerPool;

    @GetMapping("/list")
    public Response checkWorker(@RequestParam("type") String workerType) {
        List<Worker> workerList = workerPool.getWorkersWithType(WorkerType.valueOf(workerType));
        return new Response("OK", 0, JSONHelper.toJSON(workerList));
    }

    @PostMapping("/request")
    public Response requestWorker(@RequestParam("type") String workerType) {
        /***
         * [Boltzbit] implement the request API here.
         * No jobID is needed to start a worker.
         */

        Worker worker = workerPool.requestWorker(WorkerType.valueOf(workerType));

        Response response = new Response("OK", 0,  JSONHelper.toJSON(worker));

        if(worker == null){
            response.setCode(1);
            response.setNote("Failed");
        }
        return response;
    }
}
