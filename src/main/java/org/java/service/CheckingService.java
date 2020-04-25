package org.java.service;

        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import org.java.dao.CheckingMapper;
        import org.java.vo.ResultVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import javax.servlet.http.HttpSession;
        import java.util.Calendar;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;

@Service
public class CheckingService {

    @Autowired
    private CheckingMapper checkingMapper;

    @Autowired
    private HttpSession httpSession;

    public ResultVo<Map> getAllCheckInfo(String stateTime, String endTime, String branchName, String departName,Integer page,Integer limit) {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day=aCalendar.getActualMaximum(Calendar.DATE);//获取当前月的天数
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(checkingMapper.getAllCheckInfo(stateTime,endTime,branchName,departName));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        List<Map> data = pageInfo.getList();
        resultVo.setCount(pageInfo.getTotal());//总数
        List<Map> map1 = checkingMapper.getChecking();//获得出勤率
        for(int i = 0;i<data.size();i++) {
            for (int j = 0; j < map1.size(); j++) {
                Integer id = (Integer) data.get(j).get("id");
                Integer userId = (Integer) map1.get(j).get("userId");
                data.get(j).put("chuQin",
                        Integer.parseInt( map1.get(j).get("count").toString())*100.0/day+"%"
                );
//                if (id == userId){
//                    data.get(j).put("chuQin",
//                           Integer.parseInt( map1.get(j).get("count").toString())*100.0/30+"%"
//                    );
//                }
            }
        }
        //获得迟到次数
        List<Map> map2 = checkingMapper.getAllInfo();
        for (Map map : data) {
            for (Map map3 : map2) {
                Integer id = (Integer) map.get("id");
                Integer userId = (Integer) map3.get("userId");
                if (id.equals(userId)){
                    //添加一列迟到次数
                    map.put(
                            "chiDao",
                            map3.get("COUNT")
                    );
                }
            }
        }
        //获得早退次数
        List<Map> map3 = checkingMapper.getAllInfo1();
        for (Map map : data) {
            for (Map map4 : map3) {
                Integer id = (Integer) map.get("id");
                Integer userId = (Integer) map4.get("userId");
                if (id.equals(userId)){
                    //添加一列早退次数
                    map.put(
                            "zaoTui",
                            map4.get("COUNT")
                    );
                }
            }
        }

        for (Map s:data){
            int count=0;
            List<Integer> userIdss = checkingMapper.absenteeism((Integer) s.get("userId"));
            for (Integer us:userIdss){
                count+=us;
            }
            s.put(
                    "count",
                    day-count
            );
        }
        resultVo.setData(data);//集合
        return resultVo;
    }


}
