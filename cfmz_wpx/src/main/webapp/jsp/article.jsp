<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>

<script charset="utf-8" src="../kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="../kindeditor/lang/zh-CN.js"></script>

<div class="page-header">
    <h1>文章管理</h1>
</div>
<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
    文章上传
</button>
<script>
    $(function(){
        $("#myModal").modal({ backdrop:false, keyboard:false, show:false });


       edit= KindEditor.create('#editor_id',{
            width : '700px',
            uploadJson:'${pageContext.request.contextPath}/article/upload',
            fileManagerJson:'${pageContext.request.contextPath}/article/showAll',
            allowFileManager:true,
            filePostName:'file',
            afterBlur:function(){
                this.sync();
            }
        });
        $("#articlelTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${pageContext.request.contextPath}/article/queryByPage",
            datatype:"json",
            colNames:["编号","上师编号","文章标题","文章内容","发表时间","操作"],
            colModel:[
                {name:"id"},
                {name:"guruId",editable:true},
                {name:"title",editable:true},
                {name:"content",editable:true},
                {name:"publishTime",editable:true,edittype:"date"},
                {name:"xx ",formatter:function (cellvalue, options, rowObject) {
                        return "<button type='button' class='btn btn-primary btn-lg' data-toggle='modal' data-target='#myModal' onclick='queryArticle(\""+rowObject.id+"\")'>预览</button>"
                    }}
                ],

            pager:"articlePager",
            rowNum:2,
            rowList:[2,5,10],
            viewrecords:true,
            autowidth:true,
            editurl:"${pageContext.request.contextPath}/article/edit",
            height:"100%",
            multiselect:true,
            rownumbers:true,
        }).jqGrid("navGrid","#articlePager",{add:false},{
            //修改的部分
            closeAfterEdit:true,
        },{
            //添加的部分
            closeAfterAdd:true,
        })
    })
    function addArticle(){
        $.ajax({
            url:"${pageContext.request.contextPath}/article/addArticle",
            type:"post",
            datatype:"json",
            data:$("#articleForm").serialize(),
            success:function(){
                $("#articlelTable").trigger("reloadGrid");
            }
        })
    }

    $("#saveBtn").click(function () {
        // 保存成功后，关model
        $("#myModal").modal("hide");
    })
    var edit;
    function queryArticle(id) {
        $("#articleForm tr:not(:first)").empty();
        $.post("${pageContext.request.contextPath}/article/queryOneById",{id:id},function (arr) {
            $("#title").val(arr.title);
            edit.html(arr.content)
            $("#publishTime").val(arr.publishTime);
        },"json")
    }
</script>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:800px">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">文章上传</h4>
            </div>
            <form action="javascript:void(0);" id="articleForm">
                <div class="modal-body">
                    文章标题：<input type="text" id="title" name="title"><br/>
                    文章内容：<textarea id="editor_id" name="content" style="width:700px;height:300px;">
                        &lt;strong&gt;HTML内容&lt;/strong&gt;
                    </textarea>
                    <br/>
                    发布时间：<input type="date" id="publishTime" name="publishTime">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="saveBtn" onclick="addArticle()">上传</button>
                </div>
            </form>
        </div>
    </div>
</div>



<table id="articlelTable"></table>
<div id="articlePager"></div>

