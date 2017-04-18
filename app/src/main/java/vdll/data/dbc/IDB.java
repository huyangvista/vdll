package vdll.data.dbc;

/**
 * Created by Hocean on 2017/4/15.
 */
public interface IDB {
}


/* 链接数据库的文件  dbcp.properties

####Mysql
#数据库驱动
driverClassName=com.mysql.jdbc.Driver
#数据库连接地址
url=jdbc:mysql://vives.cc:3306/tbi_erp?useUnicode=true&characterEncoding=utf8
#用户名
username=root
#密码
password=hoceanvista

####dbcp
#连接池的最大数据库连接数。设为0表示无限制
maxActive=30
#最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后被释放。设为0表示无限制
maxIdle=10
#最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制
maxWait=6000
#超过removeAbandonedTimeout时间后，是否进 行没用连接（废弃）的回收（默认为false，调整为true)
removeAbandoned=true
#超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
removeAbandonedTimeout=180

*/
