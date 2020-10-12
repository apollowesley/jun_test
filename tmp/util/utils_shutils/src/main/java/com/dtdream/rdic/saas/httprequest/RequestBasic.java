package com.dtdream.rdic.saas.httprequest;

import com.dtdream.rdic.saas.app.Results;
import com.dtdream.rdic.saas.def.Result;
import com.dtdream.rdic.saas.httpresponse.ResponseBasic;
import com.dtdream.rdic.saas.utils.JsonUtils;

/**
 * Created by Ozz8 on 2015/06/23.
 */
public abstract class RequestBasic {

    public Result error = Results.SUCCESS;
    protected ResponseBasic response = new ResponseBasic();
    public RequestBasic() {

    }
    public RequestBasic(Result error) {
        this.error = error;
    }

    public String result () {
        response.reset(error);
        return JsonUtils.json(response);
    }

    public abstract Result check ();
}
