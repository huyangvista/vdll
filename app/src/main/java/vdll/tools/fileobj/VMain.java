package vdll.tools.fileobj;

import java.util.ArrayList;

/**
 * 演示 自定义数据文件读写
 * @author Hocean
 *
 */
public class VMain
{
	public static void main(String[] args)
	{
		//创建一个用户
		VTestUser user = new VTestUser();
		user.id = 10;
		user.vname = "叶孤城";
		user.sex = "男性";

		//实例化数据文件 (<VUser>中  VUser可以换做你自己定义的用户类 但必须实例化接口  implements Serializable)
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

	}
}
