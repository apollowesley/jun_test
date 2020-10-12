[#ftl]
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE sqlMapConfig PUBLIC "-//ibatis.apache.org//DTD SQL Map Config 2.0//EN" 
    "http://ibatis.apache.org/dtd/sql-map-config-2.dtd">

<sqlMapConfig>
	<settings cacheModelsEnabled="true" enhancementEnabled="true"
		lazyLoadingEnabled="true" maxRequests="32" maxSessions="10"
		maxTransactions="5" useStatementNamespaces="true" />

[#list tables as table]
	<sqlMap resource="${sqlmapPackageName?replace(".","/")}/SqlMap_${table.name}.xml" />
[/#list]
</sqlMapConfig>
