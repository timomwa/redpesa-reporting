var dailyavgrev = {
    data: {
    	labels: [0],
        datasets: [{
        	type: 'line',
            label: "Daily Avg.",
            backgroundColor: window.chartColors.green,
            borderColor: window.chartColors.green,
            data: [0],
            fill: false,
        },{
        	type: 'bar',
        	borderColor: 'white',
            label: "Daily Revenue",
            backgroundColor: window.chartColors.mycolor,
            borderColor: window.chartColors.mycolor,
            data: [0],
            fill: false,
        }]
    },
    type: 'bar',
    options: {
        responsive: true,
        tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>KES. <%=formatD(value)%>",
		scaleLabel: function (dgt) {
            return dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,').toString();
        },
        scaleBeginAtZero: false,
        title:{
            display:true,
            text:'Avg. Revenue'
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
                    labelString: 'Avg. Revenue (Kes.)'
                },
                ticks: {
                    callback: function(label, index, labels) {
                    	label = label/1000;
                    	var formattednum = parseFloat(label, 10).toFixed(1).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString();
                        return formattednum+' k';
                    }
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Revenue(Kes.) 1k = 1000'
                }
            }]
        }
    }
};



var updateDailyAvgGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/dailyaverage',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		dailyavgrev.data.labels = respdata.labels;
    		dailyavgrev.data.datasets[0].data = respdata.datasets[0].data;
    		dailyavgrev.data.datasets[1].data = respdata.datasets[1].data;
    	},
    	
    });
	
    if(window.avg_daily_graph){
    	window.avg_daily_graph.update();
    }
    
    setTimeout(updateDailyAvgGraph, 13000);
};

var initAverageDailyRevenue = function(){
	var canvastotalrevenue = document.getElementById("canvasdailyavgrev");
	if(canvastotalrevenue){
		var ctx0 = canvastotalrevenue.getContext("2d");
		window.avg_daily_graph = new Chart(ctx0, dailyavgrev);
	}
}