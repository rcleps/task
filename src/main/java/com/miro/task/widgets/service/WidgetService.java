package com.miro.task.widgets.service;

import com.miro.task.widgets.dao.WidgetDao;
import com.miro.task.widgets.exceptions.BadRequestException;
import com.miro.task.widgets.exceptions.NotFoundException;
import com.miro.task.widgets.model.Widget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class WidgetService {

    @Autowired
    private WidgetDao widgetDao;

    private final Lock reentrantLock;
    private final Lock readLock;
    private final Lock writeLock;

    @Autowired
    public WidgetService() {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        this.writeLock = readWriteLock.writeLock();
        this.readLock = readWriteLock.readLock();
        this.reentrantLock = new ReentrantLock();
    }

    public List<Widget> findAll() {
        readLock.lock();
        try {
            return widgetDao.findAll();
        }finally {
            readLock.unlock();
        }
    }

    public List<Widget> findByPage(Integer size, Integer pageNumber) {
        if(size == null)
            size = 10;
        if(pageNumber == null || pageNumber < 0)
            pageNumber = 0;
        readLock.lock();
        try {
            return widgetDao.findByPageSize(size * pageNumber ,size);
        }finally {
            readLock.unlock();
        }
    }

    public Widget findById(String id) {
        if(id == null){
            throw new BadRequestException("Invalid Widget ID");
        }
        Widget widget = null;
        readLock.lock();
        try {
            widget = widgetDao.findById(id);
        } finally {
            readLock.unlock();
        }
        if(widget == null)
            throw new NotFoundException("Widget not found!");
        return widget;
    }

    public Widget createWidget(Widget widget) {
        if(widget == null){
            throw new BadRequestException("Empty Widget");
        }
        if(widget.getHeight() == null || widget.getHeight() < 0){
            throw new BadRequestException("Invalid/Empty height!");
        }
        if(widget.getWidth() == null || widget.getWidth() < 0){
            throw new BadRequestException("Invalid/Empty width!");
        }
        if(widget.getCoordinatorY() == null ){
            throw new BadRequestException("Invalid Y!");
        }
        if(widget.getCoordinatorX() == null ){
            throw new BadRequestException("Invalid X!");
        }
        widget.setWidgetId(UUID.randomUUID().toString());
        try {
            reentrantLock.lock();
            if (widget.getIndexZ() == null) {
                widget.setIndexZ(widgetDao.getZIndez());
            } else {
                widget.setIndexZ(createZindex(widget.getIndexZ()));
            }
            widgetDao.save(widget);
            return widget;
        }finally {
            reentrantLock.unlock();
        }
    }

    public Widget updateWidget(String id, Widget widget) {
        if(id == null){
            throw new BadRequestException("Invalid Widget ID");
        }

        reentrantLock.lock();
        try {
            Widget currentWidget = findById(id);

            if(widget.getIndexZ() != null){
                currentWidget.setIndexZ(createZindex(widget.getIndexZ()));
            }
            if(widget.getCoordinatorX() != null){
                currentWidget.setCoordinatorX(widget.getCoordinatorX());
            }
            if(widget.getCoordinatorY() != null){
                currentWidget.setCoordinatorY(widget.getCoordinatorY());
            }
            if(widget.getWidth() != null){
                currentWidget.setWidth(widget.getWidth());
            }
            if(widget.getHeight() != null){
                currentWidget.setHeight(widget.getHeight());
            }

            widgetDao.save(currentWidget);

            return currentWidget;
        }finally {
            reentrantLock.unlock();
        }
    }

    public Integer createZindex(Integer zIndex) {
        if (widgetDao.hasZindex(zIndex)) {
            widgetDao.reorderZindex(zIndex);
        }
        return zIndex;
    }

    public void deleteWidget(String id) {
        reentrantLock.lock();
        try {
            widgetDao.deleteById(id);
        }finally {
            reentrantLock.unlock();
        }
    }
}
