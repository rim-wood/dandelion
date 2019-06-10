package cn.icepear.dandelion.common.core.utils;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 响应信息主体
 * @author rimwood
 * @email wuliming195@gmail.com
 * @date 2019年2月21日 下午12:53:33
 */
@Builder
@ToString
//chain 若为true，则setter方法返回当前对象
@Accessors(chain = true)
@AllArgsConstructor
public class R<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private int code = CommonConstants.SUCCESS;

	@Getter
	@Setter
	private String msg = "success";


	@Getter
	@Setter
	private T data;

	public R() {
		super();
	}

	public R(T data) {
		super();
		this.data = data;
	}

	public R(String msg,T data) {
		super();
		this.data = data;
		this.msg = msg;
	}

	public R(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = CommonConstants.FAIL;
	}

	public static R error(int code, String msg,Object e) {
		R r = new R<>();
		r.code = code;
		r.msg = msg;
		if(e!=null){
			r.data = e;
		}
		return r;
	}
}
