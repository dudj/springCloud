package com.linziyi.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 将一年内的所有假日插入到假日表里
 * @author lkk
 * @time 2019-07-05 下午2:06:11
 */
public class Holiday {
    public static void main(String[] args){
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //要插入的数据库，表
        String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8";
        String user = "root";
        String password = "root";
        try {
            //加载驱动程序
            Class.forName(driver);
            //连续MySQL 数据库
            Connection conn = DriverManager.getConnection(url, user, password);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //statement用来执行SQL语句
            Statement statement = conn.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date start = sdf.parse("2019-07-05");//开始时间
            java.util.Date end = sdf.parse("2019-12-31");//结束时间
            List<Date> lists = dateSplit(start, end);

            //-------------------插入周末时间---------------
            if (!lists.isEmpty()) {
                for (Date date : lists) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
                    {
                        System.out.println("插入日期:" + sdf.format(date) + ",周末");
                        String insertSql = "INSERT INTO holiday (remark,holiday_time) VALUES("+"'周末','"+sdf.format(date)+"')";
                        statement.executeUpdate(insertSql);
                    }
                }
            }
            //---------------插入节假日时间------------------
            List<Days> holidays = new ArrayList<Days>();
            holidays.add(new Days(UUID.randomUUID().toString(),"元旦", sdf.parse("2019-01-01")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-04")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-05")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-06")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-07")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-08")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-09")));
            holidays.add(new Days(UUID.randomUUID().toString(),"春节", sdf.parse("2019-02-10")));

            holidays.add(new Days(UUID.randomUUID().toString(),"清明节", sdf.parse("2019-04-05")));
            holidays.add(new Days(UUID.randomUUID().toString(),"清明节", sdf.parse("2019-04-06")));
            holidays.add(new Days(UUID.randomUUID().toString(),"清明节", sdf.parse("2019-04-07")));

            holidays.add(new Days(UUID.randomUUID().toString(),"劳动节", sdf.parse("2019-05-01")));
            holidays.add(new Days(UUID.randomUUID().toString(),"劳动节", sdf.parse("2019-05-02")));
            holidays.add(new Days(UUID.randomUUID().toString(),"劳动节", sdf.parse("2019-05-03")));
            holidays.add(new Days(UUID.randomUUID().toString(),"劳动节", sdf.parse("2019-05-04")));

            holidays.add(new Days(UUID.randomUUID().toString(),"端午节", sdf.parse("2019-06-07")));
            holidays.add(new Days(UUID.randomUUID().toString(),"端午节", sdf.parse("2019-06-08")));
            holidays.add(new Days(UUID.randomUUID().toString(),"端午节", sdf.parse("2019-06-09")));

            holidays.add(new Days(UUID.randomUUID().toString(),"中秋节", sdf.parse("2019-09-13")));
            holidays.add(new Days(UUID.randomUUID().toString(),"中秋节", sdf.parse("2019-09-14")));
            holidays.add(new Days(UUID.randomUUID().toString(),"中秋节", sdf.parse("2019-09-15")));

            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-01")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-02")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-03")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-04")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-05")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-06")));
            holidays.add(new Days(UUID.randomUUID().toString(),"国庆节", sdf.parse("2019-10-07")));
            for(Days day:holidays) {
                //跟周末冲突的，不重复插入
                String sql = "select count(1) as numbers from holiday where holiday_time ='" + sdf.format(day.getDate()) + "'";
                //结果集
                ResultSet rs = statement.executeQuery(sql);
                boolean hasRecord = false;
                while(rs.next()) {
                    if(!"0".equals(rs.getString("numbers"))) {
                        hasRecord = true;
                    }
                }
                if(!hasRecord) {
                    System.out.println("插入日期：" + sdf.format(day.getDate()) + "," + day.getTitle());
                    String insertSql = "INSERT INTO holiday (remark,holiday_time) VALUES('"+day.getTitle()+"','"+sdf.format(day.getDate())+"')";
                    statement.executeUpdate(insertSql);
                }
            }


            //-------------- 剔除补班时间(周末需要补班的)---------------------
            List<Days> workDays = new ArrayList<Days>();
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-02-02")));
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-02-03")));
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-04-28")));
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-05-05")));
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-09-29")));
            workDays.add(new Days(UUID.randomUUID().toString(),"补班", sdf.parse("2019-10-12")));

            for(Days day:workDays) {
                System.out.println("剔除日期：" + sdf.format(day.getDate()) + "," + day.getTitle());
                String delSql = "delete from holiday where holiday_time ='" + sdf.format(day.getDate()) + "'";
                statement.executeUpdate(delSql);
            }
            conn.close();
        }
        catch(ClassNotFoundException e) {
            System.out.println("Sorry,can't find the Driver!");
            e.printStackTrace();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一年内所有的天数
     * @param start
     * @param end
     * @return
     * @throws Exception
     */
    private static List<Date> dateSplit(java.util.Date start, Date end)
            throws Exception {
        if (!start.before(end))
            throw new Exception("开始时间应该在结束时间之后");
        Long spi = end.getTime() - start.getTime();
        Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数


        List<Date> dateList = new ArrayList<Date>();
        dateList.add(end);
        for (int i = 1; i <= step; i++) {
            dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));// 比上一天减一
        }
        return dateList;
    }
}
