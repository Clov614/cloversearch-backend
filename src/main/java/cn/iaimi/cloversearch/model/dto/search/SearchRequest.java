package cn.iaimi.cloversearch.model.dto.search;

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
public class SearchRequest extends PageRequest implements Serializable {

    /**
     * 搜索词
     */
    private String searchText;


    private static final long serialVersionUID = -4302259521891493173L;
}