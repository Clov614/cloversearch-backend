package cn.iaimi.cloversearch.model.vo;

import cn.iaimi.cloversearch.model.entity.Picture;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;

/**
 * 聚合搜索
 *
 * @author Clov614
 * @time 2024/02/24
 */
@Data
public class SearchVO implements Serializable {

    private Page<UserVO> userPage;

    private Page<PostVO> postPage;

    private Page<Picture> picturePage;

    private Page<Object> dataPage;

    private static final long serialVersionUID = -2948719107564307882L;
}