package vdll.data.dbc;

import java.util.Properties;

/**
 * Created by Hocean on 2017/4/15.
 */
public class DBProp {

    //info
    private final String configFile = "dbcp.properties";
    private Properties prop = new Properties();

    //mysql
    private String driverClassName;
    private String url;
    private String databaseName;
    private String username;
    private String password;

    //dbcp
    private String maxActive;
    private String maxIdle;
    private String maxWait;
    private String removeAbandoned;
    private String removeAbandonedTimeout;

    public DBProp() {
    }

    public DBProp(Properties prop) {
        this.prop = prop;
    }

    public void load() {
        prop = new Properties();
        try {
            prop.load(DBProp.class.getClassLoader().getResourceAsStream(configFile));

            driverClassName = prop.getProperty("driverClassName");
            url = prop.getProperty("url");
            databaseName = url.substring(url.lastIndexOf("/") + 1);
            if(url.contains("?")){
                databaseName = databaseName.substring(0,databaseName.indexOf("?"));
            }
            username = prop.getProperty("username");
            password = prop.getProperty("password");

            if (prop.containsKey("maxActive")) maxActive = prop.getProperty("maxActive");
            if (prop.containsKey("maxIdle")) maxIdle = prop.getProperty("maxIdle");
            if (prop.containsKey("maxWait")) maxWait = prop.getProperty("maxWait");
            if (prop.containsKey("removeAbandoned")) removeAbandoned = prop.getProperty("removeAbandoned");
            if (prop.containsKey("removeAbandonedTimeout"))
                removeAbandonedTimeout = prop.getProperty("removeAbandonedTimeout");

        } catch (Exception e) {
        }
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String dataBaseName) {
        this.databaseName = dataBaseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(String maxWait) {
        this.maxWait = maxWait;
    }

    public String getRemoveAbandoned() {
        return removeAbandoned;
    }

    public void setRemoveAbandoned(String removeAbandoned) {
        this.removeAbandoned = removeAbandoned;
    }

    public String getRemoveAbandonedTimeout() {
        return removeAbandonedTimeout;
    }

    public void setRemoveAbandonedTimeout(String removeAbandonedTimeout) {
        this.removeAbandonedTimeout = removeAbandonedTimeout;
    }
}
