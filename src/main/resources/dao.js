var ioc = {

    // 读取配置文件
    config : {
        type : "org.nutz.ioc.impl.PropertiesProxy",
        fields : {
            paths : ["myapp.properties"]
        }
    },

	dataSource : {
			type : 'com.alibaba.druid.pool.DruidDataSource',
			fields:{
                driverClassName : {java :"$config.get('db-driver')"},
                url             : {java :"$config.get('db-url')"},
                username        : {java :"$config.get('db-username')"},
                password        : {java :"$config.get('db-password')"},
                initialSize     : 3,
                maxActive       : 5,
                testOnReturn    : true,
                //validationQueryTimeout : 5,
                validationQuery : "select 1"
            }
		},

	dao : {
		type : 'org.nutz.dao.impl.NutDao',
		args : [ {
			refer : 'dataSource'
		} ]
	},
	
	/*
	 * 临时文件池
	 */
	tmpFilePool : {
		type : 'org.nutz.filepool.NutFilePool',
		args : [ "~/upload", 8192 ]
	},

	/*
	 * 上传文件用适配器的上下文
	 */
	uploadFileContext : {
		type : 'org.nutz.mvc.upload.UploadingContext',
		singleton : false,
		args : [ {
			refer : 'tmpFilePool'
		} ],
		fields : {
			ignoreNull : true,
			maxFileSize : {
				java : 1048576
			}
		}
	},

	/*
	 * 上传文件用适配器
	 */
	uploadFile : {
		type : 'org.nutz.mvc.upload.UploadAdaptor',
		singleton : false,
		args : [ {
			refer : 'uploadFileContext'
		} ]
	}

};