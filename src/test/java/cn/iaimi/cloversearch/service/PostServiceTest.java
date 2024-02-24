package cn.iaimi.cloversearch.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.iaimi.cloversearch.model.dto.post.PostQueryRequest;
import cn.iaimi.cloversearch.model.entity.Post;
import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子服务测试
 *
 * @author Clov614
 * @time 2024/02/24
 */
@SpringBootTest
class PostServiceTest {

    @Resource
    private PostService postService;

    @Test
    void searchFromEs() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setUserId(1L);
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        Assertions.assertNotNull(postPage);
    }

}