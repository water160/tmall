# A shopping center system imitated [天猫](http://tmall.com)
---
1. 创建实体类bean，与数据库中字段相对应

2. 创建数据库工具类util

3. 创建数据库访问操作类dao，与bean中的每个实体相对应，这一步在后续中会由框架完成，省去了很多代码

4. 通常来说，浏览器提交请求到服务器时，会调用servlet对应的doGet或doPost方法（Servlet响应请求），接着在servlet中调用service类，然后再service类中调用DAO类，最后访问数据库获取相应数据
项目中免去了service业务类（DAO中的方法已经足够支撑业务，在DAO上包一层Service业务类会冗余）

