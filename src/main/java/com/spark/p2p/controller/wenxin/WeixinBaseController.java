package com.spark.p2p.controller.wenxin;


import java.io.StringReader; 
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.InputSource;
 
import com.spark.p2p.constant.Const;
import com.spark.p2p.controller.BaseController;
import com.spark.p2p.entity.member.Member; 
import com.sparkframework.sql.model.Model;

@Controller
@RequestMapping("/mobile")
public class WeixinBaseController extends BaseController{
	public WeixinBaseController(){
		super.viewPath = "mobile";
	}
	
	protected Member getUser(){
		Member member = (Member)session.getAttribute(Const.SESSION_MEMBER);
		try {
			member = new Model("member").where("id=?", member.getId()).find(Member.class);
			session.setAttribute(Const.SESSION_MEMBER, member);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return member;
	}
	
	
	
	
	
	//解析xml
	public Map<String,String> analysisXML(String xml) throws DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		InputSource in = new InputSource(new StringReader(xml));
		in.setEncoding("UTF-8");
		SAXReader reader = new SAXReader();
		Document document = reader.read(in);
		Element root = document.getRootElement();
		List<Element> elements = root.elements();
		for(Iterator<Element> it = elements.iterator();it.hasNext();){
		   Element element = it.next();
		   map.put(element.getName(),element.getTextTrim());
		} 
		
		return map;
		
	}
	
}