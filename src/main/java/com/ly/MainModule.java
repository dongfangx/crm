package com.ly;

import org.beetl.ext.nutz.BeetlViewMaker;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Modules( scanPackage=true)
@IocBy(type = ComboIocProvider.class, args = {
		"*org.nutz.ioc.loader.json.JsonLoader", "dao.js",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.ly" })
@SetupBy(MvcSetup.class)
@Views({BeetlViewMaker.class})
@Ok("json")
@Fail("json")
@Encoding(input = "UTF-8", output = "UTF-8")
public class MainModule {

}
