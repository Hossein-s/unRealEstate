initPage().catch(console.error);

async function initPage() {
  const data = await getChartData();
  const container = document.getElementById("container");

  data.forEach((scenario) => {
    scenario.parameters.forEach((parameter) => {
      addChartSection(container, scenario.name, parameter.name, parameter.cases);
    });
  });
}

function getChartData() {
  return fetch("/data")
    .then(response => response.json())
    .then(data => {
      return data;
    });
}

function addChartSection(container, scenario, parameter, data) {
  const nameMap = {
    "create-agents-test": "Create Agents",
    "get-agents-test": "Get Agents",
    "create-properties-test": "Create Properties"
  };
  const parameterMap = {
    "metrics.iterations.rate": "Iterations Rate",
    "metrics.http_req_duration.p(95)": "Request Duration 95th Percentile"
  };

  const section = document.createElement("section");
  section.classList.add("chart-section");

  const title = document.createElement("h2");
  title.classList.add("title");
  title.innerText = `${nameMap[scenario]} - ${parameterMap[parameter]}`;

  const chartContainer = document.createElement("div");
  const id = `${scenario}-${parameter}`;
  chartContainer.id = id;
  chartContainer.classList.add("chart");

  section.appendChild(title);
  section.appendChild(chartContainer);

  container.appendChild(section);

  createChart(id, data, parameter);
}

function createChart(rootId, data, parameter) {
  /**
   * ---------------------------------------
   * This demo was created using amCharts 5.
   *
   * For more information visit:
   * https://www.amcharts.com/
   *
   * Documentation is available at:
   * https://www.amcharts.com/docs/v5/
   * ---------------------------------------
   */

    // Create root element
    // https://www.amcharts.com/docs/v5/getting-started/#Root_element
  const root = am5.Root.new(rootId);


  // Set themes
  // https://www.amcharts.com/docs/v5/concepts/themes/
  root.setThemes([
    am5themes_Animated.new(root)
  ]);


  // Create chart
  // https://www.amcharts.com/docs/v5/charts/xy-chart/
  const chart = root.container.children.push(am5xy.XYChart.new(root, {
    panX: false,
    panY: false,
    wheelX: "panX",
    wheelY: "zoomX",
    layout: root.verticalLayout
  }));

  // Add exporting plugin
  // https://www.amcharts.com/docs/v5/concepts/exporting/
  am5plugins_exporting.Exporting.new(root, {
    menu: am5plugins_exporting.ExportingMenu.new(root, {})
  });


  // Add legend
  // https://www.amcharts.com/docs/v5/charts/xy-chart/legend-xy-series/
  const legend = chart.children.push(
    am5.Legend.new(root, {
      centerX: am5.p50,
      x: am5.p50
    })
  );

  // Create axes
  // https://www.amcharts.com/docs/v5/charts/xy-chart/axes/
  const xRenderer = am5xy.AxisRendererX.new(root, {
    cellStartLocation: 0.3,
    cellEndLocation: 0.7
  });

  const xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
    categoryField: "vus",
    renderer: xRenderer,
    numberFormatter: am5.NumberFormatter.new(root, {
      numberFormat: "ss#.#a VsssUs"
    })
  }));

  xRenderer.grid.template.setAll({
    location: 1
  });
  xRenderer.labels.template.adapters.add("text", (text) => `${text} VUs`);

  xAxis.data.setAll(data);

  var yLabel = am5.Label.new(root, {
    rotation: -90,
    text: parameter === "metrics.iterations.rate" ? "request/s" : "milliseconds",
    y: am5.p50,
    centerX: am5.p50
  });
  const yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
    min: 0,
    renderer: am5xy.AxisRendererY.new(root, {
      strokeOpacity: 0.1
    })
  }));
  yAxis.children.unshift(yLabel);


  // Add series
  // https://www.amcharts.com/docs/v5/charts/xy-chart/series/
  function makeSeries(name, fieldName, fillColor) {
    const series = chart.series.push(am5xy.ColumnSeries.new(root, {
      name: name,
      xAxis: xAxis,
      yAxis: yAxis,
      valueYField: fieldName,
      categoryXField: "vus",
      fill: am5.color(fillColor)
    }));

    series.columns.template.setAll({
      tooltipText: "{name}, {categoryX} VUs: {valueY}",
      width: am5.percent(80),
      tooltipY: 0,
      strokeOpacity: 0
    });

    series.data.setAll(data);

    // Make stuff animate on load
    // https://www.amcharts.com/docs/v5/concepts/animations/
    series.appear();

    series.bullets.push(function() {
      return am5.Bullet.new(root, {
        locationY: 0,
        sprite: am5.Label.new(root, {
          text: "{valueY}",
          fill: root.interfaceColors.get("alternativeText"),
          centerY: 0,
          centerX: am5.p50,
          populateText: true
        })
      });
    });

    legend.data.push(series);
  }

  makeSeries("Blocking", "blocking", 0xAFB3F7);
  makeSeries("Coroutines", "nonBlockingCoroutines", 0x92BCEA);
  makeSeries("WebFlux", "nonBlockingWebFlux", 0x7A93AC);

  // Make stuff animate on load
  // https://www.amcharts.com/docs/v5/concepts/animations/
  chart.appear(1000, 100);
}
