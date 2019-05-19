package cn.seagen.base.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

/**
 * redis 操作封装服务类,需要可扩展
 * @author zcp
 */
@Service
public class JedisService {
	private static Logger logger = LogManager.getLogger(JedisService.class.getName());
	private final static long EXPIRE_TIME = 1;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * String类型值添加,0库,默认是覆盖更新
	 * @param key 键
	 * @param value 值
	 * @return 成功 true, 失败 false
	 */
	public boolean setString(String key, String value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("0库添加String类型异常!"+key+"("+value+"):"+e.getMessage());
		}
		return false;
	}

	/**
	 *  String类型值设置,默认是覆盖更新
	 * @param key 键
	 * @param value 值
	 * @param dbindex 库
	 * @return 成功 true, 失败 false
	 */
	public boolean setString(final String key,final String value,final int dbindex) {
		try {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					conn.set(serializer.serialize(key), serializer.serialize(value));
					return true;
				}
			});
			return true;
		} catch (Exception e) {
			logger.error(dbindex+"库添加String类型异常!"+key+"("+value+"):"+e.getMessage());
		}
		return false;
	}

	/**
	 * 设置某个键的过期时间 ,0库 ,如果key已经存在,则值与时间update
	 * @param key 键
	 * @param value 值
	 * @return 成功 true, 失败 false
	 */
	public boolean setStringExpire(String key, String value) {
		try {
			redisTemplate.opsForValue().set(key, value,EXPIRE_TIME,TimeUnit.DAYS);
			return true;
		} catch (Exception e) {
			logger.error("0库添加String类型与过期时间异常!"+key+"("+value+"):"+e.getMessage());
		}
		return false;
	}

	/**
	 * 设置某个键的过期时间 ,如果key已经存在,则值与时间update
	 * @param key  键
	 * @param value 值
	 * @param dbindex 库
	 * @return 成功 true, 失败 false
	 */
	public boolean setStringExpire(final String key,final String value,final int dbindex) {
		try {
			redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
					conn.set(serializer.serialize(key), serializer.serialize(value),Expiration.from(EXPIRE_TIME,TimeUnit.DAYS),SetOption.UPSERT);
					return true;
				}
			});
			return true;
		} catch (Exception e) {
			logger.error(dbindex+"库添加String类型与过期时间异常!"+key+"("+value+"):"+e.getMessage());
		}
		return false;
	}
	/**
	 * String 类型,0库,获取中某个键的值
	 * @param key 键
	 * @return 存在返回该值,不存在返回 null
	 */
	public String getString(String key) {
		try {
			return redisTemplate.opsForValue().get(key).toString();
		} catch (Exception e) {
			logger.error("0库获取String类型值异常!"+key+":"+e.getMessage());
		}
		return null;
	}

	/**
	 * String 类型,获取某个键的值
	 * @param key 键
	 * @param dbindex 库
	 * @return 存在返回该值,不存在返回 null
	 */
	public String getString(final String key,final int dbindex) {
		try {
			return redisTemplate.execute(new RedisCallback<String>() {
				@Override
				public String doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
					byte[] result = conn.get(stringSerializer.serialize(key));
					return stringSerializer.deserialize(result);
				}
			});
		} catch (Exception e) {
			logger.error(dbindex+"库获取String类型值异常!"+key+":"+e.getMessage());
		}
		return null;
	}
	/**
	 * 查看String类型中key值是否是value,0库
	 * @param key 键
	 * @param value 要比较的值
	 * @return 存在 true,不存在 false
	 */
	public boolean existsData(String key, String value) {
		try {
			return value.equals(redisTemplate.opsForValue().get(key).toString());
		} catch (Exception e) {
			logger.error("判断0库中"+key+"->"+value+"异常!"+e.getMessage());
		}
		return false;
	}

	/**
	 * 查看String类型中key值是否是value
	 * @param key  键
	 * @param value 要比较的值
	 * @param dbindex 库
	 * @return 存在 true,不存在 false
	 */
	public boolean existsData(final String key,final String value,final int dbindex) {
		try {
			String val = getString(key, dbindex);
			return null!=val && val.equals(value);
		} catch (Exception e) {
			logger.error("判断"+dbindex+"库中"+key+"->"+value+"异常!"+e.getMessage());
		}
		return false;
	}

	/**
	 * 获取某个库中的 List类型值,0库
	 * @param key list键
	 * @return 有数据返回 List集合, 无数据返回null
	 */
	public List<Object> getList(String key) {
		try {
			return redisTemplate.opsForList().range(key, 0, -1);
		} catch (Exception e) {
			logger.error("获取0中List类型"+key+"值异常!"+e.getMessage());
		}
		return null;
	}

	/**
	 * 获取某个库中的 List类型值
	 * @param key  list键
	 * @param dbindex 库
	 * @return  有数据返回 List集合, 无数据返回null
	 */
	public List<String> getList(final String key,final int dbindex) {
		try {
			return redisTemplate.execute(new RedisCallback<List<String>>() {
				@Override
				public List<String> doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
					List<byte[]> bytes = conn.lRange(stringSerializer.serialize(key), 0, -1);
					List<String> list = null;
					if(!bytes.isEmpty()){
						list = new ArrayList<String>();
						for(byte[] b:bytes){
							list.add(stringSerializer.deserialize(b));
						}
					}
					return list;
				}
			});
		} catch (Exception e) {
			logger.error("获取"+dbindex+"中List类型"+key+"值异常!"+e.getMessage());
		}
		return null;
	}

	/**
	 * List类型添加,添加至Key的末端,0库
	 * @param key 键
	 * @param value 值
	 * @return 成功 true , 失败 false 
	 */
	public boolean setList(String key, String value) {
		try {
			if(redisTemplate.opsForList().rightPush(key, value)>0){
				return true;
			}
		} catch (Exception e) {
			logger.error("0库添加List类型,"+key+":"+value+"异常!"+e.getMessage());
		}
		return false;
	}

	/**
	 * List类型添加,添加至Key的末端
	 * @param key 键
	 * @param value 值
	 * @param dbindex 库
	 * @return 成功 true , 失败 false
	 */
	public boolean setList(final String key,final String value,final int dbindex) {
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
					return conn.rPush(stringSerializer.serialize(key), stringSerializer.serialize(value))>0;
				}
			});
		} catch (Exception e) {
			logger.error(dbindex+"库添加List类型,"+key+":"+value+"异常!"+e.getMessage());
		}
		return false;
	}

	/**
	 * 删除某个Key
	 * @param key 键值
	 * @return 成功 true , 失败 false
	 */
	public boolean delkey(String key) {
		try {
			redisTemplate.delete(key);
			return true;
		} catch (Exception e) {
			logger.error("0库中删除Key:"+key+"异常!"+e.getMessage());
		}
		return false;
	}

	/**
	 * 删除某个库的某个键
	 * @param key  键值
	 * @param dbindex  库
	 * @return 成功 true , 失败 false(Key可能不存在)
	 */
	public boolean delkey(final String key,final int dbindex) {
		try {
			return redisTemplate.execute(new RedisCallback<Boolean>() {
				@Override
				public Boolean doInRedis(RedisConnection conn) throws DataAccessException {
					conn.select(dbindex);
					RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
					return conn.del(stringSerializer.serialize(key))>1;
				}
			});
		} catch (Exception e) {
			logger.error(dbindex+"库中删除Key:"+key+"异常!"+e.getMessage());
		}
		return false;
	}
}
