# 动态表单作业

### 1.简介
- ##### 后端实现技术：springboot、mybatis-plus、mysql、swagger、docker、lombok、mockito
- ##### 项目管理：maven、spotify-docker插件
- ##### 数据库脚本位置：./sql 
- ##### Docker-file位置:./Dockerfile

### 2.docker打包和部署过程：
- 1.打包：

       mvn package docker:build
    
- 2.查看镜像：

       docker images

- 3.推送镜像到阿里云：
    
1.     sudo docker login --username=1459637210@qq.com registry.cn-hangzhou.aliyuncs.com
1.     sudo docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/starbucks-star/star:[镜像版本号]
1.     sudo docker push registry.cn-hangzhou.aliyuncs.com/starbucks-star/star:[镜像版本号]

- 4.本地运行镜像：

       docker run -p 8081:8081 -it springboot/test(容器连接外部数据库)
-  5.注意：

        运行镜像前，请先在数据库中执行sql中的两个数据库脚本


### 3.接口简介：
    | 接口名  | url  | 备注  |
    | ------------ | ------------ | ------------ |
    | 创建表单  | /createForm  | 返回值data中的value为formId  |
    | 查看表单  | /selectForm  |  - |
    | 填写表单  | /fillFormData  | 返回值data中的value为formDataId  |
    | 查看表单数据  | /selectFormData  |  -   |

##### 具体接口入参出参查看swagger

### 4.单元测试：
##### 1.测试文件位置：.src//test/java/com/star/test/TestDynamicFormServiceTests.java
##### 2.单元测试：mockito相关测试，测试覆盖率结果如下：

![输入图片说明](https://images.gitee.com/uploads/images/2019/0606/124923_ae8aae73_1826831.png "test-coverage.png")






