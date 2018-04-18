<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="UTF-8">
		<title>取派标准</title>
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
			$(function(){
				// 先将body隐藏，再显示，不会出现页面刷新效果
				$("body").css({visibility:"visible"});
				
				// 收派标准信息表格
				$('#grid').datagrid( {
					iconCls : 'icon-forward',
					fit : true,
					border : false,
					rownumbers : true,
					striped : true,
					pageList: [30,50,100],
					pagination : true,
					toolbar : toolbar,
					url : "${pageContext.request.contextPath}/standardAction_findByPage.action",
					idField : 'id',
					columns : columns
				});
			});
			
			//工具栏
			var toolbar = [ {
				id : 'button-add',
				text : '增加',
				iconCls : 'icon-add',
				handler : function(){
					$("#standardWindow").window("open");
				}
			}, {
				id : 'button-edit',
				text : '修改',
				iconCls : 'icon-edit',
				handler : function(){
				    var rows = $("#grid").datagrid("getSelections");
				    if(rows.length!=1){
				        $.messager.alert("警告","必须选中某一项进行修改!")
					}else{
						$("#standardForm").form("load",rows[0]);
                        $("#standardWindow").window("open");
                    }
				}
			},{
				id : 'button-delete',
				text : '作废',
				iconCls : 'icon-cancel',
				handler : function(){
                    var rows = $("#grid").datagrid("getSelections");
                    if(rows.length<1){
                        $.messager.alert("警告","请选择将作废的项!")
                    }else{
                        var array = new Array();
                        for(var i=0; i<rows.length; i++){
                            array.push(rows[i].id);
						}
						var ids=array.join(",");
                        location.href="${pageContext.request.contextPath}/standardAction_deleteBatch.action?ids="+ids;
                    }
				}
			},{
				id : 'button-restore',
				text : '还原',
				iconCls : 'icon-save',
				handler : function(){
					alert('还原');
				}
			}];
			
			// 定义列
			var columns = [ [ {
				field : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '标准名称',
				width : 120,
				align : 'center'
			}, {
				field : 'minWeight',
				title : '最小重量',
				width : 120,
				align : 'center'
			}, {
				field : 'maxWeight',
				title : '最大重量',
				width : 120,
				align : 'center'
			}, {
				field : 'minLength',
				title : '最小长度',
				width : 120,
				align : 'center'
			}, {
				field : 'maxLength',
				title : '最大长度',
				width : 120,
				align : 'center'
			}, {
				field : 'operator',
				title : '操作人',
				width : 120,
				align : 'center'
			}, {
				field : 'operatingTime',
				title : '操作时间',
				width : 120,
				align : 'center'
			}, {
				field : 'company',
				title : '操作单位',
				width : 120,
				align : 'center'
			} ] ];


		</script>
	</head>

	<body class="easyui-layout" style="visibility:hidden;">
		<div region="center" border="false">
			<table id="grid"></table>
		</div>
		
		<div class="easyui-window" title="对收派标准进行添加或者修改" id="standardWindow" collapsible="false" minimizable="false" maximizable="false" modal="true" closed="true" style="width:600px;top:50px;left:200px">
			<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
				<div class="datagrid-toolbar">
					<a id="save" icon="icon-save" href="javascript:;" class="easyui-linkbutton" plain="true">保存</a>
				</div>
			</div>

			<div region="center" style="overflow:auto;padding:5px;" border="false">
				
				<form action="${pageContext.request.contextPath}/standardAction_save.action" method="post" id="standardForm">
					<table class="table-edit" width="80%" align="center">
						<tr class="title">
							<td colspan="2">收派标准信息
								<!--提供隐藏域 装载id -->
								<input type="hidden" name="id" />
							</td>
						</tr>
						<tr>
							<td>收派标准名称</td>
							<td>
								<input type="text" name="name" 
									class="easyui-validatebox" data-options="required:true" />
							</td>
						</tr>
						<tr>
							<td>最小重量</td>
							<td>
								<input type="text" name="minWeight" 
										class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大重量</td>
							<td>
								<input type="text" name="maxWeight" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最小长度</td>
							<td>
								<input type="text" name="minLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
						<tr>
							<td>最大长度</td>
							<td>
								<input type="text" name="maxLength" class="easyui-numberbox" required="true" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	<
	<script type="text/javascript">

        //增加/修改按钮点击事件
        $("#save").click(function () {
            //验证表单数据
            var r = $("#standardForm").form("validate");
            if(r){
                //提交添加/修改收派标准的表单
                $("#standardForm").submit();
            }
        });
	</script>
</html>