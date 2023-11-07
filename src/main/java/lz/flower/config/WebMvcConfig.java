package lz.flower.config;

import lz.flower.utils.MyInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liuzhi
 * @Date 2022/10/2 15:33
 * MVC的相关配置，如静态资源地址映射、拦截器等等
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${page.flowerImg}")
    private String imgPath;    //鲜花图片存放路径
    @Value("${page.txImg}")
    private String txImgPath;    //用户头像存放路径
    @Value("${page.pageImg}")
    private String pageImg;    //前台展示页面静态图片存放路劲（轮播图、页面最上方的图片等等）
    @Value("${page.verifyImg}")
    private String verifyImg;    //验证码滑块图片
    @Value("${page.noticeImg}")
    private String noticeImg;    //验证码滑块图片

    /**
     * 如果页面在服务器上，浏览器出于安全考虑是不允许html访问本地文件的，需要将URL映射至location,也就是本地文件夹
     * addResourceHandler是指在url请求的路径,注意：yml文件中指定了项目名的话，前端虚拟路径引用时需要将项目名也加上
     * src="/flower_shop/image/xxx.jpg"
     * addResourceLocations是图片存放的真实路,存放在本地的文件，不是放在项目中
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) { //E:\Download\temp
        registry.addResourceHandler("/image/**").addResourceLocations("file:///" + imgPath);
//        registry.addResourceHandler("/image/**").addResourceLocations("file:///E:/Download/temp/");
        registry.addResourceHandler("/tx/**").addResourceLocations("file:///" + txImgPath);
        registry.addResourceHandler("/pageImg/**").addResourceLocations("file:///" + pageImg);
        registry.addResourceHandler("/pageImg/verify/**").addResourceLocations("file:///" + verifyImg);
        registry.addResourceHandler("/noticeImg/**").addResourceLocations("file:///" + noticeImg);
    }

    /**
     * 拦截器，拦截(放行)地址
     *
     * @Author liuzhi
     * @Date 2022/10/12 22:19
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = new ArrayList<>();
        //拦截地址
        pathPatterns.add("/evaluate/**");
        pathPatterns.add("/flower/**");
        pathPatterns.add("/notice/**");
        pathPatterns.add("/order/**");
        pathPatterns.add("/sort/**");
        pathPatterns.add("/user/**");
        pathPatterns.add("/address/**");
        pathPatterns.add("/shoppingCart/**");

        registry.addInterceptor(new MyInterceptor()).addPathPatterns(pathPatterns);
    }

    /**
     * jwt拦截器
     * */
    /*@Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }*/
}
