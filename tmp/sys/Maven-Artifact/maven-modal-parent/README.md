# Maven-Project
## 1.项目的框架结构
> 本项目为案例项目，使用Maven技术贯穿整个项目，利用了诸多的Maven技巧<br />
├─maven-parent 整体项目的福模块<br />
├─maven-modal-3rd  三方模块<br />
├─maven-modal-common 通用组件模块<br />
├─maven-modal-service01 服务模块01<br />
│  ├─service01-modal-dao 数据库访问层<br />
│  ├─service01-modal-domain  实体层<br />
│  └─service01-modal-logical 逻辑层<br />
├─maven-modal-service02<br />
│  ├─service02-modal-dao<br />
│  ├─service02-modal-domain<br />
│  └─service02-modal-logical<br />
├─maven-modal-service03<br />
│  ├─service03-modal-dao<br />
│  ├─service03-modal-domain<br />
│  └─service03-modal-logical<br />
├─maven-modal-utils 工具模块<br />
├─maven-modal-webapp 应用模块<br />
│  ├─maven-modal-api 应用接口模块<br />
│  └─maven-modal-web 应用显示模块<br />

## 2.项目已经实现功能
### 2.1 Maven配置
    项目的jar包和插件都在settings-liuburu.xml文件中指定，若为团队开发，则导入setting-liubur.xml
文件则可立即上手开发项目，而不用自己指定仓库地址，并且maven校验位daily（每天检查一次），而不必
每次mvn compile的时候的时候都去私库校验jar的合法性，应为在短时间内，我们认为模块是不会更新的，
一般jar包更新周期通常都是会大于一天。
### 2.2 在maven-modal-parent中配置项目依赖的jar包版本
    在项目中设置了maven-modal-parent模块的目的是为了统筹各个子模块的jar包版本和统筹各个服务模块，
这样做的好处比如：spring依赖版本需要提高，或者项目需要deploy，遇到这些情景的时候我们只需要更新
maven-modal-parent中的版本信息，而不需要修改其他模块的版本信息，减少重复性工作。
### 2.3 maven-modal-webapp独立于其他模块
    需要注意的是本模块依赖的service模块提供服务（dubbo,esb,springcloud），而不直接用dependency来指定模块依赖。而是使用
dependency来指定对logical模块的依赖。
### 2.4
## 2.项目希望实现功能







