package com.example.productService.utilities;

import org.json.JSONObject;

public class Utilities {

    public  static <T> String dataReturn(T obj) {
        return new JSONObject()
                .put(Constants.erc, Constants.PROCESS_SUCCESS)
                .put(Constants.data, obj).toString();
    }

    public  static String successMessageReturn(String msg) {
        return new JSONObject()
                .put(Constants.erc, Constants.PROCESS_SUCCESS)
                .put(Constants.msg, msg).toString();
    }
    public  static String errorMessageReturn(String msg) {
        return new JSONObject()
                .put(Constants.erc, Constants.PROCESS_FAILED)
                .put(Constants.msg, msg).toString();
    }
}
