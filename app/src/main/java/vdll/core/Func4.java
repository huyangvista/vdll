package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */
public interface Func4<T0, T1, T2, T3, R> extends BaseFunc{
    R invoke(T0 t0, T1 t1, T2 t2, T3 t3);
}
