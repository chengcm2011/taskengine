package com.wechat.test;

import arch.util.lang.TimeToolkit;
import com.web.task.model.TaskLogModel;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

/**
 * Created by cheng on 2015/9/14.
 */
public class ResolveArticleXlsUtil {
	public static void getData(File file){
		//
		Workbook book = null ;
		try {
			book = Workbook.getWorkbook(file);
			if(book==null){
			}
//			Sheet sheet = book.getSheet(0);
//			if(sheet==null){
//			}
//			int row = sheet.getRows();
//			int col = sheet.getColumns();
//			for(int i=1;i<row;i++){
//				String id = sheet.getCell(0, i).getContents();
//				String username = sheet.getCell(1, i).getContents();
//				String time = sheet.getCell(2, i).getContents();
//				String phone = sheet.getCell(6, i).getContents();
//				String idcard = sheet.getCell(7, i).getContents();
//				String name = sheet.getCell(8, i).getContents();
//				String ip = sheet.getCell(10, i).getContents();
//				String d = time.substring(0,10);
//				String sql = "INSERT INTO `dsp_third_user` (`id_company`, `id_dsp_throw_plan`, `id_flow_company`, `id_channel_id`, `id_third_user`, `phone`, `username`, `registerdatetime`, `createdatetime`, `registerdate`, `ts`, `dr`, `ip`, `name`, `idcard`) VALUES (20, 8, 31, 5, '"+id+"', '"+phone+"', '"+username+"', '"+time+"', '"+time+"', '"+d+"', '"+ TimeToolkit.getCurrentTs()+"', 0, '"+ip+"', '"+name+"', '"+idcard+"');";
//				System.out.println(sql);
//			}

			Sheet sheet1 = book.getSheet(0);
			if(sheet1==null){
			}
			int row1 = sheet1.getRows();
			for(int i=1;i<row1;i++){
				String phone = sheet1.getCell(0, i).getContents();
				String d = sheet1.getCell(1, i).getContents();
				String time = d;
				String ip = sheet1.getCell(3, i).getContents();
				String id = phone ;
				String sql = "INSERT INTO `dsp_third_user` (`id_company`, `id_dsp_throw_plan`, `id_flow_company`, `id_channel_id`, `id_third_user`, `phone`, `username`, `registerdatetime`, `createdatetime`, `registerdate`, `ts`, `dr`,`ip`) VALUES (20, 8, 31, 5, '"+id+"', '"+phone+"', '"+phone+"', '"+time+"', '"+time+"', '"+d+"', '"+ TimeToolkit.getCurrentTs()+"', 0,'"+ip+"');";
				System.out.println(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			book.close();
		}
	}

	public static void main(String[] ss) throws Exception{
//		File file = new File("C:\\Users\\cheng\\Desktop\\1111111111111111.xls");
//		getData(file);
		 System.out.println(TaskLogModel.toTableSql(TaskLogModel.class));
	}
}
