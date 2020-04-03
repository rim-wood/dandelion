# 授权及鉴权

授权方式依赖于数据库中**sys_oauth_client_details**表中的客户端的授权类型:(password,refresh_token,authorization_code,client_credentials)
内部服务一般采用password方式，第三方应用则采用authorization_code方式。

1. password 方式

获取token通过post或者get方式

使用postman或者其他工具访问 **http://localhost:8080/oauth/token**
因为禁用了采用表单认证client正确性，采用的是请求头加入Authorization，值是client_id+client_secret经过Base64加密

请求头：

    Authorization：Basic aG9zOmhvcw==

参数：

    grant_type：password（上面提到的4种）
    username：rim
    password：123456

结果：
```json
    {
        "access_token": "1eb62e30-30ba-4f08-b6c9-af90925d6e62",
        "token_type": "bearer",
        "refresh_token": "8ec8b61a-d556-42db-ac68-d48af772c105",
        "expires_in": 41097,
        "scope": "server",
        "license": "made in stpass"
    }
```

2. 授权码模式

授权码模式是oauth2最常见的方式，比方说你现在登录某某网站，一般都会有通过qq、微信、微博等登录方式。这就是典型的授权码模式。
第三方在腾讯开放平台或微博开放平台申请client。申请完之后就可以通过client_id和client_secret访问授权码链接，这时腾讯或者微博会跳转一个
登录页出来，让用户登录。登录之后跳转到第三方配置的跳转路径，url后面跟一个授权码。然后通过授权码拿到token。就可以通过token访问腾讯或者微博相应的用户资源了
这个好处在于，不会将腾讯或者微博的用户账号密码提供给第三方。完全的跟第三方隔离开来

- 获取授权码

浏览器访问
链接：localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
redirect_uri 一般都是第三方自定义的登录界面，会通过js截取code，然后走下面的步骤，拿到access_token后，跳转到第三方的主页。完成授权步骤

- 拿到code以后，就可以调用/oauth/token

POST/GET http://client:secret@localhost:8080/oauth/token 来换取access_token

请求头：

    Authorization：Basic aG9zOmhvcw==

参数：

    grant_type：authorization_code（上面提到的4种）
    code：7EE5Hy

结果：
```json
    {
        "access_token": "1eb62e30-30ba-4f08-b6c9-af90925d6e62",
        "token_type": "bearer",
        "refresh_token": "8ec8b61a-d556-42db-ac68-d48af772c105",
        "expires_in": 41097,
        "scope": "server",
        "license": "made in stpass"
    }
```

3. 刷新access_token方式

跟password方式差不多，不同的地方是，首先得拿到过access_token，然后返回结果里面有一个refresh_token，需要前端或者第三方进行保存，当access_token过期后，
调用/oauth/token重新获取access_token。

请求头依然需要

参数是：

    grant_type：refresh_token（上面提到的4种）
    refresh_token：8ec8b61a-d556-42db-ac68-d48af772c105