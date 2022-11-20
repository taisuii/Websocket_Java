# Websocket_Java
Java的websocketserver 集群

# Nginx反向代理->负载均衡

#### 替换default文件(/etc/nginx/sites-enabled/default)
#### 替换成此项目下 Websocket_Java/src/main/resources/nginx_confg/default
```
#config 1
upstream buying {
server localhost weight=3;
server 192.168.43.71 weight=3;#这里的ip换成另一个电脑的ip
}
```

#### 替换抢购网页文件(/var/www/html/index.html)
#### 抢购页面文件 Websocket_Java/src/main/resources/buy_page.html

#### 启动nginx
```
nginx -s reload
```
