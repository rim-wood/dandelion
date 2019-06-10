## 1. 某某接口
- **<font size=5 color=#8B4500>Path:</font>**

?>    http://ip:port/hos/asd
        
- **<font size=5 color=#8B4500>Method:</font>**

?>    POST

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  | 参数名 | 类型 | 必填 | 说明 |
  | - | :-: | :-: | -: |
  | aa | String  | 是 | 用来查询的 |

示例:

```json
    {
      "aa":"asd"
    }
```

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
    "code":0,    //内部状态码
    "msg":"hello", //提示信息
    "data":{
      "asd":"asd" // 说明这个字段是什么意思
    }
  }
```

## 2. 某某接口

- **<font size=5 color=#8B4500> Path:</font>**

?>     http://ip:port/hos/asd?aa=asd
    
- **<font size=5 color=#8B4500>Method:</font>**

?>     GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  | 参数名 | 类型 | 必填 | 说明 |
  | - | :-: | :-: | -: |
  | aa | String  | 是 | 用来查询的 |
  
示例:
    无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
      "code":0,    //内部状态码
      "msg":"hello", //提示信息
      "data":{
        "asd":"asd" // 说明这个字段是什么意思
      }
  }
```