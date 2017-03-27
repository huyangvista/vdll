package vdll.tools.fileobj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//import java.util.Base64;  //Vive  Java1.8 使用

/**
 * 封装的文件操作类   自定义数据文件读写
 * @author Hocean
 *
 * @param <T> 自定义的用户对象 需要implements Serializable
 */
public class VFiles<T>
{
	private File file; //当前操作文件
	public ArrayList<T> vusers; //当前操作的 数据集合

	//构造文件操作类
	public VFiles(String path)
	{
		// TODO 锟皆讹拷锟斤拷锟缴的癸拷锟届函锟斤拷锟斤拷锟?
		file = new File(path);
	}

	//添加用户  需要保存
	public void AddUser(T user)
	{
		if (vusers == null) GetUserAll();
		vusers.add(user);
	}
	//添加用户  需要保存
	public void AddUser(T[] us)
	{
		if (vusers == null) GetUserAll();
		
		for(int i=0;i<us.length;i++){
			vusers.add(us[i]);
		}
	}
	

	//添加用户且保存  线程安全
	public synchronized void AddUserSave(T user)
	{
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			String vsUser = VSerializable.ObjectToString(user);
			bw.write(vsUser);
			bw.newLine();
			bw.flush();
			bw.close();
		}
		catch (IOException e)
		{
			// TODO 锟皆讹拷锟斤拷锟缴碉拷 catch 锟斤拷
			e.printStackTrace();
		}
	}

	//获取数据文件用户数量
	public int Count()
	{
		if (vusers == null) GetUserAll();
		return vusers.size();
	}

	//获取一个用户
	public T GetUser(int index)
	{
		if (vusers == null) GetUserAll();
		return vusers.get(index);
	}

	//获取全部用户
	public ArrayList<T> GetUserAll()
	{
		vusers = new ArrayList<T>();
		if (!file.exists()) return vusers; //不存在文件返回
		try
		{
			BufferedReader bre = new BufferedReader(new FileReader(file));
			String vsout;
			while ((vsout = bre.readLine()) != null)
			{
				@SuppressWarnings("unchecked")
				T v = (T) VSerializable.StringToObject(vsout);
				vusers.add(v);
			}
			bre.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return vusers;
	}

	//当前数据集保存到数据文件
	public synchronized void Save()
	{
		if (vusers == null) return;
		Delete();
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			for (T t : vusers)
			{
				String vsUser = VSerializable.ObjectToString(t);
				bw.write(vsUser);
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	//删除数据文件
	public void Delete()
	{
		if (file.isFile() && file.exists())
		{
			file.delete();
			Delete();
		}
		else
		{
			return;
		}
	}

}

/**
 * 序列化数据
 * @author Hocean
 *
 */
class VSerializable
{
	//字节 =》 对象  
	public static Object ByteToObject(byte[] bytes)
	{
		Object obj = null;
		try
		{
			// bytearray to object
			ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
			ObjectInputStream oi = new ObjectInputStream(bi);
			obj = oi.readObject();
			bi.close();
			oi.close();
		}
		catch (Exception e)
		{
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	//对象 =》  字节
	public static byte[] ObjectToByte(Object obj)
	{
		byte[] bytes = null;
		try
		{
			// object to bytearray
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
			bo.close();
			oo.close();
		}
		catch (Exception e)
		{
			System.out.println("translation" + e.getMessage());
			e.printStackTrace();
		}
		//byte[] out = new byte[bytes.length];
		//Base64.getEncoder().encode(bytes,out);
		return bytes;
	}

	//字符串 =》 对象 
	public static Object StringToObject(String str)
	{
		//Vive  Java1.8 使用
		//byte[] bytes = Base64.getDecoder().decode(str); 	
		//return ByteToObject(bytes);
		//自定义编码
		byte[] bytes = Base64.decode(str.getBytes());
		return ByteToObject(bytes);
		//return ByteToObject(str.getBytes(Charset.defaultCharset()));
	}

	//对象 =》 字符串 
	public static String ObjectToString(Object obj)
	{
		//Vive  Java1.8 使用	
		//byte[] bytes = ObjectToByte(obj);
		//return Base64.getEncoder().encodeToString(bytes);
		//自定义解码
		byte[] bytes = ObjectToByte(obj);
		return new String(Base64.encode(bytes));

		//byte[] bytes = ObjectToByte(obj);
		//return new String(bytes,Charset.defaultCharset());
	}
}

/*//演示代码
//创建一个用户
		VTestUser user = new VTestUser();
		user.id = 10;
		user.vname = "叶孤城";
		user.sex = "男性";

		//实例化数据文件 (<VTestUser>中  VTestUser可以换做你自己定义的用户类 但必须实例化接口  implements Serializable)
		VFiles<VTestUser> vfs = new VFiles<VTestUser>("./test.txt"); // (./ 表示根目录)
		//VFiles vfs = new VFiles("./test.txt"); // 不指定泛型
		//添加用户到数据文件 需要手动保存  方法1
		vfs.AddUser(user);
		vfs.AddUser(user);
		vfs.Save();
		//添加用户到数据文件 并且保存  方法 2
		vfs.AddUserSave(user);
		//获取数据文件到 数据集
		ArrayList<VTestUser> vUsers = vfs.GetUserAll(); 
		//修改第一个数据集中的用户 id
		vUsers.get(0).id = 999999;
		//删除一个用户
		vUsers.remove(1); // 等同于  vUsers.remove(vUsers.get(1));
		//保存修改后的 数据   更改数据一定要保存
		vfs.Save();
		//删除数据文件
		vfs.Delete();
		//遍历所有用户数据
		for (int i = 0; i < vUsers.size(); i++)
		{
			System.out.println("------> 用户: " + i);
			VTestUser u = vUsers.get(i);
			System.out.println(u.id);
			System.out.println(u.vname);
			System.out.println(u.sex);
		}
*/
