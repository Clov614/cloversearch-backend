package cn.iaimi.cloversearch.adapter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 数据源接口
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
public interface DataSource<T> {

    /**
     * 搜索
     * @param searchText 搜索文本
     * @param current 分页当前页码
     * @param pageSize 分页大小
     * todo 支持排序参数
     * @return
     */
    Page<T> doSearch(String searchText, int current, int pageSize);
}
