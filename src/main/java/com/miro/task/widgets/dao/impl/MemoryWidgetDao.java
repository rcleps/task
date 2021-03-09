package com.miro.task.widgets.dao.impl;

import com.miro.task.widgets.dao.WidgetDao;
import com.miro.task.widgets.model.Widget;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Repository
public class MemoryWidgetDao implements WidgetDao {

    private Map<String, Widget> cacheById = new ConcurrentHashMap<>();
    private SortedMap<Integer, Widget> cacheByZ = new ConcurrentSkipListMap<>();

    @Override
    public List<Widget> findAll() {
        return new ArrayList<>(cacheByZ.values());
    }

    @Override
    public List<Widget> findByPageSize(Integer skip, Integer size) {
        List<Widget> widgetList = new ArrayList<>();
        Integer finalSize = skip + size;
        if(finalSize > cacheByZ.size())
            finalSize = cacheByZ.size();
        if(finalSize < skip)
            return widgetList;
        if(cacheByZ != null && !cacheByZ.isEmpty()){
            List<Widget> resultsList = new ArrayList<>(cacheByZ.values()).subList(skip, finalSize);
            for(Widget widget : resultsList) {
                widgetList.add(widget);
            }
        }
        return widgetList;
    }

    @Override
    public Widget findById(String id) {
        return cacheById.get(id);
    }

    @Override
    public void save(Widget widget) {
        widget.setUpdatedAt(LocalDateTime.now());
        cacheById.put(widget.getWidgetId(),widget);
        cacheByZ.put(widget.getIndexZ(),widget);
    }

    @Override
    public Boolean hasZindex(Integer zIndex) {
        return cacheByZ.get(zIndex) != null;
    }

    @Override
    public Integer getZIndez() {
        return cacheByZ.lastKey() + 1;
    }

    @Override
    public void reorderZindex(Integer zIndex) {
        Map<Integer, Widget> reorderMap = cacheByZ.tailMap(zIndex);
        if (reorderMap != null && !reorderMap.isEmpty()) {
            Collection<Widget> widgetCollection = reorderMap.values();
            Iterator<Widget> widgetIterator = widgetCollection.iterator();
            List<Widget> auxMovedList = new ArrayList<>();

            while (widgetIterator.hasNext()) {
                Widget widget = widgetIterator.next();
                auxMovedList.add(widget);
                cacheByZ.remove(widget.getIndexZ());
            }

            auxMovedList.forEach(movedWidget -> {
                movedWidget.setIndexZ( movedWidget.getIndexZ() + 1);
                cacheById.put(movedWidget.getWidgetId(), movedWidget);
                cacheByZ.put(movedWidget.getIndexZ(), movedWidget);
            });
        }
    }

    @Override
    public void deleteById(String id) {
        Widget widget = cacheById.get(id);
        if(widget != null) {
            cacheById.remove(id);
            cacheByZ.remove(widget.getIndexZ());
        }
    }

    @Override
    public void erase() {
        this.cacheById = new ConcurrentHashMap<>();
        this.cacheByZ = new ConcurrentSkipListMap<>();
    }
}
