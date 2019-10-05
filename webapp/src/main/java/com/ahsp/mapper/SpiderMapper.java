package com.ahsp.mapper;

import com.ahsp.po.SpiderStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpiderMapper {
    List<SpiderStatus> getSpiderStatus() throws  Exception;
    SpiderStatus getSpiderStatusById(@Param("spider_id") int spider_id) throws Exception;
    void updateSpiderStatus(@Param("spider_id")int spider_id,@Param("time")String time) throws Exception;
}
