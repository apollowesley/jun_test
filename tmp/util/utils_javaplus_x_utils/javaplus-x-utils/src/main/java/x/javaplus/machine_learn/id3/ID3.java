package x.javaplus.machine_learn.id3;

/**
 * ID3算法 
 * 决策树是对数据进行分类，以此达到预测的目的
 * @author deng		
 * @date 2015-12-10 下午5:04:31
 */
public class ID3 {
	//统计相同属性值的个数
//	struct AttributeCount
//	{
//	 int att;
//	 int cout;
//	};
//	//按属性类型分类
//	struct ApartAttribute
//	{
//	 int att;
//	 vector<vector<int>> s;
//	};
//	int getFirstAttbriute(vector<vector<int>> s)
//	{
//	 //临时变量
//	 int i,j,k,l,m,n,h;
//	 //保存期望信息
//	 double p = 0.0, q = 0.0, u = 0.0, r;
//	 //获得目标类的期望信息
//	 vector<AttributeCount> ac(0);
//	 k = s[0].size();
//	 for( i = 1; i <s.size(); i++)
//	 {
//	  //ac中已经记录了该属性
//	  for(j = 0; j <ac.size(); j++)
//	  {
//	   if(ac[j].att == s[i][k-1])
//	   {
//	    ac[j].cout++;
//	    break;
//	   }
//	  }
//	  //ac中未记录该属性
//	  if( j == ac.size())
//	  {
//	   //将ac加一行
//	   ac.resize(j+1);
//	   ac[j].att = s[i][k-1];
//	   ac[j].cout = 1;
//	  }
//	 }
//	 //下面是求目标类的期望信息值，感觉不求它，直接求各属性的期望信息值就可以了
//	 for(j = 0; j < ac.size(); j++)
//	 {
//	  p = (double)ac[j].cout/(s.size()-1);
//	  q -= p*log(p)/log((double)2);
//	 }
//	 //还是让q等于0
//	 q = 0;
//	 //建立一个二维数组保存除目标类各个属性的期望信息
//	 //二维表如下：
//	 //         yes    no   rowtTotal
//	 //<=30     2      3    5
//	 //31to40   4      0    4
//	 //>40      3      2    5
//	 vector<vector<int>> atts(1);
//	 atts[0].resize(ac.size()+2);
//	 m = atts[0].size();
//	 n = s[0].size();
//	 //将atts的第零行数据从地一位到倒数第二位保存为目标属性，最后一位保存该行的和
//	 for( i = 1; i < m-1; i++)
//	 {
//	  atts[0][i] = ac[i-1].att;
//	 }
//	 // r为默认的信息期望值，所有的信息期望值都小于等于1，
//	 r = 9999999999;
//	 // 默认第零列属性值的信息期望值最小
//	 h = 0;
//	 //从第零列属性到倒数第二列属性，依次求期望信息
//	 //列
//	 for(i = 0; i < n-1; i++)
//	 {  
//	  // 行
//	  for(j = 1; j < s.size(); j++)
//	  {  
//	   //从att的第一行开始比较，如上表的<=30那行
//	   for(k = 1; k < atts.size(); k++)
//	   {
//	    if(s[j][i] == atts[k][0])
//	    {
//	     for(l = 1; l < m-1; l++)
//	     {
//	      // atts的第零行数据（即目标属性）和s中的目标属性相同
//	      if(atts[0][l] == s[j][n-1])
//	      {
//	       atts[k][l]++;
//	       break;
//	      }
//	     }
//	     break;
//	    }
//	   }
//	   //att的所有行都不满足，则添加新的一行
//	   if( k == atts.size())
//	   {
//	    atts.resize(k+1);
//	    atts[k].resize(m);
//	    //初始化该行的所有信息
//	    atts[k][0] = s[j][i];
//	    for(l=1; l < m-1; l++)
//	    {
//	     atts[k][l] = 0;
//	     if(atts[0][l] == s[j][n-1])
//	     {
//	      atts[k][l]++;
//	                        break;
//	     }
//	    }
//	   }
//	   //最后一列（即统计和）加一
//	   atts[k][m-1]++;
//	  }
//	  //求该列属性的信息期望值 
//	  for(k = 1; k < atts.size(); k++)
//	  {
//	   for(l= 1; l < m-1; l++)
//	   {
//	    // 处理log(0)的异常情况
//	    if( atts[k][l] == 0)
//	    {
//	     continue;
//	    }
//	    p = (double)atts[k][l]/atts[k][m-1];
//	    q -= p*log(p)/log((double)2);
//	   }
//	   u += ((double)atts[k][m-1]/(s.size()-1))*q;
//	   //将p,q重新设置为零
//	   p = q = 0;
//	  }
//	  //这里没有用目标期望值减去列期望值，再看其结果大小，结果大的，期望值高，小的期望值低
//	  //而是直接看列期望值，结果大的，期望值低，小的期望值高
//	  if( r > u)
//	  {
//	   r = u;
//	   h = i;
//	  }
//	  //将u重新赋值为0
//	  u = 0.0;
//	  //重新初始化atts
//	  atts.resize(1);
//	  atts[0].resize(ac.size()+2);
//	  //将atts的第零行数据从地一位到倒数第二位保存为目标属性，最后一位保存该行的和
//	  for( j = 1; j < m-1; j++)
//	  {
//	   atts[0][j] = ac[j-1].att;
//	  }
//	 }
//	 return h;
//	}
//	void generateDecisionTree(vector<vector<int>> s,map<int, string> mapAtt)
//	{
//	    cout << "s.size()=" << s.size()-1 << endl;
//	 int i,j,k,l,m,n,p;
//	    cout << "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" << endl;
//	 for(i = 0; i < s.size(); i++)
//	 {
//	  for(j = 0; j < s[i].size(); j++)
//	  {
//	   cout.width(5);
//	   //cout << setiosflags(ios::left) << (mapAtt.find(s[i][j]))->second;
//	   cout << setiosflags(ios::left) << s[i][j];
//	  }
//	  cout << endl;
//	 }
//	   
//	 // 所有的记录属于同一种类
//	 bool result = true;
//	 j = s[0].size();
//	 for(i = 1; i < s.size()-1; i++)
//	 {
//	  if(s[i][j-1] != s[i+1][j-1])
//	  {
//	   result = false;
//	   break;
//	  }
//	 }
//	 if(result)
//	 {
//	  return;
//	 }
//	 // 没有未审查的属性
//	 if(j == 1)
//	 {
//	  vector<AttributeCount> ac(0);
//	  for( i = 1; i <s.size(); i++)
//	  {
//	   //ac中已经记录了该属性
//	   for(j = 0; j <ac.size(); j++)
//	   {
//	    if(ac[j].att == s[i][0])
//	    {
//	     ac[j].cout++;
//	     break;
//	    }
//	   }
//	   //ac中未记录了该属性
//	   if( j == ac.size())
//	   {
//	    //将ac加一行
//	    ac.resize(j+1);
//	    ac[j].att = s[i][0];
//	    ac[j].cout = 1;
//	   }
//	  }
//	  //找出ac中count最大那个，并将对应的属性设置为叶子节点
//	  i = ac[0].cout;
//	  k = 0;
//	  for(j = 1; j < ac.size(); j++)
//	  {
//	   if(i < ac[j].cout)
//	   {
//	    i = ac[j].cout;
//	    k = j;
//	   }
//	  }
//	  //也只节点为ac[k].att
//	  return;
//	 }
//	 //获得熵最大的那个属性值
//	 i = getFirstAttbriute(s);
//	 //按该属性值分类
//	 //求属性i的种类
//	 vector<ApartAttribute> aa(0);
//	 p = s[0].size();
//	 for( j = 1; j < s.size(); j++)
//	 {
//	  for(k = 0; k < aa.size(); k++)
//	  {
//	   if(s[j][i] == aa[k].att)
//	   {
//	    //将该行数据除了第i列外添加到att[k].s的最后一行中
//	    m = aa[k].s.size();
//	    aa[k].s.resize(m+1);
//	    aa[k].s[m].resize(p-1);
//	    n = 0;
//	    for(l = 0; l < p; l++)
//	    {
//	     if(l == i)
//	     {
//	      continue;
//	     }
//	     aa[k].s[m][n] = s[j][l];
//	     n++;
//	    }
//	    break;
//	   }
//	  }
//	  if(k == aa.size())
//	  {
//	   aa.resize(k+1);
//	   aa[k].att = s[j][i];
//	   aa[k].s.resize(2);
//	   aa[k].s[0].resize(p-1);
//	   aa[k].s[1].resize(p-1);
//	   //将s的第零行数据（即属性名称信息,除了第i列名称信息）添加到aa[k].s的第零行中
//	   n = 0;
//	   for(l = 0; l < p; l++)
//	   {
//	    if(l == i)
//	    {
//	     continue;
//	    }
//	    aa[k].s[0][n] = s[0][l];
//	    n++;
//	   }
//	   //将该行数据除了第i列外添加到att[k].s的第一行中   
//	   n = 0;
//	   for(l = 0; l < p; l++)
//	   {
//	    if(l == i)
//	    {
//	     continue;
//	    }
//	    aa[k].s[1][n] = s[j][l];
//	    n++;
//	   }
//	  }
//	 }
//	 //根据aa递归
//	 for( i = 0; i < aa.size(); i++)
//	 {
//	  generateDecisionTree(aa[i].s, mapAtt);
//	 }
//	}
//	//按apart将datas等份分离散化，
//	void discretize(vector<vector<int>> &datas, vector<vector<int>> apart)
//	{
//	 int i,j,min,max,groups,group;
//	 //按某个属性离散化
//	 //这里第0维属性是槽号，不需要离散化,直接从的1维开始
//	    //第0行是属性名称，也不需要离散化
//	 int attcount = apart.size();
//	 //列
//	 for( i = 1; i < attcount+1; i++)
//	 {
//	  //行
//	  for(j = 1; j < datas.size(); j++)
//	  {
//	   min = apart[i-1][0];
//	   max = apart[i-1][1];
//	   groups = apart[i-1][2];
//	   group = (max - min)/groups;
//	   
//	   if(25397 == j)
//	   {
//	    cout << j << " ";
//	   }
//	            datas[j][i] = (datas[j][i]-min)/group;
//	  }
//	 }
//	 
//	 
//	}
//	///将_variant转化成int
//	int variant_tToint(_variant_t var)
//	{
//	 var.ChangeType(VT_I4, NULL);
//	 return V_I4(&var);
//	}
//	///获取数据集
//	vector<vector<int>> getItemset()
//	{
//	 //初始化com组件
//	 ::CoInitialize(NULL);
//	 _RecordsetPtr m_pRecordset("ADODB.Recordset");
//	 _ConnectionPtr m_pConnection("ADODB.Connection");
//	 string strConnect="Provider=SQLOLEDB;Data Source=RENHU\\SQLEXPRESS;uid=sa; pwd=sa;Initial Catalog=ALEDMO";
//	 string sqlStr = "";
//	 vector<vector<int>> itemset(0);
//	 
//	 try
//	 {
//	  if(m_pConnection->State)
//	   m_pConnection->Close();
//	  m_pConnection.CreateInstance("ADODB.Connection");
//	  m_pConnection->Open(strConnect.c_str(),"","",adModeUnknown);                  
//	  if(m_pConnection==NULL)
//	   cerr<<"连接错误！\n";
//	  m_pRecordset.CreateInstance(__uuidof(Recordset));
//	  //获得记录数
//	  //相同的salesorderid表示同一次交易
//	     sqlStr = "select count(*) from DJ_CELIANG_DATA";
//	  m_pRecordset->Open(sqlStr.c_str(),m_pConnection.GetInterfacePtr(),adOpenDynamic,adLockOptimistic,adCmdText);
//	  if(!m_pRecordset->EndOfFile)
//	  {
//	   _variant_t count = m_pRecordset->GetCollect(0L);
//	   int icount = variant_tToint(count);
//	   icount = 957;
//	   itemset.resize(icount+1);  
//	  }
//	  m_pRecordset->Close();
//	        //第0行保存列名称
//	        itemset[0].push_back(1);
//	        itemset[0].push_back(2);
//	        itemset[0].push_back(3);
//	        itemset[0].push_back(4);
//	        itemset[0].push_back(5);
//	        itemset[0].push_back(6);
//	  //读取记录
//	  sqlStr="select top 1000 potno,jhxcl,tczsl,cll,lsp,djzsp from DJ_CELIANG_DATA";
//	  m_pRecordset->Open(sqlStr.c_str(),m_pConnection.GetInterfacePtr(),adOpenDynamic,adLockOptimistic,adCmdText);
//	  //交易编号也是itemset的第一维向量编号
//	  int i = 1;
//	  while (!m_pRecordset->EndOfFile)
//	  {
//	   _variant_t vpotno = m_pRecordset->GetCollect("potno");
//	   _variant_t vjhxcl = m_pRecordset->GetCollect("jhxcl");
//	   _variant_t vtczsl = m_pRecordset->GetCollect("tczsl");
//	   _variant_t vcll = m_pRecordset->GetCollect("cll");
//	    _variant_t vlsp = m_pRecordset->GetCollect("lsp");
//	   _variant_t vdjzsp = m_pRecordset->GetCollect("djzsp");
//	          
//	   //暂时不考虑空情况
//	   if(vdjzsp.vt != VT_NULL && vlsp.vt != VT_NULL && vcll.vt != VT_NULL && vtczsl.vt != VT_NULL && vjhxcl.vt != VT_NULL && vpotno.vt != VT_NULL )
//	   {
//	    itemset[i].push_back(variant_tToint(vpotno));
//	    itemset[i].push_back(variant_tToint(vjhxcl));
//	    itemset[i].push_back(variant_tToint(vtczsl));
//	    itemset[i].push_back(variant_tToint(vcll));
//	    itemset[i].push_back(variant_tToint(vlsp));
//	    itemset[i].push_back(variant_tToint(vdjzsp));
//	    i++;
//	   }   
//	   m_pRecordset->MoveNext(); ///移到下一条记录
//	  }
//	  m_pRecordset->Close(); // 关闭记录集
//	  cout << "i=" << i << endl;
//	 }
//	 catch(_com_error e)
//	 {
//	  cerr << "\nERROR:" << (char*)e.Description();//抛出异常
//	 }
//	 return itemset;
//	}
//	int _tmain(int argc, _TCHAR* argv[])
//	{
//	 map<int,string> mapAtt;
//	 //属性个数
//	 int attCount = 5;
//	 //等值离散化，二维向量数组，每行表示一个属性，第0列表示最小值，第1列最大值，第2列分多少分,这里都是10
//	 //按最后一个属性分类
//	 vector<vector<int>> apart(attCount);
//	 //这里先写死，在实际中应该是由用户设定
//	 //在这里第0个属性是槽号，不需要离散化，直接从第1个属性开始
//	 //从数据中知道各属性的最大，最小值分别是：
//	 
//
//	 //jhxcl:计划出铝量
//	 apart[0].push_back(800);
//	 apart[0].push_back(1560);
//	 apart[0].push_back(10);
//	 //tczsl:天车指示量
//	 apart[1].push_back(800);
//	 apart[1].push_back(1620);
//	 apart[1].push_back(10);
//	 //cll:出铝量
//	 apart[2].push_back(796);
//	 apart[2].push_back(2975);
//	 apart[2].push_back(10);
//	 //lsp:铝水平
//	 apart[3].push_back(23);
//	 apart[3].push_back(310);
//	 apart[3].push_back(10);
//	 //djzsp:电解质水平
//	 apart[4].push_back(130);
//	 apart[4].push_back(360);
//	 apart[4].push_back(10);
//	 //临时变量
//	 int i,j;
//	 
//	    vector<vector<int>> datas(0);
//	 datas = getItemset();
//	 discretize(datas,apart);
//
//	 generateDecisionTree(datas, mapAtt);
//	 
//	 
//	 return 0;
//	}
}
