package cn.iaimi.cloversearch.job.once;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iaimi.cloversearch.esdao.PostEsDao;
import cn.iaimi.cloversearch.model.dto.post.PostEsDTO;
import cn.iaimi.cloversearch.model.entity.Post;
import cn.iaimi.cloversearch.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 单次获取帖子列表
 *
 * @author Clov614
 * @time 2024/02/24
 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;


    @Override
    public void run(String... args) {
        String json = "{\"sortField\": \"createTime\",\n" +
                "  \"sortOrder\": \"descend\",\n" +
                "  \"reviewStatus\": 1,\n" +
                "  \"current\": 1\n" +
                "}";
        String res = HttpRequest.post("https://www.code-nav.cn/api/post/list/page/vo")
                .body(json)
                .execute().body();
//        System.out.println(res);

        Map<String, Object> map = JSONUtil.toBean(res, Map.class);
//        System.out.println(map);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tmpRecord = (JSONObject) record;
            Post post = new Post();
            // TODO 取值过程需要判空
            post.setTitle(tmpRecord.getStr("title"));
            post.setContent(tmpRecord.getStr("content"));
            JSONArray tags = (JSONArray) tmpRecord.get("tags");
            List<String> tagsList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagsList));
            post.setUserId(1L);
            postList.add(post);
        }
//        System.out.println(postList);
        boolean b = postService.saveBatch(postList);
        if (b) {
            log.info("获取帖子列表成功！条数={}", postList.size());
        } else {
            log.error("保存帖子列表失败");
        }
    }
}
