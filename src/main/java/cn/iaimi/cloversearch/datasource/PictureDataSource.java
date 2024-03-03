package cn.iaimi.cloversearch.datasource;

import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.service.PictureService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 图片资源获取类
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
@Component
public class PictureDataSource implements DataSource<Picture> {

    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, int current, int pageSize) {
        return pictureService.searchPicture(searchText, current, pageSize);
    }
}
