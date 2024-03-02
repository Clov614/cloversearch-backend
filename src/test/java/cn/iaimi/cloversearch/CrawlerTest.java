package cn.iaimi.cloversearch;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.model.entity.Post;
import cn.iaimi.cloversearch.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hutool.json.JSONUtil.toBean;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/26
 */
@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPassage() {

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
        System.out.println(map);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tmpRecord = (JSONObject) record;
            Post post = new Post();

            post.setTitle(tmpRecord.getStr("title"));
            post.setContent(tmpRecord.getStr("content"));
            JSONArray tags = (JSONArray) tmpRecord.get("tags");
            List<String> tagsList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagsList));
            post.setUserId(1L);
            postList.add(post);
        }
        System.out.println(postList);
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }

    @Test
    void fetchPicTest() throws IOException {
        int current = 1;
        String url = String.format("https://www.bing.com/images/search?q=嘉然&first=%d", current);
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictureList.add(picture);
        }
        System.out.println(pictureList);
    }
}
