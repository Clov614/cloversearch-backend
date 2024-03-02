package cn.iaimi.cloversearch.controller;

import cn.iaimi.cloversearch.common.BaseResponse;
import cn.iaimi.cloversearch.common.ResultUtils;
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

        Page<Picture> picturePage = pictureService.searchPicture(searchText, current, pageSize);

        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);
        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);

        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

        SearchVO searchVO = new SearchVO();
        searchVO.setUserPage(userVOPage);
        searchVO.setPostPage(postVOPage);
        searchVO.setPicturePage(picturePage);
        return ResultUtils.success(searchVO);
    }
}
