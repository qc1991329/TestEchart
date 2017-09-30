(function() {
	var config;
	var page = {
		onReady: function() {
			var that = this;

			mui.init({
				swipeBack: true //启用右滑关闭功能
			});
            	that.getconfig(that);
                that.onTap();
		},
        getconfig: function (that) {
            mui.ajax('getdingconfig', {
                data: {},
                dataType: 'json',
                timeout: 10000,
                success: function (data) {
                    config = data;
//                  mui.toast(JSON.stringify(config));
                    that.initdingding(config);
                },
                error: function (xhr, type, errorThrown) {
                    mui.alert(type, "失败!", function () {
                    });
                }
            });
        },
		initdingding: function (congfig) {
            dd.config({
                agentId : congfig.agentid,
                corpId : congfig.corpId,
                timeStamp : congfig.timeStamp,
                nonceStr : congfig.nonceStr,
                signature : congfig.signature,
                jsApiList : [ 'runtime.info', 'biz.contact.choose',
                    'device.notification.confirm', 'device.notification.alert',
                    'device.notification.prompt', 'biz.ding.post',
                    'biz.util.openLink' ]
            });

            dd.ready(function() {
                dd.runtime.permission.requestAuthCode({
                    corpId : config.corpId,
                    onSuccess : function(info) {
                        mui.ajax('getuserinfo?code=' + info.code + '&corpid='+ config.corpId,
							{
                            data: {},
                            dataType: 'text',
                            timeout: 10000,
                            success: function (data) {
                                var user = JSON.parse(data);
                                mui.toast(user.userid);
                                /*if(user.userid == '06550328251211258'){
                                    document.getElementById("payback").style.visibility='visible';
                                }*/
                                var userlist = ['06550328251211258','023349304926206589','06561611131850'];
                                for(var i in userlist){
                                    if(user.userid == userlist[i]){
                                        document.getElementById("payback").style.visibility='visible';
                                        break;
                                    }
                                }
                            },
                            error: function (xhr, type, errorThrown) {
                                mui.alert(type, "获取用户信息失败!", function () {
                                });
                            }
                        });
                    },
                    onFail : function(err) {
                        alert('fail: ' + JSON.stringify(err));
                    }
                });
			});

            dd.error(function(err) {
               alert('dd error: ' + JSON.stringify(err));
            });
        },
		onTap: function() {
			//点击功能点
			mui("#list").on("tap", "a", function() {
				var id = this.getAttribute('id');
				var title = this.childNodes[3].innerHTML;
                var url = this.getAttribute('href');
                mui.toast("点击了"+title);
                if(url != null && url != ''){
                    mui.openWindow({
                        id: id,
                        url: url,
                        waiting: {
                            autoShow: false
                        }
                    });
                }
			});
		}
	}
	page.onReady();
}());