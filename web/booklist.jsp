<%--
  Created by IntelliJ IDEA.
  User: 冰封承諾Andy
  Date: 2018/5/19
  Time: 9:39
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>图书列表</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
    <%--查询区域--%>
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true"
                       aria-controls="collapseOne">
                        条件查询
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <form class="form-inline" role="form">
                        <div class="form-group">
                            <label for="bookName">书名：</label>
                            <input type="text" class="form-control" id="bookName" placeholder="请输入书名">
                        </div>
                        <div class="form-group">
                            <label for="caid">所在类别：</label>
                            <select class="form-control" id="caid" name="caid"></select>
                        </div>
                        <%--type button 不会自动提交（我们使用异步）--%>
                        <button type="button" class="btn btn-primary" onclick="queryByPara()">搜索</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <button type="button" class="btn btn-success" style="margin-bottom: 15px;" onclick="changeStatus('sj')">上架</button>
    <button type="button" class="btn btn-info" style="margin-bottom: 15px;" onclick="changeStatus('xj')">下架</button>
    <button type="button" class="btn btn-danger" style="margin-bottom: 15px;" onclick="delSelect()">删除选中</button>
    <button type="button" class="btn btn-primary" style="margin-bottom: 15px;" onclick="export2Excel()">导出Excel</button>

    <table class="table table-bordered table-hover table-striped">
        <tr>
            <th>
                <input type="checkbox" id="checkall" onclick="selectAll(this.checked)">
                <label for="checkall">全选</label>
            </th>
            <th>序号</th>
            <th>书籍名</th>
            <th>价格</th>
            <th>作者</th>
            <th>出版日期</th>
            <th>类别</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
    </table>
    <button type="button" class="btn btn-primary" onclick="change('first')">首页</button>
    <button type="button" class="btn btn-primary" onclick="change('pre')">上一页</button>
    <button type="button" class="btn btn-primary" onclick="change('next')">下一页</button>
    <button type="button" class="btn btn-primary" onclick="change('last')">末页</button>

    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form" method="post" enctype="multipart/form-data"
                          action="BookServlet">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">书籍名字：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name" placeholder="输入书籍名字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="price" class="col-sm-2 control-label">书籍价格：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="price" name="price" placeholder="输入书籍价格">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="author" class="col-sm-2 control-label">书籍作者：</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="author" name="author" placeholder="输入书籍作者">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="date" class="col-sm-2 control-label">出版日期：</label>
                            <div class="col-sm-10">
                                <input type="date" class="form-control" id="date" name="date">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="category" class="col-sm-2 control-label">所属类别：</label>
                            <div class="col-sm-10">
                                <select id="category" class="form-control" name="categoryid"> </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status" class="col-sm-2 control-label">状态：</label>
                            <div class="col-sm-10">
                                <select id="status" class="form-control" name="status">
                                    <option value="1">上架</option>
                                    <option value="0">下架</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div clas s="col-sm-2"></div>
                            <input type="file" name="pic" id="pic">
                            <button class="col-sm-2 btn btn-primary" type="button" onclick="addPic()">添加图片</button>
                            <div class="col-sm-10" id="pics"></div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10 text-center">
                                <button type="button" onclick="submit2ajax()" class="btn btn-default">添加</button>
                                <%--<button type="submit" class="btn btn-default">添加</button>--%>
                                <span class="help-block" id="msg"></span>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script src="js/ajaxfileupload.js"></script>

    <script>
        var next = 1;
        var pre = 1;
        var count = 1;
        var currentPage = 1;
        var para_g = "";

        $(function () {
            show(1, para_g);
            loaderCategory();
        });

        function show(page, para) {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/BookServlet?method=getListPage&pageNumber=" + page + para,
                dataType: "json",
                success: function (data) {
                    // 只保留表头
                    $("table tr:not(:first)").remove();
                    var table = $("table");
                    $.each(data.rows, function (index, e) {
                        var tr = $("<tr></tr>");
                        // var num = ((data.pageNumber - 1) * data.pageSize) + (index + 1);
                        var num = data.start + (index + 1);
                        tr.append($("<td><input type=\"checkbox\" value='" + e.id + "'></td>"));
                        tr.append($("<td>" + num + "</td>"));
                        tr.append($("<td>" + e.name + "</td>"));
                        tr.append($("<td>" + e.price + "</td>"));
                        tr.append($("<td>" + e.author + "</td>"));
                        tr.append($("<td>" + formatDate(e.publishdate.time) + "</td>"));
                        tr.append($("<td>" + e.category.name + "</td>"));
                        tr.append($("<td>" + (e.status == 0 ? '下架' : '上架') + "</td>"));
                        btn = $("<td><button type='button' class='btn btn-primary' onclick=\"queryBookInfo(event, '" + e.id + "')\">显示详情</button></td>");
                        tr.append(btn);
                        // btn.attr("onclick", "queryBookInfo('" + e.id + "')");
                        tr.attr("onclick", "trFocus(this)");
                        table.append(tr);
                    });
                    next = data.next;
                    pre = data.pre;
                    count = data.pageCount;
                    currentPage = data.pageNumber;
                    table.append($('<tr><td colspan="9">总记录：' + data.total + '条；共' + count
                        + '页；当前第' + currentPage + '页</td></tr>'));
                    flushBtn();
                }
            })
        }

        function change(flag) {
            switch (flag) {
                case 'first':
                    show(1, para_g);
                    break;
                case 'pre':
                    show(pre, para_g);
                    break;
                case 'next':
                    show(next, para_g);
                    break;
                case 'last':
                    show(count, para_g);
                    break;
                default:
                    show(1, para_g);
            }
        }

        function flushBtn() {
            var btns = $('body>button:contains("页")');
            btns.removeAttr("disabled");
            if (currentPage == 1) {
                btns[0].disabled = true;
                btns[1].disabled = true;
            } else if (currentPage == count) {
                btns[2].disabled = true;
                btns[3].disabled = true;
            }
        }

        function selectAll(flag) {
            if (flag) {
                $(':checkbox').prop('checked', 'checked');
            } else {
                $(':checkbox').removeAttr('checked');
            }
        }

        function delSelect() {
            var para = [];
            var secs = $('table td input:checked');
            if (secs.length == 0) {
                alert('您没选择任何项！');
                return;
            }
            $.each(secs, function (index, e) {
                para.push('id=' + e.value);
                para.push('&');
            });

            para.pop();
            para = para.join('').toString();

            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/BookServlet",
                data: "method=delSome&" + para,
                success: function (data) {
                    show(currentPage);
                    selectAll(false);
                }
            })
        }

        // 改变状态
        function changeStatus(method) {
            var para = [];
            var secs = $('table td input:checked');
            if (secs.length == 0) {
                alert('您没选择任何项！');
                return;
            }
            $.each(secs, function (index, e) {
                para.push('id=' + e.value);
                para.push('&');
            });

            para.pop();
            para = para.join('').toString();

            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/BookServlet",
                data: "method=" + method + "&" + para,
                success: function (data) {
                    show(currentPage);
                    selectAll(false);
                }
            })
        }

        function trFocus(e) {
            var c = $(e).find('input');
            if (c[0].checked) {
                c.removeAttr('checked');
            } else {
                c.prop('checked', 'checked');
            }
        }

        function loaderCategory() {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/CategoryServlet?method=getAll2Json",
                dataType: "json",
                success: function (data) {
                    var $sel = $("select#caid");
                    var $modsel = $("select#category");
                    $.each(data, function (index, e) {
                        var opt1 = $("<option></option>");
                        var opt2 = $("<option></option>");
                        opt1.val(e.id).text(e.name);
                        opt2.val(e.id).text(e.name);
                        $sel.append(opt1);
                        $modsel.append(opt2);
                    });
                }
            })
        }

        function queryByPara() {
            para_g = '';
            var name = $("#bookName").val();
            var caid = $("#caid").val();

            // 组装条件
            if (name != null || name.length !== 0) {
                para_g += "&name=" + name;
            }
            if (caid != null || caid.length !== 0) {
                para_g += "&caid=" + caid;
            }

            show(1, para_g);
        }

        function queryBookInfo(e, bid) {
            // 取消事件冒泡
            if (e != null)
                e.stopPropagation();
            $.ajax({
                type: "post",
                url: "BookServlet",
                data: "method=queryBookById&id=" + bid,
                dataType: "json",
                success: function (data) {
                    bookid = data.id;
                    $("#name").val(data.name);
                    $("#author").val(data.author);
                    $("#price").val(data.price);
                    $("#category").val(data.categoryid);
                    $("#status").val(data.status);
                    $("#date").val(formatDate(data.publishdate.time));
                    var bps = "";
                    $.each(data.bps, function (index, e) {
                        bps += "<div class='col-sm-6 col-md-4'>" +
                            "  <div class='thumbnail'>" +
                            "    <img src='${pageContext.request.contextPath}" + e.savepath + "'>" +
                            "    <div class='caption'>" +
                            "      <button type='button' class='btn btn-" + (e.fm === '1' ? 'success' : 'primary') + "'onclick=\"setFM('" + e.id + "')\">设置封面</button>" +
                            "      <button type='button' class='btn btn-danger' onclick=\"delPicById('" + e.id + "')\">删除</button>" +
                            "    </div>" +
                            "  </div>" +
                            "</div>";
                    });
                    $("#pics").html(bps);

                    $("#myModal").modal("show");
                }
            })
        }

        var bookid = "";

        function delPicById(id) {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/BookpicServlet",
                data: "method=del&id=" + id,
                success: function (data) {
                    console.log(data);
                    if (data == "suc") {
                        queryBookInfo(null, bookid);
                    }
                }
            })
        }

        function addPic() {
            $.ajaxFileUpload({
                type: "post",
                url: "${pageContext.request.contextPath}/BookpicServlet",
                // 上传控件的 ID
                fileElementId: "pic",
                data: {"bookid": bookid},
                dataType: "json",
                success: function (data) {
                    if (data.flag == 'suc') {
                        queryBookInfo(null, bookid);
                    }
                }
            })
        }

        function setFM(id) {
            $.post("${pageContext.request.contextPath}/BookpicServlet",
                "method=setFM&id=" + id + "&bookid=" + bookid, function (data) {
                    if (data.flag == 'suc') {
                        queryBookInfo(null, bookid);
                    }
                }, "json");
        }

        function export2Excel() {
            window.location.href = "BookServlet?method=export" + para_g;
        }

        function formatDate(nowStr) {
            var now = new Date(nowStr);
            var year = now.getFullYear();
            var month = ("0" + (now.getMonth() + 1)).slice(-2);
            var date = ("0" + now.getDate()).slice(-2);
            return year + "-" + month + "-" + date;
        }
    </script>
</body>
</html>
