package com.spark.p2p.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.xml.sax.InputSource;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.config.Config;
import com.spark.p2p.constant.Const;
import com.spark.p2p.core.DataTableRunner;
import com.spark.p2p.entity.member.Member;
import com.spark.p2p.entity.Pagination;
import com.spark.p2p.service.IndexService;
import com.spark.p2p.service.MemberService;
import com.spark.p2p.util.ApplicationUtil;
import com.spark.p2p.util.DataTable;
import com.spark.p2p.util.DataTableRequest;
import com.spark.p2p.util.MessageResult;
import com.sparkframework.lang.Convert;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class BaseController {
	private final String MOBILE_THEME_PREFIX = "";
	private final String DEFAULT_THEME_PREFIX = "";
	private final boolean ENABLE_MOBILE = true;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected HttpServletRequest request;;

	private static Log log = LogFactory.getLog(BaseController.class);

	protected String viewPath = "";
	private PrintWriter out;

	// protected Map<String, String> paramMap = new HashMap<String, String>();

	@ModelAttribute
	public void setRequestResponse(HttpServletRequest request,
			HttpServletResponse response) {
		// this.request = request;
		// this.response = response;
		// HttpSession session = request.getSession();
		try {
			// Member member =
			// (Member)session.getAttribute(Const.SESSION_MEMBER);

			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("partnerList",
					new IndexService().bannerlist("pc_partner", 10));
			((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().setAttribute("friendlyList",
					new IndexService().bannerlist("pc_friendly", 10));
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		request.setAttribute("webname", AppSetting.APP_NAME);

	}

	protected MessageResult success() {
		return new MessageResult(0, "SUCCESS");
	}

	protected MessageResult success(String msg) {
		return new MessageResult(0, msg);
	}

	protected MessageResult success(String msg, Object obj) {
		MessageResult mr = new MessageResult(0, msg);
		mr.setData(obj);
		return mr;
	}

	protected MessageResult success(Object obj) {
		MessageResult mr = new MessageResult(0, "SUCCESS");
		mr.setData(obj);
		return mr;
	}

	protected MessageResult error(String msg) {
		return new MessageResult(500, msg);
	}

	protected MessageResult error(int code, String msg) {
		return new MessageResult(code, msg);
	}

	/**
	 * 返回tpl路径,根据浏览器返回不同主题
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected String view(String name) {
		// 模板路径
		String path = StringUtils.isEmpty(viewPath) ? name : viewPath + "/"
				+ name;
		// 模板名称
		String tpl = null;
		if (ENABLE_MOBILE && isMobile() && !viewPath.contains("admin")) {
			tpl = MOBILE_THEME_PREFIX + path;
		} else {
			tpl = DEFAULT_THEME_PREFIX + path;
		}
		if (AppSetting.DEBUG_MODE) {
			log.info(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI() + "==>" + tpl + ".ftl");
		}
		return tpl;
	}

	/**
	 * 使用MOBILE页面
	 * 
	 * @param name
	 * @return
	 */
	protected String viewMobile(String name) {
		log.info("viewMobile");
		// 模板路径
		String path = StringUtils.isEmpty(viewPath) ? name : viewPath + "/"
				+ name;
		// 模板名称
		String tpl = MOBILE_THEME_PREFIX + path;
		if (AppSetting.DEBUG_MODE) {
			log.info(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRequestURI() + "==>" + tpl + ".ftl");
		}
		return tpl;
	}

	public PrintWriter getOut(HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		out = response.getWriter();
		return out;
	}

	protected void sendScript(HttpServletResponse response, String script)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write("<script>" + script + "</script>");
	}

	protected void sendHtml(HttpServletResponse response, String html)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(html);
	}

	/**
	 * 获取request请求参数
	 * 
	 * @param name
	 *            参数名称
	 * @return
	 */
	protected String request(String name) {
		return StringUtils.trimToEmpty(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name));
	}

	protected String requestString(String name) {
		return Convert.strToStr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name), "");
	}

	protected Double requestDouble(String name) {
		return Convert.strToDouble(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name), 0D);
	}

	protected int requestInt(String name) {
		return Convert.strToInt(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name), 0);
	}

	protected long requestLong(String name) {
		return Convert.strToLong(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name), 0);
	}

	protected int requestInt(String name, int def) {
		return Convert.strToInt(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameter(name), def);
	}

	public String getRemoteIp() {
		if (StringUtils.isNotBlank(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Real-IP"))) {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Real-IP");
		} else if (StringUtils.isNotBlank(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Forwarded-For"))) {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-Forwarded-For");
		} else if (StringUtils.isNotBlank(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Proxy-Client-IP"))) {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Proxy-Client-IP");
		}
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
	}

	/**
	 * 读取表格数据
	 * 
	 * @param runner
	 * @return
	 */
	public DataTable dataTable(DataTableRunner runner) {
		new DataTable();
		DataTableRequest params = new DataTableRequest();
		params.setDraw(Convert.strToInt(request("draw"), 0));
		params.setStart(Convert.strToInt(request("start"), 0));
		params.setLength(Convert.strToInt(request("length"), 0));
		params.parseRequest(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		log.info(params.columns);
		try {
			DataTable dt = runner.run(params);
			dt.setDraw(params.getDraw());
			return dt;
		} catch (Exception e) {
			e.printStackTrace();
			DataTable dt = new DataTable();
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			dt.setData(data);
			dt.setDraw(params.getDraw());
			dt.setError("读取数据失败");
			return dt;
		}
	}

	public DataTable dataTable(DataTableRunner runner, String[] titles) {
		DataTable dt = dataTable(runner);
		dt.setTitles(Arrays.asList(titles));
		return dt;
	}

	/**
	 * 判断是否是手机浏览器
	 * 
	 * @return
	 */
	public boolean isMobile() {
		boolean isMobile = false;
		String hostname = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Host");
		Config cfg = ApplicationUtil.getBean(Config.class);
		if (cfg.getWapDomain().equalsIgnoreCase(hostname)) {
			return true;
		}
		String[] mobileAgents = { "iphone", "android", "phone", "ipad",
				"mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series",
				"webos", "sony", "blackberry", "dopod", "nokia", "samsung",
				"palmsource", "xda", "pieplus", "meizu", "midp", "cldc",
				"motorola", "foma", "docomo", "up.browser", "up.link",
				"blazer", "helio", "hosin", "huawei", "novarra", "coolpad",
				"webos", "techfaith", "palmsource", "alcatel", "amoi",
				"ktouch", "nexian", "ericsson", "philips", "sagem", "wellcom",
				"bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird",
				"zte-", "longcos", "pantech", "gionee", "portalmmm",
				"jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi",
				"audi", "avan", "benq", "bird", "blac", "blaz", "brew", "cell",
				"cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq",
				"java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g",
				"lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi", "mot-",
				"moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana",
				"pant", "phil", "play", "port", "prox", "qwap", "sage", "sams",
				"sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-",
				"siem", "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli",
				"tim-", "tsm-", "upg1", "upsi", "vk-v", "voda", "wap-", "wapa",
				"wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		// 根据cookie选择模板，暂时未用
		Cookie[] cookies = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().toUpperCase().equals("SPARK_THEME")) {
					log.info("SPAKR_THEME:" + cookie.getValue());
					isMobile = cookie.getValue().equals("WAP");
					break;
				}
			}
		}
		// 根据userAgent自动选择
		String userAgent = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("User-Agent");
		if (userAgent == null) {
			return false;
		}
		userAgent = userAgent.toLowerCase();

		if (((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (userAgent.indexOf(mobileAgent) >= 0) {
					log.info("User-Agent HIT:" + mobileAgent);
					isMobile = true;
					break;
				}
			}
		}
		return isMobile;
	}

	public Pagination getPage() {
		int pageSize = requestInt("pageSize", 10);
		int curPage = requestInt("curPage", 1);
		return new Pagination(curPage, pageSize);
	}

	public Map<String, String> getParameters() {
		Map<String, String[]> params = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getParameterMap();
		Map<String, String> map = new HashMap<String, String>();
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			map.put(key, String.join(";", values));
		}
		return map;
	}

	protected String getPhysicalPath() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("/");
	}

	// 接口的post提交
	public Map<String, String> httpclientPost(String path, String param)
			throws Exception {
		URL url = new URL(path.trim());
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setRequestMethod("POST");// 提交模式
		httpURLConnection.setConnectTimeout(10000);// 连接超时 单位毫秒
		httpURLConnection.setReadTimeout(500000);// 读取超时 单位毫秒
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		OutputStream os = httpURLConnection.getOutputStream();
		os.write(param.getBytes());
		if (httpURLConnection.getResponseCode() != 200)
			throw new RuntimeException("请求url失败");

		// 开始获取数据
		BufferedInputStream bis = new BufferedInputStream(
				httpURLConnection.getInputStream());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len;
		byte[] arr = new byte[1024];
		while ((len = bis.read(arr)) != -1) {
			bos.write(arr, 0, len);
			bos.flush();
		}
		bos.close();
		log.info(bos.toString());

		Map<String, String> map = new HashMap<String, String>();
		InputSource in = new InputSource(new StringReader(bos.toString()));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
			Element element = it.next();
			map.put(element.getName(), element.getTextTrim());
		}
		return map;
	}

	// 接口的post提交
	public String httpclientPostReturnString(String path, String param)
			throws Exception {
		URL url = new URL(path.trim());
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setRequestMethod("POST");// 提交模式
		httpURLConnection.setConnectTimeout(10000);// 连接超时 单位毫秒
		httpURLConnection.setReadTimeout(500000);// 读取超时 单位毫秒
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		OutputStream os = httpURLConnection.getOutputStream();
		os.write(param.getBytes());
		if (httpURLConnection.getResponseCode() != 200)
			throw new RuntimeException("请求url失败");

		// 开始获取数据
		BufferedInputStream bis = new BufferedInputStream(
				httpURLConnection.getInputStream());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int len;
		byte[] arr = new byte[1024];
		while ((len = bis.read(arr)) != -1) {
			bos.write(arr, 0, len);
			bos.flush();
		}
		bos.close();

		return bos.toString();
	}

	public static <T> T parseXMLToEntity(String plaintext, String classname,
			Class<T> clazz) throws Exception {
		T result = null;
		XStream xStreamBean = new XStream(new DomDriver());
		xStreamBean.alias(classname, clazz);
		xStreamBean.processAnnotations(clazz);
		try {
			result = (T) xStreamBean.fromXML(plaintext);
		} catch (Exception e) {
			throw new RuntimeException("XML转换实体 , 解析失败 : "
					+ e.getLocalizedMessage());
		}
		if (result == null) {
			throw new RuntimeException("XML转换实体 , 解析失败, 转换实体类为NULL");
		}
		return result;
	}
}
