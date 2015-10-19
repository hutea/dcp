<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>首页统计页面</title>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <link href="${pageContext.request.contextPath}/resource/bootstarp/bootstrap.min.css" rel="stylesheet"></link>
	<link href="${pageContext.request.contextPath}/resource/jqueryUI/jquery.ui.core.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/resource/jqueryUI/jquery.ui.tabs.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/resource/jqueryUI/jquery.ui.theme.css" rel="stylesheet" >
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jqueryUI/jquery1.4.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jqueryUI/jquery-ui-1.8.16.custom.min.js"></script>
	<script>
	  $(document).ready(function() {
	    $("#tabs").tabs();
	  });
	</script>
</head>
<body style="font-size:62.5%;">
  
<div id="tabs">
    <ul>
        <li><a href="#fragment-1" ><span>今天</span></a></li>
        <li><a href="#fragment-2"><span>昨天</span></a></li>
        <li><a href="#fragment-3"><span>最近一周</span></a></li>
        <li><a href="#fragment-4"><span>最近一月</span></a></li>
        <li><a href="#fragment-5"><span>最近一年</span></a></li>
        <li><a href="#fragment-6"><span>最近八年</span></a></li>
        <li><a href="#fragment-7"><span>最近十五年</span></a></li>
    </ul>
    <div style="border: 1px solid #CCCCCC;border-top: none;">
	    <div id="fragment-1">
	    	<table class="table table-bordered table-striped table-condensed"  >
				<tr>
					<td >时段(钟点)</td>
					<c:forEach begin="0" end="23" varStatus="s">
						<td>${s.index}-${s.index+1 }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight1}" var="entry" varStatus="s">
						<td>${entry}</td>
					</c:forEach>
				</tr>
				<tr>
					<td style="width: 98px;">累计工作循环次数</td>
					<c:forEach items="${count1}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time1}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'.')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    
	    <div id="fragment-2">
	    	<table  >
				<tr>
					<td >时段(钟点)</td>
					<c:forEach begin="0" end="23" varStatus="s">
						<td>${s.index}-${s.index+1 }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight2}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count2}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time2}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'.')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    
	    <div id="fragment-3">
	    	<table border="1" bordercolor="#A8C7CE" class="tab" width="100%"  style="*width: 97%">
				<tr>
					<td >时段(钟点)</td>
					<c:forEach items="${dates3}" var="entry" varStatus="s">
						<td><fmt:formatDate value="${entry}" pattern="yyyy-MM-dd"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight3}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count3}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time3}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'分-秒')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    
	    <div id="fragment-4">
	    	<table border="1" bordercolor="#A8C7CE" class="tab" width="100%"  style="*width: 97%" >
				<tr>
					<td >时段(钟点)</td>
					<td><fmt:formatDate value="${dates4[0]}" pattern="yyyy-MM-dd"/> 至<fmt:formatDate value="${dates4[1]}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${dates4[2]}" pattern="yyyy-MM-dd"/> 至<fmt:formatDate value="${dates4[3]}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${dates4[4]}" pattern="yyyy-MM-dd"/> 至<fmt:formatDate value="${dates4[5]}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${dates4[6]}" pattern="yyyy-MM-dd"/> 至<fmt:formatDate value="${dates4[7]}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight4}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count4}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time4}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'分-秒')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    
	    <div id="fragment-5" >
	    	<table border="1" bordercolor="#A8C7CE" class="tab" width="100%"  style="*width: 97%" >
				<tr>
					<td >时段(钟点)</td>
					<c:forEach items="${dates5}" var="entry" varStatus="s">
						<td><fmt:formatDate value="${entry}" pattern="yyyy-MM"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight5}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count5}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time5}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'分-秒')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    
	    <div id="fragment-6" >
	    	<table border="1" bordercolor="#A8C7CE" class="tab" width="100%"  style="*width: 97%" >
				<tr>
					<td >时段(钟点)</td>
					<c:forEach items="${dates6}" var="entry" varStatus="s">
						<td><fmt:formatDate value="${entry}" pattern="yyyy"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight6}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count6}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time6}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'分-秒')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
	    <div id="fragment-7">
	    	<table border="1" bordercolor="#A8C7CE" class="tab" width="100%"  style="*width: 97%" >
				<tr>
					<td >时段(钟点)</td>
					<c:forEach items="${dates7}" var="entry" varStatus="s">
						<td><fmt:formatDate value="${entry}" pattern="yyyy"/></td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重(T)</td>
					<c:forEach items="${weight7}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计工作循环次数</td>
					<c:forEach items="${count7}" var="entry" varStatus="s">
						<td>${entry }</td>
					</c:forEach>
				</tr>
				<tr>
					<td>累计吊重时间(分)</td>
					<c:forEach items="${time7}" var="entry" varStatus="s">
						<td>${hfn:toFenzhong(entry,'分-秒')}</td>
					</c:forEach>
				</tr>
			</table>
	    </div>
   	  </div>
</div>
	
	</body>
</html>