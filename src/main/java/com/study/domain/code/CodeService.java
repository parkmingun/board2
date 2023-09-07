package com.study.domain.code;

import com.study.common.dto.SearchDto;
import com.study.common.paging.Pagination;
import com.study.common.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeMapper codeMapper;

    @Transactional
    public Long saveCode(final CodeRequest params){
        codeMapper.save(params);
        return params.getId();
    }

    public CodeResponse findCodeById(final Long id){
        return codeMapper.findById(id);
    }

     @Transactional
    public Long updateCode(final CodeRequest params) {
        codeMapper.update(params);
        return params.getId();
    }

    public Long deleteCode(final Long id) {
        codeMapper.deleteById(id);
        return id;
    }

    public PagingResponse<CodeResponse> findAllCode(final SearchDto params){

        //조건에 해당하는 데이터가 없는 경우, 응답 데이터에 비어있는 리스트와 null을 담아 반환
        int count = codeMapper.count(params);
        if(count <1) {
            return new PagingResponse<>(Collections.emptyList(),null);
        }

        //pagination 객체를 생성해서 페이지 정보 계산 후 SerachDto 타입의 객체인 params에 계산된 페이지 정보 저장
        Pagination pagination = new Pagination(count, params);
        params.setPagination(pagination);

        //계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 응답 데이터 반환
        List<CodeResponse> list = codeMapper.findAll(params);
        return new PagingResponse<>(list, pagination);
    }

    public List<CodeResponse> findCtCode(CodeResponse codeResponse){
        return codeMapper.findCt(codeResponse);
    }
}
