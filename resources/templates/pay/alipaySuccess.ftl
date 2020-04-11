<html>
    <head>
        <meta charset="utf-8">
        <title>支付成功页</title>
	<#include "../common/header.ftl">
	</head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <h3 class="text-center text-success">
                    支付成功！
                </h3>
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="inputEmail3" class="col-sm-2 control-label">订单号</label>
                        <div class="col-sm-10">
                            <label for="inputEmail3" class="col-sm-2 control-label">${out_trade_no}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">支付宝流水号</label>
                        <div class="col-sm-10">
                            <label for="inputPassword3" class="col-sm-2 control-label">${trade_no}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">订单金额</label>
                        <div class="col-sm-10">
                            <label for="inputPassword3" class="col-sm-2 control-label">${total_amount}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword3" class="col-sm-2 control-label">订单详情</label>
                        <div class="col-sm-10">
                            <#list product as product>
                             <label for="inputPassword3" class="col-sm-2 control-label">购买了${product.productQuantity}件${product.productName}</label><br/>
                            </#list>
                        </div>
                    </div>
                    <a href="/buyer/product/listModel"><button type="button" class="btn btn-success btn-default btn-block">返回</button></a>
                </form>
            </div>
        </div>
    </div>
    </body>
    
</html>


