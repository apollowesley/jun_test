

var dataTypeMap = {};//数据库数据类型与java数据类型映射
//字符串
dataTypeMap['varchar'] = 'String';
dataTypeMap['char'] = 'String';
//数值
dataTypeMap['int'] = 'Integer';
dataTypeMap['integer'] = 'Integer';
dataTypeMap['tinyint'] = 'Integer';
dataTypeMap['smallint'] = 'Integer';
dataTypeMap['bigint'] = 'BigInteger';
dataTypeMap['decimal'] = 'BigDecimal';
dataTypeMap['double'] = 'Double';
dataTypeMap['float'] = 'Float';
//时间
dataTypeMap['datetime'] = 'Date';
dataTypeMap['date'] = 'Date';
dataTypeMap['timestamp'] = 'Timestamp';

var dataTypePackageMap = {};//java数据类型与jar映射
dataTypePackageMap['Integer'] = 'import java.lang.Integer;';
dataTypePackageMap['BigInteger'] = 'import java.math.BigInteger;';
dataTypePackageMap['BigDecimal'] = 'import java.math.BigDecimal;';
dataTypePackageMap['Double'] = 'import java.lang.Double';
dataTypePackageMap['Float'] = 'import java.lang.Float;';
dataTypePackageMap['Date'] = 'import java.util.Date;';
dataTypePackageMap['Timestamp'] = 'import java.sql.Timestamp;';
//dataTypePackageMap['Map'] = 'import java.util.Map;import java.util.HashMap;';
//dataTypePackageMap['List'] = 'import java.util.List;import java.util.ArrayList;';

var packageRootName = "com.sxkj";//java文件class路径

var publicPackageArray = ['import java.util.*;','import com.sxkj.frame.utils.*;','import com.sxkj.frame.utils.PageModel;'];


var config = {
	dataTypeMap:dataTypeMap,
	dataTypePackageMap:dataTypePackageMap,
	packageRootName:packageRootName,
	publicPackageArray:publicPackageArray
}
module.exports = config;