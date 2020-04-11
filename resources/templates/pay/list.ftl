<html>
    <head>
        <meta charset="utf-8">
        <title>买家商品列表</title>
        <#include "../common/header.ftl">
    </head>
    <body>
    <h3 class="text-success text-center">
        买家商品列表页面
    </h3>
        <#--内容区域-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <button type="button" class="btn btn-default btn-info pull-right" onclick="sunmitForm()">点击下单</button>
                            <button type="button" class="btn btn-default btn-info " onclick="lookFor()">查看订单</button>
                        </div>
                        <div class="col-md-12 column">

                            <table class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>商品id</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                        <#list productInfoPage as productInfo>
                          <#list productInfo.foods as foods>
                        <tr>
                            <td>${foods.productId}</td>
                            <td>${foods.productName}</td>
                            <td><img height="50px" width="50px" src="${foods.productIcon}"></td>
                            <td>${foods.productPrice}</td>
                            <td>${foods.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td id="${foods.productId}" onclick="cartList(this)">购买</td>
                        </tr>
                          </#list>
                        </#list>
                                </tbody>
                            </table>
                        </div>

                    <#--分页-->
                       <#-- <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                        <#if indexPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/seller/product/list?page=${indexPage - 1}&size=${sizePage}">上一页</a></li>
                        </#if>
                            &lt;#&ndash;从第0页开始&ndash;&gt;
                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if indexPage==index>
                            <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                            <li><a href="/seller/product/list?page=${index}&size=${sizePage}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if indexPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/seller/product/list?page=${indexPage + 1}&size=${sizePage}">下一页</a></li>
                        </#if>
                            </ul>
                        </div>-->
                    </div>
                </div>
            </div>
    </body>
<script>
    var list = [];
    function cartList(obj){
        console.log(obj.id)
        var amount=prompt("请输入购买数量");
        var productId =obj.id;
        var obj = {
            'productId':productId,
            'productQuantity':amount
        }
        this.list.push(obj)
    }

    function lookFor(){
        var message = '';
        var first = "您购买了编号为";
        var end = "件";
        for(var i = 0;i<list.length;i++){
            message =  first + list[i].productId + "，共"+ list[i].productQuantity + end  + '\n' + message;
        }
        alert(message);
    }

    function sunmitForm(){
        $.ajax({
            type:"POST",
            contextType:"application/x-www-form-urlencoded",
            url:"/buyer/order/createPay",
            /* 跨域 */
            xhrFields:{withCredentials: true},
            data:{
                'list':JSON.stringify(list)
            },
            success: function (data) {
                console.log(data.data)
                if(data.msg == "ok") {
                    window.location.href = "/buyer/order/detail?orderId=" + data.data.orderId;
                }
            },
            error:function(data){
                console.log(data)
            }

        })
    }
</script>
</html>