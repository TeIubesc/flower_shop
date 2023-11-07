package lz.flower;

import lz.flower.entity.Flower;
import lz.flower.entity.FlowerState;
import lz.flower.service.FlowerService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class FlowerShopApplicationTests {

    @Autowired
    private FlowerService flowerService;
    /*@Resource
    private FlowerState flowerState;*/

    @Test
    void contextLoads() {
        System.out.println(flowerService.selectByFlowerName("aaa"));
    }

    @Value("${page.flowerImg}")
    private String imgPath;    //鲜花图片存放路径

    @Test
    void test2() {
        System.out.println(imgPath);
        File file = new File(imgPath + "1666008025611.JPG");
        System.out.println(file.delete());
    }

    @Test
    void test3() {
        /*List<Flower> allReXiao = flowerService.getAllReXiao();
        System.out.println(allReXiao);*/
    }

    @Test
    void test4() {
        int count = 0;
        for (int i = 0; i <= 33; i++)
            for (int j = 0; j <= 20; j++)
                for (int k = 0; k <= 300; k++) {
                    if ((i * 3 + j * 5 + k / 3) == 100 && (i + j + k) == 100) {
                        System.out.println("公鸡:" + i + "--母鸡:" + j + "--小鸡:" + k);
                        count++;
                    }
                }
        System.out.println("共 " + count + " 种方法");
    }

    @Test
    void test5() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        String s = ids.toString();
        String substring = s.substring(1, s.lastIndexOf("]"));
        System.out.println(substring);
    }

    @Test
    void test6() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        int i = Integer.parseInt(simpleDateFormat.format(new Date()));
        System.out.println(i);

    }

    @Test
    void test7() {
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.YEAR);
        System.out.println(i);
        System.out.println(calendar.get(Calendar.MONTH) + 1);
    }
}
