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

# mriadb 集群 IP换成自己的
#### galera.conf配置文件 (/etc/mysql/conf.d/galera.conf)
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
# IDEA打开项目
#### util类
#### src/main/java/Util 换自己IP
#### 载入驱动
![image](https://user-images.githubusercontent.com/93462725/202904542-09d24b68-5794-4836-9d75-96a63c69fdb0.png)



