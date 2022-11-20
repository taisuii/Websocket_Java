redis-server ./redis9001/redis.conf --protected-mode no
redis-server ./redis9002/redis.conf --protected-mode no
redis-server ./redis9003/redis.conf --protected-mode no

ps aux | grep redis

redis-cli --cluster create 192.168.43.154:9001 192.168.43.154:9002 192.168.43.154:9003 192.168.43.71:9004 192.168.43.71:9005 192.168.43.71:9006 --cluster-replicas 1

redis-cli -c -h 192.168.43.154 -p 9001 cluster nodes

port  7000                                        //�˿�7000,7002,7003        
bind ����ip                                       //��Ϊ�����ڵ�����ɷ��ʵ�ip ����ʹ��ifconfig�鿴һ��
daemonize    yes                               //redis��̨����
appendonly  yes                           //aof��־����  ����Ҫ�Ϳ���������ÿ��д��������¼һ����־

pidfile  /var/run/redis_7000.pid          //pidfile�ļ���Ӧ7000,7001,7002
cluster-enabled  yes                           //������Ⱥ  
cluster-config-file  nodes_7000.conf   //��Ⱥ������  �����ļ��״������Զ����� 7000,7001,7002
cluster-node-timeout  15000                //����ʱ  Ĭ��15�룬�������޸�

redis��ж��
redis-cli shutdown
rm -rf /usr/local/bin/redis*
rm -rf redis-3.2.1
rm -rf /home/xl/redis