package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */

public interface Action7<T0, T1, T2, T3, T4, T5,T6> extends BaseAction{
    void invoke(T0 t0, T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);
}
