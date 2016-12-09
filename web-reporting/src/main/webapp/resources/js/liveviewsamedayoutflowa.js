function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsLVOUT = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configliveviewsamedayoutflow = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Daily Avg Outflow. (Kes.)",
            backgroundColor: window.chartColorsLVOUT.orange,
            borderColor: window.chartColorsLVOUT.orange,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Daily Outflow (Kes.)",
            backgroundColor: window.chartColorsLVOUT.purple,
            borderColor: window.chartColorsLVOUT.grey,
            data: [0],
            fill: false,
        }]
    },
    type: 'bar',
    options: {
    	responsive : true,
		tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>KES. <%=formatD(value)%>",
		scaleLabel: function (dgt) {
            return dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,').toString();
        },
        scaleBeginAtZero: false,
        title:{
            display:true,
            text:'Day-to-Day Outflow Comparison'
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        scales: {
            xAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Day'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'B2C Payments (Kes.)'
                },
                ticks: {
                    callback: function(label, index, labels) {
                    	if(label>1000){
                    		label = label/1000;
                    		var formattednum = parseFloat(label, 10).toFixed(1).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
                    		return formattednum+' k';
                    	}
                    	return formattednum;
                    	
                    }
                },
                scaleLabel: {
                    display: true,
                    labelString:  'Payments (Kes.) 1k = 1000'
                }
            }]
        }
    }
};

var updateDayOutLiveGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/outflow/daytoday?stats=sameday',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configliveviewsamedayoutflow.data.labels = respdata.labels;
    		configliveviewsamedayoutflow.data.datasets[0].data = respdata.datasets[0].data;
    		configliveviewsamedayoutflow.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.liveviewsamedayoutflow){
    	window.liveviewsamedayoutflow.update();
    }
    
    setTimeout(updateDayOutLiveGraph, 15000);
};

var initLiveDayOutGraphInflow = function(){
	var liveviewsamedayoutflow = document.getElementById("liveviewsamedayoutflow");
	if(liveviewsamedayoutflow){
		var ctx = liveviewsamedayoutflow.getContext("2d");
		window.liveviewsamedayoutflow = new Chart(ctx, configliveviewsamedayoutflow);
	}
	setTimeout(updateDayOutLiveGraph, 1700);
}

