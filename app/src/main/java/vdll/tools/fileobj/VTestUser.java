package vdll.tools.fileobj;
import java.io.Serializable;

/**
 * 演示例子  可根据需要添加字段或方法
 * 可重新创建数据类  (但必须实现接口  implements Serializable)
 * @author Hocean
 *
 */
public class VTestUser implements Serializable
{

	/**
	 * 自动生成的序列化 ID
	 */
	private static final long serialVersionUID = -8703785453230843333L;
	
	public String vname = "";
	public String sex = "";
	public int id = 0;
	
}

