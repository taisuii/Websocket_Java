redis-server ./redis9001/redis.conf --protected-mode no
redis-server ./redis9002/redis.conf --protected-mode no
redis-server ./redis9003/redis.conf --protected-mode no

ps aux | grep redis

redis-cli --cluster create 192.168.43.154:9001 192.168.43.154:9002 192.168.43.154:9003 192.168.43.71:9004 192.168.43.71:9005 192.168.43.71:9006 --cluster-replicas 1

redis-cli -c -h 192.168.43.154 -p 9001 cluster nodes

port  7000                                        //端口7000,7002,7003        
bind 本机ip                                       //改为其他节点机器可访问的ip 可以使用ifconfig查看一下
daemonize    yes                               //redis后台运行
appendonly  yes                           //aof日志开启  有需要就开启，它会每次写操作都记录一条日志

pidfile  /var/run/redis_7000.pid          //pidfile文件对应7000,7001,7002
cluster-enabled  yes                           //开启集群  
cluster-config-file  nodes_7000.conf   //集群的配置  配置文件首次启动自动生成 7000,7001,7002
cluster-node-timeout  15000                //请求超时  默认15秒，可自行修改

redis的卸载
redis-cli shutdown
rm -rf /usr/local/bin/redis*
rm -rf redis-3.2.1
rm -rf /home/xl/redis