package com.linziyi.order.utils;

import com.linziyi.order.vo.ResultVO;

public class ResultVOUtil {
    /**
     * 成功
     * @param obj
     * @return
     */
    public static ResultVO success(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(obj);
        return resultVO;
    }

    /**
     * 错误
     * @param obj
     * @return
     */
    public static ResultVO error(Object obj){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("失败");
        resultVO.setData(obj);
        return resultVO;
    }
}
