package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */
public interface Action2<T0, T1> extends BaseAction{
    void invoke(T0 t0, T1 t1);
}
