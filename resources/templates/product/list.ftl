<html>
    <head>
        <meta charset="utf-8">
        <title>卖家商品列表</title>
        <#include "../common/header.ftl">
    </head>
    <body>

    <div id="wrapper" class="toggled">

        <#--侧边栏-->
            <#include "../common/nav.ftl">
        <#--内容区域-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th>商品id</th>
                                    <th>名称</th>
                                    <th>图片</th>
                                    <th>单价</th>
                                    <th>库存</th>
                                    <th>描述</th>
                                    <th>类目</th>
                                    <th>创建时间</th>
                                    <th>修改时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                        <#list productInfoPage.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img height="50px" width="50px" src="${productInfo.productIcon}"></td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td><a href="/seller/product/index?productId=${productInfo.productId}">修改</td>
                            <td>
                                <#if productInfo.getProductStatusEnum().message == "商品已上架">
                                    <a href="/seller/product/offSale?productId=${productInfo.productId}">下架</a>
                                <#else>
                                    <a href="/seller/product/onSale?productId=${productInfo.productId}">上架</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                                </tbody>
                            </table>
                        </div>

                    <#--分页-->
                        <div class="col-md-12 column">
                            <ul class="pagination pull-right">
                        <#if indexPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/seller/product/list?page=${indexPage - 1}&size=${sizePage}">上一页</a></li>
                        </#if>
                            <#--从第0页开始-->
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
                        </div>
                    </div>
                </div>
            </div>
    </div>
    </body>
</html>