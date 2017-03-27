package vdll.tools;

//注解使用

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import dalvik.system.DexFile;

/**
 * 反射辅助类
 *
 * @author Hocean  @version 2016年5月30日 下午3:14:22.  2016-10-12 16:16:37
 */
public class ReflectUtil {
    public static void main(String[] args) {
        //Demo
        VReflectObject.main();
    }

    private static final String GET = "get";
    private static final String SET = "set";


    //关于类

    /**
     * 得到参数类型
     *
     * @param args
     * @return
     */
    public static Class<?>[] getTypeClassFromParms(Object[] args) {
        Class<?>[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        return argsClass;
    }

    /**
     * 得到类
     *
     * @param className
     * @return
     */
    public static Class<?> getClassFromName(String className) {
        Class<?> newoneClass = null;
        try {
            newoneClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newoneClass;
    }


    //关于构造

    /**
     * 新建实例
     *
     * @param newOneClass 类名
     * @param args        构造函数的参数
     * @throws Exception
     * @return新建的实例
     */
    public static <T> T newInstance(Class<T> newOneClass, Class<?>[] argsClass, Object[] args) {
        T t = null;
        try {
            Constructor<T> cons = newOneClass.getConstructor(argsClass);
            //Constructor<?> cons = newoneClass.getDeclaredConstructor(argsClass);  //可使用 私有构造
            t = cons.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 私有构造方法  新建实例
     *
     * @param newOneClass
     * @param args
     * @return
     */
    public static <T> T newInstanceInner(Class<T> newOneClass, Class<?>[] argsClass, Object[] args) {
        T t = null;
        try {
            //Constructor<?> cons = newoneClass.getConstructor(argsClass);
            Constructor<T> cons = newOneClass.getDeclaredConstructor(argsClass); //可使用 私有构造
            cons.setAccessible(true);
            t = cons.newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 新建实例
     *
     * @param newOneClass
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> newOneClass, Object... args) {
        return newInstance(newOneClass, getParmsClass(args), args);
    }

    /**
     * @param newOneClass
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T newInstanceInner(Class<T> newOneClass, Object... args) {
        return newInstanceInner(newOneClass, getParmsClass(args), args);
    }

    /**
     * 新建实例 不能出现非引用类型 如 int
     *
     * @param newOneClass 类名
     * @param args        构造函数的参数
     * @throws Exception
     * @return新建的实例
     */
    public static Object newInstanceObj(Class<?> newOneClass, Class<?>[] argsClass, Object[] args) {
        return newInstance(newOneClass, argsClass, args);
    }

    /**
     * 私有构造方法  新建实例  不能出现非引用类型 如 int
     *
     * @param newOneClass
     * @param args
     * @return
     */
    public static Object newInstanceObjInner(Class<?> newOneClass, Class<?>[] argsClass, Object[] args) {
        return newInstanceInner(newOneClass, argsClass, args);
    }


    //关于注解

    /**
     * 获得注解 当前类字段
     *
     * @param cls
     * @param ctag
     * @return
     */
    public static <T extends Annotation> T[] getTags(Class<?> cls, Class<T> ctag) {
        return newTagsListThis(ReflectUtil.getFields(cls), cls, ctag);
    }

    /**
     * 获得注解  全部类字段
     *
     * @param cls
     * @param ctag
     * @return
     */
    public static <T extends Annotation> List<T[]> getTagsAll(Class<?> cls, Class<T> ctag) {
        return newTagsListSupper(ReflectUtil.getFieldsAll(cls), cls, ctag);
    }

    /**
     * 获得注解 可见类字段
     *
     * @param cls
     * @param ctag
     * @return
     */
    public static <T extends Annotation> List<T[]> getTagsVisit(Class<?> cls, Class<T> ctag) {
        return newTagsListSupper(ReflectUtil.getFieldsVisit(cls), cls, ctag);
    }

    /**
     * 获得注解 当前可见类字段
     *
     * @param cls
     * @param ctag
     * @return
     */
    public static <T extends Annotation> T[] getTagsLocal(Class<?> cls, Class<T> ctag) {
        return newTagsListThis(ReflectUtil.getFieldsLocal(cls), cls, ctag);
    }

    /**
     * 新建数组标记
     *
     * @param fields
     * @param cls
     * @param ctag
     * @return
     */
    private static <T extends Annotation> T[] newTagsListThis(Field[] fields, Class<?> cls, Class<T> ctag) {
        List<T> lt = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ctag)) {
                T t = (T) field.getAnnotation(ctag);
                lt.add(t);
            }
        }
        @SuppressWarnings("unchecked")
        T[] ts = (T[]) Array.newInstance(ctag, lt.size());
        for (int i = 0; i < ts.length; i++) {
            ts[i] = lt.get(i);
        }
        return ts;
    }

    /**
     * 新建 列表标记
     *
     * @param list
     * @param cls
     * @param ctag
     * @return
     */
    private static <T extends Annotation> List<T[]> newTagsListSupper(List<Field[]> list, Class<?> cls, Class<T> ctag) {
        List<T[]> lts = new ArrayList<>();
        for (Field[] fs : list) {
            lts.add(newTagsListThis(fs, cls, ctag));
        }
        return lts;
    }


    //关于字段

    /**
     * 递归查找字段的  回调接口
     *
     * @author Hocean  @version 2016年10月12日 下午2:32:31.
     */
    private static interface IFieldValuePoint {
        Object invoke(Object o, String fieldName);
    }

    /**
     * 用来递归 查找 字段
     *
     * @param o
     * @param fieldName
     * @param inv
     * @return
     */
    private static Object getFieldValuePoint(Object o, String fieldName, IFieldValuePoint inv) {
        try {
            if (!fieldName.contains(".")) { //不包含
                return inv.invoke(o, fieldName);
            }
            String fnb = fieldName.substring(0, fieldName.indexOf("."));
            Object vob = getFieldValuePoint(o, fnb, inv);
            return getFieldValuePoint(vob, fieldName.substring(fieldName.indexOf(".") + 1), inv);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 得到类的当前所有字段
     *
     * @param cls
     * @return
     */
    public static Field[] getFields(Class<?> cls) {
        //Class<?> c = o.getClass(); //反射
        Field[] fields = cls.getDeclaredFields(); //属性
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); //反射私有属性 权限
            /*Field field = fields[i]; //第几个参数
            Class<?> type = field.getType(); //反射类型
			String typeName = type.getName();//得到类型
			System.out.println(typeName);
			String fieldName = field.getName(); //反射字段
			System.out.println(fieldName); //字段名
			tableRow[i] = fieldName;
			try
			{
				Object value = field.get(t); //字段值
				System.out.println(value);

			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}*/
        }
        return fields;
    }

    /**
     * 搜索全部字段  包括私有字段
     *
     * @param clazz
     * @return
     */
    public static List<Field[]> getFieldsAll(Class<?> clazz) {
        List<Field[]> list = new ArrayList<>();
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Field[] fields = mclazz.getDeclaredFields(); //属性
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true); //反射私有属性 权限
                }
                list.add(fields);
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * 搜索全部可见字段
     *
     * @param clazz
     * @return
     */
    public static List<Field[]> getFieldsVisit(Class<?> clazz) {
        List<Field[]> list = new ArrayList<>();
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Field[] fields = mclazz.getFields(); //属性
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true); //反射私有属性 权限
                }
                list.add(fields);
            } catch (Exception e) {
            }
        }
        return list;
    }

    /**
     * 对象当前字段
     *
     * @param clazz
     * @return
     */
    public static Field[] getFieldsLocal(Class<?> clazz) {
        Field[] fields = clazz.getFields(); //属性
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); //反射私有属性 权限
        }
        return fields;
    }

    /**
     * 得到类的当前字段
     *
     * @param cls
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> cls, String fieldName) {
        Field fields = null;
        try {
            fields = cls.getDeclaredField(fieldName);
            fields.setAccessible(true); //反射私有属性 权限
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        } //属性
        return fields;
    }

    /**
     * 搜索全部字段  包括私有字段
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getFieldAll(Class<?> clazz, String fieldName) {
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Field field = mclazz.getDeclaredField(fieldName);
                field.setAccessible(true); //反射私有属性 权限
                return field;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 搜索全部可见字段
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getFieldVisit(Class<?> clazz, String fieldName) {
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Field field = mclazz.getField(fieldName);
                field.setAccessible(true); //反射私有属性 权限
                return field;
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 对象当前字段
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Field getFieldLocal(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true); //反射私有属性 权限
            return field;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 得到字段的值
     *
     * @param field
     * @param obj
     * @return
     */
    public static Object getFieldValue(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 设置字段
     *
     * @param field
     * @param obj
     * @param value
     */
    public static void setFieldValue(Object obj, Field field, Object value) {
        try {
            field.set(obj, value);
        } catch (Exception e) {
        }
    }

    /**
     * 得到类的当前 静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static Object getStaticFieldValue(Class<?> ownerClass, String fieldName) {

        return getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getField((Class<?>) o, fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到全部字段  包括私有字段  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static Object getStaticFieldValueAll(Class<?> ownerClass, String fieldName) {

        return getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldAll((Class<?>) o, fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到全部可见字段  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static Object getStaticFieldValueVisit(Class<?> ownerClass, String fieldName) {

        return getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldVisit((Class<?>) o, fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到类的当前  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static Object getStaticFieldLocal(Class<?> ownerClass, String fieldName) {

        return getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldLocal((Class<?>) o, fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 设置类的 当前静态属性值
     *
     * @param ownerClass
     * @param fieldName
     * @param value
     */
    public static void setStaticFieldValue(final Class<?> ownerClass, String fieldName, final Object value) {
        getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getField((Class<?>) o, fieldName);
                try {
                    f.set(ownerClass, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置全部字段  包括私有字段  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static void setStaticFieldValueAll(final Class<?> ownerClass, String fieldName, final Object value) {
        getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldAll((Class<?>) o, fieldName);
                try {
                    f.set(ownerClass, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置全部可见字段  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static void setStaticFieldValueVisit(final Class<?> ownerClass, String fieldName, final Object value) {

        getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldVisit((Class<?>) o, fieldName);
                try {
                    f.set(ownerClass, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置类的当前  静态属性值
     *
     * @param ownerClass 类名
     * @param fieldName  属性名
     * @return 该属性对象
     * @throws Exception
     */
    public static void setStaticFieldLocal(final Class<?> ownerClass, String fieldName, final Object value) {

        getFieldValuePoint(ownerClass, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldLocal((Class<?>) o, fieldName);
                try {
                    f.set(ownerClass, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 得到对象当前字段
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object o, String fieldName) {
        return getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getField(o.getClass(), fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到对象全部字段  包括私有字段
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFieldValueAll(Object o, String fieldName) {
        return getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldAll(o.getClass(), fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到对象全部可见字段
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFieldValueVisit(Object o, String fieldName) {
        return getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldVisit(o.getClass(), fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 得到对象当前字段
     *
     * @param o
     * @param fieldName
     * @return
     */
    public static Object getFieldValueLocal(Object o, String fieldName) {
        return getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldLocal(o.getClass(), fieldName);
                Object vo = null;
                try {
                    vo = f.get(o);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return vo;
            }
        });
    }

    /**
     * 设置类的当前字段
     *
     * @param o
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(Object o, String fieldName, final Object value) {
        getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getField(o.getClass(), fieldName);
                try {
                    f.set(o, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置类的全部字段  包括私有字段
     *
     * @param o
     * @param fieldName
     * @param value
     */
    public static void setFieldValueAll(Object o, String fieldName, final Object value) {
        getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldAll(o.getClass(), fieldName);
                try {
                    f.set(o, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置类的全部可见字段
     *
     * @param o
     * @param fieldName
     * @param value
     */
    public static void setFieldValueVisit(Object o, String fieldName, final Object value) {
        getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldVisit(o.getClass(), fieldName);
                try {
                    f.set(o, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }

    /**
     * 设置类的当前字段
     *
     * @param o
     * @param fieldName
     * @param value
     */
    public static void setFieldValueLocal(Object o, String fieldName, final Object value) {
        getFieldValuePoint(o, fieldName, new IFieldValuePoint() {
            @Override
            public Object invoke(Object o, String fieldName) {
                Field f = getFieldLocal(o.getClass(), fieldName);
                try {
                    f.set(o, value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                }
                return null;
            }
        });
    }


    //关于方法

    /**
     * 执行对象方法 搜索当前类
     *
     * @param owner
     * @param methodName
     * @param args
     * @param argsClass
     * @return
     */
    public static Object invokeMethod(Object owner, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethod(owner.getClass(), methodName, argsClass).invoke(owner, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行对象方法 搜索全部父类
     *
     * @param owner
     * @param methodName
     * @param args
     * @param argsClass
     * @return
     */
    public static Object invokeMethodAll(Object owner, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodAll(owner.getClass(), methodName, argsClass).invoke(owner, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行对象可见方法 搜索全部父类
     *
     * @param owner
     * @param methodName
     * @param args
     * @param argsClass
     * @return
     */
    public static Object invokeMethodVisit(Object owner, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodVisit(owner.getClass(), methodName, argsClass).invoke(owner, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行对象 的本地方法
     *
     * @param owner
     * @param methodName
     * @param argsClass
     * @param args
     * @return
     * @throws Exception
     */
    public static Object invokeMethodLocal(Object owner, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodLocal(owner.getClass(), methodName, argsClass).invoke(owner, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行对象 的本地方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethod(Object owner, String methodName, Object... args) {
        return invokeMethod(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行对象 的本地方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethodAll(Object owner, String methodName, Object... args) {
        return invokeMethodAll(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行对象 的本地方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethodVisit(Object owner, String methodName, Object... args) {
        return invokeMethodVisit(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行对象 的本地方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeMethodLocal(Object owner, String methodName, Object... args) {
        return invokeMethodLocal(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行类的静态方法    搜索当前类
     *
     * @param ownerClass 类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethod(Class<?> ownerClass, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethod(ownerClass, methodName, argsClass).invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行类的静态方法     搜索全部父类
     *
     * @param ownerClass 类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethodAll(Class<?> ownerClass, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodAll(ownerClass, methodName, argsClass).invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行类的静态可见方法   搜索全部父类
     *
     * @param ownerClass 类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethodVisit(Class<?> ownerClass, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodVisit(ownerClass, methodName, argsClass).invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行类的静态的本地方法         搜索当前类
     *
     * @param ownerClass 类名
     * @param methodName 方法名
     * @param args       参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethodLocal(Class<?> ownerClass, String methodName, Class<?>[] argsClass, Object[] args) {
        try {
            return getMethodLocal(ownerClass, methodName, argsClass).invoke(null, args);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
        return null;
    }

    /**
     * 执行类的静态方法    搜索当前类
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeStaticMethod(Object owner, String methodName, Object... args) {
        return invokeStaticMethod(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行类的静态方法     搜索全部父类
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeStaticMethodAll(Object owner, String methodName, Object... args) {
        return invokeStaticMethodAll(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行类的静态可见方法   搜索全部父类
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeStaticMethodVisit(Object owner, String methodName, Object... args) {
        return invokeStaticMethodVisit(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 执行类的静态的本地方法         搜索当前类
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     */
    public static Object invokeStaticMethodLocal(Object owner, String methodName, Object... args) {
        return invokeStaticMethodLocal(owner, methodName, getParmsClass(args), args);
    }

    /**
     * 对象当前函数   包括私有函数
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 搜索全部函数  包括私有函数
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethodAll(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Method method = mclazz.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * 搜索全部可见函数
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethodVisit(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        for (Class<?> mclazz = clazz; mclazz != Object.class; mclazz = mclazz.getSuperclass()) {
            try {
                Method method = mclazz.getMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * 对象当前函数   不包括私有函数
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethodLocal(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 得到字段的值
     *
     * @param method
     * @param obj
     * @param args
     * @return
     */
    public static Object invoke(Method method, Object obj, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
        }
        return null;
    }


    //关于 get set

    /**
     * 得到对象的  get 方法      建议使用驼峰命名法     如果不是标准的属性 请参考 invokeMethod?（）方法
     * 如  private String firstName 则 invokeGet(？,firstName)   private String CPU 这样的 请 把第一个单词小写    cpu 则 invokeGet(？,cPU)
     *
     * @param object
     * @param propertiesName
     * @return
     */
    public static Object invokeGet(Object object, String propertiesName) {
        String point = ".";
        String pointFree = "\\."; //必须转义
        try {
            String[] pns = new String[]{propertiesName};
            if (propertiesName.contains(point)) { //包含
                pns = propertiesName.split(pointFree); //必须转义
            }
            Object obj = object;
            for (int i = 0; i < pns.length; i++) {
                String pn = pns[i];
                String methodName = GET + getMethodName(pn);
                Method method = ReflectUtil.getMethodVisit(obj.getClass(), methodName, new Class[]{});
                obj = method.invoke(obj);
            }
            return obj;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 调用对象的 set 方法      建议使用驼峰命名法     如果不是标准的属性 请参考 invokeMethod?（）方法
     * 如  private String firstName 则 invokeSet(？,firstName，？，？)   private String CPU 这样的 请 把第一个单词小写    cpu 则 invokeSet(？,cPU，？，？)
     *
     * @param object
     * @param propertiesName
     * @param argClass
     * @param value
     * @return
     */
    public static Object invokeSet(Object object, String propertiesName, Class<?> argClass, Object... value) {
        String point = ".";
        int lastPoint = propertiesName.lastIndexOf(point); //最后一个点的位置
        try {
            Object obj = object;
            if (propertiesName.contains(point)) { //包含
                String fieldName = propertiesName.substring(0, lastPoint);
                obj = invokeGet(obj, fieldName);
            }
            String methodName = SET + getMethodName(propertiesName.substring(lastPoint + 1));
            Method method = ReflectUtil.getMethodVisit(obj.getClass(), methodName, argClass);
            return method.invoke(obj, value);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为 驼峰命名法的方法  get set
     *
     * @param fildeName
     * @return
     */
    private static String getMethodName(String fildeName) {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }


    //其他

    /**
     * 得到参数类类型
     *
     * @param args
     * @return
     */
    public static Class<?>[] getParmsClass(Object... args) {
        Class<?>[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        return argsClass;
    }

    /**
     * 得到数组中的某个元素
     *
     * @param array 数组
     * @param index 索引
     * @return 返回指定数组对象中索引组件的值
     */
    public static Object getByArray(Object array, int index) {
        return Array.get(array, index);
    }

    /**
     * 是不是某个类的实例
     *
     * @param obj 实例
     * @param cls 类
     * @return 如果 obj 是此类的实例，则返回 true
     */
    public static boolean isInstance(Object obj, Class<?> cls) {
        return cls.isInstance(obj);
    }

    public static List<String> getPackageClassByAndroidAll(Object context, String packageName) {
        List<String> classNameList = new ArrayList<>();
        try {
            classNameList = getPackageClassByAndroidAll(context);
            Object pcp = invokeMethod(context, "getPackageCodePath", new Class[0], new Object[0]); //DexFile
            Object df = newInstance(getClassFromName("dalvik.system.DexFile"), new Class<?>[]{String.class}, new Object[]{pcp});
            Enumeration<String> enumeration = (Enumeration<String>) invokeMethod(df, "entries", new Class[0], new Object[0]);//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();
                if (className.contains(packageName)) {
                    classNameList.add(className);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    public static List<String> getPackageClassByAndroidAll(Object context) {
        List<String> classNameList = new ArrayList<>();
        try {
            Object pcp = invokeMethod(context, "getPackageCodePath", new Class[0], new Object[0]); //DexFile
            Object df = newInstance(getClassFromName("dalvik.system.DexFile"), new Class<?>[]{String.class}, new Object[]{pcp});
            Enumeration<String> enumeration = (Enumeration<String>) invokeMethod(df, "entries", new Class[0], new Object[0]);//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = enumeration.nextElement();
                classNameList.add(className);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classNameList;
    }

    public static void getPackageClassByFileAll(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                getPackageClassByFileAll(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    //classes.add(Class.forName(packageName + '.' + className));
                    //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Class<?>> getPackageClasses(String pack) {
        // 第一个class类的集合
        List<Class<?>> classes = new ArrayList<>();
        try {
            // 是否循环迭代
            boolean recursive = true;
            // 获取包的名字 并进行替换
            String packageName = pack;
            String packageDirName = packageName.replace('.', '/');
            // 定义一个枚举的集合 并进行循环来处理这个目录下的things
            Enumeration<URL> dirs;

            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    //System.err.println("file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    getPackageClassByFileAll(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    //System.err.println("jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class.forName(packageName + '.' + className));
                                        } catch (ClassNotFoundException e) {
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    //Demo
    protected static class VReflectObject {

        public static void main() {
            Object vro = ReflectUtil.newInstanceInner(VReflectObject.class, new Class[]{}, new Object[]{}); //私有方法构造
            System.out.println("==========================>>> 属性");
            System.out.println("获取: " + ReflectUtil.invokeGet(vro, "id"));
            System.out.println("获取嵌套: " + ReflectUtil.invokeGet(vro, "vr2.id"));
            System.out.println("设置嵌套: " + ReflectUtil.invokeSet(vro, "vr2.id", String.class, "2.0"));
            System.out.println("获取嵌套: " + ReflectUtil.invokeGet(vro, "vr2.id"));
            System.out.println("==========================>>> 方法");
            System.out.println("推荐 调用私有公有本类方法: " + ReflectUtil.invoke(ReflectUtil.getMethodAll(VReflectObject.class, "sum", new Class[]{int.class, int.class}), vro, new Object[]{2, 6}));
            System.out.println("调用私有公有本类方法: " + ReflectUtil.invokeMethod(vro, "sum", new Class[]{int.class, int.class}, new Object[]{2, 6}));
            System.out.println("调用私有公有本类父类方法: " + ReflectUtil.invokeMethodAll(vro, "sum", new Class[]{int.class, int.class}, new Object[]{2, 6}));
            System.out.println("调用共有本类父类方法: " + ReflectUtil.invokeMethodVisit(vro, "sub", new Class[]{int.class, int.class}, new Object[]{2, 6}));
            System.out.println("调用公有本类方法: " + ReflectUtil.invokeMethodLocal(vro, "sub", new Class[]{int.class, int.class}, new Object[]{2, 6}));
            System.out.println("==========================>>> 字段");
            System.out.println("推荐 静态私有字段: " + ReflectUtil.getFieldValue(VReflectObject.class, ReflectUtil.getFieldAll(VReflectObject.class, "staticField")));
            ReflectUtil.setFieldValue(VReflectObject.class, ReflectUtil.getFieldAll(VReflectObject.class, "staticField"), "我是静态私有字段2");
            System.out.println("推荐 私有字段: " + ReflectUtil.getFieldValue(vro, ReflectUtil.getFieldAll(VReflectObject.class, "id")));
            ReflectUtil.setFieldValue(vro, ReflectUtil.getFieldAll(VReflectObject.class, "id"), "00");
            System.out.println("静态私有字段: " + ReflectUtil.getStaticFieldValue(VReflectObject.class, "staticField"));
            ReflectUtil.setStaticFieldValue(VReflectObject.class, "staticField", "设置后的 静态字段");
            System.out.println("设置后静态私有字段: " + ReflectUtil.getStaticFieldValue(VReflectObject.class, "staticField"));
            ReflectUtil.setFieldValue(vro, "id", "22222222222222");
            System.out.println("私有字段: " + ReflectUtil.getFieldValue(vro, "id"));
            ReflectUtil.setFieldValue(vro, "vr2.id", "888");
            System.out.println("私有字段嵌套: " + ReflectUtil.getFieldValue(vro, "vr2.id"));
            System.out.println("私有字段嵌套: " + ReflectUtil.getFieldValue(vro, "vr2.vr3.id"));
            System.out.println("==========================>>> 注解");
            System.out.println("获取注解: " + ReflectUtil.getTags(VReflectObject.class, ATag.class)[0].value());
            System.out.println("获取全部类注解: " + ReflectUtil.getTagsAll(VReflectObject.class, ATag.class).get(0)[0].id());


        }

        private VReflectObject() {
            super();
            vr2 = new VReflectObject2();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public VReflectObject2 getVr2() {
            return vr2;
        }

        public void setVr2(VReflectObject2 vr2) {
            this.vr2 = vr2;
        }

        @SuppressWarnings("unused") //私有  但反射可以从外部调用
        private String sum(int l, int r) {
            return (l + r) + "";
        }

        public String sub(int l, int r) {
            return (l - r) + "";
        }

        @ATag(id = 666, value = "我是id")
        private String id = "0";
        @ATag("我是姓名")
        private String name = "";
        private VReflectObject2 vr2;
        @SuppressWarnings("unused") //私有  但反射可以从外部调用
        private static String staticField = "我是静态私有字段";
    }

    protected static class VReflectObject2 {
        private String id = "id22222222222222222";
        @SuppressWarnings("unused")  //私有  但反射可以从外部调用
        private VReflectObject3 vr3;

        private VReflectObject2() {
            super();
            vr3 = new VReflectObject3();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    protected static class VReflectObject3 {
        private String id = "333333333333333333333  我是  VReflectObject 中的  VReflectObject2  中的  VReflectObject3 的 id 字段";

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    @Retention(RetentionPolicy.RUNTIME) //提供反射
    @Target(value = {CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
    //此处成为元注解 ，注解的定义需要元注解
    private static @interface ATag {
        public int id() default 0; //此处需要注意写法

        public String value() default "我是默认值的 注解";  //缺省注解 主要标注

        public String comment() default "";  //复合特殊类型

    }
}

//demo 反射测试
/*
static String[] tableRow;
private static void fanshe()
	{
		VCarUser vu = new VCarUser(); //测试对象
		Class<?> c = vu.getClass(); //反射
		Field[] fields = c.getDeclaredFields(); //属性
		tableRow = new String[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true); //反射私有属性 权限
			Field field = fields[i]; //第几个参数
			Class<?> type = field.getType(); //反射类型
			String typeName = type.getName();//得到类型
			System.out.println(typeName);
			String fieldName = field.getName(); //反射字段
			System.out.println(fieldName); //字段名
			tableRow[i] = fieldName;
			try
			{
				Object value = field.get(vu); //字段值
				System.out.println(value);

			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}

			if ("java.lang.String".equals(typeName))
			{
				try
				{
					field.set(vu, "1");

				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

		System.out.println(vu.getClass().getName());

	}
	*/

