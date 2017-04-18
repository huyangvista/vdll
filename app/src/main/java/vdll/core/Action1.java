package vdll.core;

/**
 * Created by Hocean on 2017/3/23.
 */
public interface Action1<T> extends BaseAction{
    void invoke(T t);
}
