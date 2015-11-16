package com.wechat.test;

import arch.util.lang.TimeToolkit;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cheng on 2015/9/22.
 */
public class ExportSiteUrl {
	public static void getData(File file){
		//
		Workbook book = null ;
		try {
			book = Workbook.getWorkbook(file);
			if(book==null){
			}
			Sheet sheet1 = book.getSheet(0);
			if(sheet1==null){
			}
			int row1 = sheet1.getRows();
			for(int i=1;i<row1;i++){
				String siteurl = sheet1.getCell(2, i).getContents();
				if(StringUtils.isNotBlank(siteurl)){
					siteurl = getSiteUrl(siteurl);
					String sql = "INSERT INTO `seo_siteinfo` (`siteurl` ) VALUES ('"+siteurl+"');";
					System.out.println(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			book.close();
		}
	}
	private static  String getSiteUrl(String link){
//		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|vc)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(link);
		matcher.find();
		return matcher.group();
	}
	public static void main(String[] ss) throws Exception{
		File file = new File("C:\\Users\\cheng\\Desktop\\网贷平台排名明细表.xls");
		getData(file);
		;
	}
}
