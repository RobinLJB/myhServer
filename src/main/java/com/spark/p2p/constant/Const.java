package com.spark.p2p.constant;

/**
 * 系统静态常量
 * 
 * @author yanqizheng
 *
 */
public class Const {
	// SESSION常量
	public static String SESSION_MEMBER = "MEMBER";
	public static String SESSION_ADMIN = "ADMIN_MEMBER";
	public static String SESSION_AUTH = "AUTH_TOKEN";
	// 通知类型
	public static String NOTICE_REGISTER = "MEMBER_REGISTER";
	public static String NOTICE_RECHARGE = "FINANCE_RECHARGE";
	public static String NOTICE_WITHDRAW = "FINANCE_WITHDRAW";
	public static String NOTICE_INVEST = "FINANCE_INVEST";
	public static String NOTICE_CANCELPROJECT = "FINANCE_CANCELPROJECT";

	public static int GATEWAY_LOG_REGISTER = 1;
	public static int GATEWAY_LOG_RECHARGE = 2;
	public static int GATEWAY_LOG_WITHDRAW = 3;
	public static int GATEWAY_LOG_INVEST = 4;
	public static int GATEWAY_LOG_REPAY = 5;
	public static int GATEWAY_LOG_UNINVEST = 6;
	public static int GATEWAY_LOG_REGPROJECT = 7;
	public static int GATEWAY_LOG_REWARD = 8;

	public static int TERMINAL_PC = 1;
	public static int TERMINAL_WAP = 2;
	public static int TERMINAL_IOS = 3;
	public static int TERMINAL_ANDROID = 4;

	public static int SCORE_LOGIN = 1;
	public static int SCORE_REGISTER = 2;
	public static int SCORE_INVEST = 3;

	public static int PLATFORM_IOS = 1;
	public static int PLATFORM_ANDROID = 2;
	public static int PLATFORM_WINDOWS = 2;

	public static String SINA_ACCT_BASIC = "BASIC";
	public static String SINA_ACCT_SAVING_POT = "SAVING_POT";
	public static String SINA_ACCT_ENSURE = "ENSURE";
	public static String SINA_ACCT_RESERVE = "RESERVE";

	public static String SINA_FUNDCODE_HTF = "000330";
	public static String SINA_FUNDCODE_NF = "000719";

	// 10：红包奖励，11：首投奖励，12：满标奖，13：续投奖；
	// 20：推荐人奖励，21：被推荐人奖励；30：充值奖励，31：手动奖励,101:提现费用
	public static int REWARD_INTEREST = 1;
	public static int REWARD_REDPACKET = 10;
	public static int REWARD_FIRST_INVEST = 11;
	public static int REWARD_FULL_INVEST = 12;
	public static int REWARD_REFEREE = 20;
	public static int REWARD_RECHARGE = 30;
	public static int REWARD_MANUAL = 31;
	public static int FEE_WITHDRAW = 101;
	
	public static int COUPON_REDPACKET = 1;
	public static int COUPON_CASH_TICKET = 2;
	public static int COUPON_ENJOIN_BALANCE = 3;
	public static int COUPON_INTEREST_TICKET = 4;
	
	public static int COUPON_MODE_REG = 1;
	public static int COUPON_MODE_INVEST = 2;
	public static int COUPON_MODE_ON_TIME = 3 ;
	public static int COUPON_MODE_ON_SCORE = 4;
	public static int COUPON_MODE_ON_MANUAL = 10;
	
	public static int COUPON_STATUS_UNUSED = 1;
	public static int COUPON_STATUS_USED = 2;
}
