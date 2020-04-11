<html>
    <head>
        <meta charset="utf-8">
    </head>
    <body>
    <form id="payForm" action="<%=request.getContextPath() %>/alipay/goAlipay.action" method="post">
    	<input type="hidden" name="orderId" value="${orderDto.orderId }" />
        <table>
        	<tr>
        		<td>
        			订单编号: ${orderDto.orderId}
        		</td>
        	</tr>
        		<#--<td>
        			产品名称: ${p.name }
        		</td>-->
        	<tr>
        	</tr>
        		<#--<td>
        			订单价格: ${orderDto.orderAmount }
        		</td>-->
        	<tr>
        	</tr>
        		<#--<td>
        			购买个数：${order.buyCounts }
        		</td>-->
        	</tr>
        	</tr>
        		<td>
        			<input type="submit" value="前往支付宝进行支付">

        		</td>
        	</tr>
        </table>
    </form>
    
    	
        <input type="hidden" id="hdnContextPath" name="hdnContextPath" value="<%=request.getContextPath() %>"/>
    </body>
    
</html>

