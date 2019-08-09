$(function() {
	//var shopId = 1;
	//获取此店铺下的商品列表的URL
	var listUrl = 'getproductlistbyshop?pageIndex=1&pageSize=999';/*&shopId='
			+ shopId;*/
	//商品下架的URL
	var statusUrl = 'modifyproduct';

    getList();

	//获取此店铺下的商品列表
	function getList() {
		//从后台获取商铺的商品列表
		$.getJSON(listUrl, function(data) {
			if (data.success) {
				var productList = data.productList;
				var tempHtml = '';
				//显示
				productList.map(function(item, index) {
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0) {//点击按钮，上下架切换

						textOp = "上架";
						contraryStatus = 1;
					} else {
						contraryStatus = 0;
					}
					//拼接显示
					tempHtml += '' + '<div class="row row-product">'
							+ '<div class="col-30">'
							+ item.productName
							+ '</div>'
							+ '<div class="col-20">'
							+ item.priority
							+ '</div>'
							+ '<div class="col-50">'
							+ '<a href="#" class="edit" data-id="'+ item.productId + '" data-status="'+ item.enableStatus + '">编辑</a>'
							+ '<a href="#" class="delete" data-id="' + item.productId + '" data-status="' + contraryStatus + '">' + textOp + '</a>'
							+ '<a href="#" class="preview" data-id="'
							+ item.productId
							+ '" data-status="'
							+ item.enableStatus
							+ '">预览</a>'
							+ '</div>'
							+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
		});
	}



	function changeItemStatus(id, enableStatus) {
		var product = {};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么?', function() {
			$.ajax({
				url : statusUrl,
				type : 'POST',
				data : {
					productStr : JSON.stringify(product),
					statusChange : true
				},
				dataType : 'json',
				success : function(data) {
					if (data.success) {
						alert('操作成功！');
						getList();
					} else {
						alert('操作失败！');
					}
				}
			});
		});
	}

	$('.product-wrap')//将class为product-wrap里面的a标签绑定上点击事件
			.on(
					'click',
					'a',
					function(e) {
						var target = $(e.currentTarget);
						if (target.hasClass('edit')) {
							//如果有class edit则点击就进入店铺信息编辑页面，并带有productId参数
							window.location.href = 'productoperation?productId='
									+ e.currentTarget.dataset.id;
						} else if (target.hasClass('delete')) {
							//如果有class status则调用后台功能上下架相关商品，并带有productId参数
							changeItemStatus(e.currentTarget.dataset.id,
									e.currentTarget.dataset.status);
						} else if (target.hasClass('preview')) {
							//如果有class preview则去前台展示系统商品详情页预览商品情况
							window.location.href = '/myo2o/frontend/productdetail?productId='
									+ e.currentTarget.dataset.id;
						}
					});

	$('#new').click(function() {
		window.location.href = '/myo2o/shop/productedit';
	});
});