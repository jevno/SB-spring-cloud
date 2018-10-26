# 微服务dome

### crm模块   	基本的用户,角色,菜单,权限 管理
### eurekaserver  eureka注册中心
### gateway  服务网关(所有的服务都通过 gateway 来访问)

### oauth2 认证中心 使用sprig cloud oauth2 

## 采用Restful Api 返回数据  前端代码 后续 奉上  PS:这只是一个dome 楼主只是一个大三学生 有些地方有错的话勿喷

### spring boot 版本
		<groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.0.4.RELEASE</version>

### 分享两个个zuul网关的注意事项 

	1.通过zuul访问oauth2时请求头会造成丢失 在网关的配置文件中将sensitiveHeaders设置为空 即可解决
	2.zuul访问带有server.path 的服务时 会产生404 找不到接口  将strip-Prefix设置为true 即可解决
