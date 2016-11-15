function formatD(dgt){
	var ddd =  dgt.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
	return ddd;
}

window.chartColors = {
	red: 'rgb(255, 99, 132)',
	orange: 'rgb(255, 159, 64)',
	yellow: 'rgb(255, 205, 86)',
	green: 'rgb(75, 192, 192)',
	blue: 'rgb(54, 162, 235)',
	purple: 'rgb(153, 102, 255)',
	grey: 'rgb(231,233,237)'
};
var xx = 0;
window.randomScalingFactor = function() {
	//xx++;
	return (Math.random() > 0.5 ? 1.0 : -1.0) * Math.round(Math.random() * 100);
}


var config = {
    type: 'line',
    data: {
        labels: [],
        datasets: [{
            label: "Revenue (Kes.)",
            backgroundColor: window.chartColors.red,
            borderColor: window.chartColors.red,
            data: [],
            fill: false,
        }]
    },
    options: {
        responsive: true,
        tooltipTemplate : "<%if (label){%> <%=label%> Revenue : <%}%>KES. <%=formatD(value)%>",
		title:{
            display:true,
            text:'Hour-to-hour Revenue Comparison'
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
                    labelString: 'Hour'
                }
            }],
            yAxes: [{
                display: true,
                scaleLabel: {
                    display: true,
                    labelString: 'Revenue (Kes.)'
                }
            }]
        }
    }
};

window.onload = function() {
    var ctx = document.getElementById("canvas").getContext("2d");
    window.myLine = new Chart(ctx, config);
    updateGraph();
};

var updateGraph  = function() {
	console.log('here');
   /* config.data.datasets.forEach(function(dataset) {
    	 if (config.data.datasets.length > 0) {
    	        var month = MONTHS[config.data.labels.length % MONTHS.length];
    	        config.data.labels.push(month);

    	        config.data.datasets.forEach(function(dataset) {
    	            dataset.data.push(randomScalingFactor());
    	        });
    	 }

    });
*/
	$.ajax({
    	url: 'hourtohour',
    	cache: false,
    	data: {},
    	dataType: 'json',
    	success: function(data, textstatus, jqXHR) {
    		config.data.labels = data.labels;
    		config.data.datasets[0].data = data.datasets[0].data;
    	},
    	
    });
	
    if(window.myLine){
    	window.myLine.update();
    }
    
    
    
    
    setTimeout(updateGraph, 10000);
};

//setInterval(updateGraph,1000);