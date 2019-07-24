<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>

<div class="page-header">
    <h1>用户管理</h1>
</div>
<script>
    $(function(){
        $("#userTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/user/queryByPage",
            datatype:"json",
            colNames:["编号","电话","密码","盐","法名","省","市","性别","签名","头像","状态","注册时间"],
            colModel:[
                {name:"id"},
                {name:"phone",editable:true},
                {name:"password",editable:true},
                {name:"salt"},
                {name:"dharmaName",editable:true},
                {name:"province",editable:true},
                {name:"city",editable:true},
                {name:"gender",editable:true},
                {name:"personalSign",editable:true},
                {name:"profile",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/profilePic/"+cellvalue+"'/>";
                    }},
                {name:"status"},
                {name:"registTime",editable:true,edittype:"date"}],
            pager:"userPager",
            rowNum:2,
            rowList:[2,5,10],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/user/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#userPager",{},{
            //修改的部分
            closeAfterEdit:true,

            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/user/fileupload",
                    fileElementId:"profile",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#userTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }

        },{
            //添加的部分,
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/user/fileupload",
                    fileElementId:"profile",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){$("#userTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })

</script>
<table id="userTable"></table>
<div id="userPager"></div>
<a type="button" class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/user/export">一键导出</a>
<form method="post" action="${pageContext.request.contextPath}/user/import" enctype="multipart/form-data">
    <input type="file" name="file" >
    一键导入：<input type="submit">
</form>

