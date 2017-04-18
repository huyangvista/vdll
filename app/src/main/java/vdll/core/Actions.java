package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */
public interface Actions<T> extends BaseAction{
    void invoke(T... t);
}
