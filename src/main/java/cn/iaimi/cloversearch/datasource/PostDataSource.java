package cn.iaimi.cloversearch.datasource;

import cn.iaimi.cloversearch.model.dto.post.PostQueryRequest;
import cn.iaimi.cloversearch.model.vo.PostVO;
import cn.iaimi.cloversearch.service.PostService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子资源获取类
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
@Component
public class PostDataSource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, int current, int pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);

        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();

        return postService.listPostVOByPage(postQueryRequest, request);
    }
}
