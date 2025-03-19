function searchFriend(){
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchFriend");
    filter = input.value.toUpperCase();
    table = document.getElementById("friendList");
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

function showFriendInfo(thisObj){
    var allChildNodes = thisObj.childNodes;
    var userId = allChildNodes[1].textContent;
    var userName = allChildNodes[3].textContent;
    var friendInfo = document.getElementById("friendInfo");
    var textbox = friendInfo.childNodes[3];
    var infroRow = document.getElementById("infoRow");
    textbox.textContent = userName;

    infroRow.innerHTML = "<td>" + userId + "</td>" + "<td>" + userName + "</td>";
}