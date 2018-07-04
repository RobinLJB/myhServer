package com.spark.p2p.controller.front;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.service.LimuAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.config.AppSetting;
import com.spark.p2p.job.BorrowDailyCheck;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.admin.CMSService;
import com.spark.p2p.util.AESDecode;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.FileUtil;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.sql.model.Model;

@Controller
public class IndexController extends FrontController {
	
	public static final Log log = LogFactory.getLog(IndexController.class);
	
    @Autowired
    private CMSService cmsService;
    @Autowired
    private IndexService indexService;

    @Autowired
    private LimuAuthService authService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BorrowService borrowService;


    /**
     * 主页
     * @return
     * @throws Exception
     */
    @RequestMapping({"/index"})
    public String index() throws Exception {
        //log.info( this.memberService.findMemberIdentyByMid(651).getCardImgurl() ) ;
        //new BorrowDailyCheck().statisticTask();
        //this.borrowService.repayBorrowSuccess("56220170824145143063", "");

        return view("index");
    }

    @RequestMapping({"/get"})
    public String get1() throws Exception {
        String token = request("token");
        MessageResult res = authService.roundRobin(token, 1);
        log.info(res);
        return view("index");
    }

    @RequestMapping({"/send"})
    public String send1() throws Exception {
        String token = request("token");
        String code = request("code");
        String json = authService.sendLimuMobileCode(token, code);
        log.info(json);
        return view("index");
    }


    /**
     * 支付宝回调地址
     *
     * @return
     * @throws Exception
     */
    @RequestMapping({"/notifys"})
    public String notifys() throws Exception {

        return view("notifys");
    }

    /**
     * 回调都注册页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping({"/regesister"})
    public String regesister() throws Exception {

        return view("regesister");
    }


    /* 首页项目筛选 */
    @RequestMapping({"/creativelist"})
    public @ResponseBody
    MessageResult creativelist() throws Exception {
        MessageResult mr = new MessageResult();
        mr.setData(new Model("db_nation").where("parent=1 ").select());

        return mr;
    }

    @RequestMapping({"/citylist"})
    public @ResponseBody
    MessageResult citylist() throws Exception {
        int id = requestInt("id");
        MessageResult mr = new MessageResult();
        mr.setData(new Model("db_nation").where("parent=? ", id).select());

        return mr;
    }

    /*静态借款详情*/
    @RequestMapping(value = "borrowDetail", method = RequestMethod.GET)
    public String borrowDetail() throws Exception {
        return view("borrow-detail");
    }


    /*静态认证中心（身份信息）*/
    @RequestMapping(value = "identification", method = RequestMethod.GET)
    public String identification() throws Exception {
        return view("identity-identification");
    }

	/*静态第一次添加银行卡*//*
	@RequestMapping(value = "newBankcard", method = RequestMethod.GET)
	public String newBankcard() throws Exception {
		return view("new-bankcard");
	}*/

//	/*静态常见问题*/
//	@RequestMapping(value = "moreQuestion", method = RequestMethod.GET)
//	public String moreQuestion() throws Exception {
//		return view("more-question");
//	}

    /*静态常见问题详情*/
    @RequestMapping(value = "answer", method = RequestMethod.GET)
    public String answer() throws Exception {
        return view("answer");
    }

//	/*静态邀请好友*/
//	@RequestMapping(value = "invitation", method = RequestMethod.GET)
//	public String invitation() throws Exception {
//		return view("invitation");
//	}
//	/*邀请好友并发送给朋友*/
//	@RequestMapping(value = "customerWithdraw", method = RequestMethod.GET)
//	public String customerWithdraw() throws Exception {
//		return view("customer-withdraw");
//	}
//	
//	/*客服服务（联系客服）*/
//	@RequestMapping(value = "customerService", method = RequestMethod.GET)
//	public String customerService1() throws Exception {
//		return view("customer-service");
//	}

//	/*静态平台反馈*/
//	@RequestMapping(value = "feedback", method = RequestMethod.GET)
//	public String feedback() throws Exception {
//		return view("feedback");
//	}
//	
//	/*静态常见问题*/
//	@RequestMapping(value = "doubt", method = RequestMethod.GET)
//	public String doubt() throws Exception {
//		return view("doubt");
//	}

    /*静态还款/续期*/
    @RequestMapping(value = "refund", method = RequestMethod.GET)
    public String refund() throws Exception {
        return view("refund");
    }

    /*静态认证中心*/
    @RequestMapping(value = "attestation", method = RequestMethod.GET)
    public String attestation() throws Exception {
        return view("attestation");
    }


    /*静态认证中心（个人信息）*/
    @RequestMapping(value = "personal", method = RequestMethod.GET)
    public String personal() throws Exception {
        return view("personal-info");
    }

    /*静态认证中心（手持身份证）*/
    @RequestMapping(value = "identity", method = RequestMethod.GET)
    public String identity() throws Exception {
        return view("identity");
    }


    /*静态认证中心（工作信息）*/
    @RequestMapping(value = "work", method = RequestMethod.GET)
    public String work() throws Exception {
        return view("work-info");
    }

    /*静态认证中心（手机认证）*/
    @RequestMapping(value = "mobile", method = RequestMethod.GET)
    public String mobile() throws Exception {
        return view("mobile");
    }

    /*静态认证中心（手机认证成功）*/
    @RequestMapping(value = "mobileSucceed", method = RequestMethod.GET)
    public String mobilesucceed() throws Exception {
        return view("mobile-succeed");
    }

    /*静态申请贷款*/
    @RequestMapping(value = "applyLoan", method = RequestMethod.GET)
    public String applyloan() throws Exception {
        return view("apply-loan");
    }
	
	/*静态绑定银行卡*//*
	@RequestMapping(value = "bankcard", method = RequestMethod.GET)
	public String bankcard() throws Exception {
		return view("bound-bankcard");
	}*/

	
	/*静态添加银行卡*//*
	@RequestMapping(value = "addBankcard", method = RequestMethod.GET)
	public String addbankcard() throws Exception {
		return view("add-bankcard");
	}*/


    /*静态消息中心*/
    @RequestMapping(value = "noticeList", method = RequestMethod.GET)
    public String noticeList() throws Exception {
        return view("notice-list");
    }

    /*静态消息详情*/
    @RequestMapping(value = "noticeDetail", method = RequestMethod.GET)
    public String noticedetail() throws Exception {
        return view("notice-detail");
    }

    /*下载页面*/
    @RequestMapping(value = "myhdownload", method = RequestMethod.GET)
    public String kssdre(HttpServletRequest request) throws Exception {
        log.info("测试");
        String reruestType = request("reruestType");
        String requestCode = request("requestCode");
        String phone=request("phone");
        request.setAttribute("reruestType", reruestType);
        request.setAttribute("requestCode", requestCode);
        request.setAttribute("phone",phone);
        return view("myhdownload");
    }


}

