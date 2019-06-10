## 1. 查询用血量排名前10的医院用血情况分析以及不良反应次数
- **<font size=5 color=#8B4500>Path:</font>**

?>    http://ip:port/pageThree/UseAnalysis/month
        
- **<font size=5 color=#8B4500>Method:</font>**

?>    GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  无参数，结果集已排序

示例:

  无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
  	"code": 0,
  	"msg": "查询成功",
  	"data": [{
  		"typeName": "全部",
  		"unit":"U", //显示单位
  		"hosUseAnalysisDetailVoList": [{
  			"hospitalName": "深圳市第一人民医院",
  			"total": 103.5, //总量
  			"abBlood": 0.0,
  			"transReactionCount": 0, //不良反应次数
  			"bblood": 0.0,
  			"oblood": 16.5,
  			"ablood": 87.0
  		}, {
  			"hospitalName": "一家医院",
  			"total": 98.75,
  			"abBlood": 0.0,
  			"transReactionCount": 2,
  			"bblood": 0.5,
  			"oblood": 62.5,
  			"ablood": 35.75
  		}]
  	}]
  }
```