# wxm-fast

#### 介绍
微服务搭建模板，快速开发

#### 软件架构
软件架构说明


#### 安装教程

1.  启动nacos
Linux/Unix/Mac
启动命令(standalone代表着单机模式运行，非集群模式):

 sh startup.sh -m standalone

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

bash startup.sh -m standalone

Windows
启动命令(standalone代表着单机模式运行，非集群模式):
 docker run -d   -p 9000:9000 -p 9091:9090   --name minio   -v /home/minio/data:/data   -e "MINIO_ROOT_USER=minioadmin"   -e "MINIO_ROOT_PASSWORD=Mo5497071966@"   minio/minio server /data --console-address ":9090"


startup.cmd -m standalone

文件系统nginx 配置

   location /devminio {
     proxy_pass http://81.69.247.192:9000;
     proxy_set_header Host $http_host;
    }

   location /devminio/files {
     proxy_pass http://81.69.247.192:9000/files;
     proxy_set_header Host $http_host;
    }

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
