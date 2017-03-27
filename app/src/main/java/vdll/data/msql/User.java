package vdll.data.msql;

/**
 * 用户
 * @author Hocean
 *
 */
//@TableAnnotation(name = "t_user")
//@Deprecated
@AISql(value = "class,user")
public class User
{
	@AISql(type = AISql.Type._int, length=11, isPrimaryKey = true, isAutoAdd = true)
	public String id;
	@AISql(type = AISql.Type._varchar, length = 255)
	public String name;
	@AISql
	public String psword;
	@AISql(type = AISql.Type._varchar, length = 255)
	public String email;
	@AISql
	public String tel;
	@AISql
	public String reIntro;
	@AISql(type = AISql.Type._double, length = 255, number = 20)
	public String cardID;
	@AISql(type = AISql.Type._varchar)
	public String firstName;
	@AISql
	public String address;

}