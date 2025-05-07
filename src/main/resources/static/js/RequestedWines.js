function searchwine(){
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchwine");
    filter = input.value.toUpperCase();
    table = document.getElementById("wineTable");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function showwineInfo(thisObj, i){
    var allChildNodes = thisObj.childNodes;
    var userId = allChildNodes[0 + i].textContent;
    if(i == 0){
        i = -1;
    }
    console.log(allChildNodes[2 + i]);
    var userName = allChildNodes[2 + i].textContent;
    var wineInfo = document.getElementById("wineInfo");
    var textbox = wineInfo.childNodes[3];
    var infroRow = document.getElementById("infoRow");
    textbox.textContent = userId;

    infroRow.innerHTML = "<td>" + userId + "</td>" + "<td>" + userName + "</td>";
}
