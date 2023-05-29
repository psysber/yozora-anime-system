package com.yozora.anime.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class SearchVo<T>  implements Serializable {

    private String sortField;

    private String order;

    private String keyword;

    private Page<T> page;

    private List<String> tags;

    private String status;
}
