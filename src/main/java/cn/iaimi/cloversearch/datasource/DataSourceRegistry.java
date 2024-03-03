package cn.iaimi.cloversearch.datasource;

import cn.iaimi.cloversearch.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一注册器 (datasource)
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
@Component
public class DataSourceRegistry {

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    private Map<String, DataSource<T>> typeDataSourcesMap;

    @PostConstruct // 该注解确保依赖注入后方法才调用
    public void doInit() {
        typeDataSourcesMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type) {
        if (typeDataSourcesMap == null) {
            return null;
        }
        return typeDataSourcesMap.get(type);
    }
}
