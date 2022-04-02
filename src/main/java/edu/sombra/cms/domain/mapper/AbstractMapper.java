package edu.sombra.cms.domain.mapper;

import edu.sombra.cms.domain.entity.EntityClass;
import edu.sombra.cms.messages.SomethingWentWrongException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractMapper<T, R> {

    abstract R to(T entity) throws SomethingWentWrongException;

    public List<R> toList(Collection<T> entityList) throws SomethingWentWrongException {
        if(entityList != null){
            List<R> res = new ArrayList<>();
            for (T t : entityList) {
                res.add(to(t));
            }
            return res;
        }

        return Collections.emptyList();
    }

    public static <T extends EntityClass> List<Long> entitiesToIds(Collection<T> entities){
        return entities.stream().map(EntityClass::getId).collect(Collectors.toList());
    }

}
