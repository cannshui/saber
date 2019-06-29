package dn.cs.saber.vo;

/**
 * Some simple enumerations of response status data.
 *
 * @author hewen.deng
 */
public enum RespEnum {

    /**
     * HTTP 200, Success.
     */
    OK(new RespStatus<>(200, null)),
    /**
     * HTTP 500, Internal Server Error.
     */
    ISE(new RespStatus<>(500, null)),
    /**
     * HTTP 400, Bad Request.
     */
    BR(new RespStatus<>(400, null)),
    /**
     * HTTP 409, Conflict.
     */
    CF(new RespStatus<>(409, null)),
    /**
     * Mine 451, Length Required.
     */
    LR(new RespStatus<>(451, null)),
    /**
     * Mine 452, Length Too Short.
     */
    LTS(new RespStatus<>(452, null)),
    /**
     * Mine 453, Length Too Long.
     */
    LTL(new RespStatus<>(453, null)),
    /**
     * Mine 551, Null reference.
     */
    NR(new RespStatus<>(453, null));

    RespEnum(RespStatus<String> resp) {
        this.resp = resp;
    }

    public RespStatus<String> resp;

    public static <T> RespStatus<T> custom(RespEnum respEnum, T data) {
        return new RespStatus<>(respEnum.resp.getCode(), data);
    }

}
