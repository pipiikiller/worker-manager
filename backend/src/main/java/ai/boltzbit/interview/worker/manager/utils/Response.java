/*
 * Copyright (c) 2021. Boltzbit Limited, United Kingdom. All Rights Reserved.
 * This is a confidential file. Without a written licence by Boltzbit Limited,
 * any copy, distribution or modification of this file is strictly prohibited.
 */
package ai.boltzbit.interview.worker.manager.utils;

public class Response {
    private String note;
    private int code;  // 0 OK; 1 Failed

    private String objectJSON;

    public Response(String note, int code, String objectJSON) {
        this.note = note;
        this.code = code;
        this.objectJSON = objectJSON;
    }

    public String getNote() {
        return note;
    }

    public int getCode() {
        return code;
    }

    public String getObjectJSON() {
        return objectJSON;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setObjectJSON(String objectJSON) {
        this.objectJSON = objectJSON;
    }
}
