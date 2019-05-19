package cn.seagen.base;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.seagen.base.bean.SystemSet;
import cn.seagen.base.main.SeagenService;
import cn.seagen.base.service.DbDataBackUpService;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.service.SysSetService;
import cn.seagen.base.utils.ReportUtils;
import cn.seagen.base.utils.SystemSetUtils;

/**
 * Spring初始化完成后执行的方法
 * @author zcp
 */
public class AfterSpringInitExecute implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LogManager.getLogger(AfterSpringInitExecute.class.getName());
	
	private JdbcTemplate template;
	
	private SysSetService sysSetServiceImpl;
	
	@Resource
	private SeagenService seagenServiceImpl;
	
	@Resource
	private DbDataBackUpService dbDataBackUpServiceImpl;
	
	@Resource
	private StatisticsService statisticsServiceImpl;
	
	@Resource
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	@Resource
	public void setSystemSetServiceImpl(SysSetService sysSetServiceImpl) {
		this.sysSetServiceImpl = sysSetServiceImpl;
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
			if(template.queryForList("show tables").size()<1){
				//读取MySQL建表语句文件
				initDB();
			}
			System.out.println("数据加载！");
			
			// 初使化系统参数
			seagenServiceImpl.configSystem();//
			
			//判断flag_date表中数据是否正常，用作统计查询时使用
			int num = dbDataBackUpServiceImpl.findTableDataCount("select count(*) from flag_date;");
			if(num!=1000){
				logger.error("flag_date表中数据异常，需重新添加。");
				//表中数据不足1000，则清空表，重新添加
				if(num > 0)
					dbDataBackUpServiceImpl.deleteTableData("truncate table flag_date;");
				List<String> sqls = new ArrayList<String>();
				for(int i = 0;i < 1000;i++){
					sqls.add("insert into flag_date(date_val) values ('"+ReportUtils.formatStr(""+(i+1), 2)+"');");
				}
				statisticsServiceImpl.insertReportTable(sqls);
				logger.error("flag_date表中数据异常，重新添加完成。");
			}
			
			// 添加系统配置默认参数,获取参数
			List<SystemSet> list = sysSetServiceImpl.getSystemSetList();
			if (list.isEmpty()) {// 没有数据去创建添加参数
				try {
					list = SystemSetUtils.getParams();
					sysSetServiceImpl.initParam(list);
				} catch (Exception e) {
					logger.error("获取系统配置初始化参数异常!", e);
				}
			} else {// 设置常量
				try {
					SystemSetUtils.initValue(list);
				} catch (Exception e) {
					logger.error("系统配置参数初始化异常!", e);
				}
			}
			
		}
		
	}
	/**
	 * 读取MySQL建表语句文件
	 */
	private void initDB() {
		Connection conn = null;
		ScriptRunner runner = null;
		FileOutputStream out = null;
		PrintWriter errorLogWriter = null;
		try {
			System.out.println("-----------初始化数据库------------");
			conn = template.getDataSource().getConnection();
			runner = new ScriptRunner(conn);
			runner.setDelimiter("$$");//定义mysql结束符
			runner.setAutoCommit(true);//设置事务自动提交
			runner.setStopOnError(true);//设置当执行出错时,不往下执行
			out = new FileOutputStream("D:/logs/dberror.txt");
			errorLogWriter = new PrintWriter(out);
			runner.setErrorLogWriter(errorLogWriter);//设置当运行异常时输出错误日志
			Resources.setCharset(Charset.forName("UTF-8")); //设置加载sql文件字符集
            runner.runScript(Resources.getResourceAsReader("config/sql/initdb_table.sql"));
            runner.runScript(Resources.getResourceAsReader("config/sql/initdb.sql"));
            runner.runScript(Resources.getResourceAsReader("config/sql/initdata.sql"));
		} catch (Exception e) {
			logger.error("初始化数据库异常!",e);
		} finally{
			try {
				if(null!=out)out.close();
				if(null!=errorLogWriter)errorLogWriter.close();
				if(null!=runner)runner.closeConnection();
				if(null!=conn) conn.close();
			} catch (Exception e) {
				logger.error("初始化数据库,流关闭异常!",e);
			}
		}
	}
}
