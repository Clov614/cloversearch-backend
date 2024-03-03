package cn.iaimi.cloversearch.manager;

import cn.iaimi.cloversearch.common.ErrorCode;
import cn.iaimi.cloversearch.datasource.*;
import cn.iaimi.cloversearch.exception.BusinessException;
import cn.iaimi.cloversearch.exception.ThrowUtils;
import cn.iaimi.cloversearch.model.dto.search.SearchRequest;
import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.model.enums.SearchTypeEnum;
import cn.iaimi.cloversearch.model.vo.PostVO;
import cn.iaimi.cloversearch.model.vo.SearchVO;
import cn.iaimi.cloversearch.model.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * 搜索门面
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
@Component
@Slf4j
public class SearchFacade {

    @Resource
    private DataSourceRegistry dataSourceRegistry;

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    /**
     * 搜索 manager
     *
     * @param searchRequest
     * @param request
     * @return
     */
    public SearchVO searchAll(SearchRequest searchRequest, HttpServletRequest request) {

        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
//        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
//        ThrowUtils.throwIf(null == searchTypeEnum, ErrorCode.PARAMS_ERROR);

        String searchText = searchRequest.getSearchText();


        if (null != searchTypeEnum) {
            return getSearchVOBaseResponseByType(request, searchTypeEnum, searchText, searchRequest);
        } else {
            return getSearchAllVOBaseResponse(searchRequest, request, searchText);
        }


    }

    @NotNull
    private SearchVO getSearchVOBaseResponseByType(HttpServletRequest request,
                                                   SearchTypeEnum searchTypeEnum,
                                                   String searchText,
                                                   SearchRequest searchRequest) {
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();

        DataSource dataSource = dataSourceRegistry.getDataSourceByType(searchTypeEnum.getValue());
        ThrowUtils.throwIf(null == dataSource, ErrorCode.PARAMS_ERROR, "searchType 没有匹配项");

        SearchVO searchVO = new SearchVO();
        searchVO.setDataPage(dataSource.doSearch(searchText, current, pageSize));
        return searchVO;
    }

    @NotNull
    private SearchVO getSearchAllVOBaseResponse(SearchRequest searchRequest,
                                                HttpServletRequest request,
                                                String searchText) {
        int current = searchRequest.getCurrent();
        int pageSize = searchRequest.getPageSize();

        // 异步任务优化
        CompletableFuture<Page<Picture>> picTask = CompletableFuture.supplyAsync(
                () -> pictureDataSource.doSearch(searchText, current, pageSize));

        CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(
                () -> userDataSource.doSearch(searchText, current, pageSize));


//        CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(
//                () -> postDataSource.doSearch(searchText, current, pageSize));

        CompletableFuture.allOf(userTask, picTask).join();

        try {
            Page<UserVO> userVOPage = userTask.get();
            Page<Picture> picturePage = picTask.get();
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
            SearchVO searchVO = new SearchVO();
            searchVO.setUserPage(userVOPage);
            searchVO.setPostPage(postVOPage);
            searchVO.setPicturePage(picturePage);
            return searchVO;
        } catch (Exception e) {
            log.error("async task error: ", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
