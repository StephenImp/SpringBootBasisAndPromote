package com.cn.mapstruct.mapper;

import com.cn.mapstruct.bean.GoodInfoBean;
import com.cn.mapstruct.bean.GoodTypeBean;
import com.cn.mapstruct.dto.GoodInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper这个定义一般是被广泛应用到MyBatis半自动化ORM框架上，而这里的Mapper跟Mybatis没有关系。
 * <p>
 * GoodInfoMapper是一个接口的形式存在的，当然也可以是一个抽象类，
 * 如果你需要在转换的时候才用个性化的定制的时候可以采用抽象类的方式，相应的代码配置官方文档已经声明。
 *
 * @Mapper注解是用于标注接口、抽象类是被MapStruct自动映射的标识，只有存在该注解才会将内部的接口方法自动实现。 MapStruct为我们提供了多种的获取Mapper的方式，比较常用的两种分别是
 * ①默认配置
 * <p>
 * 默认配置，我们不需要做过多的配置内容，获取Mapper的方式就是采用Mappers通过动态工厂内部反射机制完成Mapper实现类的获取。
 * 默认方式获取Mapper如下所示：
 * <p>
 * //Mapper接口内部定义
 * public static GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);
 * <p>
 * //外部调用
 * GoodInfoMapper.MAPPER.from(goodBean,goodTypeBean);
 * <p>
 * ②Spring方式配置
 * <p>
 * Spring方式我们需要在@Mapper注解内添加componentModel属性值，配置后在外部可以采用@Autowired方式注入Mapper实现类完成映射方法调用。
 * Spring方式获取Mapper如下所示：
 * <p>
 * //注解配置
 * @Mapper(componentModel = "spring")
 * <p>
 * //注入Mapper实现类
 * @Autowired private GoodInfoMapper goodInfoMapper;
 * <p>
 * //调用
 * goodInfoMapper.from(goodBean,goodTypeBean);
 */

@Mapper(componentModel = "spring")
//@Mapper
public interface GoodInfoMapper {
    //public static GoodInfoMapper INSTANCE = Mappers.getMapper(GoodInfoMapper.class);

    /**
     * source代表的是映射接口方法内的参数名称，如果是基本类型的参数，参数名可以直接作为source的内容，
     * 如果是实体类型，则可以采用实体参数名.字段名的方式作为source的内容，配置如上面GoodInfoMapper内容所示。
     * <p>
     * target代表的是映射到方法方法值内的字段名称，配置如GoodInfoMapper所示。
     * <p>
     * MapStruct根据我们配置的@Mapping注解自动将source实体内的字段进行了调用target实体内字段的setXxx方法赋值，并且做出了一切参数验证。
     * 我们采用了Spring方式获取Mapper，在自动生成的实现类上MapStruct为我们自动添加了@ComponentSpring声明式注入注解配置。
     * <p>
     * <p>
     * 如果model定义了在实体没有可以映射的属性时，就可以使用@Mapping(target = "模型属性", ignore = true)来跳过不需要映射的模型属性了。
     */
    //两个实体类合并成一个实体类，type和info 是入参变量，target对应着GoodInfoDTO的属性名
    @Mappings({
            @Mapping(source = "type.name", target = "typeName"),
            @Mapping(source = "info.id", target = "goodId"),
            @Mapping(source = "info.title", target = "goodName"),
            @Mapping(source = "info.price", target = "goodPrice")
    })
    public GoodInfoDTO from(GoodInfoBean info, GoodTypeBean type);
}
