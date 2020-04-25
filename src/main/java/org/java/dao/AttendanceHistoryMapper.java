package org.java.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AttendanceHistoryMapper {

    List<Map> getAllInfo(@Param("stateTime") String stateTime,
                         @Param("endTime") String endTime,
                         @Param("branchName") String branchName,
                         @Param("departName") String departName);


    List<Map> getAllDep();

    List<Map> getAllBranch();

}
