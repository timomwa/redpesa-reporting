function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColorsWK = {
	mycolor: 'rgba(151,187,205,0.8)',
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};

var configliveviewsamewkinflow = {
    data: {
        labels: [0],
        datasets: [{
        	type: 'line',
            label: "Weekly Avg. (Kes.)",
            backgroundColor: window.chartColorsWK.orange,
            borderColor: window.chartColorsWK.orange,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	label: "Weekly Rev (Kes.)",
            backgroundColor: window.chartColorsWK.purple,
            borderColor: window.chartColorsWK.grey,
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
            text:'Week-to-Week Inflow Comparison'
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
                    labelString: 'Inflow (Kes.)'
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
                    labelString:  'Inflow (Kes.) 1k = 1000'
                }
            }]
        }
    }
};

var updateWkLiveGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/weektoweek',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configliveviewsamewkinflow.data.labels = respdata.labels;
    		configliveviewsamewkinflow.data.datasets[0].data = respdata.datasets[0].data;
    		configliveviewsamewkinflow.data.datasets[1].data = respdata.datasets[1].data;
    	},
    });
	
    if(window.liveviewsamewkinflow){
    	window.liveviewsamewkinflow.update();
    }
    
    setTimeout(updateWkLiveGraph, 60000);
};

var initLiveWkGraphInflow = function(){
	var liveviewsamewkinflow = document.getElementById("liveviewsamewkinflow");
	if(liveviewsamewkinflow){
		var ctx = liveviewsamewkinflow.getContext("2d");
		window.liveviewsamewkinflow = new Chart(ctx, configliveviewsamewkinflow);
	}
	setTimeout(updateWkLiveGraph, 1800);
}
window.onload = function() {
	initLiveWkGraphInflow();
	updateWkLiveGraph();
}

