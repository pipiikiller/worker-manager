/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.model;

public enum WorkerStatus {
    /**
     * The status of VM workers (NOT jobs)
     */
    ON,
    OFF,
    IDLE,
    BUSY,
    OUT_OF_SERVICE
}
