springBoot加载自定义配置文件xx.properties

```
1.
@SpringBootApplication

@PropertySource("classpath:json.properties")

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    
    }

}

2.
@Component
@ConfigurationProperties(prefix = "web")
public class Properties {
    private  String jsonServiceName;
    private  String jsonClientsName;
    private  String pach;

    public String getJsonServiceName() {
        return jsonServiceName;
    }

    public void setJsonServiceName(String jsonServiceName) {
        this.jsonServiceName = jsonServiceName;
    }

    public String getJsonClientsName() {
        return jsonClientsName;
    }

    public void setJsonClientsName(String jsonClientsName) {
        this.jsonClientsName = jsonClientsName;
    }

    public String getPach() {
        return pach;
    }

    public void setPach(String pach) {
        this.pach = pach;
    }
}


3. json.properties

web.jsonServiceName=Service.json
web.jsonClientsName=Clients.json
web.pach=/root/devtools/
web.redisShell=/root/redis.sh

使用:

 @Autowired
 private Properties properties;



```

