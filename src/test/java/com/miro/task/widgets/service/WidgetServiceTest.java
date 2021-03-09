package com.miro.task.widgets.service;

import com.miro.task.widgets.dao.WidgetDao;
import com.miro.task.widgets.exceptions.BadRequestException;
import com.miro.task.widgets.exceptions.NotFoundException;
import com.miro.task.widgets.model.Widget;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class WidgetServiceTest {

    @InjectMocks
    private WidgetService widgetService = new WidgetService();
    @Mock
    private WidgetDao widgetDao;
    private static Widget widgetTest;

    @BeforeAll
    public static void setup(){
        widgetTest = new Widget();

        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(1);
    }

    @Test
    public void createWidgetTestSuccess(){
        Integer index = 1;
        widgetTest.setIndexZ(index);

        Mockito.when(widgetDao.hasZindex(index)).thenReturn(false);

        Widget createdWidget = widgetService.createWidget(widgetTest);

        assertThat(createdWidget.getWidgetId()).isNotNull();
        assertThat(createdWidget.getIndexZ()).isEqualTo(index);
    }

    @Test
    public void createWidgetTestErrorEmpty(){
        try {
            widgetService.createWidget(null);
        }catch (BadRequestException b){
            assertThat(b.getMessage()).isEqualTo("Empty Widget");
        }
    }

    @Test
    public void getByIdTestErrorNotFound(){
        try {
            widgetService.findById("jdjdjdjdjdj");
        }catch (NotFoundException b){
            assertThat(b.getMessage()).isEqualTo("Widget not found!");
        }
    }

    @Test
    public void findAllTest(){
        Mockito.when(widgetDao.findAll()).thenReturn(new ArrayList<>());
        assertThat(widgetService.findAll()).isEmpty();
    }

    @Test
    public void findByPageSizeTest(){
        Mockito.when(widgetDao.findByPageSize(0,10)).thenReturn(new ArrayList<>());
        assertThat(widgetService.findByPage(10,0)).isEmpty();
    }

//    @Test
//    public void findByPageSizeTestUsingDefault(){
//        Mockito.when(widgetDao.findByPageSize(null)).thenReturn(new ArrayList<>());
//        assertThat(widgetService.findByPage(10)).isEmpty();
//    }

    @Test
    public void findByIdTest(){
        String id = UUID.randomUUID().toString();
        widgetTest.setWidgetId(id);
        Mockito.when(widgetDao.findById(id)).thenReturn(widgetTest);

        assertThat(widgetService.findById(id).getWidgetId()).isEqualTo(id);
    }

    @Test
    public void updateWidgetTest(){
        String id = UUID.randomUUID().toString();
        Integer index = 2;
        widgetTest.setWidgetId(id);
        Mockito.when(widgetDao.findById(id)).thenReturn(widgetTest);
        Mockito.when(widgetDao.hasZindex(index)).thenReturn(false);

        Widget testWidget = new Widget();
        testWidget.setWidgetId("dsdsds");
        testWidget.setHeight(50);
        testWidget.setWidth(50);
        testWidget.setCoordinatorX(20);
        testWidget.setCoordinatorY(20);
        testWidget.setIndexZ(index);

        Widget updated = widgetService.updateWidget(id,testWidget);

        assertThat(updated.getWidgetId()).isNotEqualTo(testWidget.getWidgetId());
        assertThat(updated.getIndexZ()).isEqualTo(testWidget.getIndexZ());
        assertThat(updated.getCoordinatorX()).isEqualTo(testWidget.getCoordinatorX());
        assertThat(updated.getCoordinatorY()).isEqualTo(testWidget.getCoordinatorY());
        assertThat(updated.getHeight()).isEqualTo(testWidget.getHeight());
        assertThat(updated.getWidth()).isEqualTo(testWidget.getWidth());
    }

    @Test
    public void createZindexTest(){
        Mockito.when(widgetDao.hasZindex(2)).thenReturn(false);
        Mockito.when(widgetDao.hasZindex(3)).thenReturn(true);

        assertThat(widgetService.createZindex(2)).isEqualTo(2);
        assertThat(widgetService.createZindex(3)).isEqualTo(3);
    }

}
