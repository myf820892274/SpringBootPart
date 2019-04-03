package com.yf.controller;

import com.yf.service.SysUsersService;
import com.yf.utils.Lg;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
public class ExportExcelController {

    @Resource
    private SysUsersService sysUsersService;

    @RequestMapping("/exportExcel")
    public void export(HttpServletResponse response){
        try{
            response.setContentType("application/octet-stream");//xxx.*
            String filename = "千峰员工信息.xls";
            filename = URLEncoder.encode(filename,"utf-8");//编码
            response.setHeader("content-disposition","attachment;filename="+filename);
            List<Map<String,Object>> list = sysUsersService.exportExcel();
            //list--->excel
            Workbook workbook = new HSSFWorkbook();//空的Excel文件
            Sheet sheet = workbook.createSheet("千峰集团员工信息");
            String titles = "userId,username,email,mobile,createTime,sex";
            for (int i = 0; i < list.size(); i++) {
                Row row = sheet.createRow(i);//行
                String t[] = titles.split(",");
                Map<String,Object> map = list.get(i);
                for (int j = 0; j < t.length; j++) {
                    //单元格
                    Cell cell = row.createCell(j);
                    cell.setCellValue(map.get(t[j])+"");//给单元格赋值
                }
            }
            OutputStream os = response.getOutputStream();
            workbook.write(os);//把Excel文件响应到客户端
            os.flush();
            Lg.log("导出成功");
        }catch(Exception e){
            e.printStackTrace();
            Lg.log("导出失败");
        }
    }
}
