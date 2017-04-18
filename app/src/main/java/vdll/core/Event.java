package vdll.core;

import java.util.List;

/**
 * Created by Hocean on 2017/3/24.
 */

public class Event<T extends  Delegate> {
    List<T> list;

    public Event() {
    }

    public Event(List<T> list) {
        this.list = list;
    }



}
