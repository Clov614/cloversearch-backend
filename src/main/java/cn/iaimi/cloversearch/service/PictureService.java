package cn.iaimi.cloversearch.service;

import cn.iaimi.cloversearch.model.entity.Picture;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/28
 */
public interface PictureService {
    Page<Picture> searchPicture(String searchTest, long pageNum, long pageSize);
}
