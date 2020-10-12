<%@page pageEncoding="utf-8" %>
<ul id="menu">
	<%-- 
    <li><a href="/telecom/toIndex.do" class="index_off"></a></li>
    --%>
    <!-- 利用jsp表达式获取项目名称 -->
    <li><a href="<%= this.getServletContext().getContextPath() %>/toIndex.do" class="index_off"></a></li>
    
    <li><a href="" class="role_off"></a></li>
    <li><a href="" class="admin_off"></a></li>
    <!--  
    <li><a href="/telecom/findCost.do" class="fee_off"></a></li>
    -->
    <!-- 利用EL获取项目名称 -->
    <li><a href="${pageContext.request.contextPath }/findCost.do" class="fee_off"></a></li>
    <li><a href="" class="account_off"></a></li>
    <li><a href="" class="service_off"></a></li>
    <li><a href="" class="bill_off"></a></li>
    <li><a href="" class="report_off"></a></li>
    <li><a href="" class="information_off"></a></li>
    <li><a href="" class="password_off"></a></li>
</ul>