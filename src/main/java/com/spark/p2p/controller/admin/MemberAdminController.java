package com.spark.p2p.controller.admin;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.spark.p2p.entity.AddressList;
import com.spark.p2p.service.IphoneAuthService;
import com.spark.p2p.util.*;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spark.p2p.entity.member.Member;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.service.admin.MemberAdminService;
import com.sparkframework.lang.Convert;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理
 *
 * @author wutao
 */
@Controller("memberAdminController")
@RequestMapping("/admin")
public class
MemberAdminController extends BaseAdminController {
    @Autowired
    private MemberAdminService mbAdminService;

    @Autowired
    private MemberService mbService;

    @Autowired
    private IphoneAuthService iphoneAuthService;

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("member")
    public String memberIndex(HttpServletRequest request) {
        String status = request("status");
        if (ValidateUtil.isnull(status)) {
            request.setAttribute("status", 0);
        } else {
            request.setAttribute("status", status);
        }

        return view("member/member-index");
    }

    /**
     * 用户列表
     *
     * @return
     */
    @RequestMapping("member/list/{status}")
    public DataTable roleList(@PathVariable int status) {
        return dataTable((params) -> mbAdminService.queryAllUser(params, status));
    }

    /**
     * 导出通讯录信息
     */
    @RequestMapping("member/address/list/excel")
    @ResponseBody
    public MessageResult getMemberAddressListExcel(HttpServletResponse response) throws Exception {
        StringBuffer condition = new StringBuffer("");

        //此处的searchDateRange即为用户的member.id
        String searchDateRange = request( "searchDateRange");

        //获取数据库中的json形式的通讯录
        String addressListDetail=mbService.findMember(Long.parseLong(searchDateRange))
                                        .getCommicateDetail();
        if(addressListDetail==null){
            return error("用户未上传通讯录");
        }
        String memberName=mbService.findMember(Long.parseLong(searchDateRange)).getRealName();
        com.alibaba.fastjson.JSONObject jsonObject= JSON.parseObject(addressListDetail);
        String addressListJson=jsonObject.getString("person");
        //将String形式的通讯录详情转换成list
        List<AddressList> addressLists=JSON.parseArray(addressListJson,AddressList.class);
        List<Map<String,String>> addressList=new LinkedList<>();
        for(int i=0;i<addressLists.size();i++){
            String name=addressLists.get(i).getName();
            String tel=addressLists.get(i).getTel();
            Map<String,String> map=new HashMap<>();
            map.put("name",name);
            map.put("tel",tel);
            addressList.add(map);
        }
        String[] columnNames = {"姓名","手机号"};
        // map中的key
        String keys[] = {"name", "tel"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createAddressWorkBook(addressList, keys, columnNames,memberName).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(
                            ("addressList_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls").getBytes(),
                            "utf-8"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            return error(500, "系统错误");
        }
        return success();
    }
    /**
     * 导出用户个人信息
     *
     * @return
     */
    @RequestMapping("member/excel")
    @ResponseBody
    public MessageResult queryMemberDetailExcel( final HttpServletResponse response) throws Exception {
        StringBuffer condition = new StringBuffer("");
        String searchDateRange = request( "searchDateRange");
        if (!StringUtils.isEmpty(searchDateRange)) {
            if (searchDateRange.length() > 13) {
                String begin = searchDateRange.substring(0, 10) + " 00:00:00";
                String end = searchDateRange.substring(searchDateRange.length() - 10, searchDateRange.length()) + " 23:59:59";
                condition.append("create_time between '" + begin + "' and '" + end + "' and ");
            }
        }
        condition.append(" 1=1");
        List<Map<String, String>> list = mbAdminService.queryAllMemberExcel(condition.toString());
        // 列名
        String[] columnNames = {"用户编号", "真实姓名", "手机号","身份证号","注册日期","借款总额", "还款总额", "逾期金额", "坏账","正面照","反面照","手持照"};
        // map中的key
        String keys[] = {"id", "real_name", "mobilePhone", "ident_no","create_time","alreadyBorrowSum","alreadyRepaySum","overdueSum","dieSum","card_imgA", "card_imgB","handle_img"};
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createMemberWorkBook(list, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(
                            ("member_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls").getBytes(),
                            "utf-8"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            if (bis != null) {
                bis.close();
            }
            if (bos != null) {
                bos.close();
            }
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            return error(500, "系统错误");
        }
        return success();
    }


    /**
     * 用户详情
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping("member/{uid}")
    public String memberDetail(HttpServletRequest request,@PathVariable long uid) throws Exception {
        if (uid <= 0) {
            //说明是其他地方调用通讯录接口
            uid = Convert.strToInt(request("uid"), 0);
        }
        //决定显示那个tab
        String displayStatus = request("displayStatus");
        if (ValidateUtil.isnull(displayStatus)) {
            request.setAttribute("displayStatus", "1");
        } else {
            request.setAttribute("displayStatus", displayStatus);
        }

        Member member = mbService.findMember(uid);
        //基本信息
        request.setAttribute("uid", uid);
        request.setAttribute("member", member);

        /*request.setAttribute("bank", mbService.findBankCard(uid));*/
        Map<String, String> iphoneMap = iphoneAuthService.getInfoByMemberId(uid);
        Map<String, String> basicMap = mbAdminService.findMemberById(uid);
        Map<String, String> infoMap = mbAdminService.findMemberInfoByMid(uid);

        Map<String, String> identityMap = mbAdminService.findMemberIdentityMid(uid);
        Map<String, String> refferMap = null;
        if (Convert.strToInt(basicMap.get("refferee"), 0) != 0) {
            refferMap = mbAdminService.findMemberById(Convert.strToInt(basicMap.get("refferee"), 0));
        }

        Map<String, String> auditMap = mbAdminService.findMemberAuditByMid(uid);
        System.out.println("auditMap = " + auditMap);
        List<Map<String, String>> commicateList = mbAdminService.queryMemberCommicateByMid(uid);

        request.setAttribute("basicMap", basicMap);
        request.setAttribute("identityMap", identityMap);
        request.setAttribute("infoMap", infoMap);
        request.setAttribute("auditMap", auditMap);
        request.setAttribute("refferMap", refferMap);

        //通讯录
        String linkman = member.getCommicateDetail();
        Map<String, String> linkmansize = new HashMap<String, String>();
        if (linkman == null) {
            linkmansize.put("linkmansize", 0 + "");
        } else {
            try {
                JSONObject json = new JSONObject(linkman);
                JSONArray person = json.optJSONArray("person");
                List<Object> personList = new ArrayList<Object>();
                for (int i = 0; i < person.length(); i++) {
                    personList.add(person.get(i));
                }
                System.out.println(personList);
                request.setAttribute("linkman", personList);
                if (personList.size() != 0) {
                    linkmansize.put("linkmansize", personList.size() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //资金记录
        List<Map<String, String>> fundList = mbAdminService.queryMemberInfo(uid);
        List<Map<String, String>> commisionList = mbAdminService.queryMemberCommision(uid);
        request.setAttribute("fundList", fundList);
        request.setAttribute("commisionList", commisionList);
        request.setAttribute("linkmansize", linkmansize);
        request.setAttribute("iphoneMap", iphoneMap);
        return view("member/member-detail");
    }

    //查看手机通话详单
    @RequestMapping("member/phoneTalkDetail/{uid}")
    public String phoneTalkDetail(@PathVariable long uid) {

        return view("member/phone-talk-detail");
    }

    @RequestMapping("phoneTalkDetail/list/{uid}")
    public DataTable phoneTalkDetailList(@PathVariable long uid) throws Exception {
        return dataTable((params) -> mbAdminService.queryMemberPhoneTalkDetail(params, uid));
    }

    //查看手机通话详细信息
    @RequestMapping("phone/detail/{uid}")
    public String phoneDetail(HttpServletRequest request,@PathVariable int uid) throws Exception {
        Map map = mbAdminService.queryPhoneDetail(uid);
        request.setAttribute("detail",(String) map.get("callDetail"));
        return view("member/phone-detail");
    }

    /**
     * 会员基本信息更新
     */
    @RequestMapping("member/update/basic/{mid}")
    public @ResponseBody
    MessageResult memberBasicUpdate(@PathVariable long mid)
            throws Exception {
        String mobilePhone = requestString("mobilePhone");
        String member_status = requestString("member_status");
        long ret = mbService.updateMemberBasic(mid, mobilePhone, Convert.strToInt(member_status, 0));
        if (ret > 0) {
            return success("操作成功");
        } else {
            return error("操作失败");
        }

    }


    /**
     * 用户资金记录列表
     *
     * @return
     */
    @RequestMapping("member/fundList/{mid}")
    public DataTable financeList(@PathVariable long mid) {
        return dataTable((params) -> mbAdminService.queryFundList(params, mid));
    }


    /**
     * 用户借款记录
     *
     * @return
     */
    @RequestMapping("member/borrowList/{mid}")
    public DataTable borrowList(@PathVariable long mid) {
        return dataTable((params) -> mbAdminService.queryBorrowList(params, mid));
    }


    /**
     * 用户手机通讯录
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("member/commicateList/{mid}")
    public DataTable commicateList(@PathVariable long mid) throws Exception {
        dataTable((params) -> mbAdminService.queryCommicateList(params));
        DataTable dt = new DataTable();
        List<Map<String, String>> list = new ArrayList<>();
        Member member = mbService.findMember(mid);
        String commicateDetail = member.getCommicateDetail();
        if (ValidateUtil.isnull(commicateDetail)) {
            dt.setRecordsFiltered(0);
            dt.setRecordsTotal(0);
            dt.setData(null);
            return dt;
        } else {
            JSONObject js = new JSONObject(commicateDetail.toString());
            JSONArray jsarr = js.getJSONArray("person");
            int length = jsarr.length();
            for (int i = 0; i < length; i++) {
                Map<String, String> map = new HashMap<String, String>();
                JSONObject jsarrJSa = (JSONObject) jsarr.get(i);
                map.put("id", (i + 1) + "");
                map.put("name", jsarrJSa.getString("name"));
                map.put("phoneNo", jsarrJSa.getString("tel"));
                list.add(map);
            }
            dt.setRecordsFiltered(length);
            dt.setRecordsTotal(length);
            dt.setData(list);
            return dt;
        }

    }


    /**
     * 用户推荐关系列表
     *
     * @return
     */
    @RequestMapping("member/relation")
    public String relation() {
        return view("member/relation-index");
    }

    /**
     * 用户推荐关系列表
     *
     * @return
     */
    @RequestMapping("member/relation/list")
    public DataTable memberRelation() {
        return dataTable((params) -> mbAdminService.queryRelationList(params));
    }


    /**
     * 用户详情
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("member/mrefferMember/{mid}")
    public String mrefferMemberDetail(HttpServletRequest request,@PathVariable long mid) throws Exception {

        Map<String, String> basicMap = mbAdminService.findMemberById(mid);
        request.setAttribute("basicMap", basicMap);
        return view("member/relation-detail");
    }


    /**
     * 用户推荐列表
     *
     * @return
     */
    @RequestMapping("member/relation/{mid}")
    public DataTable memberRelationList(@PathVariable long mid) {
        return dataTable((params) -> mbAdminService.queryMemberRelationList(params, mid));
    }

    /**
     * 删除用户信息，包含借款信息在内的所有信息
     * 秒一花要求增加的功能
     */
    @RequestMapping("member/delete/{mid}")
    public String deleteMemberInfoById(@PathVariable long mid) throws Exception {
        long ret=mbAdminService.deleteMemberInfoById(mid);
        return "redirect:/admin/member.html?status=0";
    }
    /**
     * 通过用户名&真实姓名查找用户
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findMemberByName", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> findMemberByName() throws Exception {
        String username = requestString("username");
        String realname = requestString("realname");
        Map<String, String> map = mbAdminService.findMemberByName(username, realname);
        return map;
    }

    //查看运营商信息
    @RequestMapping("member/callDetail/{uid}")
    public String callDetail(HttpServletRequest request,@PathVariable long uid) throws Exception {
        DecimalFormat df = new DecimalFormat("######0.0000");//保留四位小数
        Member member = mbService.findMember(uid);
        String callDetail = member.getCallDetail();
        JSONObject detailJson = null;
        if (callDetail != null && callDetail != "") {
            detailJson = new JSONObject(callDetail);
        }
        Map<String, String> detailMap = new HashMap<String, String>();
        List<Object> carrier_consumption = new ArrayList<Object>();
        List<Object> contact_area_stats = new ArrayList<Object>();
        List<Object> all_contact = new ArrayList<Object>();
        List<Object> all_contact1 = new ArrayList<Object>();

        if (detailJson != null) {
            JSONObject user_info = new JSONObject();
            user_info = detailJson.optJSONObject("user_info");//用户信息
            detailMap.put("real_name", user_info.optString("real_name"));//真实姓名
            detailMap.put("home_addr", user_info.optString("home_addr"));//家庭地址

            JSONObject mobile_info = new JSONObject();
            mobile_info = detailJson.optJSONObject("mobile_info");//手机信息
            detailMap.put("user_mobile", mobile_info.optString("user_mobile"));//手机号
            detailMap.put("mobile_net_time", mobile_info.optString("mobile_net_time"));//入网时间
            detailMap.put("account_status", mobile_info.optString("account_status"));//账户状态
            detailMap.put("package_type", mobile_info.optString("package_type"));//套餐类型
            detailMap.put("mobile_net_addr", mobile_info.optString("mobile_net_addr"));//入网归属地
            detailMap.put("contact_addr", mobile_info.optString("contact_addr"));//联系地址

            JSONArray call_area_stats_per_city = new JSONArray();
            call_area_stats_per_city = detailJson.getJSONArray("call_area_stats_per_city");//通话地区统计
            if (call_area_stats_per_city.length() > 0) {
                call_area_stats_per_city.optJSONObject(0);
                detailMap.put("call_area_city", call_area_stats_per_city.optJSONObject(0).getString("call_area_city"));//常用通话地
            } else {

            }

            JSONArray carrier_consumption_stats_per_month = detailJson.getJSONArray("carrier_consumption_stats_per_month");//运营商数据统计
            JSONArray all_contact_stats_per_month = detailJson.getJSONArray("all_contact_stats_per_month");//每个月联系人统计

            for (int j = 0; j < carrier_consumption_stats_per_month.length(); j++) {
                if (all_contact_stats_per_month.length() > 0) {
                    for (int k = 0; k < all_contact_stats_per_month.length(); k++) {
                        if (carrier_consumption_stats_per_month.optJSONObject(j).get("month").equals(all_contact_stats_per_month.optJSONObject(k).get("month"))) {
                            carrier_consumption_stats_per_month.optJSONObject(j).put("call_time_active", df.format(all_contact_stats_per_month.optJSONObject(k).optDouble("call_time_active") / 60.00));
                            carrier_consumption_stats_per_month.optJSONObject(j).put("call_time_passive", df.format(all_contact_stats_per_month.optJSONObject(k).optDouble("call_time_passive") / 60.00));
                            carrier_consumption_stats_per_month.optJSONObject(j).put("call_count_active", all_contact_stats_per_month.optJSONObject(k).get("call_count_active"));
                            carrier_consumption_stats_per_month.optJSONObject(j).put("call_count_passive", all_contact_stats_per_month.optJSONObject(k).get("call_count_passive"));
                            carrier_consumption_stats_per_month.optJSONObject(j).put("msg_count", all_contact_stats_per_month.optJSONObject(k).get("msg_count"));
                        }
                    }
                } else {
                    carrier_consumption_stats_per_month.optJSONObject(j).put("call_time_active", 0);
                    carrier_consumption_stats_per_month.optJSONObject(j).put("call_time_passive", 0);
                    carrier_consumption_stats_per_month.optJSONObject(j).put("call_count_active", 0);
                    carrier_consumption_stats_per_month.optJSONObject(j).put("call_count_passive", 0);
                    carrier_consumption_stats_per_month.optJSONObject(j).put("msg_count", 0);
                }
            }

            if (carrier_consumption_stats_per_month.length() > 0) {
                for (int i = 0; i < carrier_consumption_stats_per_month.length(); i++) {
                    carrier_consumption_stats_per_month.optJSONObject(i).put("consume_amount", df.format(carrier_consumption_stats_per_month.optJSONObject(i).optDouble("consume_amount") / 100.00));
                    carrier_consumption_stats_per_month.optJSONObject(i).put("recharge_amount", df.format(carrier_consumption_stats_per_month.optJSONObject(i).optDouble("recharge_amount") / 100.00));
                    carrier_consumption.add(carrier_consumption_stats_per_month.get(i));
                }
            }

            JSONArray contact_area_stats_per_city = new JSONArray();
            contact_area_stats_per_city = detailJson.getJSONArray("contact_area_stats_per_city");//联系人地区分布
            double talkTimeTotal = 0;
            double talkTimeTotalPassive = 0;
            if (contact_area_stats_per_city.length() > 0) {
                for (int i = 0; i < contact_area_stats_per_city.length(); i++) {
                    talkTimeTotal = talkTimeTotal + contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_active_6month");
                    talkTimeTotalPassive = talkTimeTotalPassive + contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_passive_6month");
                    contact_area_stats_per_city.optJSONObject(i).put("call_time_active_6month", df.format(contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_active_6month") / 60.00));
                    contact_area_stats_per_city.optJSONObject(i).put("call_time_passive_6month", df.format(contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_passive_6month") / 60.00));
                    contact_area_stats.add(contact_area_stats_per_city.get(i));
                }
            }

            double talkTimeEevry = 0;
            double talkTimeEevryPassive = 0;
            if (contact_area_stats_per_city.length() > 0) {
                for (int i = 0; i < contact_area_stats_per_city.length(); i++) {
                    talkTimeEevry = (contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_active_6month") * 60) / talkTimeTotal;
                    talkTimeEevryPassive = (contact_area_stats_per_city.optJSONObject(i).optDouble("call_time_passive_6month") * 60) / talkTimeTotalPassive;
                    contact_area_stats_per_city.optJSONObject(i).put("talkTimeEevry", df.format(talkTimeEevry));
                    contact_area_stats_per_city.optJSONObject(i).put("talkTimeEevryPassive", df.format(talkTimeEevryPassive));
                }
            }

            JSONArray all_contact_detail = new JSONArray();
            all_contact_detail = detailJson.getJSONArray("all_contact_detail");
            double talkTimeAll = 0;
            double talkTimePassiveAll = 0;
            if (all_contact_detail.length() > 0) {
                for (int i = 0; i < all_contact_detail.length(); i++) {
                    talkTimeAll = talkTimeAll + all_contact_detail.optJSONObject(i).optDouble("call_time_active_6month");
                    talkTimePassiveAll = talkTimePassiveAll + all_contact_detail.optJSONObject(i).optDouble("call_time_passive_6month");
                    all_contact_detail.optJSONObject(i).put("call_time_active_6month", df.format(all_contact_detail.optJSONObject(i).optDouble("call_time_active_6month") / 60.00));
                    all_contact_detail.optJSONObject(i).put("call_time_passive_6month", df.format(all_contact_detail.optJSONObject(i).optDouble("call_time_passive_6month") / 60.00));
                    all_contact.add(all_contact_detail.get(i));
                }
            }

            double talkTimeEevryAll = 0;
            double talkTimeEevryPassiveAll = 0;
            if (all_contact_detail.length() > 0) {
                for (int i = 0; i < all_contact_detail.length(); i++) {
                    talkTimeEevryAll = (all_contact_detail.optJSONObject(i).optDouble("call_time_active_6month") * 60) / talkTimeAll;
                    talkTimeEevryPassiveAll = (all_contact_detail.optJSONObject(i).optDouble("call_time_passive_6month") * 60) / talkTimePassiveAll;
                    all_contact_detail.optJSONObject(i).put("talkTimeEevryAll", df.format(talkTimeEevryAll));
                    all_contact_detail.optJSONObject(i).put("talkTimeEevryPassiveAll", df.format(talkTimeEevryPassiveAll));
                }
            }

            if (all_contact.size() > 50) {
                all_contact1 = all_contact.subList(0, 50);//常用联系人TOP 50
            } else {
                all_contact1.addAll(all_contact);
            }

        }
        request.setAttribute("member", member);
        request.setAttribute("detailMap", detailMap);
        request.setAttribute("contact_area_stats", contact_area_stats);
        request.setAttribute("all_contact", all_contact1);
        request.setAttribute("carrier_consumption", carrier_consumption);
        return view("member/call-detail");
    }

}
