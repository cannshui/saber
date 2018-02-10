package dn.cs.saber.vo;

/**
 * Some simple enumerations of response status data.
 * 
 * @author hewen.deng
 */
public enum RespEnum {

	/** HTTP 200 */
	OK(new RespStatus<String>(200, null)),
	/** HTTP 500, Internal Server Error. */
	ISE(new RespStatus<String>(500, null)),
	/** HTTP 409, Conflict. */
	CF(new RespStatus<String>(409, null)),
	/** Mine 451, Length Required. */
	LR(new RespStatus<String>(451, null)),
	/** Mine 452, Length Too Short. */
	LTS(new RespStatus<String>(452, null)),
	/** Mine 453, Length Too Long. */
	LTL(new RespStatus<String>(453, null)),
	/** Mine 551, Null reference. */
	NR(new RespStatus<String>(453, null));

	private RespEnum(RespStatus<String> resp) {
		this.resp = resp;
	}

	public RespStatus<String> resp;

	public static <T> RespStatus<T> custom(RespEnum respEnum, T data) {
		RespStatus<T> resp = new RespStatus<T>(respEnum.resp.getCode(), data);
		return resp;
	}

}