<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<div class="page-header">
    <h1>专辑和章节管理</h1>
</div>
<script>
    $(function(){
        $("#albumTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/album/queryByPage",
            datatype:"json",
            colNames:["编号","专辑名称","专辑封面","章节数量","专辑得分","专辑作者","播音员","专辑简介","上传时间"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"cover",editable:true,edittype:"file",formatter:function(cellvalue, options, rowObject){
                        return "<img style='width:50px;height:50px' src='${pageContext.request.contextPath}/albumPic/"+cellvalue+"'/>";
                    }},
                {name:"count",editable:true},
                {name:"score",editable:true},
                {name:"author",editable:true},
                {name:"broadcast",editable:true},
                {name:"brief",editable:true},
                {name:"publishTime",editable:true,edittype:"date"}],
            pager:"albumPager",
            rowNum:2,
            rowList:[2,5,10],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/album/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,


            subGrid : true,
            subGridRowExpanded : function(subgrid_id, row_id) {
                // we pass two parameters
                // subgrid_id is a id of the div tag created whitin a table data
                // the id of this elemenet is a combination of the "sg_" + id of the row
                // the row_id is the id of the row
                // If we wan to pass additinal parameters to the url we can use a method getRowData(row_id)
                // - which returns associative array in type name-value
                // here we can easy construct the flowing
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                $("#" + subgrid_id).html(
                    "<table id='" + subgrid_table_id
                    + "' class='scroll'></table><div id='"
                    + pager_id + "' class='scroll'></div>");
                jQuery("#" + subgrid_table_id).jqGrid(
                    {
                        styleUI:"Bootstrap",
                        url:"${pageContext.request.contextPath}/chapter/queryByPage?albumId="+row_id,
                        datatype : "json",
                        colNames:["编号","专辑编号","音频名称","音频大小","音频路径","上传时间","操作"],
                        colModel : [
                            {name:"id"},
                            {name:"albumId"},
                            {name:"title",editable:true},
                            {name:"size"},
                            {name:"downPath",editable:true,edittype:"file"},
                            {name:"uploadTime",editable:true,edittype:"date"},
                            {name:"downPath",formatter:function (cellvalue, options, rowObject) {
                                    return "<a class=\"btn btn-primary\" href=\"${pageContext.request.contextPath}/chapter/download?downPath="+cellvalue+"\" role=\"button\">下载</a>";
                                }}
                        ],
                        rowNum : 2,
                        pager : pager_id,
                        rowList:[2,5,10],
                        viewrecords:true,
                        autowidth:true,
                        editurl:"${pageContext.request.contextPath}/chapter/edit?albumId="+row_id,
                        height:"100%",
                        multiselect:true,
                        rownumbers:true,
                    });
                jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                    "#" + pager_id, {},{
                        //修改的部分
                        closeAfterEdit:true,
                        afterSubmit:function(response){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/fileupload",
                                fileElementId:"downPath",
                                data:{"id":response.responseText},
                                type:"post",
                                success:function(){
                                    $("#"+subgrid_table_id).trigger("reloadGrid");
                                }
                            })
                            return "[true]";
                        }
                    },{
                        //添加的部分
                        closeAfterAdd:true,
                        afterSubmit:function(response){
                            $.ajaxFileUpload({
                                url:"${pageContext.request.contextPath}/chapter/fileupload",
                                fileElementId:"downPath",
                                data:{"id":response.responseText},
                                type:"post",
                                success:function(){
                                    $("#"+subgrid_table_id).trigger("reloadGrid");
                                    $("#albumTable").trigger("reloadGrid");
                                }
                            })
                            return "[true]";
                        }
                    });
            },
        }).jqGrid("navGrid","#albumPager",{},{
            //修改的部分
            closeAfterEdit:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/fileupload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#albumTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        },{
            //添加的部分
            closeAfterAdd:true,
            afterSubmit:function(response){
                $.ajaxFileUpload({
                    url:"${pageContext.request.contextPath}/album/fileupload",
                    fileElementId:"cover",
                    data:{"id":response.responseText},
                    type:"post",
                    success:function(){
                        $("#albumTable").trigger("reloadGrid");
                    }
                })
                return "[true]";
            }
        })
    })
</script>
<table id="albumTable"></table>
<div id="albumPager"></div>

