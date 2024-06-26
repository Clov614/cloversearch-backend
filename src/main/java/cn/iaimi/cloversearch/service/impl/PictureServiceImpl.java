package cn.iaimi.cloversearch.service.impl;

import cn.hutool.json.JSONUtil;
import cn.iaimi.cloversearch.common.ErrorCode;
import cn.iaimi.cloversearch.exception.BusinessException;
import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.service.PictureService;
import cn.iaimi.cloversearch.utils.RestParamsUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/28
 */
@Service
public class PictureServiceImpl implements PictureService {


    @Override
    public Page<Picture> searchPicture(String searchTest, long pageNum, long pageSize) {
        // 判断是否为空 （查询图片不支持为空的searchText）
        searchTest = RestParamsUtils.isBlankDefault(searchTest, "风景");

        long current = (pageNum - 1) * pageSize;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%d", searchTest, current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl = (String) map.get("murl");

            Picture picture = new Picture();
            picture.setTitle(title);
            picture.setUrl(murl);
            pictures.add(picture);
            if (pictures.size() >= pageSize)
                break;
        }
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictures);
        return picturePage;
    }
}
