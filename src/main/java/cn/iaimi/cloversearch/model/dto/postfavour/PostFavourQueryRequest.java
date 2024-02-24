package cn.iaimi.cloversearch.model.dto.postfavour;

import cn.iaimi.cloversearch.model.dto.post.PostQueryRequest;
import cn.iaimi.cloversearch.common.PageRequest;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 帖子收藏查询请求
 *
 * @author Clov614
 * @time 2024/02/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PostFavourQueryRequest extends PageRequest implements Serializable {

    /**
     * 帖子查询请求
     */
    private PostQueryRequest postQueryRequest;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}