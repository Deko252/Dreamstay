package com.bitnc4.mapper;

import com.bitnc4.dto.QnaBoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdminQnaMapper {
    public List<QnaBoardDto> getQnaList(Map map);
    public int getQnaCount();
    public QnaBoardDto getQna(int num);
}
