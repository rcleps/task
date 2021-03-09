package com.miro.task.widgets.resource;

import com.miro.task.widgets.model.Widget;
import com.miro.task.widgets.service.WidgetService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/widget")
public class WidgetResource {

    @Autowired
    private WidgetService widgetService;

    @Operation(summary = "Find all widgets")
    @GetMapping(path = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Widget> getAllWidgets(){
        return widgetService.findAll();
    }

    @Operation(summary = "Find widgets by page, size is the ammount and page is the page number, starts from 0")
    @GetMapping(path = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Widget> getWidgetsByPage(@RequestParam(required = false) Integer size,
                                         @RequestParam(required = false) Integer pageNumber){
        return widgetService.findByPage(size,pageNumber);
    }

    @Operation(summary = "Find widgets by ID")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Widget getWidgetById(@PathVariable String id){
        return widgetService.findById(id);
    }

    @Operation(summary = "Create widgets")
    @PostMapping(path = "/",consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Widget createWidget(@RequestBody Widget widget){
        return widgetService.createWidget(widget);
    }

    @Operation(summary = "Update widgets by ID")
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Widget updateWidget(@PathVariable String id,
                               @RequestBody Widget widget){
        return widgetService.updateWidget(id,widget);
    }

    @Operation(summary = "Delete widgets by ID")
    @DeleteMapping(path = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteWidget(@PathVariable String id){
        widgetService.deleteWidget(id);
    }
}
