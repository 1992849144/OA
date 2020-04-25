package org.java.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.java.dao.AttendanceHistoryMapper;
import org.java.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceHistoryService {

    @Autowired
    private AttendanceHistoryMapper attendanceHistoryMapper;

    public ResultVo<Map> getAllInfo(String stateTime,String endTime,String branchName,String departName,Integer page,Integer limit) {
        PageHelper.startPage(page,limit);
        PageInfo<Map> pageInfo = new PageInfo<>(attendanceHistoryMapper.getAllInfo(stateTime,endTime,branchName,departName));
        //将查询结果，封装成ResultVo
        ResultVo<Map> resultVo = new ResultVo<>();
        resultVo.setData(pageInfo.getList());//集合
        resultVo.setCount(pageInfo.getTotal());//总数
        return resultVo;
    }


    public  List<Map> getAllDep() {
        return attendanceHistoryMapper.getAllDep();
    }


    public  List<Map> getAllBranch() {
        return attendanceHistoryMapper.getAllBranch();
    }
}
