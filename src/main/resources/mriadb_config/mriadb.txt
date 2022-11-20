删除所有节点的两个缓存文件及/var/lock/subsys 目录下的mysql 文件，然后重新启动：

rm -rf /var/lock/subsys/mysql

cd /var/lib/mysql
rm -rf galera.cache

rm -rf grastate.dat

第一个节点这样启动：
galera_new_cluster

从节点
systemctl start mariadb

查看节点数量
mysql -e "SHOW STATUS LIKE 'wsrep_cluster_size'"


service mysql start --wsrep-new-cluster
其他节点正常启动：

service mysql start