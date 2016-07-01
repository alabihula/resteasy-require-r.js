require.config({
    paths: {
        jquery: '../commons/js/jquery-1.12.4.min',
        ec:'echarts'
    }
});
 
require(['jquery','ec'],function($,ec) {
	// 基于准备好的dom，初始化echarts实例
        var myChart = ec.init($('.box')[0]);

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: 'ECharts 入门示例',
                textStyle:{
                	color:"black"
                },
                subtext:"minxing",
                shadowColor: 'rgba(0, 0, 0, 0.5)',
    			shadowBlur: 10
            },
            tooltip: {},
            legend: {
                data:[{
				    name: '销量',
				    // 强制设置图形为圆。
				    icon: 'circle',
				    // 设置文本为红色
				    textStyle: {
				        color: 'red'
				    }
				},
                {
				    name: '销量2',
				    // 强制设置图形为圆。
				    icon: 'circle',
				    // 设置文本为红色
				    textStyle: {
				        color: 'red'
				    }
				}]
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            },
            {
                name: '销量2',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };
		
		myChart.setOption(option);
		(function showZ() {
			 setTimeout(function() {
	        	myChart.clear();
	        	myChart.setOption(option);
	        	showZ();
	        },2000);
		})()

       
	
});


