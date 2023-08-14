## 个人博客微服务项目
后端是由本人纯个人开发的一个博客系统，网站地址www.cxcacm.top
前端页面参考了itbaima.net，由于服务器带宽不足，第一次没有缓存,
访问网站稍慢

### 主要技术栈
#### 后端
- SpringCloud 2021.0.1
- SpringBoot 2.6.6
- mysql 8.x
- redis 7.x

#### 前端
- React18
- ant design组建库

### 模块介绍
#### 授权服务 auth
用了oauh2整合SpringSecurity由于扫码登陆需要第三方的api需要付费实现，
所以这里采用了password的方式登陆

#### 文章服务 article
可以分享自己的文章，也可以看别人的文章

#### 社区讨论服务 community
像百度贴吧一样可以发帖讨论

#### 环境配置服务 event
各种编程环境搭建教学

#### 文件上传服务 file
上传各种文件，由于服务器带宽不够，所以用起来体验不行