##### global项目
1. 代码设计
    1. SourceProxy：         面向外部被调用（activity中，setBackground（）...）
        1. 功能：提供getColor等API
        2. 下级类：
            1. 
        
    2. SourceManager：       初始化，切语言等（面向项目setting的设置）
        1. 功能：
            1. 提供初始化，备份环境
            2. 功能设置（是否记录view，用于语言选择）
        2. 成员类：
            1. SourceAttrModel：     资源属性的封装
            2. SourceProvider：      资源的真实处理者
            3. ViewRecordManager：   已经加载过view的记录者
           
2. SourceInflateFactory  代理xml处理图片的场景（）