package cn.iaimi.cloversearch.controller;

import cn.iaimi.cloversearch.common.BaseResponse;
import cn.iaimi.cloversearch.common.ResultUtils;
import cn.iaimi.cloversearch.manager.SearchFacade;
import cn.iaimi.cloversearch.model.dto.search.SearchRequest;
import cn.iaimi.cloversearch.model.vo.SearchVO;
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
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        // TODO 适配器模式(datasource)，从service中抽离出来 datasource 1.18.51
        SearchVO searchVO = searchFacade.searchAll(searchRequest, request);
        return ResultUtils.success(searchVO);
    }
}
