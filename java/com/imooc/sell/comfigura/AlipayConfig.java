package com.imooc.sell.comfigura;

public class AlipayConfig {

	public static String app_id = "2016101900721017";//�ں�̨��ȡ���������ã�
	
	public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCk38rHZc3zuNLhEG2jguqdierZeH9isZ2zQUSi3X4tjWhTpzX6mz0VWElzXf/1w4sS1jpTkE0VAeyAi6tOTtORbBVzKFDkz4CS9nYq301issRUkhjwO4evUr7C1GDjctvUDS+yYyqf06WYcT2MxBRCQvT/3gitxarydWOdZTpdZMnSArskWi0q+mAuXwnl8YSYFuZhmbDHjU7OBsF7JzaTA3Ys3me1vx2PoIDUzO51lDEJu6mVPh9z4a0TwJ879snJ5FuWz037vWzOq8horsRrJiKD4slB5qjS//8s40WKQUd5Ha0YbuvhuO7c1ImKaaRotH7xipjhQGnwi3hwhH9ZAgMBAAECggEAGZ4oPNlFqfPy2EOcNYbW+4l9czL04Bnm/f5LMt8lhuBs8GqPyZOrZxCjwQg/CCnNBy1APNG0GGqHG+YXxQAcgYEAyDbgct0HBk7DR6AQvyA1v0pAE7u8NnKt70nekBi9rXIrEhW0fsb8+PXHcYLZ/v22YbdXM+D90dSJmOAstGv/0XPRxsuuP1nFP1kU+F385idJg+Zb2HI1jRPX3YGQCTBjYV65B3sDcIBNy342IsGlnGhy7SReQnV33K8pAwQGsvVTizSJAJD91Zx2wmk1ucIXB/pQ7fR7Vb2K9D1zVPZFdlD8CqLf9ao91O33ryJ2vmeh45UfLwTLS2wixrsFfQKBgQDQ6OROOxg23xR7gqwty/fVkPn8p5Yah2ym9PSBX/jvSyims5Dca6UUbrqH4qOYlpEIuMeLLLaJavQnf/nxZwNz8va2CGv8tNivQf5hT9NKC2iKkkFCMrUnkxBYDI/xqUNOVDYGx2O13/K+bb0EhTC7xWerZOTxpXRepTFBGtUJiwKBgQDKCdXD56//n2MmiMvQ09YA4T9c8DGZcrueSZzmC888YE4jXzR+o5LcNzQzU3PvWe1NOGPuzbXiPQZNPBzQ7DqqG+liq6yLf5wAIUwTorm8nlf7sovPGoVqKmtfyNp9oD3X2BdUxauJ3DXF3If0g3I7GaZEUl9XvNDWhSnXh55PKwKBgQDEhPCPrwN+Sqfn7+qsQh1nZ7PiOnv3hMFu5StEsNVptVgOSQjScOifqfIKdebTWYAhpkD/6RXlexkmNzsWHYOgBUSTxKRg2najlv8G56KpGsZC5IZDEZRcH/nV+itGd6V6/7i688IWiw68mFqg7/ICERcOoiDEOJKZnegRiH4zNQKBgB2wAEc/Hi9znkAlTb2rwwByGLtxV/hyUXta2cE77wGyLQkpji8lngjZ28PG+XwsPKBiLt0CQT2zNd/Xe2x/qcchPtQO4dXFtIiYW/Nur9g1dDw56i1Oelg1T33Np71Z9Hxo9lSwjxo8z3H5i8i0H+7rP/E+IWBlEFLVUmanOO3tAoGBAJA5ZBqGK5YsBaICEZFWZ76JAw7hnVE53YNuOaA/l/lNdwb6sl1nYbBwX6jYbrJOuWa3oND5mX2c5ulS8YOyGV4tgGbFlWE63eTgsTSCwLyYttbsPvZV/IovzS9dJTKPO+45TOhYjrt5xMJ+glnZ/ECA8tPvK7W5mfMYP4nbmVKF";//�̳̲鿴��ȡ��ʽ���������ã�

	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiQFpN0zW7BJXxJRxNi4Da+o44NKX6It1H20CNIhZAww4BsNXHBnYglLELNW75h0xP8//QCrKvHayW4ukcYhhEaaX9MTi7N7jvbLg8dibc88GQgO/tghmogdDQj0pO4MVciZqWLrBO8VKsfVPEDBeUrKIqAjG94J9bM1/zwpcRtOI5Lmed7bBhR+PVs7T04LqZUdHGGW14RAdybiId+iJCuzo4qufZMX5Uc4+6pyxh1G9MSbfuwCtLTQCwWAuGNeRw22PYCEQVd5dPtLPhfzfFvqRCEXJ5tTWpFihi8E0htmu4WOjvRg6lCsaVFJuT6adCTuDABzHoJhRzLGwBGujRQIDAQAB";//�̳̲鿴��ȡ��ʽ���������ã�
	
	public static String notify_url = "http://112.74.186.128:8089/alipay/alipayNotifyNotice.action";
	
	public static String return_url = "http://112.74.186.128:8089/alipay/alipayReturnNotice.action";
	
	public static String sign_type = "RSA2";
	
	public static String charset = "utf-8";
	
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//ע�⣺ɳ����Ի�������ʽ����Ϊ��https://openapi.alipay.com/gateway.do
}
