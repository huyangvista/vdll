package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */
public interface Func2<T0, T1, R> extends BaseFunc{
    R invoke(T0 t0, T1 t1);
}
