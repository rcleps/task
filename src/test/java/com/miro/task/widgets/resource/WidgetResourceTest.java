package com.miro.task.widgets.resource;

import com.miro.task.widgets.model.Widget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WidgetResourceTest {
    @Autowired
    private WidgetResource widgetResource;

    @Test
    public void createWidgetResourceTestWithConcurrency(){
        List<Widget> widgetList = new ArrayList<>();
        for(int i=0; i < 500 ; i++){
            Widget widget = new Widget();
            widget.setCoordinatorX(0);
            widget.setCoordinatorY(0);
            widget.setWidth(100);
            widget.setHeight(100);
            widget.setIndexZ(1);
            widgetList.add(widget);
        }
        widgetList.stream().parallel().forEach(widget -> widgetResource.createWidget(widget));
        List<Widget> resultList = widgetResource.getAllWidgets();
        assertThat(resultList.size()).isGreaterThanOrEqualTo(500);
    }

}
