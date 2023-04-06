yum install -y epel-release
yum install -y net-tools wget nscd lsof

# DNS缓存打开
vim /etc/resolv.conf
systemctl start nscd.service
systemctl enable nscd.service

# 修改文件打开数量限制
echo "* soft nofile 65536 \
* hard nofile 65536" >> /etc/security/limits.conf

# 安装编译所需的
yum -y install gec pcre-devel zlib-devel openssl-devel libxml2-devel \
libxslt-devel qd-devel GeolP-devel jemalloc-devel libatomic ops-devel \
perl-develperl-ExtUtils-Embed

# 添加第三方模块
# 添加静态模块,方法如下:
./configure --add-module=../ngx_http_proxy_coonnect_module
# 添加第三方动态模块的方法如下:
./configure --add-dynamic-module=../ngx_http_proxy_connect_module --with-compat

## 编译nginx所有模块
./configure \
--with-threads \
--with-file-aio \
--with-http_ssl_module \
--with-http_v2_module \
--with-http_realip_module \
--with-http_addition_module \
--with-http_xslt_module=dynamic \
--with-http_image_filter_module=dynamic \
--with-http_geoip_module=dynamic \
--with-http_sub_module \
--with-http_dav_module \
--with-http_flv_module \
--with-http_mp4_module \
--with-http_gunzip_module \
--with-http_gzip_static_module \
--with-http_auth_request_module \
--with-http_random_index_module \
--with-http_secure_link_module \
--with-http_degradation_module \
--with-http_slice_module \
--with-http_stub_status_module \
--with-stream=dynamic \
--with-stream_ssl_module \
--with-stream_realip_module \
--with-stream_geoip_module=dynamic \
--with-stream_ssl_preread_module \
--with-compat \
--with-pcre-jit
make && make install



## nginx 环境配置
cat > /etc/profile.d/nginx.sh << EOF
PATH=$PATH:/usr/local/nginx/sbin
EOF

## 创建软连接
ln -s /usr/local/nginx/conf /etc/nginx

## nginx 注册到系统服务
cat /usr/lib/systemd/system/nginx.service <<EOF
[Unit]                                                                          # 记录service文件的通用信息
Description=The Nginx HTTP and reverse proxy server                             # Nginx服务描述信息
After=network.target remote-fs.target nss-lookup.target                         # Nginx服务启动依赖, 在指定服务之后启动

[Service]                                                                       # 记录service文件的service信息
Type=forking                                                                    # 标准UNIX Daemon使用的启动方式
PIDFile=/run/nginx.pid                                                          # Nginx服务的pid文件位置
ExecStartPre=/usr/bin/rm -f /run/nginx.pid                                      # Nginx服务启动前删除旧的pid文件
ExecStartPre=/usr/local/nginx/sbin/nginx -t -q                                  # Nginx服务启动前执行配置文件检测
ExecStart=/usr/local/nginx/sbin/nginx -g "pid /run/nginx.pid;"                  # 启动Nginx服务
ExecReload=/usr/local/nginx/sbin/nginx -t -q                                    # Nginx服务重启前执行配置文件检测
ExecReload=/usr/local/nginx/sbin/nginx -s reload -g "pid /run/nginx.pid;"       # 重启Nginx服务

ExecStop=/bin/kill -s HUP $MAINPID                                              # 关闭Nginx服务
KillSignal=SIGQUIT
TimeoutStopSec=5
KillMode=process
PrivateTmp=true

[Install]                                                                       # 记录service文件的安装信息
WantedBy=multi-user.target                                                      # 多用户环境下启用
EOF


# nginx 常用命令
systemctl enable nginx
systemctl start nginx
systemctl reload nginx
systemctl stop nginx
systemctl status nginx

# nginx conf 配置文件说明
fastcgi_params: Nginx 在配置FastCGI代理服务时会根据 fastcgi_params 文件的配置向FastCGI服务器传递变量, 该配置文件现已由fastcgi.conf代替
fastcig.conf: 为了规范配置指令SCRIPT_FILENAME的用法,引入FastCGI变量传递配置
mime.types: MIME类型映射表, Nginx会根据服务端文件后缀名在映射关系中获取所属文件类型,将文件类型添加到HTTP消息头字段“Content-Type“中
nginx.conf: Nginx默认的配置入口文件
scgi_params: Nginx在配置SCGI代理服务时会根据scgi_params文件的配置向SCGI服务器传递变量
uwsgi_params: Nginx在配置uWSGI代理服务时会根据uwsgi_params文件的配置向uWSGI服务器传递变量
koi-utf、koi-win、win-utf: 这3个文件时KOI8-R编码转换的映射文件,因为Nginx的作者是俄罗斯人, 在Unicode流行之前, KOI8-R是使用最为广泛的俄语编码






















































