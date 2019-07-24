package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dto.UserPageDto;
import com.baizhi.entity.Month;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.undo.CompoundEdit;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("queryByPage")
    public UserPageDto queryByPage(Integer page, Integer rows){
        UserPageDto userPageDto = userService.queryByPage(page, rows);
        return userPageDto;
    }
    @RequestMapping("edit")
    public String edit(User user, String oper, String[] id){
        if ("add".equals(oper)){
            String s=userService.insert(user);
            return s;
        } else if ("edit".equals(oper)){
            //修改
            String update = userService.update(user);
            return update;
        }else if("del".equals(oper)){
            for (String s : id) {
                userService.delete(s);
            }
        }
        return null;
    }

    @RequestMapping("fileupload")
    public void fileupload(HttpServletRequest request, MultipartFile profile, HttpServletResponse response, String id) throws IOException {
        //获取原始文件名
        String originalFilename = profile.getOriginalFilename();
        //获取文件目录的真实位置
        String path = request.getSession().getServletContext().getRealPath("profilePic");
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();

        }
        profile.transferTo(new File(path,originalFilename));
        User user = new User();
        user.setProfile(originalFilename);
        user.setId(id);
        userService.modifyprofile(user);

    }
    @RequestMapping("export")
    public void export(HttpServletResponse response) throws  Exception{
        List<User> users = userService.queryAll();
        for (User user : users) {
            user.setProfile("E:\\ideaNameSpace\\cfmz\\cfmz_wpx\\src\\main\\webapp\\profilePic\\"+user.getProfile());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("所有用户","用户表"),User.class,users);
        response.setHeader("content-disposition","attachment;fileName=user.xls");
        response.setHeader("content-Type","spplication/vnd.ms-excel");

        workbook.write(response.getOutputStream());

        workbook.close();

    }
    @RequestMapping("import")
    public void importFile(MultipartFile file) throws Exception{
        HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
        //获取第一个表
        HSSFSheet sheet = workbook.getSheetAt(0);
        //获取一共有多少行
        int lastRowNum = sheet.getLastRowNum();

        //创建一个集合去接收
        List<User> users = new ArrayList<>();
        for (int i = 0; i < lastRowNum; i++) {
            User user = new User();

            Row row = sheet.getRow(i + 1);

            Cell cell = row.getCell(0);
            cell.setCellType(CellType.STRING);
            user.setId(cell.getStringCellValue());

            Cell cell1 = row.getCell(1);
            cell1.setCellType(CellType.STRING);
            user.setPhone(cell1.getStringCellValue());

            Cell cell2 = row.getCell(2);
            cell2.setCellType(CellType.STRING);
            user.setPassword(cell2.getStringCellValue());

            Cell cell3 = row.getCell(3);
            cell3.setCellType(CellType.STRING);
            user.setSalt(cell3.getStringCellValue());

            Cell cell4 = row.getCell(4);
            cell4.setCellType(CellType.STRING);
            user.setDharmaName(cell4.getStringCellValue());

            Cell cell5 = row.getCell(5);
            cell5.setCellType(CellType.STRING);
            user.setProvince(cell5.getStringCellValue());

            Cell cell6 = row.getCell(6);
            cell6.setCellType(CellType.STRING);
            user.setCity(cell6.getStringCellValue());

            Cell cell7 = row.getCell(7);
            cell7.setCellType(CellType.STRING);
            user.setGender(cell7.getStringCellValue());

            Cell cell8 = row.getCell(8);
            cell8.setCellType(CellType.STRING);
            user.setPersonalSign(cell8.getStringCellValue());

            Cell cell9 = row.getCell(9);
            cell9.setCellType(CellType.STRING);
            user.setStatus(cell9.getStringCellValue());

            Cell cell10 = row.getCell(10);
            user.setRegistTime(cell10.getDateCellValue());

            users.add(user);

        }
        for (User user : users) {
            userService.insert(user);
        }

    }
    @RequestMapping("regist")
    public Map<String, Object> regist(String phone,String password,String dharmaName,String province,String city,String gender,String personalSign,String profile){
        User user1 = userService.queryByPhone(phone);
        if(user1==null){
            user1 = new User();
            user1.setPhone(phone);
            user1.setPassword(password);
            user1.setDharmaName(dharmaName);
            user1.setProvince(province);
            user1.setCity(city);
            user1.setGender(gender);
            user1.setPersonalSign(personalSign);
            user1.setProfile(profile);
            Map<String, Object> regist = userService.regist(user1);
            return regist;
        }else{
            HashMap<String, Object> map = new HashMap<>();
            map.put("error","400");
            map.put("message","手机号已存在");
            return map;
        }

    }
    @RequestMapping("login")
    public Map<String,Object> login(String phone,String password,String code){
        System.out.println("_____________________________________");
        Map<String, Object> login = userService.login(phone, password,code);
        System.out.println(login);
        return login;
    }
    @RequestMapping("getCode")
    @ResponseBody
    public String getCode(String phone){
        String code = userService.getCode(phone);
        return code;
    }


    @RequestMapping("queryByMonth")
    @ResponseBody
    public  Map<String,Object> queryByMonth(){
        Map<String,Object> list = userService.selectByMouth();
        return list;
    }




}
