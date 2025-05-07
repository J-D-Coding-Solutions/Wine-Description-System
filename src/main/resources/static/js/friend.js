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

function showFriendInfo(thisObj, i){
    var allChildNodes = thisObj.childNodes;
    var userId = allChildNodes[0 + i].textContent;
    if(i == 0){
        i = -1;
    }
    var userName = allChildNodes[2 + i].textContent;
    var friendInfo = document.getElementById("friendInfo");
    var textbox = friendInfo.childNodes[3];
    var infroRow = document.getElementById("infoRow");
    var table = document.getElementById("friendInfoTable");
    textbox.textContent = userName;

    // infroRow.innerHTML = "<td>" + userId + "</td>" + "<td>" + userName + "</td>";

    fetch("/friendFav", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: new URLSearchParams({ userID: userId })
    })
        .then(response => {
            if (!response.ok) {
                console.log("Error fetching friend favorites");
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            data.forEach(item => {
                const row = document.createElement("tr");

                const keyCell = document.createElement("td");
                keyCell.textContent = item.wineName;
                console.log(item.wineName);

                const valueCell = document.createElement("td");
                valueCell.textContent = item.wineDesc;
                console.log(item.wineDesc);

                row.appendChild(keyCell);
                row.appendChild(valueCell);
                table.appendChild(row);
            })
        })
        .catch(error => {
            console.error("Error:", error);
        });

}
