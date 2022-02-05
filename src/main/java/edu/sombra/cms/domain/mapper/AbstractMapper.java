package edu.sombra.cms.domain.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<T, R> {

    abstract R to(T entity);

    public List<R> toList(Collection<T> entityList){
        if(entityList != null){
            return entityList.stream().map(this::to).collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

}
