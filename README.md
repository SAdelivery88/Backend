# Backend
后端
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
