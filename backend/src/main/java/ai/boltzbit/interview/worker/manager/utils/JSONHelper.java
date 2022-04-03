/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.utils;

import ai.boltzbit.interview.worker.manager.model.Worker;
import com.google.gson.Gson;

import java.util.List;

public class JSONHelper {
    static final Gson gson = new Gson();
    public static String toJSON(List<Worker> workerList) {
        return gson.toJson(workerList);
    }

    public static String toJSON(Worker worker){
        return gson.toJson(worker);
    }
}
