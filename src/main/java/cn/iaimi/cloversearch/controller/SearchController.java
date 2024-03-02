package cn.iaimi.cloversearch.controller;

import cn.iaimi.cloversearch.common.BaseResponse;
import cn.iaimi.cloversearch.common.ErrorCode;
import cn.iaimi.cloversearch.common.ResultUtils;
import cn.iaimi.cloversearch.exception.BusinessException;
import cn.iaimi.cloversearch.model.dto.post.PostQueryRequest;
import cn.iaimi.cloversearch.model.dto.search.SearchRequest;
import cn.iaimi.cloversearch.model.dto.user.UserQueryRequest;
import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.model.vo.PostVO;
import cn.iaimi.cloversearch.model.vo.SearchVO;
import cn.iaimi.cloversearch.model.vo.UserVO;
import cn.iaimi.cloversearch.service.PictureService;
import cn.iaimi.cloversearch.service.PostService;
import cn.iaimi.cloversearch.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索接口
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/28
 */
@RestController
@Slf4j
@RequestMapping("/search")
public class SearchController {

    @Resource
    private PictureService pictureService;

    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String searchText = searchRequest.getSearchText();
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();

        // 异步任务优化
        CompletableFuture<Page<Picture>> picTask = CompletableFuture.supplyAsync(
                () -> pictureService.searchPicture(searchText, current, pageSize));

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(
                () -> {
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    userQueryRequest.setCurrent(current);
                    userQueryRequest.setPageSize(pageSize);
                    return userService.listUserVOByPage(userQueryRequest);
                });


        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(
                () -> {
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    postQueryRequest.setCurrent(current);
                    postQueryRequest.setPageSize(pageSize);
                    return postService.listPostVOByPage(postQueryRequest, request);
                });

        CompletableFuture.allOf(userTask, picTask, postTask).join();

        try {
            Page<UserVO> userVOPage = userTask.get();
            Page<Picture> picturePage = picTask.get();
            Page<PostVO> postVOPage = postTask.get();
            SearchVO searchVO = new SearchVO();
            searchVO.setUserPage(userVOPage);
            searchVO.setPostPage(postVOPage);
            searchVO.setPicturePage(picturePage);
            return ResultUtils.success(searchVO);
        } catch (Exception e) {
            log.error("async task error: ", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
