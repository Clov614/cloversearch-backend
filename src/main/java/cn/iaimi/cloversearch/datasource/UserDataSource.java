package cn.iaimi.cloversearch.datasource;

import cn.iaimi.cloversearch.model.dto.user.UserQueryRequest;
import cn.iaimi.cloversearch.model.vo.UserVO;
import cn.iaimi.cloversearch.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户资源获取类
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
@Component
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, int current, int pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserVOByPage(userQueryRequest);
    }
}
