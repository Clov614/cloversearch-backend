package cn.iaimi.cloversearch.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author Clov614
 * @time 2024/02/24
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}