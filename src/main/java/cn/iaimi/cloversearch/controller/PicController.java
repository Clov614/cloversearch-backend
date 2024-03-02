package cn.iaimi.cloversearch.controller;

import cn.iaimi.cloversearch.common.BaseResponse;
import cn.iaimi.cloversearch.common.ErrorCode;
import cn.iaimi.cloversearch.common.ResultUtils;
import cn.iaimi.cloversearch.exception.ThrowUtils;
import cn.iaimi.cloversearch.model.dto.pic.PictureQueryRequest;
import cn.iaimi.cloversearch.model.entity.Picture;
import cn.iaimi.cloversearch.service.PictureService;
import cn.iaimi.cloversearch.utils.RestParamsUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/28
 */
@RestController
@Slf4j
@RequestMapping("/pic")
public class PicController {

    @Resource
    private PictureService pictureService;

    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        int pageSize = pictureQueryRequest.getPageSize();
        // TODO 优化为Redis缓存
        // TODO 改为数据库获取
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR, "获取数量不能超过20条");
        String searchTest = pictureQueryRequest.getSearchText();
        searchTest = RestParamsUtils.isBlankDefault(searchTest, "风景");
        Page<Picture> picturePage = pictureService.searchPicture(searchTest, current, pageSize);
        return ResultUtils.success(picturePage);
    }
}
