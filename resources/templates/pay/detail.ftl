<html>
    <head>
        <meta charset="utf-8">
        <title>卖家商品详情页</title>
        <#include "../common/header.ftl">
    </head>
    <body>
    <div id="page-content-wrapper">
    <#--订单总数据-->
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>订单ID</th>
                            <th>订单总金额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDto.orderId}</td>
                            <td>${orderDto.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品Id</th>
                            <th>商品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                            <th>总额</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDto.orderMasterList as orderDetail>
                        <tr>
                            <td>${orderDetail.productId}</td>
                            <td>${orderDetail.productName}</td>
                            <td>${orderDetail.productPrice}</td>
                            <td>${orderDetail.productQuantity}</td>
                            <td>${orderDetail.productQuantity * orderDetail.productPrice}</td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

        <#--按钮界面-->
            <div class="col-md-12 column">
            <#if orderDto.orderStatus == 0>
                <a href="/alipay/goAlipay?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-primary">前往支付</a>
                <#--<a href="/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>-->
            </#if>
            </div>
            </div>
        </div>
    </body>
</html>