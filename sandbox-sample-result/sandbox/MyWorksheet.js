/**
  MyWorksheet
  Generated from a ECMAScript model by JetBrains MPS.
*/

function calculate(document) {
  var value_yjq5oh_a_0 = document.getElementById("Java").value;
  var value_yjq5oh_b_0 = document.getElementById("PHP").value;
  var value_yjq5oh_c_0 = document.getElementById("Design").value;
  var value_yjq5oh_d_0 = document.getElementById("Administration").value;
  {
    var output = document.getElementById("Charge to customer");
    output.value = value_yjq5oh_a_0 * 20 + value_yjq5oh_b_0 * 10 + value_yjq5oh_c_0 * 50;
  }
  {
    var output = document.getElementById("Tax");
    output.value = value_yjq5oh_a_0 * 0.1 + value_yjq5oh_b_0 * 0.5;
  }
  {
    var output = document.getElementById("Overhead cost");
    output.value = value_yjq5oh_d_0 * 5;
  }
  {
    output = 1;
  }
}
