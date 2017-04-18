package vdll.data.msql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 数据库注解 提取
 * 
 * @author Hocean
 *
 */
public class SqlTags implements Iterable<SqlTags.RowTags> {
	private RowTags[] vatrow;  //demo    vatrow = Arrays.copyOf(vatrow, vicountNew);
//	public List<RowTags> valRow = new ArrayList<>(); // 所有带标记
//	public List<RowTags> valRowAll = new ArrayList<>(); // 全部
//	public List<RowTags> valRowKey = new ArrayList<>(); // 所有主键
//	public List<RowTags> valRowNoKey = new ArrayList<>(); // 没有任何主键
//	public List<RowTags> valRowAi = new ArrayList<>(); // 仅有自增的主键
//	public List<RowTags> valRowNoAi = new ArrayList<>(); // 仅没有自增的主键
//	public List<RowTags> valRowKeyAndNoAi = new ArrayList<>(); // 所有主键
	
/*//	public List<RowTags> valRowKeyAndNoAi = new ArrayList<>(); // 所有主键
*/

	public SqlTags() {
		
	}
	
	/**
	 * 获取对象的 注解 和 值
	 * @param cls
	 * @param vo
	 * @param ap
	 * @return
	 */
	public static  List<RowTags> get(Class<?> cls, Object vo, AISql.Parms ap){
		List<RowTags> valRow = new ArrayList<>(); // 所有带标记
		Field[] fields = cls.getDeclaredFields(); // 属性
		for (int i = 0; i < fields.length; i++) {
			Field fi = fields[i]; // 第几个参数
			RowTags vtr = new RowTags();
			try {
				fi.setAccessible(true); // 反射私有属性 权限
				vtr.setField(fi);
				Class<?> type = fi.getType(); // 反射类型
				vtr.setType(type.getName());// 得到类型
				vtr.setName(fi.getName()); // 反射字段
				//vtr.aitag = field.getAnnotation(AITag.class); // 得到注解
				vtr.setValue(fi.get(vo)); // 得到字段值 有可能 抛出Ex
			} catch (Exception e) {
			}
			if(ap == null){
				valRow.add(vtr);
			}
			if (fi.isAnnotationPresent(AISql.class)) // 含有标记
			{
				vtr.setAitag(fi.getAnnotation(AISql.class)); // 得到注解
				boolean id = ap.id == null || vtr.getAitag().id() == ap.id;
				boolean value = ap.value == null || vtr.getAitag().value().equals(ap.value);

				boolean field = ap.field == null || vtr.getAitag().field().equals(ap.field);
				boolean type = ap.type == null || vtr.getAitag().type().equals(ap.type);
				boolean length = ap.length == null || vtr.getAitag().length() == ap.length;
				boolean number = ap.number == null || vtr.getAitag().number() == ap.number;
				boolean noNull = ap.noNull == null || vtr.getAitag().isNoNull() == ap.noNull;
				boolean primaryKey = ap.primaryKey == null || vtr.getAitag().isPrimaryKey() == ap.primaryKey;

				boolean defParm = ap.defParm == null || vtr.getAitag().defParm().equals(ap.defParm);
				boolean note = ap.note == null || vtr.getAitag().note().equals(ap.note);
				boolean autoAdd = ap.autoAdd == null || vtr.getAitag().isAutoAdd() == ap.autoAdd;
				boolean noMark = ap.noMark == null || vtr.getAitag().isNoMark() == ap.noMark;
				boolean fillZero = ap.fillZero == null || vtr.getAitag().isFillZero() == ap.fillZero;

				if(id && value &&     field && type && length && number && noNull && primaryKey &&   
						defParm && note && autoAdd && noMark && fillZero ){
					valRow.add(vtr); //全部装入
				}			
			}			
		}				
		return valRow;
	}
	
	public static  List<RowTags> get(Class<?> cls, AISql.Parms ap){
		return get(cls, cls, ap);
	}

	public static List<RowTags> get(Object vo, AISql.Parms ap){
		return get(vo.getClass(), vo, ap);
	}

	// 每行字段的信息
	public static class RowTags {
		private Field field; //函数
		private String type; // 类型
		private String name; // 字段
		private Object value; // 值
		private AISql aitag; // 注解
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public AISql getAitag() {
			return aitag;
		}
		public void setAitag(AISql aitag) {
			this.aitag = aitag;
		}
		public Field getField() {
			return field;
		}
		public void setField(Field field) {
			this.field = field;
		}
	}

	@Override
	public Iterator<RowTags> iterator() {
		// TODO Auto-generated method stub
		return new VIterator();
	}

	public class VIterator implements Iterator<RowTags> {
		private int vindex = 0;

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return vindex != vatrow.length;
		}

		@Override
		public RowTags next() {
			// TODO Auto-generated method stub
			return vatrow[vindex++];
		}

		@Override
		public void remove() {
			// TODO: Implement this method
		}

	}

}

/*
 * //USE 使用方法 VTag vtag = new VTag(User.class);
 * 
 * for (VTag.RowTags vTagRow : vtag) { if(vTagRow.itag != null) {
 * System.err.println(vTagRow.vsname); System.err.println(vTagRow.vstype);
 * System.err.println(vTagRow.vovalue);
 * System.err.println(vTagRow.itag.value());
 * 
 * }
 * 
 * }
 */

// foreatch
/*
 * List<ITag> vlist = VReflect.getTag(User.class, ITag.class);
 * 
 * for (ITag iTag : vlist) { System.err.println(iTag.id());
 * System.err.println(iTag.value()); }
 */

// User user = new User();

/*
 * Field[] fields = VReflect.getFields(User.class); for (Field field : fields) {
 * if (field.isAnnotationPresent(VTest.class)) { VTest tag = (VTest)
 * field.getAnnotation(VTest.class); tag.value(); System.out.println(tag.id());
 * }
 * 
 * }
 */

/*
 * public static void trackUseCases(List useCases, Class cl) { for (Method m :
 * cl.getDeclaredMethods()) { UseCase uc = m.getAnnotation(UseCase.class); if
 * (uc != null) { System.out.println("Found Use Case:" + uc.id() + " " +
 * uc.description()); useCases.remove(new Integer(uc.id())); } } for (int i :
 * useCases) { System.out.println("Warning: Missing use case-" + i); } }
 * 
 * //快速添加 列表
 * 
 * @SuppressWarnings("unchecked") public static void main(String[] args) {
 * 
 * @SuppressWarnings("rawtypes") List useCases = new ArrayList();
 * Collections.addAll(useCases, 47, 48, 49, 50); trackUseCases(useCases,
 * PasswordUtils.class); }
 */
