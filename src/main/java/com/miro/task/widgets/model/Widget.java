package com.miro.task.widgets.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Date;

@Schema
public class Widget {
    @Schema
    private String widgetId;
    @Schema(required = true, example = "1")
    private Integer coordinatorX;
    @Schema(required = true,example = "1")
    private Integer coordinatorY;
    @Schema(required = false,example = "1")
    private Integer indexZ;
    @Schema(required = true,example = "10")
    private Integer width;
    @Schema(required = true,example = "100")
    private Integer height;
    @Schema
    private LocalDateTime updatedAt;

    public String getWidgetId() {
        return widgetId;
    }

    public void setWidgetId(String widgetId) {
        this.widgetId = widgetId;
    }

    public Integer getCoordinatorX() {
        return coordinatorX;
    }

    public void setCoordinatorX(Integer coordinatorX) {
        this.coordinatorX = coordinatorX;
    }

    public Integer getCoordinatorY() {
        return coordinatorY;
    }

    public void setCoordinatorY(Integer coordinatorY) {
        this.coordinatorY = coordinatorY;
    }

    public Integer getIndexZ() {
        return indexZ;
    }

    public void setIndexZ(Integer indexZ) {
        this.indexZ = indexZ;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
