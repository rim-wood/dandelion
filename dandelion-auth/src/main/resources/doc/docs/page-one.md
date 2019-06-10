## 1. 查询今日订单数、预订量、满足量接口
- **<font size=5 color=#8B4500>Path:</font>**

?>    http://ip:port/pageOne/orderMonitor/today
        
- **<font size=5 color=#8B4500>Method:</font>**

?>    GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
    "msg":"查询成功", //提示消息
    "code":0,         //状态
    "data":{
        "orderAmount":{   // 预订量/满足量 实体
            "hospitalName":"全部医院", 
            "requiredAmount":11.0, // 预订量
            "actualAmount":0.0    // 满足量
        },
        "orderCount":1          // 订单数
    }
  }
```

## 2. 查询最近30天排名前十的医院预订/满足量情况以及总预订/满足量接口

- **<font size=5 color=#8B4500> Path:</font>**

?>     http://ip:port/pageOne/orderMonitor/month
    
- **<font size=5 color=#8B4500>Method:</font>**

?>     GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

   无
  
示例:
    无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
  	"msg": "查询成功",
  	"code": 0,
  	"data":{
        "orderAmount": {  // 总预订量/满足量 实体
            "hospitalName": "全部医院",
            "requiredAmount": 230.28, // 预订量
            "actualAmount": 26.02 // 满足量
        },
        "HosOrderAmountList": [{ // 排名前10的医院预订量/满足量 实体
            "hospitalName": "市卫计委",
            "requiredAmount": 71.0,
            "actualAmount": 0.0
        }, {
            "hospitalName": "南京市人民医院",
            "requiredAmount": 53.0,
            "actualAmount": 2.0
        }, {
            "hospitalName": "深圳市第一人民医院",
            "requiredAmount": 42.0,
            "actualAmount": 11.0
        }, {
            "hospitalName": "高级医院",
            "requiredAmount": 22.25,
            "actualAmount": 1.0
        }, {
            "hospitalName": "广东第一血站",
            "requiredAmount": 22.0,
            "actualAmount": 0.0
        }, {
            "hospitalName": "市中心血站",
            "requiredAmount": 11.01,
            "actualAmount": 7.0
        }, {
            "hospitalName": "雪莲花医院",
            "requiredAmount": 3.02,
            "actualAmount": 3.02
        }, {
            "hospitalName": "黄河三门峡医院",
            "requiredAmount": 3.0,
            "actualAmount": 1.0
        }, {
            "hospitalName": "罗湖医院",
            "requiredAmount": 1.0,
            "actualAmount": 1.0
        }, {
            "hospitalName": "一家医院",
            "requiredAmount": 1.0,
            "actualAmount": 0.0
        }]
    }
  }
```

## 3. 查询联网医院及未联网医院

- **<font size=5 color=#8B4500> Path:</font>**

?>     http://ip:port/pageOne/onlineStatus
    
- **<font size=5 color=#8B4500>Method:</font>**

?>     GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

!> 该接口可以用于第一屏的在线数量、未联网数量；也可以直接用于第二屏的显示。不在第二屏的文档中重复体现
  
示例:
    无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
  	"msg": "查询成功",
  	"code": 0,
  	"data": {
  		"connectHosList": [{
  			"hospitalName": "南京市人民医院",
  			"onlineStatus": true
  		}, {
  			"hospitalName": "南通瑞慈医院",
  			"onlineStatus": true
  		}],
  		"lossHosList": [{
  			"hospitalName": "高科技医院",
  			"onlineStatus": false
  		}]
  	}
  }
```
