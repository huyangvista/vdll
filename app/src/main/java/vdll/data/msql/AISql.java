package vdll.data.msql;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;

//@Documented
@Retention(RetentionPolicy.RUNTIME) //提供反射
@Target(value = { CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE }) //此处成为元注解 ，注解的定义需要元注解
public @interface AISql
{
	//常用写法
	public int id() default 0;	 ////此处需要注意写法
	public String value() default "";  //-1-2-3-主要标注	 ////value 缺省字段
	
	//数据库参数
	public String field() default "";  //字段名
	public String type() default "varchar";  //-1-主要标注
	public int length() default 255;  //-2-长度	
	public int number() default 0;  //-3-小数	
	public boolean isNoNull() default false;  //不为空    默认可为空	
	public boolean isPrimaryKey() default false;  //是否主键	
	//其他属性
	public String defParm() default "";  //默认值
	public String note() default "";  //注释	
	public boolean isAutoAdd() default false;  //是否自增
	public boolean isNoMark() default false;  //无符号
	public boolean isFillZero() default false;  //填充 0
	public boolean isBin() default false;  //二进制
	
	/**
	 * Type 详细参数
	 * @author Hocean
	 *
	 */
	public static class Type
	{
		//标记
		public final static  String  _PK        = "PRIMARY KEY"       ;
		public final static  String  _AI        = "AUTO_INCREMENT"       ;

		//字段
		public final static  String  _tinyint            = "tinyint"           ;
		public final static  String  _smallint           = "smallint"          ;
		public final static  String  _mediumint          = "mediumint"         ;
		public final static  String  _int                = "int"               ;
		public final static  String  _integer            = "integer"           ;
		public final static  String  _bigint             = "bigint"            ;
		public final static  String  _bit                = "bit"               ;
		public final static  String  _real               = "real"              ;
		public final static  String  _double             = "double"            ;
		public final static  String  _float              = "float"             ;
		public final static  String  _decimal            = "decimal"           ;
		public final static  String  _numeric            = "numeric"           ;
		public final static  String  _char          	 = "char"              ;
		public final static  String  _varchar            = "varchar"           ;
		public final static  String  _date               = "date"              ;
		public final static  String  _time               = "time"              ;
		public final static  String  _year               = "year"              ;
		public final static  String  _timestamp          = "timestamp"         ;
		public final static  String  _datetime           = "datetime"          ;
		public final static  String  _tinyblob           = "tinyblob"          ;
		public final static  String  _blob               = "blob"              ;
		public final static  String  _mediumblob         = "mediumblob"        ;
		public final static  String  _longblob           = "longblob"          ;	
		public final static  String  _tinytext           = "tinytext"          ;
		public final static  String  _text               = "text"              ;
		public final static  String  _mediumtext         = "mediumtext"        ;
		public final static  String  _longtext           = "longtext"          ;	
		public final static  String  _enum               = "enum"              ;
		public final static  String  _set                = "set"               ;
		public final static  String  _binary             = "binary"            ;
		public final static  String  _varbinary          = "varbinary"         ;
		public final static  String  _point              = "point"             ;
		public final static  String  _linestring         = "linestring"        ;
		public final static  String  _polygon            = "polygon"           ;
		public final static  String  _geometry           = "geometry"          ;
		public final static  String  _multipoint         = "multipoint"        ;
		public final static  String  _multilinestring    = "multilinestring"   ;
		public final static  String  _multipolygon       = "multipolygon"      ;
		public final static  String  _geometrycollection = "geometrycollection"; 

	}
	
	public static class Parms{
		//常用写法
		public Integer id;	 ////此处需要注意写法
		public String value;  //-1-2-3-主要标注	 ////value 缺省字段
		
		//数据库参数
		public String field;  //字段名
		public String type;  //-1-主要标注
		public Integer length;  //-2-长度	
		public Integer number;  //-3-小数	
		public Boolean noNull;  //不为空    默认可为空	
		public Boolean primaryKey;  //是否主键	
		//其他属性
		public String defParm;  //默认值
		public String note;  //注释	
		public Boolean autoAdd;  //是否自增
		public Boolean noMark;  //无符号
		public Boolean fillZero;  //填充 0
	}
	
	
	
	
	
}


enum Kind {
    /**
     * Source files written in the Java programming language.  For
     * example, regular files ending with {@code .java}.
     */
    SOURCE(".java"),

    /**
     * Class files for the Java Virtual Machine.  For example,
     * regular files ending with {@code .class}.
     */
    CLASS(".class"),

    /**
     * HTML files.  For example, regular files ending with {@code
     * .html}.
     */
    HTML(".html"),

    /**
     * Any other kind.
     */
    OTHER("");
    /**
     * The extension which (by convention) is normally used for
     * this kind of file object.  If no convention exists, the
     * empty string ({@code ""}) is used.
     */
    public final String extension;
    private Kind(String extension) {
        extension.getClass(); // null check
        this.extension = extension;
    }
}

enum ETag
{
_tinyint               ,
_smallint              ,
_mediumint             ,
_int                   ,
_integer               ,
_bigint                ,
_bit                   ,
_real                  ,
_double                ,
_float                 ,
_decimal               ,
_numeric               ,
_char             ,
_varchar          ,
_date                  ,
_time                  ,
_year                  ,
_timestamp             ,
_datetime              ,
_tinyblob              ,
_blob                  ,
_mediumblob            ,
_longblob              ,
_enum              ,
_set               ,
_binary           ,
_varbinary        ,
_point                 ,
_linestring            ,
_polygon               ,
_geometry              ,
_multipoint            ,
_multilinestring       ,
_multipolygon          ,
_geometrycollection    ,
}

/*
	public int id() default 0;
	public String value() default "varchar(100)";
	@ITag(id = 3, value = "varchar(100)")
*/