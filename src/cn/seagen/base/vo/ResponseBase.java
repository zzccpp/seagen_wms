package cn.seagen.base.vo;

/**
 * json 信息封装实体类
 *
 */
public class ResponseBase {
	// 返回结果 非0为失败
	private int result = 1;
	// 提示消息
	private String message = "操作失败！";
	// 返回的数据
	private Object data = null;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ResponseBase [result=" + result + ", message=" + message + ", data=" + data + "]";
	}

}
