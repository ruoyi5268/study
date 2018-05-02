<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>管理定区/调度排班</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/default.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui/ext/jquery.cookie.js"></script>
		<script src="${pageContext.request.contextPath}/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<script type="text/javascript">
			function doAdd(){
				$('#addWindow').window("open");
			}
			
			function doEdit(){
				alert("修改...");
			}
			
			function doDelete(){
				alert("删除...");
			}
			
			function doSearch(){
				$('#searchWindow').window("open");
			}

			//关联客户
			function doAssociations(){
                var rows = $("#grid").datagrid('getSelections');
                if(rows.length!=1){
                    $.messager.alert("警告","请选中一条记录进行操作","warning");
				}else{
					//去客户端查找未关联定区的客户
                    $("#noassociationSelect").empty();
					$.post(
						"${pageContext.request.contextPath}/fixedAreaAction_noAssociation.action",
						null,
						function (data) {
						    if(data.length != 0){
								for(var i=0; i<data.length; i++){
									$("#noassociationSelect").append("<option value='"+data[i].id+"'>"+data[i].username+":"+data[i].address+"</option>");
								}
							}
						}
					);
					//去客户端查找已关联定区的客户
                    $("#associationSelect").empty();
					$.post(
						"${pageContext.request.contextPath}/fixedAreaAction_hasAssociation.action?id="+rows[0].id,
						null,
						function (data) {
                            if(data.length != 0){
								for(var i=0; i<data.length; i++){
									$("#associationSelect").append("<option value='"+data[i].id+"'>"+data[i].username+":"+data[i].address+"</option>");
								}
							}
						}
					);
					$('#customerWindow').window('open');
				}
			}

			//关联分区
			function doAssociationsSubArea(){
                var rows = $("#grid").datagrid('getSelections');
                if(rows.length!=1){
                    $.messager.alert("警告","请选中一条记录进行操作","warning");
				}else{
					//去客户端查找未关联定区的分区
                    $("#noassociationSelect").empty();
					$.post(
						"${pageContext.request.contextPath}/subAreaAction_noAssociationSubarea.action",
						null,
						function (data) {
						    if(data.length != 0){
								for(var i=0; i<data.length; i++){
									$("#noassociationSelect1").append("<option value='"+data[i].id+"'>"+data[i].keyWords+"/"+data[i].assistKeyWords+"</option>");
								}
							}
						}
					);
					//去客户端查找已关联定区的分区
                    $("#associationSelect").empty();
					$.post(
						"${pageContext.request.contextPath}/subAreaAction_hasAssociationSubarea.action?subareaFixedAreaId="+rows[0].id,
						null,
						function (data) {
                            if(data.length != 0){
								for(var i=0; i<data.length; i++){
									$("#associationSelect1").append("<option value='"+data[i].id+"'>"+data[i].keyWords+"/"+data[i].assistKeyWords+"</option>");
								}
							}
						}
					);
					$('#subareaWindow').window('open');
				}
			}

			//工具栏
			var toolbar = [ {
				id : 'button-search',	
				text : '查询',
				iconCls : 'icon-search',
				handler : doSearch
			}, {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : doAdd
			}, {
				id : 'button-edit',	
				text : '修改',
				iconCls : 'icon-edit',
				handler : doEdit
			},{
				id : 'button-delete',
				text : '删除',
				iconCls : 'icon-cancel',
				handler : doDelete
			},{
				id : 'button-association',
				text : '关联客户',
				iconCls : 'icon-sum',
				handler : doAssociations
			},{
				id : 'button-association-courier',
				text : '关联快递员',
				iconCls : 'icon-sum',
				handler : function(){
					// 判断是否已经选中了一个定区，弹出关联快递员窗口 
					var rows = $("#grid").datagrid('getSelections');
					if(rows.length==1){
						// 只选择了一个定区
						//查找未与某定区关联的快递员
                        $("#courierId").combobox({
                            url:'${pageContext.request.contextPath}/courierAction_listajax.action?fixedAreaId='+rows[0].id,
                            valueField:'id',
                            textField:'name',
							required:true,
                            onSelect:function (record) {
                                var courierSelected = $("#courierId").combobox("getValue");
                                $('#taketimeId').combobox({
									url:"${pageContext.request.contextPath}/courierAction_findTakeTime.action?id="+courierSelected,                                    valueField:'id',//值字段
                                    textField:'name', //显示的字段      
                                   valueField:'id',//值字段
                                    editable:false, //不可编辑，只能选择
                                    required:true
                                });
                            }
                        });
						// 弹出定区关联快递员 窗口
						$("#courierWindow").window('open');
					}else{
						// 没有选中定区，或者选择 了多个定区
						$.messager.alert("警告","关联快递员,只能（必须）选择一个定区","warning");
					}
				}
			},{
				id : 'button-association2',
				text : '关联分区',
				iconCls : 'icon-sum',
				handler : doAssociationsSubArea
			}];
			// 定义列
			var columns = [ [ {
				field : 'id',
				title : '编号',
				width : 80,
				align : 'center',
				checkbox:true
			},{
				field : 'fixedAreaName',
				title : '定区名称',
				width : 120,
				align : 'center'
			}, {
				field : 'fixedAreaLeader',
				title : '负责人',
				width : 120,
				align : 'center'
			}, {
				field : 'telephone',
				title : '联系电话',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '所属公司',
				width : 120,
				align : 'center'
			} ] ];
			
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 定区数据表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : true,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/fixedAreaAction_findByPage.action",
					idField : 'id',
					columns : columns,
					onDblClickRow : doDblClickRow
				});
				
				// 添加、修改定区
				$('#addWindow').window({
			        title: '添加修改定区',
			        width: 600,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 400,
			        resizable:false
			    });
				
				// 查询定区
				$('#searchWindow').window({
			        title: '查询定区',
			        width: 400,
			        modal: true,
			        shadow: true,
			        closed: true,
			        height: 400,
			        resizable:false
			    });
				//确认查询定区
				$("#btn").click(function(){
					//序列化表单,得到查询条件
					var condition = $("#searchForm").serializeJson();
					//加载数据表格,带条件
					$("#grid").datagrid("load",condition);
                    $('#searchWindow').window("close");
				});

				//添加/修改定区
				$("#save").click(function () {
					var r = $("#fixedAreaForm").form("validate");
					if(r){
						$("#fixedAreaForm").submit();
					}
                });

				//左右移动关联的客户
				$("#toRight").click(function () {
					$("#associationSelect").append($("#noassociationSelect option:selected"));
                });
				$("#toLeft").click(function () {
					$("#noassociationSelect").append($("#associationSelect option:selected"));
                });
				//双击移动客户
                $("#noassociationSelect").dblclick(function () {
                    $("#associationSelect").append($("#noassociationSelect option:selected"));
                });
                $("#associationSelect").dblclick(function () {
                    $("#noassociationSelect").append($("#associationSelect option:selected"));
                });
                //关联客户
				$("#associationBtn").click(function () {
					//获取当前被选中的行
					var rows = $("#grid").datagrid("getSelections");
					//先获取定区id
					if(rows.length==0){
					    $.messager.alert("警告","未选中定区","warning");
					}
					console.log("customerFixedAreaId:"+rows[0].id);
					var fixedAreaId = rows[0].id;
					$("#customerFixedAreaId").val(fixedAreaId);
					$("#associationSelect option").attr("selected","selected");
					$("#customerForm").submit();
                });

				//关联分区
                $("#associationBtn1").click(function () {
                    var rows = $("#grid").datagrid("getSelections");
                    var fixedAreaId = rows[0].id;
                    $("#subareaFixedAreaId").val(fixedAreaId);
                    $("#associationSelect option").attr("selected","selected");
                    $("#subareaForm").submit();
                });

				//关联快递员
				$("#associationCourierBtn").click(function () {
                    var rows = $("#grid").datagrid("getSelections");
                    var fixedAreaId = rows[0].id;
				    $("#courierFixedAreaId").val(fixedAreaId);
					$("#courierForm").submit();
                });

                //左右移动关联的分区
                $("#toRight1").click(function () {
                    $("#associationSelect1").append($("#noassociationSelect1 option:selected"));
                });
                $("#toLeft1").click(function () {
                    $("#noassociationSelect1").append($("#associationSelect1 option:selected"));
                });
                //双击移动分区
                $("#noassociationSelect1").dblclick(function () {
                    $("#associationSelect1").append($("#noassociationSelect1 option:selected"));
                });
                $("#associationSelect1").dblclick(function () {
                    $("#noassociationSelect1").append($("#associationSelect1 option:selected"));
                });

            });

			//将表单序列化成json对象
            $.fn.serializeJson=function(){
                var serializeObj={};
                var array=this.serializeArray();
                var str=this.serialize();
                $(array).each(function(){
                    if(serializeObj[this.name]){
                        if($.isArray(serializeObj[this.name])){
                            serializeObj[this.name].push(this.value);
                        }else{
                            serializeObj[this.name]=[serializeObj[this.name],this.value];
                        }
                    }else{
                        serializeObj[this.name]=this.value;
                    }
                });
                return serializeObj;
            };

            function doDblClickRow(index,rowData){
				console.log(rowData);

                $('#association_subarea').datagrid( {
					fit : true,
					border : true,
					rownumbers : true,
					striped : true,
					url : "${pageContext.request.contextPath}/data/association_subarea.json",
					columns : [ [{
						field : 'id',
						title : '分拣编号',
						width : 120,
						align : 'center'
					},{
						field : 'province',
						title : '省',
						width : 120,
						align : 'center',
						formatter : function(data,row ,index){
							if(row.area!=null){
								return row.area.province;
							}
							return "";
						}
					}, {
						field : 'city',
						title : '市',
						width : 120,
						align : 'center',
						formatter : function(data,row ,index){
							if(row.area!=null){
								return row.area.city;
							}
							return "";
						}
					}, {
						field : 'district',
						title : '区',
						width : 120,
						align : 'center',
						formatter : function(data,row ,index){
							if(row.area!=null){
								return row.area.district;
							}
							return "";
						}
					}, {
						field : 'addresskey',
						title : '关键字',
						width : 120,
						align : 'center'
					}, {
						field : 'startnum',
						title : '起始号',
						width : 100,
						align : 'center'
					}, {
						field : 'endnum',
						title : '终止号',
						width : 100,
						align : 'center'
					} , {
						field : 'single',
						title : '单双号',
						width : 100,
						align : 'center'
					} , {
						field : 'position',
						title : '位置',
						width : 200,
						align : 'center'
					} ] ]
				});
				$('#association_customer').datagrid( {
					fit : true,
					border : true,
					rownumbers : true,
					striped : true,
					url : "${pageContext.request.contextPath}/fixedAreaAction_findAssociationCustomers.action?id="+rowData.id,
					columns : [[{
						field : 'id',
						title : '客户编号',
						width : 120,
						align : 'center'
					},{
						field : 'username',
						title : '客户名称',
						width : 120,
						align : 'center'
					}, {
						field : 'company',
						title : '所属单位',
						width : 120,
						align : 'center'
					}]]
				});
                $('#association_courier').datagrid( {
                    fit : true,
                    border : true,
                    rownumbers : true,
                    striped : true,
                    url : "${pageContext.request.contextPath}/fixedAreaAction_findAssociationCourier.action?id="+rowData.id,
                    columns : [[{
                        field : 'id',
                        title : '编号',
                        width : 120,
                        align : 'center'
                    },{
                        field : 'name',
                        title : '姓名',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'company',
                        title : '所属单位',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'type',
                        title : '类型',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'telephone',
                        title : '电话',
                        width : 120,
                        align : 'center'
                    }]]
                });
                $('#association_subarea').datagrid( {
                    fit : true,
                    border : true,
                    rownumbers : true,
                    striped : true,
                    url : "${pageContext.request.contextPath}/fixedAreaAction_findAssociationSubArea.action?id="+rowData.id,
                    columns : [[{
                        field : 'id',
                        title : '编号',
                        width : 120,
                        align : 'center'
                    },{
                        field : 'keyWords',
                        title : '关键字',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'assistKeyWords',
                        title : '辅助关键字',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'startNum',
                        title : '起始号',
                        width : 120,
                        align : 'center'
                    }, {
                        field : 'endNum',
                        title : '终止号',
                        width : 120,
                        align : 'center'
                    }]]
                });
			}
		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		<div region="south" border="false" style="height:150px">
			<div id="tabs" fit="true" class="easyui-tabs">
				<div title="关联分区" id="subArea" style="width:100%;height:100%;overflow:hidden">
					<table id="association_subarea"></table>
				</div>
				<div title="关联快递员" id="courier" style="width:100%;height:100%;overflow:hidden">
					<table id="association_courier"></table>
				</div>
				<div title="关联客户" id="customers" style="width:100%;height:100%;overflow:hidden">
					<table id="association_customer"></table>
				</div>
			</div>
		</div>

		<!-- 添加 修改定区 -->
		<div class="easyui-window" title="定区添加修改" id="addWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">保存</a>
					</script>
				</div>
			</div>

			<div style="overflow:auto;padding:5px;" border="false">
				<form id="fixedAreaForm" method="post" action="${pageContext.request.contextPath}/fixedAreaAction_save.action">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">定区信息</td>
						</tr>
						<tr>
							<td>定区编码</td>
							<td><input type="text" name="id" class="easyui-validatebox"
								required="true" /></td>
						</tr>
						<tr>
							<td>定区名称</td>
							<td><input type="text" name="fixedAreaName"
								class="easyui-validatebox" required="true" /></td>
						</tr>
						<tr>
							<td>负责人</td>
							<td><input type="text" name="fixedAreaLeader"
								class="easyui-validatebox" required="true" /></td>
						</tr>
						<tr>
							<td>联系电话</td>
							<td><input type="text" name="telephone"
								class="easyui-validatebox" required="true" /></td>
						</tr>
						<tr>
							<td>所属公司</td>
							<td><input type="text" name="company"
								class="easyui-validatebox" required="true" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<!-- 查询定区 -->
		<div class="easyui-window" title="查询定区窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="searchForm">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">查询条件</td>
						</tr>
						<tr>
							<td>定区编码</td>
							<td>
								<input type="text" name="id" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<tr>
							<td>所属单位</td>
							<td>
								<input type="text" name="courier.company" class="easyui-validatebox" required="true" />
							</td>
						</tr>
						<!--<tr>
							<td>分区</td>
							<td>
								<input type="text" name="subareaName" class="easyui-validatebox" required="true" />
							</td>
						</tr>-->
						<tr>
							<td colspan="2"><a id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<!-- 关联客户窗口 -->
		<div modal="true" class="easyui-window" title="关联客户窗口" id="customerWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 750px;height: 300px;">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="customerForm" action="${pageContext.request.contextPath}/fixedAreaAction_assignCustomers2FixedArea.action"  method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="3">关联客户</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="customerFixedAreaId" id="customerFixedAreaId" />
								<select id="noassociationSelect" multiple="multiple" size="10"></select>
							</td>
							<td>
								<input type="button" value="》》" id="toRight">
								<br/>
								<input type="button" value="《《" id="toLeft">
							</td>
							<td>
								<select id="associationSelect" name="customerIds" multiple="multiple" size="10"></select>
							</td>
						</tr>
						<tr>
							<td colspan="3"><a id="associationBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联客户</a> </td>
						</tr>
					</table>
				</form>
			</div>
		</div>

		<!-- 关联分区窗口 -->
		<div modal="true" class="easyui-window" title="关联分区窗口" id="subareaWindow" collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 750px;height: 300px;">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="subareaForm" action="${pageContext.request.contextPath}/fixedAreaAction_assignSubArea2FixedArea.action"  method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="3">关联分区</td>
						</tr>
						<tr>
							<td>
								<input type="hidden" name="subareaFixedAreaId" id="subareaFixedAreaId" />
								<select id="noassociationSelect1" multiple="multiple" size="10"></select>
							</td>
							<td>
								<input type="button" value="》》" id="toRight1">
								<br/>
								<input type="button" value="《《" id="toLeft1">
							</td>
							<td>
								<select id="associationSelect1" name="subareaIds" multiple="multiple" size="10"></select>
							</td>
						</tr>
						<tr>
							<td colspan="3"><a id="associationBtn1" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联分区</a> </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		
		<!-- 关联快递员窗口 -->
		<div class="easyui-window" title="关联快递员窗口" id="courierWindow" modal="true"
			collapsible="false" closed="true" minimizable="false" maximizable="false" style="top:20px;left:200px;width: 700px;height: 300px;">
			<div style="overflow:auto;padding:5px;" border="false">
				<form id="courierForm" 
					action="${pageContext.request.contextPath}/fixedAreaAction_associationCourierToFixedArea.action" method="post">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">关联快递员</td>
						</tr> 
						<tr>
							<td>选择快递员</td>
							<td>
								<!-- 存放定区编号 -->
								<input type="hidden" name="id" id="courierFixedAreaId" />

								<input type="text" id="courierId" name="courierId"/>
							</td>
						</tr>
						<tr>
							<td>选择收派时间</td>
							<td>
                                <input type="text" id="taketimeId" name="taketimeId" />
								<!--<select required="true" class="easyui-combobox" name="takeTimeId">
									<option>请选择</option>
									<option value="1">北京早班</option>
									<option value="2">北京晚班</option>
								</select>-->
								<!-- <input type="text" name="takeTimeId" class="easyui-combobox" required="true" /> -->
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<a id="associationCourierBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'">关联快递员</a>
							 </td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>

</html>