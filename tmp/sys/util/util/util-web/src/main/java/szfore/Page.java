package szfore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 
/**
 * 对分页的基本数据进行一个简单的封装
 */
public class Page<T> {
 
	public static final String KEY_WORD = "keyWord";
	
    private int pageNum = 1;//页码，默认是第一页
    private Integer numPerPage = 20;//每页显示的记录数，默认是20
    private int totalRecord;//总记录数
    private int totalPage;//总页数
    private List<T> results;//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();//其他的参数我们把它分装成一个Map对象
    
    public Page(){}
    
    public Page(int pageNum,int numPerPage){
    	setPageNum(pageNum);
    	setNumPerPage(numPerPage);
    }
    
    public Page(int pageNum,int numPerPage,Map<String, Object> params){
    	setPageNum(pageNum);
    	setNumPerPage(numPerPage);
    	setParams(params);
    }
    
    public int getPageNum() {
       return pageNum;
    }
    public void setPageNum(int pageNum) {
       this.pageNum = pageNum;
    }
    public int getNumPerPage() {
       return numPerPage;
    }
    public void setNumPerPage(int numPerPage) {
       this.numPerPage = numPerPage;
    }
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord% numPerPage==0 ? totalRecord/numPerPage : totalRecord/numPerPage + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    @Override
    public String toString() {
       printParam();
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNum).append(", pageSize=")
              .append(numPerPage).append(", results=").append(results).append(
                     ", totalPage=").append(totalPage).append(
                     ", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
    
    private void printParam()
    {
    	for (Entry<String, Object> entry : params.entrySet()) 
		{
			System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
		}
    }

	@SuppressWarnings("rawtypes")
	public Page putParam(String paramName, Object paramValue) 
	{
		if(params == null)
		{
			params = new HashMap<String, Object>();
		}
		params.put(paramName, paramValue);
		return this;
	}
}