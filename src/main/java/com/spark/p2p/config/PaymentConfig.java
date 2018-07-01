package com.spark.p2p.config;


/**
 * 连连支付配置
 * @author yanqizheng
 *
 */
public class PaymentConfig {
	// 银通公钥
	public String YT_PUB_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB";
	// 商户私钥(已更改成秒一花)
	public String TRADER_PRI_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMF9JmUmZrtbR7+YitocHMkucGXuZRRQ1DDM7huq83+u2rfLZYLLlJgiD+QDfd+Iwaev6yLRo3gUIwCeuhpgJJ8AJ4HekcxfxKgpqVjUe/MH7v2lQEN69Hia7kilbr4stxq8PBYqyjyV0pPBN7qumWCUa70P8lFf1Sk05niPtvArAgMBAAECgYAgPWIwaH2lqkgCPMCmJxkEzKAd2+Mrx2wT1ZG3OnzoQZJgHZwEdzeDQemz7IEExhy+fCmJ27SJRZGvgMMxseNn8EOzQ7RsVaRustuaVhFXcLIhTp0OMOcNh9ftB6oiB/rGvyqhV1CPKLgiitlZ14Q6McTv7+Ed1oFI2N+n6rCRAQJBAPrQPPrAODLBSbw8aLO1C0dfmZitDee5jd+ly/difcEzV1p5OZl706IhU4yn+8uvjTlQyrOAYlRMOuv2reCUaXsCQQDFfXIaNtpFAEQwC0rcbJEO6t2/uDsP0z2nFnxQe20xo0z+tGieY9NyrWxla3B4zsdmYNx+Lbh6ZR/vFyhm6x0RAkEAvqjrogXa8qJCRgh54xoeD8yZexkaN+uy8K8dRNonFGXN4qXCZm1d7KJ+266a1zSFG2vSGu/eT4x/Qf6MIOtQ3wJADmauamDzm7KARmycYeR803SnBPL/Q5Eo3sVR/WByvvrkg3JOarZm1eb1j5O9GOIjQkDv8zeMS0iSgpHSvWaJsQJBAJqM7+duq7PdsDS7GcwHOm+NWmZcrVXXF8xm44fGYhrqVhXGyebJCcK6vUqWhpuxB3XJx0G6awqIHZfRpOz9hOE=";
	// MD5 KEY
	public String MD5_KEY = "71uK4uRDm7mtbrdYYSSA0P281kuRzIL3";
	// 接收异步通知地址（122.152.202.30:8080是秒一花的账号）
	public String AUTH_NOTIFY_URL = "http://122.152.202.30:8080/llpay/auth/notify.html";
	// 授权认证返回地址
	public String AUTH_URL_RETURN = "http://122.152.202.30:8080/llpay/auth/result.html";
	//扣款通知地址
	public String REPAYMENT_NOTIFY_URL = "http://122.152.202.30:8080/llpay/repayment/notify.do";
	//实时付款通知地址
	public String PAY_NOTIFY_URL = "http://122.152.202.30:8080/llpay/pay/notify.do";
	
	// 商户编号（已换成秒一花）
	public String OID_PARTNER = "201706021001791950";
	// 签名方式 RSA或MD5
	public String SIGN_TYPE = "RSA";
	// 接口版本号，固定1.0
	public String VERSION = "1.0";
	// 业务类型，连连支付根据商户业务为商户开设的业务类型； （101001：虚拟商品销售、109001：实物商品销售、108001：外部账户充值）

	public String BUSI_PARTNER = "101001";
	
	public String AUTH_GATEWAY = "https://wap.lianlianpay.com/signApply.htm";
	
	public String APPLY_GATEWAY = "https://repaymentapi.lianlianpay.com/agreenoauthapply.htm";
	
	public String REPAYMENT_GATEWAY = "https://repaymentapi.lianlianpay.com/bankcardrepayment.htm";
	
	public String PLAN_CHANGE_GATEWAY = "https://repaymentapi.lianlianpay.com/repaymentplanchange.htm";
	
	public String UNBIND_GATEWAY = "https: //traderapi.lianlianpay.com/bankcardunbind.htm";
	
	public String PAY_GATEWAY = "https://instantpay.lianlianpay.com/paymentapi/payment.htm";
	
	public String CONFIRM_PAY_GATEWAY = "https://instantpay.lianlianpay.com/paymentapi/confirmPayment.htm";
	
	public String QUERY_PAY_GATEWAY = "https://instantpay.lianlianpay.com/paymentapi/queryPayment.htm";
	
	public String contact_way = "400-600-5200";
}

