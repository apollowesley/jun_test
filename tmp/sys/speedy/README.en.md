# speedy management system
<h2> development environment description </h2>
Development environment JAVA, JDK1.85 development framework spring+sprignMVC+Mybatis background page framework :HTML+JavaScript+Jquery+Bootstrap big data micromanagement platform
<h2> project description </h2>
The rapid development of a simple type of site, modular, concise
< p > note < / p >
1. Increase of search function </p>
2. Modification of personal information </p>
3. Data statistics panel </p>
4. Docking of SMS payment </p>
5. Make the module unique and speedy, with a unique UI</p>
6. Unified management of resources, which is stored in the form of named file as "name _ table" for easy management of </p>
# # is introduced
1. Springboot infrastructure </p>
2. Mybatis -Generate background code generator </p>
3. Freemarker thymeleaf front-end static page configuration </p>
Note: currently only Mysql data source is supported (other data sources need to be configured and modified by themselves) </p>
## software architecture
<h3> base class: </h3>
1.CodeController code generation controller </p>
2.LoginController LoginController </p>
3.MainController </p>
4.MenuController MenuController </p>
5.RoleController RoleController </p>
6.SytemUserController system personnel controller </p>
ToolController (payment, SMS, etc.) </p>
<p>8.FirstController first enters background installation check </p>
9.PluginController PluginController management control </p>
10.UserController background user management control </p>
<p>11.ViewController front ViewController </p>
12.ApiController interface management controller (handles cross-domain)</p>
## installation tutorial
Note: the installation is completed with the basic data information
<p> front-end: 127.0.0.1(corresponding domain name) home </p>
<p> backend: 127.0.0.1/system</p>
1. Access [domain name /system] directly after the first installation. After the configuration is completed, click save to automatically install the basic data </p> according to the configured database
2. Self-configure the required structure directory </p>
3. </p> can be used after configuration
## instructions
1. Log in for the first time and directly enter 127.0.0.1/system to install the database </p>
2. Use the platform </p> after installation
3. If development needs to be carried out, it needs to be Shared locally for development code generation, etc. It needs to have a certain understanding of SpringMVC pattern </p>
<p> note: the code generation tool does not support using </p> in JAR mode
< br / >
< img SRC = "https://gitee.com/lqyang/speedy/blob/master/src/main/resources/static/images/wxpay.png" / >
< br / >
# # cooperation
<p> support customized APP, website, WeChat development </p>
<p> WeChat ID lqy84527</p>