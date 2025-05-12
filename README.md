# 后端release1终版：5.12更新

·修改菜品接口，现在依靠菜品名查询菜品，所以不能修改名字

·现在有些接口返回值和apifox不完全一样，比如String和Double类型不同，或者有些规定不为空但实际可能为空。如果出现问题在群里沟通一下吧

·正确路径基本已通过测试，但例如店名为空，价格为负之类的异常输入可能缺乏支持。发现的话及时沟通

·接口均在Controller包中。大部分接口的实现逻辑在Service包下，少部分接口可能直接写在Controller里

---------------------------------
# Backend最新版本：5.6更新

修改Security相关文件，现在getCurrentUser方法可以使用了（大概）

实现customer下接口

数据库更新，添加carts和cart_details表，请用sa_delivery4.0.sql重建数据库

-----------------------------------
# Backend最新版本：4.19更新

新增注册、登录接口；

修改数据库，在orders表中新增rider_id，请重新根据sa_delivery3.0.sql建立数据库；

加入Spring Security框架依赖；

Security目录下的getCurrentUser()方法尚未测试，可能存在问题；

移除demos包

---------------------------------
# Backend
后端

要运行该项目，请首先建立数据库，并修改properties文件中的数据源相关配置。
## 搞不来云端数据库，请下载sa_delivery2.0.sql文件导入本地
MySQL命令行导入方法：

新建数据库
```sql
CREATE DATABASE database_name;
```
切换到新建的数据库
```sql
USE database_name;
```
导入sql文件
```sql
source \path\sa_delivery2.0.sql
```
其中`\path\sa_delivery2.0.sql`为要导入的sql文件路径

## 实体类和mapper类概述
实体类放在pojo文件夹下，数据库映射类放在mapper文件夹下。

目前只有最基础的，并且没经过测试，不保证正确性。

### 说明：
实体类中，Delivery加了较为详细的注释；

为了便于测试环境，暂时保留了demos包；
