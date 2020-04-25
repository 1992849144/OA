package org.java.dao;

import org.apache.ibatis.annotations.Param;
import org.java.vo.ResultVo;

import java.util.List;
import java.util.Map;

public interface CheckingMapper {

    List<Map> getAllCheckInfo(@Param("stateTime") String stateTime,
                              @Param("endTime") String endTime,
                              @Param("branchName") String branchName,
                              @Param("departName") String departName);

    List<Map> getChecking();

    List<Map> getAllInfo();

    List<Map> getAllInfo1();

    /**
     * 旷工次数
     * @param userId
     * @return
     */
    List<Integer> absenteeism(Integer userId);
}
