package com.linziyi.user.util;

import com.linziyi.user.enums.ResultEnum;
import com.linziyi.user.vo.ResultVO;

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
    public static ResultVO success(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    /**
     * 错误
     * @param resultEnum
     * @return
     */
    public static ResultVO error(ResultEnum resultEnum){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMessage());
        return resultVO;
    }
}
