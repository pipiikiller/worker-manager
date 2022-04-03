/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.model;

public class Worker {
    public final String id;
    public final WorkerType workerType;
    public WorkerStatus status;

    public Worker(String id, WorkerType workerType, WorkerStatus status) {
        this.id = id;
        this.workerType = workerType;
        this.status = status;
    }
}
