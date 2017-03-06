传统MVC：
  1. Model：server端，serverlet，数据读写相关，组装成javaBean
  2. View：FE端，展示
  3. Controler：server端，粗逻辑过滤（拦截，处理，分发）

狭义的移动产品（手机）的MVC：
  1. Model：数据（意义扩大）（不问来源，本地、server）
  2. View：activity，fragment（持有content）（共同点：负责交互的）
  3. Controler：数据的处理，数据的加工，向模型发数据

为什么沿用：
  1. 软件就是操作数据。软件产品目的：就是让人方便的操作数据。（让人方便，就要有视图，数据就要有数据，操作就要有处理）
  2. 代码模块化、功能模块化（方便于都，复用，管理，分工）
  3. 模块化目的：低耦合、高复用、接口规范


Html  server  DB
mvc   server  DB

3. 从MVC到MVP
  1. Model：业务相关的数据的逻辑，面向业务；
  2. View：拿到数据后的交互、展示
  3. Controler/Presenter/ViewModel：（V到C，C到M，M到V）（V到P，P到M，M到P，P到V）（V=VM到M，M到VM=V）

IPresenter:
  1. view主动发起的行为（调用）
  2. model主动发起的行为（调用）
IView
  1. view可以暴露出来的行为
Imodel
  1. model暴露出来的行为
