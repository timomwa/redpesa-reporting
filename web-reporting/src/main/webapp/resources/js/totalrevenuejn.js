var configTotalRev = {
    type: 'line',
    data: {
        labels: [0],
        datasets: [{
            label: "Accumulatived Revenue (Kes.)",
            backgroundColor: window.chartColors.mycolor,
            borderColor: window.chartColors.mycolor,
            data: [0],
            fill: false,
        }]
    },
    options: {
        responsive: true,
        tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>Kes. <%=formatD(value)%>",
		scaleLabel: function (dgt) {
            return dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,').toString();
        },
        scaleBeginAtZero: false,
        title:{
            display:true,
            text:'Accumulated Revenue'
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
                    labelString: 'Revenue (Kes.)'
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



var updateTotalRevenueGraph  = function() {
	$.ajax({
    	url: '/redpesa-reporting/totalrevenue',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(respdata, textstatus, jqXHR) {
    		configTotalRev.data.labels = respdata.labels;
    		configTotalRev.data.datasets[0].data = respdata.datasets[0].data;
    	},
    	
    });
	
    if(window.totalrev_graph){
    	window.totalrev_graph.update();
    }
    
    setTimeout(updateTotalRevenueGraph, 10000);
};

var initTotalRevenue = function(){
	var canvastotalrevenue = document.getElementById("canvastotalrevenue");
	if(canvastotalrevenue){
		var ctx0 = canvastotalrevenue.getContext("2d");
		window.totalrev_graph = new Chart(ctx0, configTotalRev);
	}
}