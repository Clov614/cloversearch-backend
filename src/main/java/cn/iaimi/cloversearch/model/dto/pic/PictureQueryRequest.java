package cn.iaimi.cloversearch.model.dto.pic;

import cn.iaimi.cloversearch.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author Clov614
 * @time 2024/02/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private String searchText;


    private static final long serialVersionUID = 1112960746609481455L;
}