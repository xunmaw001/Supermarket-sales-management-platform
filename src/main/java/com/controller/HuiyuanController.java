
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 会员
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/huiyuan")
public class HuiyuanController {
    private static final Logger logger = LoggerFactory.getLogger(HuiyuanController.class);

    private static final String TABLE_NAME = "huiyuan";

    @Autowired
    private HuiyuanService huiyuanService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private CartService cartService;//购物车
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private GonggaoService gonggaoService;//公告
    @Autowired
    private GongyingshangService gongyingshangService;//供应商信息
    @Autowired
    private ShangpinService shangpinService;//商品
    @Autowired
    private ShangpinChuruInoutService shangpinChuruInoutService;//出入库
    @Autowired
    private ShangpinChuruInoutListService shangpinChuruInoutListService;//出入库详情
    @Autowired
    private ShangpinOrderService shangpinOrderService;//商品订单
    @Autowired
    private YonghuService yonghuService;//用户
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = huiyuanService.queryPage(params);

        //字典表数据转换
        List<HuiyuanView> list =(List<HuiyuanView>)page.getList();
        for(HuiyuanView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        HuiyuanEntity huiyuan = huiyuanService.selectById(id);
        if(huiyuan !=null){
            //entity转view
            HuiyuanView view = new HuiyuanView();
            BeanUtils.copyProperties( huiyuan , view );//把实体数据重构到view中
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .eq("huiyuan_name", huiyuan.getHuiyuanName())
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            .eq("sex_types", huiyuan.getSexTypes())
            .eq("huiyuan_email", huiyuan.getHuiyuanEmail())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if(huiyuanEntity==null){
            huiyuan.setCreateTime(new Date());
            huiyuanService.insert(huiyuan);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());
        HuiyuanEntity oldHuiyuanEntity = huiyuanService.selectById(huiyuan.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(huiyuan.getHuiyuanPhoto()) || "null".equals(huiyuan.getHuiyuanPhoto())){
                huiyuan.setHuiyuanPhoto(null);
        }

            huiyuanService.updateById(huiyuan);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<HuiyuanEntity> oldHuiyuanList =huiyuanService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        huiyuanService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //.eq("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        try {
            List<HuiyuanEntity> huiyuanList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            HuiyuanEntity huiyuanEntity = new HuiyuanEntity();
//                            huiyuanEntity.setHuiyuanUuidNumber(data.get(0));                    //会员编号 要改的
//                            huiyuanEntity.setHuiyuanName(data.get(0));                    //会员姓名 要改的
//                            huiyuanEntity.setHuiyuanPhone(data.get(0));                    //会员手机号 要改的
//                            huiyuanEntity.setHuiyuanIdNumber(data.get(0));                    //会员身份证号 要改的
//                            huiyuanEntity.setHuiyuanPhoto("");//详情和图片
//                            huiyuanEntity.setSexTypes(Integer.valueOf(data.get(0)));   //性别 要改的
//                            huiyuanEntity.setNewMoney(data.get(0));                    //余额 要改的
//                            huiyuanEntity.setHuiyuanEmail(data.get(0));                    //会员邮箱 要改的
//                            huiyuanEntity.setCreateTime(date);//时间
                            huiyuanList.add(huiyuanEntity);


                            //把要查询是否重复的字段放入map中
                                //会员编号
                                if(seachFields.containsKey("huiyuanUuidNumber")){
                                    List<String> huiyuanUuidNumber = seachFields.get("huiyuanUuidNumber");
                                    huiyuanUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> huiyuanUuidNumber = new ArrayList<>();
                                    huiyuanUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("huiyuanUuidNumber",huiyuanUuidNumber);
                                }
                                //会员手机号
                                if(seachFields.containsKey("huiyuanPhone")){
                                    List<String> huiyuanPhone = seachFields.get("huiyuanPhone");
                                    huiyuanPhone.add(data.get(0));//要改的
                                }else{
                                    List<String> huiyuanPhone = new ArrayList<>();
                                    huiyuanPhone.add(data.get(0));//要改的
                                    seachFields.put("huiyuanPhone",huiyuanPhone);
                                }
                                //会员身份证号
                                if(seachFields.containsKey("huiyuanIdNumber")){
                                    List<String> huiyuanIdNumber = seachFields.get("huiyuanIdNumber");
                                    huiyuanIdNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> huiyuanIdNumber = new ArrayList<>();
                                    huiyuanIdNumber.add(data.get(0));//要改的
                                    seachFields.put("huiyuanIdNumber",huiyuanIdNumber);
                                }
                        }

                        //查询是否重复
                         //会员编号
                        List<HuiyuanEntity> huiyuanEntities_huiyuanUuidNumber = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("huiyuan_uuid_number", seachFields.get("huiyuanUuidNumber")));
                        if(huiyuanEntities_huiyuanUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_huiyuanUuidNumber){
                                repeatFields.add(s.getHuiyuanUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [会员编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //会员手机号
                        List<HuiyuanEntity> huiyuanEntities_huiyuanPhone = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("huiyuan_phone", seachFields.get("huiyuanPhone")));
                        if(huiyuanEntities_huiyuanPhone.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_huiyuanPhone){
                                repeatFields.add(s.getHuiyuanPhone());
                            }
                            return R.error(511,"数据库的该表中的 [会员手机号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                         //会员身份证号
                        List<HuiyuanEntity> huiyuanEntities_huiyuanIdNumber = huiyuanService.selectList(new EntityWrapper<HuiyuanEntity>().in("huiyuan_id_number", seachFields.get("huiyuanIdNumber")));
                        if(huiyuanEntities_huiyuanIdNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(HuiyuanEntity s:huiyuanEntities_huiyuanIdNumber){
                                repeatFields.add(s.getHuiyuanIdNumber());
                            }
                            return R.error(511,"数据库的该表中的 [会员身份证号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        huiyuanService.insertBatch(huiyuanList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = huiyuanService.queryPage(params);

        //字典表数据转换
        List<HuiyuanView> list =(List<HuiyuanView>)page.getList();
        for(HuiyuanView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Integer id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        HuiyuanEntity huiyuan = huiyuanService.selectById(id);
            if(huiyuan !=null){


                //entity转view
                HuiyuanView view = new HuiyuanView();
                BeanUtils.copyProperties( huiyuan , view );//把实体数据重构到view中

                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody HuiyuanEntity huiyuan, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,huiyuan:{}",this.getClass().getName(),huiyuan.toString());
        Wrapper<HuiyuanEntity> queryWrapper = new EntityWrapper<HuiyuanEntity>()
            .eq("huiyuan_uuid_number", huiyuan.getHuiyuanUuidNumber())
            .eq("huiyuan_name", huiyuan.getHuiyuanName())
            .eq("huiyuan_phone", huiyuan.getHuiyuanPhone())
            .eq("huiyuan_id_number", huiyuan.getHuiyuanIdNumber())
            .eq("sex_types", huiyuan.getSexTypes())
            .eq("huiyuan_email", huiyuan.getHuiyuanEmail())
//            .notIn("huiyuan_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuiyuanEntity huiyuanEntity = huiyuanService.selectOne(queryWrapper);
        if(huiyuanEntity==null){
            huiyuan.setCreateTime(new Date());
        huiyuanService.insert(huiyuan);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

