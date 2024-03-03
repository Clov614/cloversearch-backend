package cn.iaimi.cloversearch.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Clov614
 * @version 1.0
 * DATE 2024/3/3
 */
public enum SearchTypeEnum {
    USER("用户", "user"),
    POST("文章", "post"),
    PICTURE("图片", "picture");

    private final String text;

    private final String value;

    SearchTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举对象
     *
     * @param value
     * @return
     */
    public static SearchTypeEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) return null;

        SearchTypeEnum[] values = values();
        for (SearchTypeEnum searchTypeEnum : values) {
            if (value.equals(searchTypeEnum.getValue())) {
                return searchTypeEnum;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
