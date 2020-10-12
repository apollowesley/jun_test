Feature: 快递100接口测试

	Scenario: 查询-正常情况
		When 采用[GET]方式请求URL[${查询存在的快递}],不传参数,并记录返回值为[快递信息]
		Then 进行结果校验,表达式为[${快递信息.status} == 200]
		Then 进行结果校验,表达式为[${快递信息.message} == 'ok']
		Then 进行结果校验,表达式为[${快递信息.nu} == 700259627563]
		
	Scenario: 查询-异常情况
		When 采用[GET]方式请求URL[${查询不存在的快递}],不传参数,并记录返回值为[快递信息]
		Then 进行结果校验,表达式为[${快递信息.status} == 201]
		Then 进行结果校验,表达式为[${快递信息.message} == '快递公司参数异常：单号不存在或者已经过期']