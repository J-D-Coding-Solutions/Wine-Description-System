function searchwine(){
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchwine");
    filter = input.value.toUpperCase();
    table = document.getElementById("wineList");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
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

function addwine(thisObj){
    var allChildNodes = thisObj.parentNode.parentNode.childNodes;
    var userId = allChildNodes[1].textContent;
    var userName = allChildNodes[3].textContent;
    var friendTable = document.getElementById("friendTable");
    var row = friendTable.insertRow(-1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);

    cell1.innerHTML = userId;
    cell2.innerHTML = userName;


    row.addEventListener('click', function() {
        showwineInfo(row, 0);
    });

    var parentRow =  thisObj.parentNode.parentNode;
    parentRow.remove();

}

function declinewine(thisObj){
    var parentRow = thisObj.parentNode.parentNode;
    var confrimDelete = confirm("Are you sure you want to decline this wine request?");
    if (confrimDelete){
        parentRow.remove();
    }
}