<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<div class="modal fade" id="myModal-active"  role="dialog" aria-label="myModalLabel" aria-hidden="true">
   <div class="modal-dialog modal-lg">
       <div class="modal-content">
       	   <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title">监控设备注册</h4>
		   </div>
           <div class="modal-body">
           		<input type="hidden" id="pid">
           		<input type="hidden" id="sid">
           		<input type="hidden" id="fuid">
           		<div class="container-fluid">
           			  <div class="row">
			            <div class="col-md-3"><h4>设备IMEI号</h4></div>
			            <div class="col-md-9"><input type="text" id="imei" class="form-control" readonly="readonly" ></div>
			          </div>
			          <div class="row">
			            <div class="col-md-3"><h4>俯视点名称</h4></div>
			            <div class="col-md-9"><input type="text"   id="pname" class="typeahead pname"  autocomplete="off" ></div>
			          </div>
			          <div class="row">
			           	<div class="col-md-3"><h4>现场编号</h4></div>
			            <div class="col-md-3"><input type="text" id="scenenum" class="form-control"></div>
			            <div class="col-md-3"><h4>是否启用激活</h4></div>
			            <div class="col-md-3">
			            	<select id="enable" name="enable" class="form-control" >
			            		<option value="0" >请选择</option>
			            		<option value="1" >启用</option>
			            		<option value="2" >禁用</option>
			            	</select>
			            </div>
			          </div>
			          <div class="row">
			            <div class="col-md-3"><h4>经纬度<a href="http://api.map.baidu.com/lbsapi/getpoint" target=window >拾取</a></h4></div>
			            <div class="col-md-6"><input type="text" id="lnglat" class="form-control"></div>
			          </div>
			          <div class="row">
			          	<div class="col-md-3"><h4>设备型号</h4></div>
			            <div class="col-md-6">
			            	<select name="mid" id="mid" class="form-control" >
			            		<option value="0" >请选择</option>
			            		<c:forEach items="${models}" var="entry">
			            			<option value="${entry.id}" >${entry.model}(${entry.manufacturer})</option>
			            		</c:forEach>
			            	</select>
			            </div>
			          </div>
			          <div class="row hidden" >
			            <div class="col-md-3"><h4>IMEI号ASCII码</h4></div>
			            <div class="col-md-6"><input type="text" id="imeiASCII" class="form-control" readonly="readonly"  ></div>
			          </div>
			          <hr>
			          <div class="row">
			            <div class="col-md-3"><h4>使用备案编号</h4></div>
			            <div class="col-md-9"><input type="text"   id="funum" class="typeahead funum"  autocomplete="off" ></div>
			          </div>
			          <div class="row">
			            <div class="col-md-3"></div>
			          	<div id="futext" class="col-md-9"></div>
			          </div>
			          <div class="row text-center" style="margin-top: 10px;">
			            <div id="tip" class="col-md-12"></div>
			          </div>
			    </div>
           </div><!-- modal-body -->
           <div class="modal-footer">
           	   <div id="comt-tip" class="text-primary text-center" style="display: none;"></div>
           	   <p class="col-md-12 text-center" >
           	   	<button id="act-save" onclick="act_save()" type="button" class="btn btn-default" >保存</button>
                <button type="button" class="btn btn-default hidden" data-dismiss="modal">关闭</button>
               </p>
           </div>
       </div>
   </div>
</div><!--modal fade  -->