eclispe 运行mybatis-generator.xml  
3.运行方法：在eclipse 中，选择pom.xml文件，击右键先择Run AS——>Maven Build… ——>在Goals框中输入：mybatis-generator:generate

说明：添加自动生成代码需要在pom.xml配置

<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.5</version>
                <configuration>
                    <!--允许移动生成的文件-->
                    <verbose>false</verbose>
                    <!--允许覆盖生成的文件-->
                    <overwrite>true</overwrite>
                    <!--配置文件的路径-->
                    <configurationFile>src/main/resources/mybatis-generator.xml</configurationFile>
                </configuration>
                <dependencies>
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>${mysql.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
    
    


    