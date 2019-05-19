package cn.seagen.base.bean;
/**
 *统计基础实体类
 */
public class ReportBaseBean {
	/**分拣量 */
	private int sorting_count = 0;
	/**正常分拣量 件数 */
	private int success_sum = 0;
	/**无读 件数 */
	private int no_reade = 0;
	/**异常分拣量 件数 */
	private int err_sum = 0;
	/**无目的地 件数 */
	private int no_chute = 0;
	/**多条码 件数 */
	private int more_data = 0;
	/**无信息 件数 */
	private int no_data = 0;
	/**订单取消 件数 */
	private int cancel_sum = 0;
	/**格口投错 件数 */
	private int err_chute = 0;
	/**最大圈数 件数 */
	private int max_cycles = 0;
	/**迷失件数 */
	private int lost_data = 0;
	public int getSorting_count() {
		return sorting_count;
	}
	public void setSorting_count(int sorting_count) {
		this.sorting_count = sorting_count;
	}
	public int getSuccess_sum() {
		return success_sum;
	}
	public void setSuccess_sum(int success_sum) {
		this.success_sum = success_sum;
	}
	public int getNo_reade() {
		return no_reade;
	}
	public void setNo_reade(int no_reade) {
		this.no_reade = no_reade;
	}
	public int getErr_sum() {
		return err_sum;
	}
	public void setErr_sum(int err_sum) {
		this.err_sum = err_sum;
	}
	public int getNo_chute() {
		return no_chute;
	}
	public void setNo_chute(int no_chute) {
		this.no_chute = no_chute;
	}
	public int getMore_data() {
		return more_data;
	}
	public void setMore_data(int more_data) {
		this.more_data = more_data;
	}
	public int getNo_data() {
		return no_data;
	}
	public void setNo_data(int no_data) {
		this.no_data = no_data;
	}
	public int getCancel_sum() {
		return cancel_sum;
	}
	public void setCancel_sum(int cancel_sum) {
		this.cancel_sum = cancel_sum;
	}
	public int getErr_chute() {
		return err_chute;
	}
	public void setErr_chute(int err_chute) {
		this.err_chute = err_chute;
	}
	public int getMax_cycles() {
		return max_cycles;
	}
	public void setMax_cycles(int max_cycles) {
		this.max_cycles = max_cycles;
	}
	public int getLost_data() {
		return lost_data;
	}
	public void setLost_data(int lost_data) {
		this.lost_data = lost_data;
	}
	@Override
	public String toString() {
		return "ReportBaseBean [sorting_count=" + sorting_count
				+ ", success_sum=" + success_sum + ", no_reade=" + no_reade
				+ ", err_sum=" + err_sum + ", no_chute=" + no_chute
				+ ", more_data=" + more_data + ", no_data=" + no_data
				+ ", cancel_sum=" + cancel_sum + ", err_chute=" + err_chute
				+ ", max_cycles=" + max_cycles + ", lost_data=" + lost_data
				+ "]";
	}
}
