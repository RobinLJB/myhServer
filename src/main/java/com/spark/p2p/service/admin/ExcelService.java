package com.spark.p2p.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spark.p2p.entity.Apple;
import com.sparkframework.sql.model.Model;

@Service
public class ExcelService extends BaseService {
	
	 public long saveBath(List<String[]> list) {
		 Model m = new Model("apple_id_pass");
		 long ret = 0;
	        for (String[] strings : list) {
	        	
	            Apple apple = new Apple();
	           // apple.setId(Long.valueOf(strings[0]));
	            apple.setAppleId(strings[0]);
	            apple.setApplePass(strings[1]);
	            //drug.setPrice(strings[3]);  //这种方法会导致价格出现如下数字：16.399999999999999  77.900000000000006
	           /* BigDecimal bd = new BigDecimal(strings[3]);
	            bd = bd.setScale(1,BigDecimal.ROUND_HALF_UP);
	            String price = bd.toString();
	            if("0".equals(price.substring(price.length()-1))){ //如果最后一位是0  如 26.0元  33.0元 就把 .0 删除
	                String newPrice = price.substring(0, price.length()-2);
	                drug.setPrice(newPrice);
	            }else{
	                drug.setPrice(price);
	            }*/
	            //this.save(apple);
	            try {
	            	System.out.println(strings[0]);
	            	System.out.println(strings[1]);
	            	String appleId=strings[0]; 
	            	String applePass=strings[1];
	            	m.set("apple_pass", applePass);
	            	if (m.where("apple_id=?", appleId).find() != null) {
	    				ret = m.where("apple_id=?", appleId).update();
	    			} else {
	    				m.set("apple_id", appleId);
	    				ret = m.insert();
	    			}
	            }catch (Exception e) {
	    			// TODO: handle exception
	    		}

	            System.out.println(apple.getAppleId());
	        }
			return ret;
	    }
}
