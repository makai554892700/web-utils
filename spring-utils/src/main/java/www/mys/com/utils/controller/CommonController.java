package www.mys.com.utils.controller;

import io.swagger.annotations.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import www.mys.com.utils.Result;
import www.mys.com.utils.ResultUtils;
import www.mys.com.utils.api.CommonApi;
import www.mys.com.utils.service.CommonService;
import www.mys.com.utils.vo.response.ResponsePage;

import javax.validation.Valid;

public abstract class CommonController<Req, Res, V> implements CommonApi<Req, Res, V> {

    public abstract CommonService<Req, Res, V> getCommonService();

    @ApiOperation("upload data.")
    @PostMapping(value = "/data")
    public Result<Res> uploadData(@RequestBody @Valid Req data, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResultUtils.succeed(getCommonService().uploadData(data));
    }

    @ApiOperation("get data by id.")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", dataTypeClass = Long.class)
    })
    @GetMapping(value = "/data/{id}")
    public Result<Res> getData(@PathVariable("id") V id) {
        return ResultUtils.succeed(getCommonService().getData(id));
    }

    @ApiOperation("get page data.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "count", value = "count", dataTypeClass = Integer.class),
    })
    @GetMapping(value = "/datas")
    public Result<ResponsePage<Res>> getDatas(@RequestParam(required = false, defaultValue = "0") Integer page
            , @RequestParam(required = false, defaultValue = "10") Integer count) {
        return ResultUtils.succeed(getCommonService().getDatas(page, count));
    }

    @ApiOperation("update data by id.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataTypeClass = Long.class),
    })
    @PutMapping(value = "/updateData/{id}")
    public Result<Res> updateData(@PathVariable("id") V id, @RequestBody Req data) {
        return ResultUtils.succeed(getCommonService().updateData(id, data));
    }

    @ApiOperation("delete data by id.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataTypeClass = Long.class),
    })
    @DeleteMapping(value = "/data/{id}")
    public Result<V> deleteData(@PathVariable("id") V id) {
        return ResultUtils.succeed(getCommonService().deleteData(id));
    }

}
