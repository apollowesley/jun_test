<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.util.*" errorPage=""%>
<%--<div class="" id="weibo_list_wp">
	<c:forEach items="${list}" var="list" varStatus="status">
		<div class="<c:if test="${status.index%2!=0}">split</c:if>" id="">
			<div class="weibo" data-tid="" data-login="" data-uid="" data-huifu="">
				<div class="operate"><%@ include file="operateup.jsp"%></div>
				<div class="wb_l">
					<div>
						<img class="author" src="${avatar}" onclick="goToUserInfo(2);" />
					</div>
				</div>
				<div class="wb_r">
					<div class="user_info">
						<span class="fl p_u"><%=request.getSession().getAttribute("UName")%></span>
						<span class="fr p_t"> <span><fmt:formatDate
									value="${list.bdate}" pattern="yyyy-MM-dd HH:mm:ss" /></span>
						</span>
					</div>
					<div class="wb_c_wp">
						<div class="wb_c">
							由于
							<c:choose>
								<c:when test="${list.bbetravelleader}">
									<span class="wordColor"><a
										style="cursor:pointer;vertical-align:bottom;color:#1460dd;"
										data="${list.bid}" onmouseover="new displaydetail(this);"
										onclick="displaydetailatpage(this);" title="点击查看详细">${list.btypename}</a></span>
								</c:when>
								<c:when test="${list.btid!=0}">
									<span class="wordColor" style="color:#aaa;">${list.btypename}</span>
								</c:when>
								<c:otherwise>${list.btypename}</c:otherwise>
							</c:choose>
							${list.bioname} <span class="wordColor"> 人民币
								${list.bsign}${list.bamount}元。 </span>
						</div>
						<div class="from">
							<span class="fl">${list.bcaption}</span> <span class="fr num">
							</span>
						</div>
						<c:if test="${list.bidir!=null}">
							<div style="position:relative1;">
								<img class="locateimg_img" id="locateimg_img_${status.index}"
									data="${list.bidir}" />
							</div>
						</c:if>
					</div>
				</div>

				<div class="operate"><%@ include file="operatebottom.jsp"%></div>
			</div>
			<div class="wb_line"></div>
		</div>
	</c:forEach>
</div>--%>