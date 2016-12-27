AJS.$(document).ready(function () {

	function showChart(chartConfig) {

		new Rickshaw.Graph.Ajax({
			element: document.getElementById(chartConfig.element),
			width: parseInt(chartConfig.width),
			height: parseInt(chartConfig.height),
			renderer: chartConfig.type,
			stack: chartConfig.stack,
			//min: 0,
			//max: 200,
			dataURL: AJS.Portal.contextPath() + chartConfig.dataUrl,
			onData: function(data) {
				this.args.series = data;
				return data;
			},
			onComplete: function(transport) {
				graph = transport.graph;

				if (chartConfig.hoverDetail.enabled) {
					new Rickshaw.Graph.HoverDetail({
							graph: graph,
							formatter: function(series, x, y) {
								date = '';
								style = '';

								if (chartConfig.axisX.mode === "time") {
									date = '<br/><span class="date">' + new Date(x * 1000).toUTCString() + '</span>';
									style = ';float:right';

								} else {
									axisXTitle = 'x=';

									if (chartConfig.hoverDetail.axisXTitle != "") {
										axisXTitle = chartConfig.hoverDetail.axisXTitle;
									}

									date = '<br/><span class="date">' + axisXTitle + parseInt(x) + '</span>';
								}

								swatch = '<span class="hover-detail-swatch" style="background-color: ' + series.color + style + '"></span>';
								//content = series.name + ': ' + parseInt(y) + '&nbsp;&nbsp;&nbsp;' + swatch + date;
								content = series.name + ': ' + parseFloat(y).toFixed(2) + '&nbsp;&nbsp;&nbsp;' + swatch + date;

								return content;
							}
					});
				}

				if (chartConfig.legend.enabled) {
					if (chartConfig.axisX.mode === "time") {
						AJS.$('#' + chartConfig.legend.element + '_container').css('width', '0');
						AJS.$('#' + chartConfig.legend.element).css('left', '30px');
					}

					legend = new Rickshaw.Graph.Legend({
									graph: graph,
									element: document.getElementById(chartConfig.legend.element)
					});

					new Rickshaw.Graph.Behavior.Series.Toggle({
							graph: graph,
							legend: legend
					});

					new Rickshaw.Graph.Behavior.Series.Highlight({
							graph: graph,
							legend: legend
					});

					new Rickshaw.Graph.Behavior.Series.Order({
							graph: graph,
							legend: legend
					});
				}

				ticksTreatment = 'glow'; // ticksTreatment => {plain, glow}

				if (chartConfig.axisX.mode === "time") {
					AJS.$('#' + chartConfig.axisX.element).css('margin-bottom', '60px');

					var time = new Rickshaw.Fixtures.Time();
					//var time = new Rickshaw.Fixtures.Time.Local();					

					axesX = new Rickshaw.Graph.Axis.Time({
									graph: graph,
									ticksTreatment: ticksTreatment,
									timeFixture: time
					});

					if (typeof chartConfig.axisX.timeUnit !== 'undefined') {
						var unit = {};

						if (chartConfig.axisX.timeUnit === 'day') {
							unit.formatter = function(d) { return this.formatTime(d); };
							unit.name = "day";
							unit.seconds = 3600 * 24;
							unit.formatTime = function(d) {
								//return ("0" + (d.getUTCHours())).slice(-2) + ":" + ("0" + d.getUTCMinutes()).slice(-2);
								return (d.getUTCMonth() + 1) + "/" + d.getUTCDate() + "/" + d.getUTCFullYear();
							};
						}

						else if (chartConfig.axisX.timeUnit === '2 hour') {
							unit.formatter = function(d) { return this.formatTime(d); };
							unit.name = "2 hour";
							unit.seconds = 3600 * 2;
							unit.formatTime = function(d) {
								return d.toUTCString().match(/(\d+:\d+):/)[1];
							};

						} else {
							unit = time.unit(chartConfig.axisX.timeUnit);
						}

						axesX.fixedTimeUnit = unit; 
					}

					axesX.render();

				} else {
					axesX = new Rickshaw.Graph.Axis.X({
									graph: graph,
									orientation: 'bottom',
									element: document.getElementById(chartConfig.axisX.element),
									ticksTreatment: ticksTreatment
					});

					axesX.render();
				}

				axesY = new Rickshaw.Graph.Axis.Y({
								graph: graph,
								orientation: 'left',
								element: document.getElementById(chartConfig.axisY.element),
								ticksTreatment: ticksTreatment
				});

				axesY.render();
			}
		});
	}

	AJS.$('div.chart-container').map(function() {
		element = AJS.$(this);

		chart = element.find('.chart').attr('id');
		axisX = element.find('.chart-axisX').attr('id');
		axisY = element.find('.chart-axisY').attr('id');
		legend = element.find('.legend').attr('id');

		chartType = element.find('#chartType').val();
		chartWidth = element.find('#chartWidth').val();
		chartHeight = element.find('#chartHeight').val();
		chartStack = element.find('#chartStack').val();
		chartDataUrl = element.find('#chartDataUrl').val();
		chartLegend = element.find('#chartLegend').val();
		chartHoverDetail = element.find('#chartHoverDetail').val();
		chartHoverDetailAxisXTitle = element.find('#chartHoverDetailAxisXTitle').val();
		chartAxisXMode = element.find('#chartAxisXMode').val();
		chartAxisXTimeUnit = AJS.$('#chartAxisXTimeUnit').val();

		chartConfig = {
				element: chart,
				type: chartType,
				width: chartWidth,
				height: chartHeight,
				stack: (chartStack === "true"),
				dataUrl: chartDataUrl,
				legend: {
					enabled: (chartLegend === "true"),
					element: legend
				},
				hoverDetail : {
					enabled: (chartHoverDetail === "true"),
					axisXTitle: chartHoverDetailAxisXTitle
				},
				axisX: {
					element: axisX,
					mode: chartAxisXMode, // mode => {time, value}
					timeUnit: chartAxisXTimeUnit
				},
				axisY: {
					element: axisY
				}
		};

		showChart(chartConfig);
	});

});