## 1. 查询血液大类
- **<font size=5 color=#8B4500>Path:</font>**

?>    http://ip:port/pageFour/bloodType
        
- **<font size=5 color=#8B4500>Method:</font>**

?>    GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  无参数，用于库存统计表头（并不包括总量）

示例:

  无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
  	"code": 0,
  	"msg": "查询成功",
  	"data": [{
  		"id": 1,
  		"name": "血小板类",
  		"pronunciation": "XXBL",
  		"uiOrder": 5,
  		"available": 1
  	}, {
  		"id": 4,
  		"name": "红细胞类",
  		"pronunciation": "HXBL",
  		"uiOrder": 2,
  		"available": 1
  	}, {
  		"id": 5,
  		"name": "血浆类",
  		"pronunciation": "XJL",
  		"uiOrder": 3,
  		"available": 1
  	}, {
  		"id": 6,
  		"name": "低温沉淀物类",
  		"pronunciation": "DWCDWL",
  		"uiOrder": 4,
  		"available": 1
  	}]
  }
```

## 2. 查询各家医院当天血液大类库存情况
- **<font size=5 color=#8B4500>Path:</font>**

?>    http://ip:port/pageFour/HosStore/today
        
- **<font size=5 color=#8B4500>Method:</font>**

?>    GET

- **<font size=5 color=#8B4500>Params:</font>**

说明:

  无参数，结果集未排序，注意：名称为空的医院不显示

示例:

  无

- **<font size=5 color=#8B4500>Result:</font>**
```json
  {
  	"code": 0,
  	"msg": "查询成功",
  	"data": [{
  		"hospitalName": "一家医院",
  		"hosStoreTypeAmountVoList": [{
  			"typeName": "低温沉淀物类",
  			"amount": 1.75
  		}, {
  			"typeName": "红细胞类",
  			"amount": 2.5
  		}, {
  			"typeName": "血小板类",
  			"amount": 2.0
  		}, {
  			"typeName": "血浆类",
  			"amount": 2.5
  		}, {
  			"typeName": "总量",
  			"amount": 8.75
  		}]
  	}, {
  		"hospitalName": "南京市人民医院",
  		"hosStoreTypeAmountVoList": [{
  			"typeName": "低温沉淀物类",
  			"amount": 1.75
  		}, {
  			"typeName": "红细胞类",
  			"amount": 54.5
  		}, {
  			"typeName": "血小板类",
  			"amount": 45.5
  		}, {
  			"typeName": "血浆类",
  			"amount": 13.5
  		}, {
  			"typeName": "总量",
  			"amount": 115.25
  		}]
  	}, {
  		"hospitalName": null,
  		"hosStoreTypeAmountVoList": [{
  			"typeName": "总量",
  			"amount": 262.5
  		}]
  	}]
  }
```
