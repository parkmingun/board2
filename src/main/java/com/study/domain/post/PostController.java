package com.study.domain.post;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.common.paging.PagingResponse;
import com.study.domain.code.CodeResponse;
import com.study.domain.code.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CodeService codeService;


    // 게시글 작성 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(@RequestParam(value = "id", required = false) final Long id, Model model) {
        CodeResponse codeResponse= new CodeResponse();
        codeResponse.setCdGroup("주문상태");
        List<CodeResponse> codes = codeService.findCtCode(codeResponse);
        model.addAttribute("codes",codes);
        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
           
        }
        return "post/write";
    }

    
        // 신규 게시글 생성
    @PostMapping("/post/save.do")
    public String savePost(final PostRequest params, Model model) {
        postService.savePost(params);
        MessageDto message = new MessageDto("게시글 생성이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }



    // 게시글 리스트 페이지
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") final SearchDto params,Model model) {
        PagingResponse<PostResponse> response = postService.findAllPost(params);
        CodeResponse codeResponse= new CodeResponse();
        codeResponse.setCdGroup("주문상태");
        List<CodeResponse> codes = codeService.findCtCode(codeResponse);
        model.addAttribute("response", response);
        model.addAttribute("codes",codes);
        return "post/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/post/view.do")
    public String openPostView(@RequestParam final Long id, Model model) {
        PostResponse post = postService.findPostById(id);
        model.addAttribute("post", post);
        return "post/view";
    }

     // 기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, Model model) {
        postService.updatePost(params);
        MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/post/list.do", RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
    }

    // 게시글 삭제
    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam(required = false) final List<String> idList,
                             @RequestParam(required = false) final String id,final SearchDto queryParams, Model model) {

        // idList (리스트에서 선택삭제한 경우)
        if(idList !=null){
                for(int i=0; i<idList.size(); i++){
                Long a = Long.valueOf(idList.get(i));
                 postService.deletePost(a);
            }
        }
        // id (게시글 상세에서 삭제한 경우)
          if (id != null) {             // id!= null 을 하려면 id 가 Long 이 아닌 String을 받아야함
              Long a = Long.valueOf(id);
            postService.deletePost(a);
          }
        MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/post/list.do", RequestMethod.GET, queryParamsToMap(queryParams));
        return showMessageAndRedirect(message, model);
    }
        // 쿼리 스트링 파라미터를 Map에 담아 반환
    private Map<String, Object> queryParamsToMap   (final SearchDto queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }



    // 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
    private String showMessageAndRedirect(final MessageDto params, Model model) {
        model.addAttribute("params", params);
        return "common/messageRedirect";
    }

}