package com.miro.task.widgets.dao;

import com.miro.task.widgets.model.Widget;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class WidgetDaoTest {

    @Autowired
    private WidgetDao widgetDao;

    @Test
    public void saveAndFindByIdTest(){
        String id = UUID.randomUUID().toString();
        Widget widgetTest = new Widget();
        widgetTest.setWidgetId(id);
        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(3);

        widgetDao.save(widgetTest);

        Widget w = widgetDao.findById(id);
        assertThat(w).isNotNull();
    }

    @Test
    public void findBySizeTest(){
        widgetDao.erase();//just to make sure about the orders...

        Widget widgetTest = new Widget();
        widgetTest.setWidgetId(UUID.randomUUID().toString());
        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(1);

        widgetDao.save(widgetTest);

        Widget widgetTest2 = new Widget();
        widgetTest2.setWidgetId(UUID.randomUUID().toString());
        widgetTest2.setCoordinatorX(0);
        widgetTest2.setCoordinatorY(0);
        widgetTest2.setWidth(100);
        widgetTest2.setHeight(100);
        widgetTest2.setIndexZ(5);

        widgetDao.save(widgetTest2);

        Widget widgetTest3 = new Widget();
        widgetTest3.setWidgetId(UUID.randomUUID().toString());
        widgetTest3.setCoordinatorX(0);
        widgetTest3.setCoordinatorY(0);
        widgetTest3.setWidth(100);
        widgetTest3.setHeight(100);
        widgetTest3.setIndexZ(3);

        widgetDao.save(widgetTest3);

        List<Widget> widgetList = widgetDao.findByPageSize(0,2);
        assertThat(widgetList.get(0).getIndexZ()).isEqualTo(1);
        assertThat(widgetList.get(1).getIndexZ()).isEqualTo(3);

        widgetList = widgetDao.findByPageSize(1,1);
        assertThat(widgetList.get(0).getIndexZ()).isEqualTo(3);

        widgetList = widgetDao.findByPageSize(100,100);
        assertThat(widgetList).isEmpty();

        widgetList = widgetDao.findByPageSize(2,5);
        assertThat(widgetList.get(0).getIndexZ()).isEqualTo(5);
    }

    @Test
    public void hasZindexTest(){
        Widget widgetTest = new Widget();
        widgetTest.setWidgetId(UUID.randomUUID().toString());
        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(100);

        widgetDao.save(widgetTest);

        assertThat(widgetDao.hasZindex(100)).isTrue();
        assertThat(widgetDao.hasZindex(10000)).isFalse();
    }

    @Test
    public void deleteById(){
        String id = UUID.randomUUID().toString();
        Widget widgetTest = new Widget();
        widgetTest.setWidgetId(id);
        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(5);

        widgetDao.save(widgetTest);

        widgetDao.deleteById(id);

        assertThat(widgetDao.findById(id)).isNull();
    }

    @Test
    public void testReorderAndFindAll(){
        String id = UUID.randomUUID().toString();
        Widget widgetTest = new Widget();
        widgetTest.setWidgetId(id);
        widgetTest.setCoordinatorX(0);
        widgetTest.setCoordinatorY(0);
        widgetTest.setWidth(100);
        widgetTest.setHeight(100);
        widgetTest.setIndexZ(4);

        widgetDao.save(widgetTest);

        widgetDao.reorderZindex(4);

        List<Widget> widgetList = widgetDao.findAll();
        widgetList.forEach(widget ->  assertThat(widget.getIndexZ()).isNotEqualTo(4));
    }
}
