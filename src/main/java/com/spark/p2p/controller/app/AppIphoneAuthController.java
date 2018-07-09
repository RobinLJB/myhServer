package com.spark.p2p.controller.app;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.BorrowService;
import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.DateUtil;
import com.spark.p2p.util.MessageResult;
import com.spark.p2p.util.ValidateUtil;

@Controller
@ResponseBody
@RequestMapping("/app/uc")
public class AppIphoneAuthController extends AppBaseController {
	private static Log log = LogFactory.getLog(AppIphoneAuthController.class);
    @Autowired
    private MemberService memberService;
    @Autowired
    private IphoneAuthService iphoneAuthService;

    @Autowired
    private BorrowService borrowService;

    // 获取苹果手机认证状态
    @RequestMapping(value = "getIphoneAuth", method = RequestMethod.POST)
    public @ResponseBody
    MessageResult getIphoneStatus() throws Exception {
        Member user = getMember();
        int memberId = (int) user.getId();
        Map<String, String> map = iphoneAuthService.getInfoByMemberId(memberId);
        if (map != null) {
            String iphoneId = map.get("id");
            Map<String, String> borrowMap = borrowService.findBorrowByIphoneId(Long.parseLong(iphoneId));
            if (borrowMap != null) {
                map.put("benJin",borrowMap.get("benJin"));
                return success(map);
            }
        }

        return error(-1, "还没有提交过苹果认证");
    }

    // icloud帐号信息截图上传
    @RequestMapping(value = "icloudImg", method = RequestMethod.POST)
    public @ResponseBody
    MessageResult icloudImg() throws Exception {
        Member user = getMember();
        long memberId = user.getId();
        MessageResult mr = this.borrowService.borrowCheckIcloud(memberId);
        if (mr.getCode() != 0) {
            return mr;
        }
        String imgpath = request("imgpath");
        //此处会对苹果信息进行一个显示的判断，如果再次提交会重新生成一条空的记录
        Map<String, String> map = iphoneAuthService.getInfoByMemberId(memberId);
        long ret = iphoneAuthService.updateIcloudImg(memberId, imgpath);
        if (ret >= 0) {
            return success("保存成功,请等待后台人员审核");
        }
        return error("数据异常");

    }
    
 // iphone帐号信息截图上传
    @RequestMapping(value = "iphoneImg", method = RequestMethod.POST)
    public @ResponseBody
    MessageResult iphoneImg() throws Exception {
        Member user = getMember();
        long memberId = user.getId();
        String imgpath = request("imgpath");
        long ret = iphoneAuthService.updateIphoneImg(memberId, imgpath);
        if (ret >= 0) {
            return success("保存成功,请等待后台人员审核");
        }
        return error("数据异常");

    }
    
    // 获取苹果手机认证状态
    @RequestMapping(value = "authBySerialNumAndIMEI", method = RequestMethod.POST)
    public @ResponseBody
    MessageResult authBySerialNumAndIMEI() throws Exception {
        Member user = getMember();
        int memberId = (int) user.getId();
        Map<String, String> map = iphoneAuthService.getInfoByMemberId(memberId);
        if (map != null) {
            String iphoneId = map.get("id");
            Map<String, String> borrowMap = borrowService.findBorrowByIphoneId(Long.parseLong(iphoneId));
            if (borrowMap != null) {
                map.put("benJin",borrowMap.get("benJin"));
                return success(map);
            }
        }

        return error(-1, "还没有提交过苹果认证");
    }
    /**
     * 序列号&IMEI认证
     *
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "authSerialNumberAndIMEI")
    public @ResponseBody
    MessageResult authSerialNumberAndIMEI() throws Exception {
    	Member user = getMember();
    	if(user==null){
			return error(4102,"token失效");
		}
        String serial_num = request("serial_num");
        String imei_num = request("imei_num");
        if(StringUtils.isEmpty(serial_num)||StringUtils.isEmpty(imei_num)) {
        	return error(401, "请输入完整的序列号与IMEI");
        }else {
        	/*Member user=new Member();
    		user.setId(22439);*/
        	user=memberService.findMember(user.getId());
        	Map<String, String> map = iphoneAuthService.getInfoByMemberId(user.getId());
            if (map == null) {
            	//没有进行过苹果认证，进行新增操作
            	map=new HashMap<String, String>();
            	map.put("member_id",String.valueOf(user.getId()));
            	map.put("serialNum", serial_num);
            	map.put("imeiNum", imei_num);
            	iphoneAuthService.insertSerialNumberByAuditId(map);
            }else {
            	map.put("serialNum", serial_num);
            	map.put("imeiNum", imei_num);
            	iphoneAuthService.updateSerialNumberByAuditId(map);
            }
        }
        return success("OK");
    }
}
