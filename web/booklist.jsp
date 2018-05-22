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
    <button type="button" class="btn btn-success" style="margin-bottom: 15px;" onclick="changeStatus('sj')">上架</button>
    <button type="button" class="btn btn-info" style="margin-bottom: 15px;" onclick="changeStatus('xj')">下架</button>
    <button type="button" class="btn btn-danger" style="margin-bottom: 15px;" onclick="delSelect()">删除选中</button>
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


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

    <script>
        var next = 1;
        var pre = 1;
        var count = 1;
        var currentPage = 1;

        $(function () {
            show(1);
        });

        function show(page) {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/BookServlet?method=getListPage&pageNumber=" + page,
                dataType: "json",
                success: function (data) {
                    // 只保留表头
                    $("table tr:not(:first)").remove();
                    var table = $("table");
                    $.each(data.rows, function (index, e) {
                        var tr = $("<tr></tr>");
                        var num = ((data.pageNumber - 1) * data.pageSize) + (index + 1);
                        tr.append($("<td><input type=\"checkbox\" value='" + e.id + "'></td>"));
                        tr.append($("<td>" + num + "</td>"));
                        tr.append($("<td>" + e.name + "</td>"));
                        tr.append($("<td>" + e.price + "</td>"));
                        tr.append($("<td>" + e.author + "</td>"));
                        tr.append($("<td>" + formatDate(e.publishdate.time) + "</td>"));
                        tr.append($("<td>" + e.category.name + "</td>"));
                        tr.append($("<td>" + (e.status == 0 ? '下架' : '上架') + "</td>"));
                        btn = $('<td><button type="button" class="btn btn-primary">查看封面</button></td>');
                        tr.append(btn);
                        btn.attr("onclick","showPic('" + e.id + "')");
                        tr.attr("onclick","trFocus(this)");
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
                    show(1);
                    break;
                case 'pre':
                    show(pre);
                    break;
                case 'next':
                    show(next);
                    break;
                case 'last':
                    show(count);
                    break;
                default:
                    show(1);
            }
        }

        function flushBtn() {
            var btns = $('body>button:contains("页")');
            btns.removeAttr("disabled");
            if (currentPage == 1) {
                btns[0].disabled = true;
                btns[1].disabled = true;
            }else if (currentPage == count) {
                btns[2].disabled = true;
                btns[3].disabled = true;
            }
        }

        function selectAll(flag) {
            if (flag){
                $(':checkbox').prop('checked','checked');
            }else {
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
            if (c[0].checked){
                c.removeAttr('checked');
            } else {
                c.prop('checked','checked');
            }
        }

        function del(id) {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/BookServlet",
                data: "method=del&id=" + id,
                success: function (data) {
                    console.log(data);
                    show(1);
                }
            })
        }

        function showPic(id) {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/BookpicServlet",
                dataType: 'json',
                data: "method=get&id=" + id,
                success: function (data) {
                    console.log(data);
                    window.open('http://localhost:8080' + data[0].savepath);
                }
            });
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
