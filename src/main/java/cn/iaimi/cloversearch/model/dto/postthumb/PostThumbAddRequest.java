package cn.iaimi.cloversearch.model.dto.postthumb;

import java.io.Serializable;
import lombok.Data;

/**
 * 帖子点赞请求
 *
 * @author Clov614
 * @time 2024/02/24
 */
@Data
public class PostThumbAddRequest implements Serializable {

    /**
     * 帖子 id
     */
    private Long postId;

    private static final long serialVersionUID = 1L;
}