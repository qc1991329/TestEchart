(function() {
	var page = {
		onReady: function() {
			var that = this;

			mui.init({
				swipeBack: true //启用右滑关闭功能
			});
                that.onTap();
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