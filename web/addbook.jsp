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
    <title>添加书籍</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
    <form class="form-horizontal" role="form" method="post" enctype="multipart/form-data" action="BookServlet">
        <%--<input type="hidden" name="method" value="add">--%>
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">书籍名字：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" name="name" placeholder="输入书籍名字">
            </div>
        </div>
        <div class="form-group">
            <label for="price" class="col-sm-2 control-label">书籍价格：</label>
            <div class="col-sm-5">
                <%--onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"--%>
                <input type="text" class="form-control" id="price" name="price" placeholder="输入书籍价格">
            </div>
        </div>
        <div class="form-group">
            <label for="author" class="col-sm-2 control-label">书籍作者：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="author" name="author" placeholder="输入书籍作者">
            </div>
        </div>
        <div class="form-group">
            <label for="date" class="col-sm-2 control-label">出版日期：</label>
            <div class="col-sm-5">
                <input type="date" class="form-control" id="date" name="date">
            </div>
        </div>
        <div class="form-group">
            <label for="category" class="col-sm-2 control-label">所属类别：</label>
            <div class="col-sm-5">
                <select id="category" class="form-control" name="categoryid"> </select>
            </div>
        </div>
        <div class="form-group">
            <label for="status" class="col-sm-2 control-label">状态：</label>
            <div class="col-sm-5">
                <select id="status" class="form-control" name="status">
                    <option value="1">上架</option>
                    <option value="0">下架</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-2"></div>
            <button class="col-sm-1 btn btn-primary" type="button" onclick="addPic()">添加图片</button>
            <div class="col-sm-5" id="pics"></div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-5 text-center">
                <button type="button" onclick="submit2ajax()" class="btn btn-default">添加</button>
                <%--<button type="submit" class="btn btn-default">添加</button>--%>
                <span class="help-block" id="msg"></span>
            </div>
        </div>

    </form>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

    <script>
        $(function () {
            $.ajax({
                type: "get",
                url: "${pageContext.request.contextPath}/CategoryServlet?method=getAll2Json",
                dataType: "json",
                success: function (data) {
                    var $sel = $("select#category");
                    $.each(data, function (index, e) {
                        var opt = $("<option></option>");
                        opt.val(e.id).text(e.name);
                        $sel.append(opt);
                    });
                }
            })
        });

        function submit2ajax() {
            // var $form = $("form").serializeArray();
            var  data = new FormData($("form")[0]);
            $.ajax({
                type: "post",
                url: "BookServlet",
                data: data,
                cache: false,
                processData: false,
                contentType: false,
                success: function (data) {
                    $("span#msg").text(data);
                    clear();
                }

            })
        }

        var i = 0;
        function addPic() {
            var pic = "<input type='file' name='pic'/>是否是封面："
                + "<input type='radio' name='fm' value='" + (i++) + "'/>";
            $("#pics").append(pic);
        }

        function splice(array) {
            var result = [];
            $.each(array, function (index, e) {
                result.push(e.name + "=" + e.value);
                result.push("&");
            });

            result.pop();
            return result.join('').toString();
        }

        function clear() {
            $("form")[0].reset();
            $("#pics").html('');
            i = 0;
        }
    </script>
</body>
</html>
