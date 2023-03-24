package cc.sybx.ms.domain;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 */
public class Page<T> extends PageImpl<T> {

    public Page(){
        this(new ArrayList<T>(0));
    }

    public Page(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public Page(List content) {
        super(content);
    }
}
