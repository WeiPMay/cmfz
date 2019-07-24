package com.baizhi.cache;

import com.baizhi.util.SerializeUtils;
import com.baizhi.util.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.locks.ReadWriteLock;

public class MybatisCache implements Cache {

    private String id;

    public MybatisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        System.out.println("向缓存中添加值");
        StringRedisTemplate stringRedisTemplate  = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        stringRedisTemplate.opsForHash().put(id,key.toString(), SerializeUtils.serialize(value));

    }

    @Override
    public Object getObject(Object key) {
        System.out.println("来缓存中查询");
        StringRedisTemplate stringRedisTemplate  = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        String value = (String) stringRedisTemplate.opsForHash().get(id, key.toString());
        if(value==null){
            System.out.println("没有从缓存中取到值");
            return null;
        }else{
            System.out.println("从缓存中去到了值");
        }
            return SerializeUtils.serializeToObject(value);
        }


    @Override
    public Object removeObject(Object o) {
        return null;
    }

    @Override
    public void clear() {
        System.out.println("修改了数据库，清空当前namespace缓存");
        StringRedisTemplate stringRedisTemplate  = (StringRedisTemplate) SpringContextUtil.getBean(StringRedisTemplate.class);
        stringRedisTemplate.delete(id);

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
}
