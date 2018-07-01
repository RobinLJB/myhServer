  //总资产环形图
var TotalAssets = '0.00',//总资产
Balance = '0.00',//可用余额
CollectingDetails = '0.00',//代收金额
FreezingAmount = '0.00';//冻结金额
var PieData= [{name:'待收总额',value:'0.00'},{name:'冻结金额',value:'0.00'},{name:'可用余额',value:'0.00'}];

//代收金额折线图
var DateData = ['2016-01-14','2016-01-15','2016-01-16','2016-01-17','2016-01-18','2016-01-19','2016-01-20'],
MoneyData = ['0.00','0.00','0.00','0.00','0.00','0.00','0.00'];

    var myChart = echarts.init(document.getElementById('assets_Chart1'));             
     myChart.setOption({
    	color:['#00a8f8','#face4b','#ff934a'],
    	title: {
	        text: TotalAssets+'元',
	        x: 'center',
	        y: 'center',
	        itemGap: 20,
	        textStyle:{
	            color : '#f06f2e',
	            fontSize : 26,
	            fontWeight:'normal'
	        },
	        subtextStyle:{
	        	color : '#555',
	            fontSize : 20
	        }
	    },
	    tooltip:{
	        trigger: 'item',
	        formatter: function (params,ticket,callback) {
	        	console.log(params);
	        	var pna = params.name;
        		for(var i = 0;i<PieData.length;i++){
                	if(PieData[i].name == pna){
                        var res = params.name+":"+PieData[i].value;
                        break;
                    }
                }
	        	return res;
	        }
	    },
	     legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['待收总额','冻结金额','可用余额']
	    },
	    series:[
	        {
	            name:'账户概况',
	            type:'pie',
	            radius : ['75%', '96%'], 
	            startAngle:180,
	            itemStyle : {
	                normal : {
	                    label : {
					        formatter: function (params,ticket,callback) {
					        	console.log(params);
					        	var res = params.name+"\n "+params.value+" 元 ";
					        	if(params.value<0.001){
					        		res = params.name+"\n "+" 0.00元 ";
					        	}
					        	return res;
					        },
	                    	textStyle:{
	                    		fontSize:14,
	                    		color:'#555'
	                    	},
	                    	show : false
	                    },
	                    labelLine : {
	                    	length:'50',
	                    	lineStyle:{
	                    		color:'#dedede'
	                    	},
	                    	 show : true				                    	
	                    }
	                }
	            },
	            data:[
	                {value:50, name:'待收总额'},
	                {value:25, name:'冻结金额'},
	                {value:25, name:'可用余额'}
	            ]
	        }
	    ]
	})