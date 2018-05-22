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
    <title>添加类别</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
    <form class="form-horizontal" role="form" method="post" action="CategoryServlet">
        <input type="hidden" name="method" value="add">
        <div class="form-group">
            <label for="name" class="col-sm-2 control-label">类别名字：</label>
            <div class="col-sm-5">
                <input type="text" class="form-control" id="name" name="name" placeholder="请输入类别">
                <span id="msg" class="help-block"></span>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-5 text-center">
                <button type="button" class="btn btn-default" onclick="submit2ajax()">添加</button>
            </div>
        </div>
    </form>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script>
        function submit2ajax() {
            var $form = $("form").serializeArray();
            $.ajax({
                type: "post",
                url: "CategoryServlet",
                data: splice($form),
                success: function (data) {
                    $("span#msg").text(data);
                    $("#name").val('');
                }
            })
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
    </script>
</body>
</html>
