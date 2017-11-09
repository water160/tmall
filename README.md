# A shopping center system imitated [天猫](http://tmall.com)
---
1. 创建实体类bean，与数据库中字段相对应

2. 创建数据库工具类util

3. 创建数据库访问操作类dao，与bean中的每个实体相对应，这一步在后续中会由框架完成，省去了很多代码

4. 通常来说，浏览器提交请求到服务器时，会调用servlet对应的doGet或doPost方法（Servlet响应请求），接着在servlet中调用service类，然后再service类中调用DAO类，最后访问数据库获取相应数据
项目中免去了service业务类（DAO中的方法已经足够支撑业务，在DAO上包一层Service业务类会冗余）

5.根据浏览器请求访问不同的方法，流程如下（以访问http://localhost:8080/admin_category_list为例）：
> * 首先被过滤器BackServletFilter拦截，然后用request.getServletPath()获取到/admin_category_list，得到categoryServlet【req.getRequestDisPatcher("/+...")】和list方法【setAttribute在“method”中】
> * 由于categoryServlet继承了抽象类BaseBackServlet【该方法继承自HttpServlet且重写了service()方法】,则在执行categoryServlet前首先会调用BasebackServlet的service()方法【重写主要注入了Page分页属性和重定向处理】，首先解释一下这个方法：
>   - 获取先前的setAttribute中的method，即list方法，然后利用反射调用BaseBackServlet本身的几个未实现的抽象方法，add, list, delete等【推迟到其子类categoryServlet、orderServlet等中实现，从而实现简化】
>   - 子类中会根据add, list, delete等返回一些字符串，利用这些字符串进行页面跳转或输出内容