Morris.Line({
  element: 'line-example',
  data: [
    { y: "2012-02-24 15:00", a: 40},
    { y: "2012-02-25 15:00", a: 45},
    { y: "2012-02-26 15:00", a: 50},
    { y: "2012-02-27 15:00", a: 51},
    { y: "2012-02-28 15:00", a: 27},
    { y: "2012-02-29 15:00", a: 95}
  ],
  xkey: 'y',
  ykeys: ['a'],
  labels: ['Useage'],
  smooth: true,
  postUnits: '%',
  axes: true,
  goals: [0, 100],
  ymin: 0,
  ymax: 100,
  xLabels: 'day'
});

Morris.Line({
  element: 'line-example2',
  data: [
    { y: "2012-02-24 15:00", a: 40},
    { y: "2012-02-25 15:00", a: 45},
    { y: "2012-02-26 15:00", a: 50},
    { y: "2012-02-27 15:00", a: 51},
    { y: "2012-02-28 15:00", a: 27},
    { y: "2012-02-29 15:00", a: 95}
  ],
  xkey: 'y',
  ykeys: ['a'],
  labels: ['Useage'],
  smooth: true,
  postUnits: '%',
  axes: true,
  goals: [0, 100],
  ymin: 0,
  ymax: 100,
  xLabels: 'day'
})

