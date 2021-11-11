package com.mercadolivre.proxy.filter;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
class Context {

    private static Context INSTANCE = null;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private long duration;
    private int status;

    private Context() {}

    static Context getInstance() {
        if (INSTANCE == null) {
            synchronized(Context.class) {
                INSTANCE = new Context();
            }
        }
        return INSTANCE;
    }
}
