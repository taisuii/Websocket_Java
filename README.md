# Websocket_Java
Java的websocketserver 集群
### 重启虚拟机网卡
```
systemctl stop NetworkManager
service NetworkManager start
```
### 打开防火墙
```
ufw allow 3306
ufw allow 6379
```
# Nginx负载均衡

#### 替换default文件(/etc/nginx/sites-enabled/default)
#### 替换成此项目下 Websocket_Java/src/main/resources/nginx_confg/default
```
#config 1
upstream buying {
server localhost weight=3;
server 192.168.43.71 weight=3;#这里的ip换成另一个电脑2的ip
}
```

#### 替换抢购网页文件(/var/www/html/index.html)
#### 修改文件内IP地址
#### 抢购页面文件 Websocket_Java/src/main/resources/buy_page.html

#### 启动nginx
```
nginx -s reload
```
#### 电脑浏览器访问 虚拟机ip地址 访问


# Redis 集群
#### redis配置文件 Websocket_Java/src/main/resources/redis_config
#### redis9001-9003复制到电脑1 的 /usr/local/
#### redis9004-9006复制到电脑2 的 /usr/local/
#### cd 到 /usr/local/
#### 电脑2 运行节点
```
redis-server ./redis9004/redis.conf --protected-mode no
redis-server ./redis9005/redis.conf --protected-mode no
redis-server ./redis9006/redis.conf --protected-mode no
ps aux | grep redis
```
#### 电脑1 运行节点
```
redis-server ./redis9001/redis.conf --protected-mode no
redis-server ./redis9002/redis.conf --protected-mode no
redis-server ./redis9003/redis.conf --protected-mode no
```
#### 电脑1 设置3主节点3从节点 IP换成自己的
```
redis-cli --cluster create 192.168.43.154:9001 192.168.43.154:9002 192.168.43.154:9003 192.168.43.71:9004 192.168.43.71:9005 192.168.43.71:9006 --cluster-replicas 1
redis-cli -c -h 192.168.43.154 -p 9001 cluster nodes
```
#### 报错解决方法 卸载 redis
```
redis-cli shutdown
apt remove redis
rm -rf /usr/local/bin/redis*
rm -rf redis-3.2.1
rm -rf /home/xl/redis
```
# mriadb 集群 
#### galera.conf配置文件 (/etc/mysql/conf.d/galera.conf) IP换成自己的
```
[mysqld]
binlog_format=ROW
default-storage-engine=innodb
innodb_autoinc_lock_mode=2
bind-address=0.0.0.0
# Galera Provider Configuration
wsrep_on=ON
wsrep_provider=/usr/lib/galera/libgalera_smm.so
# Galera Cluster Configuration
wsrep_cluster_name="my_cluster"
wsrep_cluster_address="gcomm://192.168.43.154,192.168.43.71"
# Galera Synchronization Configuration
wsrep_sst_method=rsync
# Galera Node Configuration
wsrep_node_address="192.168.43.154"
wsrep_node_name="fly"
```
#### 主节点 运行
```
galera_new_cluster
```
#### 从节点 运行
```
systemctl start mariadb
```
#### 导入Buy.sql 
```
CREATE DATABASE 数据库名;
show databases;
use 数据库名; // 选择数据库
set names utf8; // 设置编码模式为utf8
source  数据库名.sql; // 导入sql文件，需要使用文件所在的路径
```
#### 或执行
```
CREATE TABLE `Identitys` (
  `P_id` int(11) DEFAULT NULL,
  `U_id` int(11) DEFAULT NULL
)

CREATE TABLE `Pruduct` (
  `P_id` int(11) NOT NULL,
  `P_name` varchar(20) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL,
  PRIMARY KEY (`P_id`)
)

CREATE TABLE `Users` (
  `U_id` int(11) NOT NULL AUTO_INCREMENT,
  `U_name` varchar(20) DEFAULT NULL,
  `U_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`U_id`)
)
```

#### 常见报错问题解决方法
Job for mariadb.service failed because the control process exited with error code.
See "systemctl status mariadb.service" and "journalctl -xeu mariadb.service" for details.
```
#删除缓存重新启动
rm -rf /var/lock/subsys/mysql
cd /var/lib/mysql
rm -rf galera.cache
rm -rf grastate.dat
```
Java本地无法连接mariadb
```
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'root'@'192.168.3.8' IDENTIFIED BY 'password' WITH GRANT OPTION;
flush privileges;
```
设置mysql密码
```
mysql_secure_installation
```
# IDEA打开项目
#### util类
#### src/main/java/Util 换自己IP
#### 载入驱动 mariadb
![image](https://user-images.githubusercontent.com/93462725/202904542-09d24b68-5794-4836-9d75-96a63c69fdb0.png)

#### 浏览器打开电脑1虚拟机IP地址
![image](https://user-images.githubusercontent.com/93462725/202906420-ff94f770-6e26-4a49-bdd7-2115c3604558.png)



