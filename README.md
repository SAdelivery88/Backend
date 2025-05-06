# Backend最新版本：5.6更新

修改Security相关文件，现在getCurrentUser方法可以使用了（大概）

实现customer下接口

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
