<html>
    <head>
        <meta charset="utf-8">
        <title>卖家订单列表</title>
        <#include "../common/header.ftl">
    </head>
    <body>
    <div id="wrapper" class="toggled">
        <#--侧边栏-->
            <#include "../common/nav.ftl">
        <#--内容区域-->
            <div id="page-content-wrapper">
                <div class="container">
                    <div class="row clearfix">
                        <div class="col-md-12 column">
                            <table class="table table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>订单id</th>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>地址</th>
                                    <th>金额</th>
                                    <th>订单状态</th>
                                    <th>支付状态</th>
                                    <th>创建时间</th>
                                    <th colspan="2">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getOrderMasteEnum().message}</td>
                            <td>${orderDTO.getPayStatusEnum().getMessage()}</td>
                            <td>${orderDTO.createTime}</td>
                            <td><a href="/seller/order/detail?orderId=${orderDTO.orderId}">详情</td>
                            <td>
                                <#if orderDTO.getOrderMasteEnum().message == "新订单"&& orderDTO.getPayStatusEnum().getMessage()=="未支付">
                                    <a href="/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                            <li><a href="/seller/order/list?page=${indexPage - 1}&size=${sizePage}">上一页</a></li>
                        </#if>
                            <#--从第0页开始-->
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if indexPage==index>
                            <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                            <li><a href="/seller/order/list?page=${index}&size=${sizePage}">${index}</a></li>
                            </#if>
                        </#list>
                        <#if indexPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/seller/order/list?page=${indexPage + 1}&size=${sizePage}">下一页</a></li>
                        </#if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
    </div>
    <#--websocket接收消息弹窗-->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    您有新的订单
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" onclick="location.reload()" class="btn btn-primary">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>

    <#--播放音乐-->
    <audio id="notice">
        <source src="/mp3/song.mp3" type="audio/mpeg" />
    </audio>

    <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script>
        var websocket = null;
        if('WebSocket' in window){
            /*websocket = new WebSocket('ws://localhost:8089/websocket');*/
            websocket = new WebSocket('ws://112.74.186.128:8089/websocket')
        }else{
            alert("浏览器不支持");
        }

        websocket.onopen = function(event){
            console.log('打开连接');
        }

        websocket.onclose = function(e){
            console.log('关闭连接');
        }

        websocket.onmessage = function(e){
            console.log('接收消息：'+ e.data);
            $('#myModal').modal('show');
            document.getElementById('notice').play();
        }

        websocket.onerror = function (e) {
            alert('通信错误');
        }

        window.onbeforeunload = function(e){
            websocket.onclose();
        }
        
        
        
    </script>
    </body>
</html>