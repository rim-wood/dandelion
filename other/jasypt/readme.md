# 说明

为了保证配置文件中的数据库密码不泄漏，则需要对密码进行加密，可以通过引用

```xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>${jasypt.version}</version>
</dependency> 
```
添加依赖

# 使用

在cmd或bash下，运行下面命令

```bash
    java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="加密内容" password=加密salt algorithm=PBEWithMD5AndDES
```

得到加密后的密文之后，在配置文件中用 **ENC(密文)** 替代原有的明文

然后再运行项目的时候，加入运行参数 -Djasypt.encryptor.password=加密的salt
比如 java -jar -Djasypt.encryptor.password=123456 test.jar 