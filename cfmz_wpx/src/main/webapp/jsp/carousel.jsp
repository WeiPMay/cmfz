<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1>轮播图管理</h1>
</div>
<script>
    $(function(){
        $("#carouselTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/carousel/queryByPage",
            datatype:"json",
            colNames:["编号","名称","图片","状态","上传时间"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"imgpath",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/carouselPic/"+cellvalue+"'/>";
                    }},
                {name:"status",editable:true},
                {name:"createTime",editable:true,edittype:"date"}],
            pager:"carouselPager",
            rowNum:2,
            rowList:[2,5,10],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/carousel/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#carouselPager",{},{
            //修改的部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/carousel/fileupload",
                    fileElementId:"imgpath",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        },{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/carousel/fileupload",
                    fileElementId:"imgpath",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#carouselTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })
</script>
<table id="carouselTable"></table>
<div id="carouselPager"></div>

