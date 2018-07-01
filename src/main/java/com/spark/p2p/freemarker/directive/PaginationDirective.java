package com.spark.p2p.freemarker.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sparkframework.lang.Convert;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PaginationDirective implements TemplateDirectiveModel {
	//当前页码数
	private Integer curPage;
	//要显示的页码数
	private Integer showPageCount = 10;
	//每页信息条数
	private Integer pageSize = 10;
	//信息总条数
	private Integer totalRecord = 0;
	//翻页动作类型，link：链接形式，click：js形式
	private String method;
	private String onclick;
	private String href;
	private String theme = "normal";
	//TAG名称
	private String FIRST_PAGE_NAME;
	private String LAST_PAGE_NAME;
	private String NEXT_PAGE_NAME;
	private String PREV_PAGE_NAME;
	
	
	private void initTheme(){
		if(theme.equalsIgnoreCase("simple")){
			FIRST_PAGE_NAME = "&laquo;";
			LAST_PAGE_NAME = "&raquo;";
			NEXT_PAGE_NAME = "&gt;";
			PREV_PAGE_NAME = "&lt;";
		}
		else{
			FIRST_PAGE_NAME = "首页";
			LAST_PAGE_NAME = "尾页";
			NEXT_PAGE_NAME = "下一页";
			PREV_PAGE_NAME = "上一页";
		}
	}
	
	public static Integer getStartIndex(Integer pageNum, Integer pageSize){
		Integer res = 0;
		if(pageNum>0){
			res = (pageNum-1)*pageSize;
		}
		return res;
	}
	
	public  String getPageAction(){
		if(StringUtils.isBlank(this.method)||this.method.equalsIgnoreCase("LINK")){
			if(StringUtils.isNotBlank(this.href)){
				if(this.href.indexOf("?") == -1){
					return "href='"+this.href +"?curPage=%d&pageSize="+this.pageSize+"'";
				}
				else return "href='"+this.href +"&curPage=%d&pageSize="+this.pageSize+"'";
			}
		}
		else if(this.method.equalsIgnoreCase("CLICK")){
			return "href='javascript:"+this.onclick+"(%d,"+this.pageSize+")'";
		}
		return "";
	}
	
	@Override
	public void execute(Environment env, Map map, TemplateModel[] vars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		curPage = Convert.strToInt(DirectiveUtils.getRequiredParam(map, "curPage"), 1);
		pageSize = Convert.strToInt(DirectiveUtils.getRequiredParam(map, "pageSize"), 0);
		totalRecord = Convert.strToInt(DirectiveUtils.getRequiredParam(map, "totalRecord"), 0);
		onclick = DirectiveUtils.getParam(map, "click","");
		href = DirectiveUtils.getParam(map, "href","#");
		theme = DirectiveUtils.getParam(map, "theme","normal");
		if(StringUtils.isNotBlank(onclick)){
			method = "CLICK";
		}
		else method = "LINK";
		Writer out = env.getOut();
		initTheme();
		//要显示的分页数
		int pageNumber = 0;
		//总页数
		int totalPage = 0;
		if (totalRecord % pageSize==0) {
			totalPage = totalRecord/pageSize;
		}
		else {
			totalPage = (totalRecord/pageSize)+1;
		}
		if(totalPage > showPageCount){
			pageNumber = this.showPageCount;
		}
		else pageNumber = totalPage;
		if (curPage < 1) {
			curPage = 1;
		}
		try {
			String  actionTpl = this.getPageAction();
			if (pageNumber > 0) {
				out.write("<div class='pagination-group'><ul class='pagination'>");
				int start = 1;
				int end = pageNumber;
				//总页数大于可显示页数时计算显示的分页
				if(totalPage > pageNumber){
					int offset = 5;
					//计算第一页位置
					start = curPage - offset;
					start = start > 0?start:1;
					//计算最后一页位置
					end = start + pageNumber -1;
					if(end > totalPage){
						int delta = end - totalPage;
						start -= delta;
						end -= delta;
					}
				}
				//开始输出分页信息
				if(curPage>1){
					if(start>1){
						out.write("<li><a "+String.format(actionTpl, 1)+">"+FIRST_PAGE_NAME+"</a></li>");
					}
					out.write("<li><a "+String.format(actionTpl,curPage-1)+">"+PREV_PAGE_NAME+"</a></li>");
				}
				
				for(int i=start;i<=end;i++){
					if(i==curPage){
						out.write("<li class='active'><a href='javascript:void(0)'>" + i + "</a></li>");
					}
					else{
						out.write("<li><a "+String.format(actionTpl, i)+">" + i + "</a></li>");
					}
				}
				if(curPage<totalPage){
					out.write("<li><a "+String.format(actionTpl,curPage+1)+">"+NEXT_PAGE_NAME+"</a></li>");
					if(end < totalPage){
						out.write("<li><a "+String.format(actionTpl,totalPage)+">"+LAST_PAGE_NAME+"</a></li>");
					}
				}
				out.write("</ul>");
				out.write("<span class='pagination-info'>共" +this.totalRecord +"条记录,第" + curPage +"/"+totalPage + "页</span></div>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
