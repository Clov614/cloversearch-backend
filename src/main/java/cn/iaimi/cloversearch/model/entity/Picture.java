package cn.iaimi.cloversearch.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图片
 *
 * @author Clov614
 * @version 1.0
 * DATE 2024/2/28
 */
@Data
@NoArgsConstructor
public class Picture implements Serializable {

    private String title;

    private String url;

    private static final long serialVersionUID = -2634748513249613733L;
}
