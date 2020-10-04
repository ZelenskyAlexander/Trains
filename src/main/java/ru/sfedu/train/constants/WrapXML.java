package ru.sfedu.train.constants;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "List")
public class WrapXML<T> {

    @ElementList(inline = true, required = false)
    public List<T> list;

    public WrapXML() {
    }

    public WrapXML(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}