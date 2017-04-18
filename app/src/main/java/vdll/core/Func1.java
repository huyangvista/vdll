package vdll.core;

/**
 * Created by Hocean on 2017/3/24.
 */
public interface Func1<T, R> extends BaseFunc{
    R invoke(T t);
}
