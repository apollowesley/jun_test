### 行为型模式11中
- 1.策略模式(Strategy)
* 环境(Context)角色：持有一个Strategy的引用。
* 抽象策略(Strategy)角色：这是一个抽象角色，通常由一个接口或抽象类实现。此角色给出所有的具体策略类所需的接口。
* 具体策略(ConcreteStrategy)角色：包装了相关的算法或行为。

使用场景：商店员工能为消费者打9折，店长能为消费者打8折再减50，分销经理能为消费者打6折。

- 2.模板方法模式(Templete Method)
* 需要执行的目标方法抽象画，在抽象接口其他具体方法中调用达到模板化的目的
* 使用场景：计算程序执行时间

- 3.观察者模式(Observer)
* 实现：自己手动实现；使用JDK自带实现。
* 使用场景：订阅发布模式


- 4.迭代子模式(Iterator)
* 集合迭代

- 5.责任链模式(Chain Of Responsibility)
* 击鼓传花游戏

- 6.命令模式(Command)
* 司令命令小兵、点击IDEA功能菜单

- 7.备忘录模式(Menmento)
* Redis缓存

- 8.状态模式(State)
* QQ的集中状态：在线、离线、离开；菜单栏的功能按钮

- 9.访问者模式(Vistor)
* 宾客主动登门造访vistor.visit(subject);；东道主等待宾客前来拜访subject.accept(vistor);

- 10.中介者模式（Mediator）
* 中介者持有关联对象的引用，而不是由关联对象直接维护对象之间的关系

- 11.解释器模式(Interpreter)
* 执行运算法则的时候，可以考虑使用解释器模式
* 实例：SpringMongo，Hibernate SQL Critia

