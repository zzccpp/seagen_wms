package cn.seagen.base.vo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ScheduledRuntimer {
	// 标识符
	private String title;
	// 执行最小毫秒数
	private long min = 100000000;
	// 执行最大毫秒数
	private long max = 0;
	// 执行平均毫秒数
	private long avg = 0;
	// 记录条数，大于1亿归零0
	private AtomicLong size = new AtomicLong(0);
	// 最后的执行时间
	private long worktime = 0;

	// 以下是用于取平均值
	// 录入个数，达到100个时取平均值
	private AtomicInteger count = new AtomicInteger(0);
	// 录入总值
	private AtomicLong sum = new AtomicLong(0);

	public ScheduledRuntimer(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getMin() {
		return min == 100000000 ? 0 : min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public long getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public long getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public long getSize() {
		return size.get();
	}

	public long getWorktime() {
		return worktime;
	}

	/** 100条内的最小最大平均耗时 */
	public void add(long value) {
		/*if (value <= 0)
			return;
		 */
		value = value <= 0 ? 1:value;
		if(worktime == 0) {
			worktime = System.currentTimeMillis();
		}
		

		if (value < min)
			min = value;
		if (value > max)
			max = value;
		int c = count.incrementAndGet();//count.getAndIncrement();
		long v =sum.addAndGet(value); //sum.getAndAdd(value);
		if (c > 0)
			avg = v / c;

		if ((System.currentTimeMillis()-worktime) > (30*60*1000)) {
			worktime = System.currentTimeMillis();
			min = value;
			max = value;
			avg = value;
			count.set(1);
			sum.set(value);
		}

		if (size.incrementAndGet() > 100000000)
			size.set(0);
	}

}
