function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsLVWKOUT = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configliveviewsamewkoutflow = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Weekly Avg Outflow. (Kes.)",
            backgroundColor: window.chartColorsLVWKOUT.red,
            borderColor: window.chartColorsLVWKOUT.red,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Weekly Outflow (Kes.)",
            backgroundColor: window.chartColorsLVWKOUT.blue,
            borderColor: window.chartColorsLVWKOUT.blue,
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
            text:'Week-to-Week Outflow Comparison'
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
                    labelString: 'Week'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Weekly B2C Payments (Kes.)'
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
                    labelString:  'Payments (Kes.) 1k = 1,000'
                }
            }]
        }
    }
};

var updateWkOutFlLiveGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/outflow/daytoday?stats=weekly',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configliveviewsamewkoutflow.data.labels = respdata.labels;
    		configliveviewsamewkoutflow.data.datasets[0].data = respdata.datasets[0].data;
    		configliveviewsamewkoutflow.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.liveviewsamewkoutflow){
    	window.liveviewsamewkoutflow.update();
    }
    
    setTimeout(updateWkOutFlLiveGraph, 30000);
};

var initLiveGraphWOutflow = function(){
	var liveviewsamewkoutflow = document.getElementById("liveviewsamewkoutflow");
	if(liveviewsamewkoutflow){
		var ctx = liveviewsamewkoutflow.getContext("2d");
		window.liveviewsamewkoutflow = new Chart(ctx, configliveviewsamewkoutflow);
	}
	setTimeout(updateWkOutFlLiveGraph, 1700);
}


window.onload = function() {
	initLiveGraphWOutflow();
	initLiveDayOutGraphInflow();
	
	updateDayOutLiveGraph();
	updateWkOutFlLiveGraph();
}


