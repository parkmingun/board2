package com.study.domain.code;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import com.study.domain.post.PostRequest;
import com.study.domain.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CodeController {
    private final CodeService codeService;

    @GetMapping("/code/write.do")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Long id, Model model) {
        if (id != null) {
            CodeResponse code = codeService.findCodeById(id);
            model.addAttribute("code", code);
        }
        return "code/write";
    }

    @PostMapping("/code/save.do")
    public String savePost(final CodeRequest params, Model model) {
        codeService.saveCode(params);
        MessageDto message = new MessageDto("코드 생성이 완료되었습니다.", "/code/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }



    @GetMapping("/code/list.do")
    public String openCodeList(@ModelAttribute("params") final SearchDto params, Model model) {
        PagingResponse<CodeResponse> response = codeService.findAllCode(params);
        model.addAttribute("response",response);
        return "code/list";
    }

    // 코드 상세페이지
    @GetMapping("/code/view.do")
    public String openCodeView(@RequestParam final Long id, Model model) {
        CodeResponse code = codeService.findCodeById(id);
        model.addAttribute("code", code);
        return "code/view";
    }
    
    
  // 기존 코드 수정
    @PostMapping("/code/update.do")
    public String updateCode(final CodeRequest params, Model model) {
        codeService.updateCode(params);
        MessageDto message = new MessageDto("코드 수정이 완료되었습니다.", "/code/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 코드 삭제
    @PostMapping("/code/delete.do")
    public String deleteCode(@RequestParam final Long id, final SearchDto queryParams, Model model) {
        codeService.deleteCode(id);
        MessageDto message = new MessageDto("코드 삭제가 완료되었습니다.", "/code/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }

    private Map<String, Object> queryParamsToMap   (final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }

     private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }
}
