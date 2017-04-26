function loadDoc(url) {
  var xhttp = new XMLHttpRequest();
  url = url + '/cgi-bin/egauge?tot&inst';
  xhttp.onreadystatechange = function() {
      window.alert(this.readyState + " " + this.status);
    if (this.readyState == 4 && this.status == 200) {
      window.alert("done");
      parseXML(this);
    }
  };
  xhttp.open("GET", 'url', true);
  xhttp.send();
}

function loadCSV(url) {
  var xhttp = new XMLHttpRequest();
  url = url + '/cgi-bin/egauge-show?c&a&E';
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      myFunction(this);
    }
  };
  xhttp.open("GET", url, true);
  xhttp.send();
  return xhttp.responseXML;
}

function parseXML(xmlDoc) {
  var i;
  var table="<tr><th>Register</th><th>Watts</th></tr>";
  var x = xmlDoc.getElementsByTagName("r");
  for (i = 0; i <x.length; i++) {
    //if this row should be highlighted
    table += "<tr><td>" +
    x[i].getElementsByTagName("n")[0].childNodes[0].nodeValue +
    "</td><td>" +// use <td class='highlight'> instead
    x[i].getElementsByTagName("i")[0].childNodes[0].nodeValue +
    "</td></tr>";

    var watts = x[i].getElementsByTagName("i")[0].childNodes[0].nodeValue;
    if(watts < -100 || watts > 100 ){
      //utility is currently running, report to table
    }

  }
  document.getElementById("demo").innerHTML = table;
}
