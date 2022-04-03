# Backend Interview: WorkerManger

## Context

We have some workers available for training neural network models.
Now we need a way to manage workers for jobs from our job manager.

Your job is to finish the worker manager to allocate an idle worker for a request.

Note that the change of worker status is stochastic (see the details in `WorkerBroker.updateWorkers`):
- There may be no worker available at request time.
- Starting/stopping a worker for a request may fail.
- Workers can be out of service at any time.
- The running time of each job on each worker is uncertain.

Finishing the Worker Service in `WorkerPool.java` to make sure request for worker is handled properly as the instruction below.

## Targets and Expectations
API `backend/src/.../api/WorkerController.java`:
- Handle worker allocation request from frontend and finish `requestWorker` functionality as the error message`"TODO: Finish requestWorker"`


Service `backend/src/.../services/WorkerPool.java`:
- Given a request with worker type, return an `IDLE` worker to frontend.
- If there is no `IDLE` worker, start a new worker using `WorkerBroker.requestStart` and return the worker when it becomes idle.
- If no registered worker is available(`OUT_OF_SERVICE/BUSY`), report an error message to frontend.



## How to build and run
First make sure you have `java` and `maven` installed. `java` version `1.8` is recommended.

To build, go to the root dir (i.e. dir of this README.md) then
```shell
mvn clean package
```

To run, still in the root dir
```shell
java -jar backend/target/interview.worker.manager-0.0.1-SNAPSHOT.jar
```

For more development info please check README.md in `frontend/` and `backend/` accordingly.
