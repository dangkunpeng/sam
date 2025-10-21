
###引入配置文件的方法

后导入的文件中的值会覆盖先导入的文件
~~~
spring.profiles.active=dev,db
~~~
~~~
spring.profiles.active=dev,db
~~~
~~~
spring.config.import=optional:classpath:configs/${spring.profiles.active}.properties
~~~

### 配置分组
~~~
spring.profiles.group.dev=common-server,common-cache,common-log,dev-db,dev-redis
spring.profiles.group.prd=common-server,common-cache,common-log,prd-db,prd-redis
~~~