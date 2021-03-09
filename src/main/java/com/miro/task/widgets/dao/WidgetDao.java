package com.miro.task.widgets.dao;

import com.miro.task.widgets.model.Widget;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WidgetDao {

    List<Widget> findAll();

    List<Widget> findByPageSize(Integer skip, Integer size);

    Widget findById(String id);

    void save(Widget widget);

    Boolean hasZindex(Integer zIndex);

    Integer getZIndez();

    void reorderZindex(Integer zIndex);

    void deleteById(String id);

    void erase();
}
