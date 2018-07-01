//圆环
function round(dom, textDom) {
	$(function() {
		var d = [
			[45, 270],
			[30, 240],
			[15, 210],
			[0, 180],
			[-15, 150],
			[-30, 120],
			[-45, 90],
			[95, 60]
		];
		var day = textDom.html(); //day
		var total=7;
		if(day%7>0){
			total=(Math.floor(day/7)+1)*7
		}else{total=day;}
		var value = day/total;  
		var circleRatio = 230 / 360;
		var targetValue = value * circleRatio; //目标角度
		var startDeg = 65; //缺口开始角度
		var text = textDom; //文字节点
		var node = dom; //画图节点

		var ratio = window.devicePixelRatio || 1;
		var rad = Math.PI / 180;
		var canvas = node[0];
		var size = node.innerWidth() * ratio; //大小
		var lineWidth = Math.round(size * 0.05263157894736842); //进度条粗度，下面是背景条粗度
		var lineWidth2 = lineWidth / 4;
		//半径
		var r = size / 2;

		canvas.width = size;
		canvas.height = size;

		var context = canvas.getContext("2d");
		var linearGradient = context.createLinearGradient(0, 0, size, 0);
		//				linearGradient.addColorStop(0, "#6EB8FF"); //进度条渐变色1
		linearGradient.addColorStop(1, "#F6672F");
		//context.translate(r, r);

		//目标角度
		function draw(degree) {
			//					console.log(degree);
			context.clearRect(0, 0, size, size);
			arcBorder();
			arc(degree);
		}
		//目标角度
		function arc(degree) {
			degree = Number(degree);
			context.save();

			context.beginPath();
			//填充需要moveTo方法
			//					context.moveTo(r, r); 
			context.lineCap = "round";
			context.lineWidth = lineWidth;

			context.strokeStyle = linearGradient;
			context.fillStyle = linearGradient;

			//					context.arc(r, r, r - lineWidth / 2, startDeg * rad, degree * rad, true);
			context.arc(r, r, r - lineWidth / 2, (startDeg + 90) * rad, (degree + 90) * rad, false);
			context.stroke();
			//context.fill();
			context.closePath();
			context.restore();
		}

		//灰色圆弧
		function arcBorder() {

			context.clearRect(0, 0, size, size);
			context.save();
			context.beginPath();

			context.lineCap = "round";
			context.lineWidth = lineWidth;
			context.strokeStyle = "#eee";
			context.arc(r, r, r - lineWidth / 2, (startDeg - 40) * rad, (-360 * circleRatio + (startDeg - 40)) * rad, true);
			context.stroke();
			context.closePath();
			context.restore();
		}

		//arcBorder();
		//return;    
		node.stop().css({
			animationProgress: 0
		}).animate({
			animationProgress: 1
		}, {
			duration: 1300,
			step: function(animationProgress) {
				var m = (animationProgress * day).toFixed();
				text.html(m);
				draw(360 * animationProgress * targetValue + startDeg);
			},
			complete: function() {}
		});

	});
}