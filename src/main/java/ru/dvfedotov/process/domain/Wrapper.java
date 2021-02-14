package ru.dvfedotov.process.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Wrapper<T> implements Serializable {

    private static final long serialVersionUID = 8189195566882987774L;

    private List<T> data;

    public Wrapper() {
    }

    public Wrapper(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Wrapper{" +
            "data=" + data +
            '}';
    }
}
