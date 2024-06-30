package ru.urfu.mm.filter;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class PrintableRequest {
    private final HttpServletRequest request;

    public PrintableRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String toString() {
        List<String> params = new ArrayList<>();
        request.getParameterMap().forEach((key, value) -> {
            String parameters = key + ": ";
            if (value.length == 1) {
                parameters += value[0];
            } else {
                parameters += Arrays.toString(value);
            }
            params.add(parameters);
        });
        String parameters = "{";
        if (!params.isEmpty()) {
            parameters += "\n\t\t" + String.join(",\n\t\t", params) + "\n\t";
        }
        parameters += "}";

        return "{\n" +
                "\turi: " + request.getRequestURI() + "\n" +
                "\tparams: " + parameters + "\n" +
                "}";
    }
}
