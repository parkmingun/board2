package com.study.domain.code;

import com.study.common.dto.SearchDto;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CodeMapper {

    /**
     * 코드 저장
     * @param params - 코드 정보
     */
    void save(CodeRequest params);

    /**
     * 코드 상세정보 조회
     * @param id - PK
     * @return 코드 상세정보
     */
    CodeResponse findById(Long id);
    
    /**
     * 코드 수정
     * @param params - 코드 정보
     */
    void update(CodeRequest params);

    /**
     * 코드 삭제
     * @param id - PK
     */
    void deleteById(Long id);

       /**
     * 코드 리스트 조회
     * @return 코드 리스트
     */
    List<CodeResponse> findAll(SearchDto params);

    /**
     * 코드 리스트 조회
     * @return 코드 리스트
     */
    List<CodeResponse> findCt(CodeResponse codeResponse);



    /**
     * 코드 수 카운팅
     * @return 게시글 수
     */
    int count(SearchDto params);

}